package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import co.revely.gradient.RevelyGradient
import com.afollestad.materialdialogs.MaterialDialog
import com.androidnetworking.AndroidNetworking
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.activity_logreg.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import com.androidnetworking.error.ANError
import org.json.JSONArray
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import io.github.uditkarode.ididit.models.CredentialFactory
import org.json.JSONObject


class LogReg : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logreg)
        AndroidNetworking.initialize(applicationContext)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1), Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)))
            .on(header)

        login.setOnClickListener {
            val cLogin = CredentialFactory(user.text.toString(), pass.text.toString()).makeCredentials()
            if(cLogin.isErrorFree()){
                initializeLoading()
                AndroidNetworking.post(Constants.BASE_URL + Constants.LOGIN_ENDPOINT)
                    .addHeaders("Content-Type", "application/json")
                    .addJSONObjectBody(JSONObject().put("username", cLogin.username).put("password", cLogin.password))
                    .build()
                    .getAsJSONObject(object : JSONObjectRequestListener {
                        override fun onResponse(response: JSONObject) {
                            Log.e("GOTRESPONSE", response.toString())
                        }

                        override fun onError(error: ANError) {
                            if(JSONObject(error.errorBody.toString()).getString("message") == "User is not registered"){
                                MaterialDialog(this@LogReg).show {
                                    title(text = "Login Failed")
                                    message(text = "Incorrect username/password!")
                                }
                            }
                        }
                    }
                    )
            } else {
                Log.e("GOTERROR", cLogin.errors + " ")

            }
        }
    }

    private fun initializeLoading(){
        logregbutts.animate().setDuration(200).alpha(0f).start()
        Handler().postDelayed({
            logregbutts.visibility = View.GONE
            loader_logreg.visibility = View.VISIBLE
            loader_logreg.alpha = 0f
            loader_logreg.animate().alpha(1f).setDuration(200).start()
        }, 200)
    }

    private fun stopLoading(){
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
