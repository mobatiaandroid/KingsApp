package com.example.kingsapp.activities.calender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.model.CalendarModel


class CustomLisAdapter(private val mContext: Context, eventArrayList: ArrayList<CalendarModel>) :
    RecyclerView.Adapter<CustomLisAdapter.MyViewHolder>() {

    private val eventArrayList: ArrayList<CalendarModel>

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var event: TextView
        var eventDate: TextView
        var relSub: LinearLayout

        init {
            event = view.findViewById(R.id.event)
            eventDate = view.findViewById(R.id.eventTxt)
            relSub = view.findViewById(R.id.relSub)
        }
    }

    init {
        this.eventArrayList = eventArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.calender_listview_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.event.setText(eventArrayList[position].enddate)

        holder.eventDate.setText(eventArrayList[position].tittle)
        // System.out.println("title::"+eventArrayList.get(position).getTittle());
    }

    override fun getItemCount(): Int {
        return eventArrayList.size
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}