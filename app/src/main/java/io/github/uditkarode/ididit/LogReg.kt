package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.core.content.edit
import co.revely.gradient.RevelyGradient
import com.afollestad.materialdialogs.MaterialDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.OkHttpResponseAndJSONObjectRequestListener
import es.dmoral.toasty.Toasty
import io.github.uditkarode.ididit.models.CredentialFactory
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.activity_logreg.*
import okhttp3.Response
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.security.MessageDigest
import kotlin.experimental.and

class LogReg : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(getSharedPreferences("account", 0).getBoolean("shouldAutoLogin", false)){
            startActivity(Intent(this@LogReg, Home::class.java))
            finish()
        }

        setContentView(R.layout.activity_logreg)
        AndroidNetworking.initialize(applicationContext)

        RevelyGradient.linear()
            .colors(
                intArrayOf(
                    Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1),
                    Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)
                )
            )
            .on(header)

        login.setOnClickListener {
            val cLogin = CredentialFactory(user.text.toString(), pass.text.toString()).makeCredentials()
            if (cLogin.isErrorFree()) {
                initializeLoading()
                AndroidNetworking.post(Constants.BASE_URL + Constants.LOGIN_ENDPOINT)
                    .addBodyParameter("user", cLogin.username)
                    .addBodyParameter("password", sha512(cLogin.password))
                    .build()
                    .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                        override fun onResponse(okhttpResponse: Response, response: JSONObject) {
                            Toasty.success(this@LogReg, "Success!", Toast.LENGTH_SHORT, true).show()
                            getSharedPreferences("account", 0).edit {
                                putString("token", okhttpResponse.header("X-Auth-Token"))
                                putString("username", cLogin.username)
                                putString("joined_on", response.getString("joined_on"))
                                putBoolean("shouldAutoLogin", true)
                            }
                            loader_logreg.animate().alpha(0f).setDuration(100).start()
                            startActivity(Intent(this@LogReg, Home::class.java)) ; finish()
                        }

                        override fun onError(error: ANError) {
                            if(error.errorBody == null){
                                MaterialDialog(this@LogReg).show {
                                    title(text = "Login Failed")
                                    message(text = "Either you do not have a stable internet connection or our servers are down." +
                                            "If it is the latter, we are working on it and will soon resolve the issue.")
                                    positiveButton(text="Okay")
                                    positiveButton {
                                        stopLoading()
                                    }
                                }
                            } else {
                                MaterialDialog(this@LogReg).show {
                                    title(text = "Login Failed")
                                    message(text = JSONObject(error.errorBody.toString()).getString("status"))
                                    positiveButton(text="Okay")
                                    positiveButton {
                                        stopLoading()
                                    }
                                }
                            }

                        }
                    }
                    )
            } else {
                MaterialDialog(this@LogReg).show {
                    title(text = "Login Failed")
                    message(text = cLogin.errors)
                    positiveButton(text="Okay")
                }
            }
        }

        register.setOnClickListener {
            val cLogin = CredentialFactory(user.text.toString(), pass.text.toString()).makeCredentials()
            if (cLogin.isErrorFree()) {
                initializeLoading()
                AndroidNetworking.post(Constants.BASE_URL + Constants.SIGN_UP_ENDPOINT)
                    .addBodyParameter("user", cLogin.username)
                    .addBodyParameter("password", sha512(cLogin.password))
                    .build()
                    .getAsOkHttpResponseAndJSONObject(object : OkHttpResponseAndJSONObjectRequestListener {
                        override fun onResponse(okhttpResponse: Response, response: JSONObject) {
                            Toasty.success(this@LogReg, "Registration successful!", Toast.LENGTH_SHORT, true).show()
                            login.performClick()
                        }

                        override fun onError(error: ANError) {
                            if(error.errorBody == null){
                                MaterialDialog(this@LogReg).show {
                                    title(text = "Login Failed")
                                    message(text = "Either you do not have a stable internet connection or our servers are down." +
                                            "If it is the latter, we are working on it and will soon resolve the issue.")
                                    positiveButton(text="Okay")
                                    positiveButton {
                                        stopLoading()
                                    }
                                }
                            } else {
                                MaterialDialog(this@LogReg).show {
                                    title(text = "Register Failed")
                                    message(text = JSONObject(error.errorBody.toString()).getString("status"))
                                    positiveButton(text="Okay")
                                    positiveButton {
                                        stopLoading()
                                    }
                                }
                            }
                        }
                    }
                    )
            } else {
                MaterialDialog(this@LogReg).show {
                    title(text = "Login Failed")
                    message(text = cLogin.errors)
                    positiveButton(text="Okay")
                }
            }
        }
    }

    private fun sha512(str: String): String{
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest(str.toByteArray())
        val sb = StringBuilder()
        for (i in digest.indices) {
            sb.append(Integer.toString((digest[i] and 0xff.toByte()) + 0x100, 16).substring(1))
        }
        return sb.toString()
    }

    private fun initializeLoading() {
        logregbutts.animate().setDuration(200).alpha(0f).start()
        Handler().postDelayed({
            logregbutts.visibility = View.GONE
            loader_logreg.visibility = View.VISIBLE
            loader_logreg.alpha = 0f
            loader_logreg.animate().alpha(1f).setDuration(200).start()
        }, 200)
    }

    private fun stopLoading() {
        loader_logreg.animate().setDuration(200).alpha(0f).start()
        Handler().postDelayed({
            loader_logreg.visibility = View.GONE
            logregbutts.visibility = View.VISIBLE
            logregbutts.alpha = 0f
            logregbutts.animate().alpha(1f).setDuration(200).start()
        }, 200)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
