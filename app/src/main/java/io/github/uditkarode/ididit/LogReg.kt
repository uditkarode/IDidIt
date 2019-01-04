package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import co.revely.gradient.RevelyGradient
import com.androidnetworking.AndroidNetworking
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.activity_logreg.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class LogReg : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logreg)
        AndroidNetworking.initialize(applicationContext)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1), Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)))
            .on(header)

        login.setOnClickListener {
            initializeLoading()
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
