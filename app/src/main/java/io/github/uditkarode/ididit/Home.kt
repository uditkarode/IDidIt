package io.github.uditkarode.ididit

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.revely.gradient.RevelyGradient
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.uditkarode.ididit.adapters.ExpandableAdapter
import io.github.uditkarode.ididit.models.Habit
import io.github.uditkarode.ididit.utils.BABDrawer
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatus
import kotlinx.android.synthetic.main.activity_home.bottombar
import kotlinx.android.synthetic.main.activity_home.homeLoader
import kotlinx.android.synthetic.main.activity_home.homeheader
import kotlinx.android.synthetic.main.activity_home.rv_habits
import org.json.JSONArray
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class Home : AppCompatActivity() {

    private lateinit var token: String
    private lateinit var username: String
    private lateinit var joinDate: String
    private lateinit var fab: FloatingActionButton

    private lateinit var adapterArray: ArrayList<Habit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(bottombar)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HOME_GRADIENT_COLOR1), Color.parseColor(Constants.HOME_GRADIENT_COLOR2)))
            .on(homeheader)

        AndroidNetworking.initialize(applicationContext)

        fab = findViewById(R.id.fab)

        val sp = getSharedPreferences("account", 0)
        token = sp.getString("token", "ERROR")!!
        username = sp.getString("username", "ERROR")!!
        joinDate = sp.getString("joinDate", "4/2/1337")!!

        adapterArray = ArrayList()

        rv_habits.adapter = ExpandableAdapter(adapterArray)
        rv_habits.layoutManager = LinearLayoutManager(this@Home)

        refreshRvData()
    }

    private fun refreshRvData(){
        dataIsLoading()

        AndroidNetworking.get(Constants.BASE_URL + Constants.GET_RESOLUTIONS_ENDPOINT)
            .addHeaders("Authorization", token)
            .build()
            .getAsJSONArray(object: JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    for(i in 0 until response?.length()!!) {
                        val tmpObj = response.getJSONObject(i)
                        adapterArray.add(Habit(tmpObj.getString("resolution"), HabitStatus.NOT_MARKED, tmpObj.getString("_id")))
                    }

                    datahasLoaded(isEmpty = false)
                }

                override fun onError(anError: ANError?) {
                    MaterialDialog(this@Home).show {
                        title(text = "Data Retrieval Failed")
                        message(text = anError?.errorDetail)
                        positiveButton(text="Okay")
                        positiveButton {
                            datahasLoaded(isEmpty = true)
                        }
                    }
                }
            })

        fab.setOnClickListener {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val oldY = fab.y
            fab.animate().x(fab.x).y(metrics.heightPixels/2.3f).setInterpolator(AccelerateDecelerateInterpolator()).setDuration(500).start()

            Handler().postDelayed({
                fab.visibility = View.GONE
                MaterialDialog(this).show {
                    cancelable(false)

                    input { _, text ->
                        addResolution(text.toString())
                        fab.visibility = View.VISIBLE
                        fab.animate().x(fab.x).y(oldY).setInterpolator(AccelerateDecelerateInterpolator()).setDuration(400).start()
                    }

                    negativeButton {
                        fab.visibility = View.VISIBLE
                        fab.animate().x(fab.x).y(oldY).setInterpolator(AccelerateDecelerateInterpolator()).setDuration(400).start()
                    }

                    positiveButton(text = "Add")
                    negativeButton(text = "Cancel")
                }
            }, 450)
        }
    }

    private fun addResolution(title: String){
        dataIsLoading()

        AndroidNetworking.post(Constants.BASE_URL + Constants.ADD_RESOLUTION_ENDPOINT)
            .addHeaders("Authorization", token)
            .addJSONObjectBody(JSONObject().put("title", title))
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("GOTYA", response?.toString())
                    datahasLoaded(isEmpty = false)
                }

                override fun onError(anError: ANError?) {
                    Log.e("NOTGOTYA", anError?.errorDetail)
                }
            })
    }

    private fun dataIsLoading(){
        rv_habits.animate().alpha(0f).setDuration(150).start()
        Handler().postDelayed({
            rv_habits.visibility = View.GONE
            homeLoader.visibility = View.VISIBLE
            homeLoader.alpha = 0f
            homeLoader.animate().alpha(1f).setDuration(150).start()
        }, 150)
    }

    private fun datahasLoaded(isEmpty: Boolean){
        rv_habits.adapter?.notifyDataSetChanged()

        if(!isEmpty){
            homeLoader.animate().alpha(0f).setDuration(150).start()
            Handler().postDelayed({
                homeLoader.visibility = View.GONE
                rv_habits.visibility = View.VISIBLE
                rv_habits.alpha = 0f
                rv_habits.animate().alpha(1f).setDuration(150).start()
            }, 150)
        }
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
                val tbpBundle = Bundle()
                tbpBundle.putString("userName", username)
                tbpBundle.putString("joinDate", joinDate)
                bottomNavDrawerFragment.arguments = tbpBundle
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }
        }
        return true
    }
}