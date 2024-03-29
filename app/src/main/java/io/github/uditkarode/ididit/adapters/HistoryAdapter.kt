package io.github.uditkarode.ididit.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.models.Habit
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatistics

class HistoryAdapter(private val date: ArrayList<Int>,
                     private val day: ArrayList<String>,  private val month: ArrayList<String>,
                     private val stats: ArrayList<HabitStatistics>, private val dhl: ArrayList<ArrayList<Habit>>):
    RecyclerView.Adapter<HistoryVH>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HistoryVH(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))

    override fun getItemCount() = date.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryVH, position: Int) {

        holder.itemRoot.setOnClickListener {
            val detailMd = MaterialDialog(holder.itemRoot.context)
                .customView(R.layout.historydetail_dialog)

            val detailView = detailMd.getCustomView()

            detailView.findViewById<TextView>(R.id.diadate).text =
                date[itemCount-position-1].toString() + ' ' + month[itemCount-position-1]
            val dvRv = detailView.findViewById<RecyclerView>(R.id.dia_habit_holder)
            dvRv.adapter = DetailAdapter(dhl[itemCount-position-1])
            dvRv.layoutManager = LinearLayoutManager(holder.itemRoot.context)

            detailMd.show()
        }

        holder.tvDate.text = date[itemCount-position-1].toString()
        holder.tvDay.text = day[itemCount-position-1]
        holder.tvMonth.text = month[itemCount-position-1]
        holder.tvCompleted.text = "${stats[itemCount-position-1].completed} completed"
        holder.tvFailed.text = "${stats[itemCount-position-1].failed}+${stats[itemCount-position-1].notMarked} failed"

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HOME_GRADIENT_COLOR1), Color.parseColor(Constants.HOME_GRADIENT_COLOR2)))
            .on(holder.tvDate)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR1), Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR2)))
            .on(holder.tvCompleted)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR1), Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR2)))
            .on(holder.tvFailed)

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1), Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2)))
            .on(holder.tvMonth)
    }

}

class HistoryVH(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tvDate: TextView = itemView.findViewById(R.id.tvDate)
    val tvDay: TextView = itemView.findViewById(R.id.tvDay)
    val tvCompleted: TextView = itemView.findViewById(R.id.tvCompleted)
    val tvFailed: TextView = itemView.findViewById(R.id.tvFailed)
    val tvMonth: TextView = itemView.findViewById(R.id.tvMonth)
    val itemRoot: View = itemView.findViewById(R.id.history_item_root)
}