package com.mobatia.calendardemopro.adapter

import android.annotation.SuppressLint
import android.app.Dialog
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
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.model.CalendarDateModel
import com.example.kingsapp.activities.calender.model.CalendarDetailModel

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

lateinit var linearLayoutManager: LinearLayoutManager
var startTime16format: Long = 0
var endTime16format: Long = 0
var startTime8format: Long = 0
var endTime8format: Long = 0
lateinit var outputDateStrstart: String
lateinit var outputDateStrend: String
lateinit var SummaryCalendar: String
lateinit var DescriptionCalendar: String

lateinit var StartCalendar: String
lateinit var EndCalendar: String
lateinit var dialogcalendar: Dialog
lateinit var difference_In_Days: String
var detailArray = ArrayList<CalendarDetailModel>()

class CalendarDateAdapter(
    private var mContext: Context,
    private var calendarArrayList: ArrayList<CalendarDateModel>,
    private var  calendarRecycler: RecyclerView


) :
    RecyclerView.Adapter<CalendarDateAdapter.MyViewHolder>() {
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
        val summary = calendarArrayList[position]



        if (summary.startDate.length != 0) {
Log.e("size", summary.startDate.length.toString())
            Log.e("dateeeee", summary.startDate)

            if (summary.startDate.length == 20) {

                val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                val outputFormat: DateFormat = SimpleDateFormat("EEEE dd MMMM")
                val startdate: Date = inputFormat.parse(summary.startDate)
                var outputDateStrstart: String = outputFormat.format(startdate)
                holder.dateTxt.text = outputDateStrstart


            }
            else if (summary.startDate.length == 11)
            {
                val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("EEEE dd MMMM")
                val startdate: Date? = inputFormat.parse(summary.startDate)
             //   val enddate: Date = inputFormat.parse(summary.endDate)
                var outputDateStrstart: String = outputFormat.format(startdate)

                holder.dateTxt.text = outputDateStrstart

            }
            else if (summary.startDate.length==10)
            {
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val inputFormatend: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("EEEE dd MMMM")
                val startdate: Date? = inputFormat.parse(summary.startDate)
                //val enddate: Date = inputFormatend.parse(summary.endDate)
                var outputDateStrstart: String = outputFormat.format(startdate)
              //  var outputDateStrend: String = outputFormat.format(enddate)
                var outputDateStrstartupdated: String = inputFormat.format(startdate)
             //   var outputDateStrendupdated: String = inputFormat.format(enddate)
                holder.dateTxt.text = outputDateStrstart
            }
            else {

                holder.dateTxt.text = summary.startDate


            }


        }

        linearLayoutManager = LinearLayoutManager(mContext)
        holder.detailRecycler.layoutManager = linearLayoutManager
        holder.detailRecycler.itemAnimator = DefaultItemAnimator()
        Log.e("sizeee", summary.detailList.size.toString())

        if (summary.detailList.size > 0) {

           /* detailArray = summary.detailList
            val calendarListAdapter = CalendarDetailAdapter(mContext,detailArray)
            holder.detailRecycler.adapter = calendarListAdapter*/
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
    ): ArrayList<String>? {
        val dates = java.util.ArrayList<String>()
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