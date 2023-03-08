package com.example.kingsapp.calender

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

class CalenderActivity:AppCompatActivity() {
    lateinit var context: Context
    lateinit var header: TextView
    lateinit var arrow_prev: ImageView
    lateinit var arrow_nxt: ImageView
    lateinit var monthlist:Array<String>
    lateinit var week_day:Array<String>
    lateinit var nums_Array:ArrayList<String>
    var month_total_days:Int?=null
    var count_month:Int?=null
    var count_year:Int?=null
    val dateTextView = arrayOfNulls<TextView>(42)
    lateinit var datesToPlot:ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parentmeeting_calendar)

       initFn()
        headerfnc()
        daysinweek()
        onclick()
        holiday()
    }

    private fun initFn() {
        context=this
        nums_Array= ArrayList()
        header=findViewById(R.id.Header)
        arrow_prev=findViewById(R.id.arrow_back)
        arrow_nxt=findViewById(R.id.arrow_nxt)
        monthlist=getResources().getStringArray(R.array.Months)
        week_day=getResources().getStringArray(R.array.Weeks)
        datesToPlot= ArrayList()
        datesToPlot.add("1/5/2020")
        datesToPlot.add("12/12/2021")
        datesToPlot.add("28/3/2022")
        datesToPlot.add("15/3/2022")
        datesToPlot.add("23/10/2022")
        datesToPlot.add("18/11/2022")
        datesToPlot.add("28/11/2022")
        datesToPlot.add("28/2/2023")

        setTextview()
    }

    private fun setTextview(){
        for (i in 0..41)
        {
            val resID: Int = context.getResources().getIdentifier("textview_$i", "id", context.getPackageName())
            dateTextView[i]=findViewById(resID)
            dateTextView[i]!!.setBackgroundColor(Color.CYAN)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun headerfnc(){
        var x=9
        var currentMonth=monthlist[x]
        var month= Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("MM")
        var s=SimpleDateFormat("MMMM")
        var month_tt=s.format(month.time)
        var dateMonth = simpleDateFormat.format(month.time).toString()
        var simpleDateFormat2 = SimpleDateFormat("yyyy")
        var dateYear = simpleDateFormat2.format(month.time).toString()
        count_month=dateMonth.toInt()-1
        count_year=dateYear.toInt()
        header.text = month_tt +" " +count_year

        arrow_prev.setOnClickListener {
            nums_Array= ArrayList()
            if (count_month!=0)
            {
                count_month= count_month!! -1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                //allotedDatedApi()
                holiday()
            }
            else
            {
                count_year= count_year!! -1
                val m = monthlist[11]
                count_month=11
                header.text = m + count_year
                daysinweek()
                setTextview()
                //allotedDatedApi()
                holiday()
            }
        }
        arrow_nxt.setOnClickListener {

            nums_Array= ArrayList()
            if (count_month!=11)
            {
                count_month= count_month!! +1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                //allotedDatedApi()
                holiday()
            }
            else
            {
                count_year= count_year!! +1
                val m = monthlist[0]
                count_month=0
                header.text = m + count_year
                daysinweek()
                setTextview()
                //allotedDatedApi()
                 holiday()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysinweek(){

        var count_month2= count_month!!+1
        val yearMonthObject: YearMonth = YearMonth.of(count_year!!, count_month2)
        val firstday_date: LocalDate? =yearMonthObject.atDay(1)
        val day_name: DayOfWeek = firstday_date!!.dayOfWeek
        month_total_days=yearMonthObject.lengthOfMonth()


        if (day_name.toString().equals("SUNDAY"))
        {
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("MONDAY"))
        {
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("TUESDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("WEDNESDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("THURSDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("FRIDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        else if (day_name.toString().equals("SATURDAY"))
        {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!)
            {
                nums_Array.add(i.toString())
            }
        }

        for (i in 0..41){
            if (i<=nums_Array.size-1)
            {
                dateTextView[i]!!.visibility= View.VISIBLE
                var value=nums_Array.get(i).toString()

                if (value.equals("0"))
                {
                    dateTextView[i]!!.visibility= View.INVISIBLE
                }
                else
                {
                    dateTextView[i]!!.visibility= View.VISIBLE
                    dateTextView[i]!!.text = value.toString()
                }
            }
            else
            {
                dateTextView[i]!!.visibility= View.INVISIBLE
            }
        }
    }

    private fun onclick(){
        for (i in 0..41) {
            dateTextView[i]!!.setBackgroundColor(Color.CYAN)
            dateTextView[i]!!.setOnClickListener() {
                Log.e("c_postn",i.toString())
                var c_day=nums_Array.get(i)
                var c_month= count_month!! +1
                var c_year=count_year
                var c_date=c_day+"/"+c_month+"/"+c_year
                Log.e("c_date",c_date)

            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun holiday(){


            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
        Log.e("date",currentDate)
            System.out.println(" C DATE is  "+currentDate)
            for (i in 0..nums_Array.size-1) {

                Log.e("td", month_total_days.toString())
                var c_day=nums_Array.get(i)
                var c_month= count_month!! +1
                var c_year=count_year
                var c_date=c_day+"/"+c_month+"/"+c_year
                Log.e("c_date",c_date)


                if (currentDate.equals(c_date)){

                    Log.e("match","match")
                    dateTextView[i]!!.setBackgroundColor(R.color.kings_blue)

                }

                else{
                    //Log.e("no_match","no_match")

                }
            }

    }
}