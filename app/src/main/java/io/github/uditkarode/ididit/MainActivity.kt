package io.github.uditkarode.ididit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sample_text.text = stringFromJNIX()
    }

    external fun stringFromJNIX(): String
    companion object {
        init {
            System.loadLibrary("ididit")
        }
    }
}
