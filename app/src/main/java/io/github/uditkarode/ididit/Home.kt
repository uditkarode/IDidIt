package io.github.uditkarode.ididit

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.lang.ref.WeakReference

class Home : AppCompatActivity() {

    lateinit var token: String
    private lateinit var username: String
    private lateinit var joinDate: String
    private lateinit var fab: FloatingActionButton

    private lateinit var adapterArray: ArrayList<Habit>
    private lateinit var adapter: ExpandableAdapter

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
        joinDate = sp.getString("joined_on", "420/69/1337")!! //@TODO

        adapterArray = ArrayList()

        adapter = ExpandableAdapter(adapterArray, WeakReference(this@Home))
        rv_habits.adapter = adapter
        rv_habits.layoutManager = LinearLayoutManager(this@Home)

        refreshRvData()
    }

    fun refreshRvData(){
        dataIsLoading()

        AndroidNetworking.get(Constants.BASE_URL + Constants.GET_RESOLUTIONS_ENDPOINT)
            .addHeaders("Authorisation", token)
            .build()
            .getAsJSONArray(object: JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    if(adapterArray.isNotEmpty()) adapterArray.clear()

                    for(i in 0 until response?.length()!!) {
                        val tmpObj = response.getJSONObject(i)
                        adapterArray.add(Habit(tmpObj.getString("title"), stringStatus(tmpObj.getString("status"))))
                    }
                    if(adapterArray.isEmpty()) dataHasLoaded(isEmpty = true)
                    else dataHasLoaded(isEmpty = false)
                }

                override fun onError(anError: ANError?) {
                    MaterialDialog(this@Home).show {
                        title(text = "Data Retrieval Failed")
                        message(text = anError?.errorBody)
                        positiveButton(text="Okay")
                        positiveButton {
                            dataHasLoaded(isEmpty = true)
                        }
                    }
                }
            })

        fab.setOnClickListener {
            MaterialDialog(this@Home).show {
                input { _, text ->
                    addResolution(text.toString())
                }
                positiveButton(text = "Okay")
                negativeButton (text = "Cancel")
            }
        }
    }

    private fun addResolution(title: String){
        dataIsLoading()

        AndroidNetworking.post(Constants.BASE_URL + Constants.ADD_RESOLUTION_ENDPOINT)
            .addHeaders("Authorisation", token)
            .addBodyParameter("title", title)
            .build()
            .getAsJSONObject(object: JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("GOTYA", response?.toString())
                    refreshRvData()
                }

                override fun onError(anError: ANError?) {
                    Log.e("NOTGOTYA", anError?.errorDetail)
                }
            })
    }

    fun dataIsLoading(){
        rv_habits.animate().alpha(0f).setDuration(150).start()
        Handler().postDelayed({
            rv_habits.visibility = View.GONE
            homeLoader.visibility = View.VISIBLE
            homeLoader.alpha = 0f
            homeLoader.animate().alpha(1f).setDuration(150).start()
        }, 150)
    }

    fun dataHasLoaded(isEmpty: Boolean){
        if(!isEmpty){
            adapter.notifyDataSetChanged()

            homeLoader.animate().alpha(0f).setDuration(250).start()
            Handler().postDelayed({
                homeLoader.visibility = View.GONE
                no_habits.visibility = View.GONE
                rv_habits.visibility = View.VISIBLE
                rv_habits.alpha = 0f
                rv_habits.animate().alpha(1f).setDuration(150).start()
            }, 150)
        } else {
            homeLoader.visibility = View.INVISIBLE
            if(adapterArray.isEmpty()) no_habits.visibility = View.VISIBLE
        }
    }

    private fun stringStatus(str: String): HabitStatus {
        when(str){
            "C" -> return HabitStatus.COMPLETED
            "F" -> return HabitStatus.FAILED
            "N" -> return HabitStatus.NOT_MARKED
        } ; return HabitStatus.NOT_MARKED
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
                tbpBundle.putString("joinDate", "joined on: " + joinDate)
                bottomNavDrawerFragment.arguments = tbpBundle
                bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
            }

            R.id.aboutus -> {
                startActivity(Intent(this@Home, AboutUs::class.java))
            }
        }
        return true
    }
}