package com.kingseducation.app.activities.calender.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.calender.model.CalendarOutlookResponseModel
import com.mobatia.calendardemopro.adapter.CalendarDetailAdapter
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date


class CalendarOutlookAdapter(
    private var mContext: Context,
    private var calendarArrayList: Map<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>>,
    private var calendarRecycler: RecyclerView


) :
    RecyclerView.Adapter<CalendarOutlookAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dateTxt: TextView = view.findViewById(R.id.dateTxt)
        var detailRecycler: RecyclerView = view.findViewById(R.id.detailRecycler)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_date_recycler, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = calendarArrayList.keys.toList()[position]

        holder.dateTxt.text = summary.toString()
        linearLayoutManager = LinearLayoutManager(mContext)
        holder.detailRecycler.layoutManager = linearLayoutManager
        holder.detailRecycler.itemAnimator = DefaultItemAnimator()
//        Log.e("sizeee", summary.detailList.size.toString())
        val currentDateEvents = calendarArrayList[summary]
        if (currentDateEvents != null && currentDateEvents.size > 0) {

            val detailArray = currentDateEvents
            val calendarListAdapter = CalendarOutlookDetailAdapter(mContext, detailArray)
            holder.detailRecycler.adapter = calendarListAdapter
        }

    }


    override fun getItemCount(): Int {
        Log.e("insideadaptersize", calendarArrayList.size.toString())
        return calendarArrayList.size


    }

    @SuppressLint("SimpleDateFormat")
    private fun getDates(
        dateString1: String,
        dateString2: String
    ): ArrayList<String> {
        val dates = ArrayList<String>()
        val df1: DateFormat = SimpleDateFormat("MMM dd,yyyy")
        var date1: Date? = null
        var date2: Date? = null
        try {
            date1 = df1.parse(dateString1)
            date2 = df1.parse(dateString2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        while (!cal1.after(cal2)) {
            dates.add(cal1.time.toString())
            cal1.add(Calendar.DAY_OF_MONTH, 1)
        }
        return dates
    }
}