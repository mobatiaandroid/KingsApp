package com.kingseducation.app.activities.calender.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.calender.model.CalendarDetailModel
import com.kingseducation.app.activities.calender.model.CalendarOutlookResponseModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


class CalendarOutlookDetailAdapter(private var context: Context, private var calendarArrayList: ArrayList<CalendarOutlookResponseModel.CalendarEvent>) :
    RecyclerView.Adapter<CalendarOutlookDetailAdapter.MyViewHolder>() {

    lateinit var difference_In_Days: String
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var timeTxt: TextView = view.findViewById(R.id.timeTxt)
        var backReal: RelativeLayout = view.findViewById(R.id.backReal)
        var clickLinear: LinearLayout = view.findViewById(R.id.clickLinear)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_calender_list_new, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = calendarArrayList[position]


        holder.title.text = summary.subject
        if (summary.start.dateTime.length!=0)
        {
            if (summary.end.dateTime.length!=0)
            {
                if (summary.start.dateTime.length==20)
                {
                    val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")

                    val outputFormat: DateFormat = SimpleDateFormat("HH:mm")
                    val startdate: Date = inputFormat.parse(summary.start.dateTime)
                    var outputDateStrstart:String= outputFormat.format(startdate)
                    if (summary.end.dateTime.length==20)
                    {
                        val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                        val outputFormat: DateFormat = SimpleDateFormat("HH:mm")
                        val endDate: Date = inputFormat.parse(summary.end.dateTime)
                        var outputDateEND:String= outputFormat.format(endDate)
                        if(outputDateStrstart.equals("00:00")&&outputDateEND.equals("00:00"))
                        {
                            holder.timeTxt.text = "All day"
                        }
                        else
                        {
                            holder.timeTxt.text = outputDateStrstart+" - "+outputDateEND
                            Log.e("start",outputDateStrstart)

                        }

                    }
                    else if (summary.end.dateTime.length==11)
                    {
                        holder.timeTxt.text = outputDateStrstart
                    }
                }
                else
                {
                    holder.timeTxt.text = "All day"
                }



            }
            else
            {
                if (summary.start.dateTime.length==20)
                {
                    val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val outputFormat: DateFormat = SimpleDateFormat("HH:mm")
                    val startdate: Date = inputFormat.parse(summary.start.dateTime)
                    var outputDateStrstart:String= outputFormat.format(startdate)
                    holder.timeTxt.text = outputDateStrstart

                }
                else if (summary.start.dateTime.length==11)
                {
                    holder.timeTxt.text = "All day"

                }
                else
                {
                    holder.timeTxt.text = summary.start.dateTime
                }
            }

        }
        // holder.timeTxt.text = summary.DTSTART
        holder.backReal.setBackgroundColor((context.resources.getColor(R.color.kings_blue)))

        holder.clickLinear.setOnClickListener(View.OnClickListener {
            Log.e("suesss","jhgdjgewfjkg")
            dialogcalendar = Dialog(context)
            dialogcalendar.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogcalendar.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogcalendar.setCancelable(false)
            // dialog.window!!.setLayout(1200,1400)
            dialogcalendar.setContentView(R.layout.calendar_popup)
            val summary = dialogcalendar.findViewById(R.id.summary) as TextView
            val close_popup = dialogcalendar.findViewById(R.id.close_popup) as ImageView
            val description = dialogcalendar.findViewById(R.id.description) as TextView
            val startcalendar = dialogcalendar.findViewById(R.id.start_date) as TextView
            val endcalendar = dialogcalendar.findViewById(R.id.end_date) as TextView
            val start_text = dialogcalendar.findViewById(R.id.start_text) as TextView
            val end_text = dialogcalendar.findViewById(R.id.end_text) as TextView
            val save_calendar = dialogcalendar.findViewById(R.id.save_calendar) as TextView
            SummaryCalendar = calendarArrayList[position].subject
            DescriptionCalendar = calendarArrayList[position].subject
            StartCalendar = calendarArrayList[position].start.dateTime
            EndCalendar = calendarArrayList[position].end.dateTime
            summary.text = SummaryCalendar
            description.visibility = View.GONE
            description.text = DescriptionCalendar
            start_text.setTextColor(context.let {
                ContextCompat.getColor(
                    it,
                    R.color.kings_blue
                )
            })
            end_text.setTextColor(context.let {
                ContextCompat.getColor(
                    it,
                    R.color.kings_blue
                )
            })

            if (StartCalendar.length == 20) {

                if (calendarArrayList[position].start.dateTime.contains("12:00 am") && calendarArrayList[position].end.dateTime.contains(
                        "12:00 am"
                    )
                ) {
                    val substr: String = calendarArrayList[position].start.dateTime.substring(0, 11)
                    val substr1: String = calendarArrayList[position].end.dateTime.substring(0, 11)
                    startcalendar.text = substr
                    endcalendar.text = substr1

                } else {
                    startcalendar.text = calendarArrayList[position].start.dateTime
                    endcalendar.text = calendarArrayList[position].end.dateTime
                }

            }

            if (StartCalendar.length == 11)
            {
                val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")

                val startdate: Date = inputFormat.parse(calendarArrayList[position].start.dateTime)
                val enddate: Date = inputFormat.parse(calendarArrayList[position].start.dateTime)

                outputDateStrstart = outputFormat.format(startdate)
                outputDateStrend = outputFormat.format(enddate)

                startcalendar.text = calendarArrayList[position].start.dateTime
                endcalendar.text = calendarArrayList[position].end.dateTime
            }
            save_calendar.setOnClickListener {

                val calendar = Calendar.getInstance()
                calendar.timeZone = TimeZone.getDefault()

                if (StartCalendar.length == 20) {
                    try {
                        val startdatehelper =
                            SimpleDateFormat("MMM dd,yyyy hh:mm a").parse(calendarArrayList[position].start.dateTime)
                        val stopdatehelper =
                            SimpleDateFormat("MMM dd,yyyy hh:mm a").parse(calendarArrayList[position].end.dateTime)

                        startTime16format = startdatehelper.time
                        endTime16format = stopdatehelper.time



                    } catch (e: Exception) {
                    }

                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.type = "vnd.android.cursor.item/event"
                    intent.putExtra("beginTime", startTime16format)
                    intent.putExtra("allDay", false)
                    intent.putExtra("rule", "FREQ=YEARLY")
                    intent.putExtra("endTime", endTime16format)
                    intent.putExtra("title", SummaryCalendar)
                    context.startActivity(intent)
                    dialogcalendar.dismiss()
                }

                if (StartCalendar.length == 11) {
                    try {
                        val startdatehelper =
                            SimpleDateFormat("MMM dd,yyyy").parse(outputDateStrstart)
                        val stopdatehelper =
                            SimpleDateFormat("MMM dd,yyyy").parse(outputDateStrend)

                        startTime8format = startdatehelper.time
                        endTime8format = stopdatehelper.time
                    } catch (e: Exception) {
                    }

                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.type = "vnd.android.cursor.item/event"
                    intent.putExtra("beginTime", startTime8format)
                    intent.putExtra("allDay", false)
                    intent.putExtra("rule", "FREQ=YEARLY")
                    intent.putExtra("endTime", endTime8format)
                    intent.putExtra("title", SummaryCalendar)
                    context.startActivity(intent)
                    dialogcalendar.dismiss()
                }
                if (StartCalendar.length == 10) {
                    try {
                        val startdatehelper =
                            SimpleDateFormat("yyyy-MM-dd").parse(outputDateStrstart)
                        val stopdatehelper =
                            SimpleDateFormat("yyyy-MM-dd").parse(outputDateStrend)

                        startTime8format = startdatehelper.time
                        endTime8format = stopdatehelper.time
                    } catch (e: Exception) {
                    }

                    val intent = Intent(Intent.ACTION_EDIT)
                    intent.type = "vnd.android.cursor.item/event"
                    intent.putExtra("beginTime", startTime8format)
                    intent.putExtra("allDay", false)
                    intent.putExtra("rule", "FREQ=YEARLY")
                    intent.putExtra("endTime", endTime8format)
                    intent.putExtra("title", SummaryCalendar)
                    context.startActivity(intent)
                    dialogcalendar.dismiss()
                }

            }

            if (DescriptionCalendar.length < 5) {
                description.visibility = View.GONE

            }

            dialogcalendar.show()

            close_popup.setOnClickListener {
                dialogcalendar.dismiss()
            }
        })

    }
    override fun getItemCount(): Int {

        return calendarArrayList.size

    }
}