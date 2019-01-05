package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import com.google.android.material.button.MaterialButton
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatus

class HabitViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var expanded = true
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

class ExpandableAdapter(private val str: ArrayList<String>, private val stats: ArrayList<HabitStatus>):
    RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false))

    override fun getItemCount() = str.size

    override fun onBindViewHolder(holder: HabitViewHolder, pos: Int) {
        holder.tv.text = str[pos]

        when(stats[pos]){
            HabitStatus.COMPLETED -> {
                RevelyGradient.linear()
                    .colors(intArrayOf(Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR1),
                        Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR2)))
                    .on(holder.tv)
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                holder.bFailed.isEnabled = false
                holder.bCompleted.isEnabled = true
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
            }

            HabitStatus.FAILED -> {
                RevelyGradient.linear()
                    .colors(intArrayOf(Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR1),
                        Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR2)))
                    .on(holder.tv)
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                holder.bFailed.isEnabled = true
                holder.bCompleted.isEnabled = false
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
            }

            HabitStatus.NOT_MARKED -> {
                RevelyGradient.linear()
                    .colors(intArrayOf(Color.parseColor(Constants.HABIT_GRADIENT_COLOR1),
                        Color.parseColor(Constants.HABIT_GRADIENT_COLOR2)))
                    .on(holder.tv)
                holder.bFailed.isEnabled = true
                holder.bCompleted.isEnabled = true
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
            }
        }
    }
}
