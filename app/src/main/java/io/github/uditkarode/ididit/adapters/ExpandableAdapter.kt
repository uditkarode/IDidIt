package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.button.MaterialButton
import io.github.uditkarode.ididit.Home
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.models.Habit
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatus
import org.json.JSONObject
import java.lang.ref.WeakReference

class HabitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private var expanded = true
    val tv: TextView = itemView.findViewById(R.id.dabtv)
    val bCompleted: MaterialButton = itemView.findViewById(R.id.completed)
    val bFailed: MaterialButton = itemView.findViewById(R.id.failed)

    init { itemView.setOnClickListener(this) }

    override fun onClick(v: View?) {
        expanded = !expanded
        val vButtons = v?.findViewById<LinearLayout>(R.id.buttonsPanel)!!

        if(expanded){
            itemView.findViewById<ImageView>(R.id.arrow).animate().rotation(0f)
            vButtons.animate().alpha(0f).setDuration(200).start()
            vButtons.clearAnimation()
            vButtons.visibility = View.GONE
        }
        else {
            itemView.findViewById<ImageView>(R.id.arrow).animate().rotation(180f)
            vButtons.visibility = View.VISIBLE
            vButtons.animate().alpha(1f).setDuration(200).start()
        }
    }
}

class ExpandableAdapter(private val habitList: ArrayList<Habit>, val home: WeakReference<Home>):
    RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false))

    override fun getItemCount() = habitList.size

    override fun onBindViewHolder(holder: HabitViewHolder, pos: Int) {
        holder.tv.text = habitList[pos].habitName

        holder.bCompleted.setOnClickListener {
            home.get()?.dataIsLoading()

            AndroidNetworking.post(Constants.BASE_URL + Constants.SET_RESOLUTION_STATUS_ENDPOINT)
                .addHeaders("Authorisation", home.get()?.token)
                .addBodyParameter("title", habitList[pos].habitName)
                .addBodyParameter("new_status", "C")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        Log.e("MCGOT_SUCCESSRESP", response.toString())
                        home.get()?.refreshRvData()
                    }

                    override fun onError(anError: ANError?) {
                        Log.e("MCGOT_FAILURERESP", anError?.errorDetail.toString())
                        home.get()?.dataHasLoaded(isEmpty = true)
                    }
                })
        }

        holder.bFailed.setOnClickListener {
            holder.tv.text = habitList[pos].habitName
            home.get()?.dataIsLoading()

            AndroidNetworking.post(Constants.BASE_URL + Constants.SET_RESOLUTION_STATUS_ENDPOINT)
                .addHeaders("Authorisation", home.get()?.token)
                .addBodyParameter("title", habitList[pos].habitName)
                .addBodyParameter("new_status", "F")
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        home.get()?.refreshRvData()
                    }

                    override fun onError(anError: ANError?) {
                        home.get()?.dataHasLoaded(isEmpty = true)
                    }
                })
        }

            Log.e("HSHERE", habitList[pos].habitStatus.toString())

            when (habitList[pos].habitStatus) {
                HabitStatus.COMPLETED -> {
                    RevelyGradient.linear()
                        .colors(
                            intArrayOf(
                                Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR1),
                                Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR2)
                            )
                        )
                        .on(holder.tv)
                    holder.bFailed.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                    holder.bFailed.isEnabled = false
                    holder.bCompleted.isEnabled = true
                    holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
                }

                HabitStatus.FAILED -> {
                    RevelyGradient.linear()
                        .colors(
                            intArrayOf(
                                Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR1),
                                Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR2)
                            )
                        )
                        .on(holder.tv)
                    holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                    holder.bFailed.isEnabled = true
                    holder.bCompleted.isEnabled = false
                    holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
                }

                HabitStatus.NOT_MARKED -> {
                    RevelyGradient.linear()
                        .colors(
                            intArrayOf(
                                Color.parseColor(Constants.HABIT_GRADIENT_COLOR1),
                                Color.parseColor(Constants.HABIT_GRADIENT_COLOR2)
                            )
                        )
                        .on(holder.tv)
                    holder.bFailed.isEnabled = true
                    holder.bCompleted.isEnabled = true
                    holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
                    holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
                }
            }
        }
    }
