package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.R

class ResolutionAdapter(val alHabits: ArrayList<String>): RecyclerView.Adapter<ResolutionAdapter.ResolutionV>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ResolutionV(LayoutInflater.from(parent.context).inflate(R.layout.item_habit, parent, false))

    override fun getItemCount() = 50 //alHabits.size

    override fun onBindViewHolder(holder: ResolutionV, position: Int) {
        RevelyGradient.linear()
            .colors(intArrayOf(Color.parseColor("#f85032"), Color.parseColor("#ef473a")))
            .on(holder.tv)
    }


    inner class ResolutionV(@NonNull itemView: View): RecyclerView.ViewHolder(itemView){
        val tv: TextView

        init {
            tv = itemView.findViewById(R.id.dabtv)
        }
    }
}