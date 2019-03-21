package io.github.uditkarode.ididit.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatistics

class HistoryAdapter(private val date: ArrayList<Int>,
                     private val day: ArrayList<String>, private val stats: ArrayList<HabitStatistics>):
    RecyclerView.Adapter<HistoryVH>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryVH(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

    override fun getItemCount() = date.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        holder.tvDate.text = date[position].toString()
        holder.tvDay.text = day[position]
        holder.tvCompleted.text = "${stats[position].completed} completed"
        holder.tvFailed.text = "${stats[position].failed}+${stats[position].notMarked} failed"
        Log.e("POPOL", holder.tvDate.text.toString())

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HOME_GRADIENT_COLOR1), Color.parseColor(Constants.HOME_GRADIENT_COLOR2)))
            .on(holder.tvDate)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR1), Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR2)))
            .on(holder.tvCompleted)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR1), Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR2)))
            .on(holder.tvFailed)
    }

}

class HistoryVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    val tvDay: TextView = itemView.findViewById(R.id.tvDay)
    val tvCompleted: TextView = itemView.findViewById(R.id.tvCompleted)
    val tvFailed: TextView = itemView.findViewById(R.id.tvFailed)
}