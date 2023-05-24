package com.example.kingsapp.activities.timetable.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.timetable.model.MondayList
import com.example.kingsapp.activities.timetable.model.TimeTableApiListModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
internal class TimeTableSingleWeekSelectionAdapter (private var mContetx:Context, private var calendarArrayList: ArrayList<MondayList>) :
    RecyclerView.Adapter<TimeTableSingleWeekSelectionAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var llread: RelativeLayout = view.findViewById(R.id.llread)
        var llreadbreak: RelativeLayout = view.findViewById(R.id.llreadbreak)
        var starLinear: LinearLayout = view.findViewById(R.id.starLinear)
        var relSub: LinearLayout = view.findViewById(R.id.relSub)
        var card_view: CardView = view.findViewById(R.id.card_view)
        var breakTxt: TextView = view.findViewById(R.id.breakTxt)
        var subjectTxt: TextView = view.findViewById(R.id.subjectTxt)
        var tutorNameTxt: TextView = view.findViewById(R.id.tutorNameTxt)
        var subjectNameTxt: TextView = view.findViewById(R.id.subjectNameTxt)
        var timeAPTxt: TextView = view.findViewById(R.id.timeAPTxt)
        var timeTxt: TextView = view.findViewById(R.id.timeTxt)
        var breakTxtt: TextView = view.findViewById(R.id.breakTxtt)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_selection_time_table, parent, false)
        return MyViewHolder(itemView)
    }
    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = calendarArrayList[position]
        Log.e("calendarArrayList", calendarArrayList.toString())


if(calendarArrayList[position].is_break.equals(1))
{
    holder.breakTxtt.visibility = View.VISIBLE
    holder.tutorNameTxt.visibility = View.GONE
    holder.subjectTxt.visibility = View.GONE
    holder.subjectNameTxt.visibility = View.GONE
    holder.timeTxt.setTextColor(mContetx.getResources().getColor(R.color.white))
    holder.relSub.setBackgroundColor(mContetx.getResources().getColor(R.color.kings_blue))
    val time = calendarArrayList[position].start_time


    if(calendarArrayList[position].period_name.equals("Break"))
    {
        holder.breakTxtt.text = calendarArrayList[position].period_name
    }
    else
    {
        holder.breakTxtt.text = calendarArrayList[position].period_name
    }

    try {
        val sdf = SimpleDateFormat("HH:mm")
        val dateObj: Date = sdf.parse(time)
        println(dateObj)
        println(SimpleDateFormat("hh:mm a").format(dateObj))
        Log.e("breaktime",SimpleDateFormat("hh:mm a").format(dateObj))
        holder.timeTxt.text = SimpleDateFormat("hh:mm a").format(dateObj)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

}


        else
{


    holder.llread.visibility = View.VISIBLE
    holder.timeAPTxt.visibility = View.GONE
    holder.llreadbreak.visibility = View.GONE
    holder.starLinear.visibility = View.GONE
    holder.tutorNameTxt.text = calendarArrayList[position].staff
    holder.subjectTxt.text = calendarArrayList[position].period_name
    holder.subjectNameTxt.text = calendarArrayList[position].subject_name

    val time = calendarArrayList[position].start_time

    try {
        val sdf = SimpleDateFormat("HH:mm")
        val dateObj: Date = sdf.parse(time)
        println(dateObj)
        println(SimpleDateFormat("hh:mm a").format(dateObj))
        holder.timeTxt.text = SimpleDateFormat("hh:mm a").format(dateObj)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
}
    }
    override fun getItemCount(): Int {

        return calendarArrayList.size

    }
}