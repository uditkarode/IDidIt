package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import co.revely.gradient.RevelyGradient
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatus
import io.github.uditkarode.ididit.utils.HabitStatus.*

class HabitViewHolder(itemView: View) : GroupViewHolder(itemView) {
    var expanded = true
    val tv: TextView = itemView.findViewById(R.id.dabtv)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        expanded = !expanded
        if(expanded) itemView.findViewById<ImageView>(R.id.arrow).animate().rotation(0f)
        else itemView.findViewById<ImageView>(R.id.arrow).animate().rotation(180f)
    }
}

class ButtonsViewHolder(itemView: View) : ChildViewHolder(itemView) {
    val bCompleted: Button = itemView.findViewById(R.id.completed)
    val bFailed: Button = itemView.findViewById(R.id.failed)
}

class ExpandableAdapter(habits: List<ExpandableGroup<*>>, private val str: ArrayList<String>, private val stats: ArrayList<HabitStatus>):
    ExpandableRecyclerViewAdapter<HabitViewHolder, ButtonsViewHolder>(habits) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false))


    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int) =
        ButtonsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_buttons, parent, false))


    override fun onBindChildViewHolder(holder: ButtonsViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
        when(stats[flatPosition]){
            COMPLETED -> {
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
            }

            FAILED -> {
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.COLOR_DISABLED))
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
            }

            NOT_MARKED -> {
                holder.bCompleted.setBackgroundColor(Color.parseColor(Constants.POSITIVE_BUTTON_COLOR))
                holder.bFailed.setBackgroundColor(Color.parseColor(Constants.NEGATIVE_BUTTON_COLOR))
            }
        }
    }

    override fun onBindGroupViewHolder(holder: HabitViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        holder.tv.text = str[flatPosition]

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor(Constants.HABIT_GRADIENT_COLOR1), Color.parseColor(Constants.HABIT_GRADIENT_COLOR2)))
            .on(holder.tv)
    }
}
