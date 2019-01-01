package io.github.uditkarode.ididit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.revely.gradient.RevelyGradient
import kotlinx.android.synthetic.main.activity_logreg.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class LogReg : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logreg)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor("#70e1f5"), Color.parseColor("#ffd194")))
            .on(header)

        login.setOnClickListener {
            startActivity(Intent(this@LogReg, Home::class.java)) //@todo: do actual login here
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
