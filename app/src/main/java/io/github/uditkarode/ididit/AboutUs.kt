package io.github.uditkarode.ididit

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.utils.Constants
import kotlinx.android.synthetic.main.about_us.*
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

        val color1 = Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1)
        val color2 = Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)

        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)
        valueAnimator.duration = 150
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()

        RevelyGradient.linear()
            .colors(intArrayOf(color1, color2, color1))
            .animate(valueAnimator) { _valueAnimator, _gradientDrawable ->
                _gradientDrawable.angle = _valueAnimator.animatedValue as Float
            }
            .on(udit)

        RevelyGradient.linear()
            .colors(intArrayOf(color1, color2, color1))
            .animate(valueAnimator) { _valueAnimator, _gradientDrawable ->
                _gradientDrawable.angle = _valueAnimator.animatedValue as Float
            }
            .on(karthik)

        RevelyGradient.linear()
            .colors(intArrayOf(color1, color2, color1))
            .animate(valueAnimator) { _valueAnimator, _gradientDrawable ->
                _gradientDrawable.angle = _valueAnimator.animatedValue as Float
            }
            .on(himanshu)

        valueAnimator.start()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}


