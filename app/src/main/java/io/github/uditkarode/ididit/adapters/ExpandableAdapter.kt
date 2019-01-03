package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import co.revely.gradient.RevelyGradient
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import io.github.uditkarode.ididit.R

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

class ButtonsViewHolder(itemView: View) : ChildViewHolder(itemView)

class ExpandableAdapter(habits: List<ExpandableGroup<*>>, val str: ArrayList<String>) :
    ExpandableRecyclerViewAdapter<HabitViewHolder, ButtonsViewHolder>(habits) {

    override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int) =
        HabitViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false))


    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int) =
        ButtonsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buttons, parent, false))


    override fun onBindChildViewHolder(holder: ButtonsViewHolder, flatPosition: Int, group: ExpandableGroup<*>, childIndex: Int) {
    }

    override fun onBindGroupViewHolder(holder: HabitViewHolder, flatPosition: Int, group: ExpandableGroup<*>) {
        holder.tv.text = str[flatPosition]

        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor("#ddd6f3"), Color.parseColor("#70e1f5")))
            .on(holder.tv)
    }
}
