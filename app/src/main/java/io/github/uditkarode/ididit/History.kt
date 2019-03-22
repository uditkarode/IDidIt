package io.github.uditkarode.ididit

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import io.github.uditkarode.ididit.adapters.HistoryAdapter
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatistics
import kotlinx.android.synthetic.main.activity_history.*
import org.json.JSONArray
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class History: Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val dateArray = java.util.ArrayList<Int>()
        val dayArray = java.util.ArrayList<String>()
        val monthArray = java.util.ArrayList<String>()
        val hsArray = ArrayList<HabitStatistics>()

        historyDataIsLoading()

        AndroidNetworking.get(Constants.BASE_URL + Constants.GET_HISTORY_ENDPOINT)
            .addHeaders("Authorisation", getSharedPreferences("account", 0).getString("token", "ERROR"))
            .build()
            .getAsJSONArray(object: JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {
                    if(hsArray.isNotEmpty()) hsArray.clear()
                    for(i in 0 until response?.length()!!) {
                        val tmpObj = response.getJSONObject(i)
                        dateArray.add(tmpObj.getString("date").toInt())
                        dayArray.add(tmpObj.getString("day"))
                        monthArray.add(tmpObj.getString("month"))
                        hsArray.add(HabitStatistics(tmpObj.getString("completed").toInt(),
                            tmpObj.getString("failed").toInt(), tmpObj.getString("not_marked").toInt()))
                    }
                    historyDataHasLoaded()
                }

                override fun onError(anError: ANError?) {
                    if(anError?.errorBody == null){
                        MaterialDialog(this@History).show {
                            title(text = "Login Failed")
                            message(text = "Either you do not have a stable internet connection or our servers are down." +
                                    "If it is the latter, we are working on it and will soon resolve the issue.")
                            positiveButton(text="Okay")
                            positiveButton {
                                finish()
                            }
                        }
                    } else {
                        MaterialDialog(this@History).show {
                            title(text = "Data retrieval failed!")
                            message(text = JSONObject(anError.errorBody).getString("status"))
                            positiveButton(text="Okay")
                            positiveButton {
                                finish()
                            }
                        }
                    }

                }
            })

        history_rv.adapter = HistoryAdapter(dateArray, dayArray, monthArray, hsArray)
        history_rv.layoutManager = LinearLayoutManager(this@History)
    }

    private fun historyDataIsLoading(){
        Handler().postDelayed({
            history_loader.visibility = View.VISIBLE
            history_loader.alpha = 0f
            history_loader.animate().alpha(1f).setDuration(150).start()
        }, 150)
    }

    private fun historyDataHasLoaded(){
        history_loader.animate().alpha(0f).setDuration(250).start()
        Handler().postDelayed({
            history_loader.visibility = View.GONE
            history_rv.alpha = 0f
            history_rv.animate().alpha(1f).setDuration(150).start()
        }, 150)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}