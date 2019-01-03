package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.activity_logreg.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class LogReg : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logreg)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1), Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)))
            .on(header)

        login.setOnClickListener {
            startActivity(Intent(this@LogReg, Home::class.java)) //@todo: do actual login here
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
