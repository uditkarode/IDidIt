package io.github.uditkarode.ididit

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.revely.gradient.RevelyGradient
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class LogReg : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder().setFontAttrId(R.attr.fontPath).build())
        setContentView(R.layout.activity_main)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor("#70e1f5"), Color.parseColor("#ffd194")))
            .on(header)

        register.setOnClickListener {

        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
