package io.github.uditkarode.ididit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.adapters.ResolutionAdapter
import io.github.uditkarode.ididit.utils.BABDrawer
import kotlinx.android.synthetic.main.activity_home.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(bottombar)


        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor("#ddd6f3"), Color.parseColor("#faaca8")))
            .on(homeheader)

        rv_habits.adapter = ResolutionAdapter(ArrayList())
        rv_habits.layoutManager = LinearLayoutManager(this@Home)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                val bottomNavDrawerFragment = BABDrawer()
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }
}