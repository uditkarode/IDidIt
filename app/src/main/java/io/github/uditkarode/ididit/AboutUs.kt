package io.github.uditkarode.ididit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.about_us.about_header
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AboutUs: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_us)

        RevelyGradient.linear()
            .colors(
                intArrayOf(
                    Color.parseColor(Constants.HOME_GRADIENT_COLOR1),
                    Color.parseColor(Constants.HOME_GRADIENT_COLOR2)
                )
            )
            .on(about_header)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}


