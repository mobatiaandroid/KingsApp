package com.kingseducation.app.activities.timetable.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.timetable.model.WeekModel

class TimeTableWeekListAdapter(
    private val mContext: Context,
    mWeekList: ArrayList<WeekModel>,
    weekPosition: Int
) :
    RecyclerView.Adapter<TimeTableWeekListAdapter.MyViewHolder>() {
    private val mWeekList: ArrayList<WeekModel>
    var weekPosition = 0
    lateinit var dayOfTheWeek: String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var weekTxt: TextView


        init {
            weekTxt = view.findViewById<View>(R.id.weekTxt) as TextView

        }
    }

    init {
        this.mWeekList = mWeekList
        this.weekPosition = weekPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_weeklist_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.weekTxt.text = mWeekList[position].weekName
        Log.e("Size", mWeekList[position].positionSelected.toString())
        if (mWeekList[position].positionSelected !== -1){
            holder.weekTxt.setTextColor(mContext.resources.getColor(R.color.kings_blue))
           // holder.lineImage.visibility = View.VISIBLE
           // holder.downArrowImage.visibility = View.VISIBLE
        } else {
            holder.weekTxt.setTextColor(mContext.resources.getColor(R.color.dark_grey1))

        }

    }

    override fun getItemCount(): Int {
        return mWeekList.size
    }
}