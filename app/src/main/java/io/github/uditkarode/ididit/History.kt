package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.uditkarode.ididit.adapters.HistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class History: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val h = ArrayList<String>()
        h.add("Heya")
        val k = ArrayList<Int>()
        k.add(2)

        rvRoot.adapter = HistoryAdapter(k, h)
        rvRoot.layoutManager = LinearLayoutManager(this@History)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}