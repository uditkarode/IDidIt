package io.github.uditkarode.ididit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.revely.gradient.RevelyGradient
import com.androidnetworking.AndroidNetworking
import io.github.uditkarode.ididit.adapters.ExpandableAdapter
import io.github.uditkarode.ididit.utils.*
import kotlinx.android.synthetic.main.activity_home.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.*
import kotlin.collections.ArrayList

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(bottombar)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HOME_GRADIENT_COLOR1), Color.parseColor(Constants.HOME_GRADIENT_COLOR2)))
            .on(homeheader)

        AndroidNetworking.initialize(applicationContext)

        // @todo: remove after UI testing //
        val habits = ArrayList<String>()

        habits.add("Dab everyday")
        habits.add("Press F everyday")
        habits.add("Fail builds everyday")
        habits.add("using namespace std; everyday")
        habits.add("Pray for Harambe everyday")
        habits.add("Go commit die everyday")
        habits.add("using namespace std; everyday")
        habits.add("Pray for Harambe everyday")

        val stats = ArrayList(Arrays.asList(HabitStatus.NOT_MARKED, HabitStatus.COMPLETED, HabitStatus.FAILED, HabitStatus.NOT_MARKED, HabitStatus.NOT_MARKED, HabitStatus.NOT_MARKED, HabitStatus.NOT_MARKED, HabitStatus.NOT_MARKED))

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

        rv_habits.adapter = ExpandableAdapter(habits, stats)
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