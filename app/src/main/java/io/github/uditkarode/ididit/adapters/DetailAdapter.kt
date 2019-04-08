package io.github.uditkarode.ididit.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.revely.gradient.RevelyGradient
import io.github.uditkarode.ididit.R
import io.github.uditkarode.ididit.models.Habit
import io.github.uditkarode.ididit.utils.Constants
import io.github.uditkarode.ididit.utils.HabitStatus

class DetailAdapter(val arr: ArrayList<Habit>): RecyclerView.Adapter<DetailAdapter.DetailViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder =
        DetailViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.historydetail_element, parent, false))

    override fun getItemCount() = arr.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.rootTv.text = arr[position].habitName
        val colorSet = when(arr[position].habitStatus){
            HabitStatus.COMPLETED ->
                intArrayOf(Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR1),
                    Color.parseColor(Constants.HABIT_COMPLETED_GRADIENT_COLOR2))
            HabitStatus.FAILED ->
                intArrayOf(Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR1),
                    Color.parseColor(Constants.HABIT_FAILED_GRADIENT_COLOR2))
            HabitStatus.NOT_MARKED ->
                intArrayOf(Color.parseColor(Constants.LOGIN_GRADIENT_COLOR1),
                    Color.parseColor(Constants.LOGIN_GRADIENT_COLOR2))
        }

        RevelyGradient.linear().colors(colorSet).on(holder.rootTv)
    }

    inner class DetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val rootTv: TextView = itemView.findViewById(R.id.hdroot_ele)
    }
}