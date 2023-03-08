package com.example.kingsapp.activities.calender

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.AbsenceActivity
import com.example.kingsapp.activities.calender.adapter.CustomLisAdapter
import com.example.kingsapp.activities.calender.adapter.ListViewSpinnerAdapter
import com.example.kingsapp.activities.calender.model.CalendarModel
import com.example.kingsapp.activities.calender.model.ListViewSpinnerModel
import com.example.kingsapp.activities.studentName
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

class SchoolCalendarActivity:AppCompatActivity() {
    lateinit var mcontext: Context
    lateinit var header:TextView
    lateinit var arrow_prev:ImageView
    lateinit var arrow_nxt:ImageView
    lateinit var monthlist:Array<String>
    lateinit var week_day:Array<String>
    lateinit var nums_Array:ArrayList<String>
    var month_total_days:Int?=null
    var count_month:Int?=null
    var count_year:Int?=null
    val dateTextView = arrayOfNulls<TextView>(42)
    lateinit var datesToPlot:ArrayList<String>
    lateinit var mListViewArray : ArrayList<ListViewSpinnerModel>
    lateinit var mEventArrayListFilterList : ArrayList<CalendarModel>

    lateinit var txtSpinner:LinearLayout
    private var daySpinSelect = true
    lateinit var listSpinner: RelativeLayout
    lateinit var  spinnnerList: ListView
    lateinit var list: RecyclerView
    lateinit var arrowUpImg:ImageView
    lateinit var arrowDwnImg:ImageView
    lateinit var rel_calendar_title:RelativeLayout
    lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_school_calendar_layout)
        mcontext = this
        initFn()
        headerfnc()
        daysinweek()
       // onclick()
    }

    private fun onclick() {
        for (i in 0..41) {
            dateTextView[i]!!.setBackgroundColor(Color.WHITE)
            dateTextView[i]!!.setOnClickListener {

                var c_day = nums_Array.get(i)
                var c_month = count_month!! + 1
                var c_year = count_year
                var c_date = c_day + "/" + c_month + "/" + c_year

                for (i in 0..datesToPlot.size - 1) {


                }
            }
        }
    }

    private fun headerfnc() {
        var x = 9
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

        arrow_prev.setOnClickListener() {
            nums_Array = ArrayList()
            if (count_month != 0) {
                count_month = count_month!! - 1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                //  holiday()
            } else {
                count_year = count_year!! - 1
                val m = monthlist[11]
                count_month = 11
                header.text = m + count_year
                daysinweek()
                setTextview()
                //  holiday()
            }
        }
        arrow_nxt.setOnClickListener() {
            nums_Array = ArrayList()
            if (count_month != 11) {
                count_month = count_month!! + 1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                // holiday()
            } else {
                count_year = count_year!! + 1
                val m = monthlist[0]
                count_month = 0
                header.text = m + count_year
                daysinweek()
                setTextview()
                // holiday()
            }
        }
    }

    private fun initFn() {
        nums_Array= ArrayList()
        mEventArrayListFilterList= ArrayList()
        mListViewArray= ArrayList()

        var modelcal= CalendarModel("Parent Partnership Series","Wednesday 1st February","1")
        mEventArrayListFilterList.add(modelcal)
        var modelcal1= CalendarModel("Parent Partnership Series","Thursday 2nd February","1")
        mEventArrayListFilterList.add(modelcal1)
        var modelcal2= CalendarModel("Parent Partnership Series","Wednesday 10th February","1")
        mEventArrayListFilterList.add(modelcal2)
        var modelcal3= CalendarModel("Parent Partnership Series","Thursday 16th February","1")
        mEventArrayListFilterList.add(modelcal3)
        var modelcal4= CalendarModel("Parent Partnership Series","Thursday 16th February","1")
        mEventArrayListFilterList.add(modelcal4)
        var modell= ListViewSpinnerModel("Year View"," ",",")
        mListViewArray.add(modell)
        var xmodel= ListViewSpinnerModel("Month View"," "," ")
        mListViewArray.add(xmodel)
        var nmodel= ListViewSpinnerModel("Week View"," "," ")
        mListViewArray.add(nmodel)
        list = findViewById<RecyclerView>(R.id.mEventList)

        spinnnerList = findViewById(R.id.eventSpinner)
        back = findViewById(R.id.back)
        txtSpinner=findViewById(R.id.txtSpinner)
        listSpinner=findViewById(R.id.listSpinner)
        header=findViewById(R.id.Header)
        arrow_prev=findViewById(R.id.arrow_back)
        arrow_nxt=findViewById(R.id.arrow_nxt)
        arrowUpImg=findViewById(R.id.arrowUpImg)
        arrowDwnImg=findViewById(R.id.arrowDwnImg)
        rel_calendar_title=findViewById(R.id.rel_calendar_title)
        monthlist=getResources().getStringArray(R.array.Months)
        week_day=getResources().getStringArray(R.array.Weeks)
        setTextview()
        back.setOnClickListener {
            val intent = Intent(mcontext, MainActivity::class.java)
            startActivity(intent)
        }
        arrowUpImg.setOnClickListener {
            arrowDwnImg.visibility=View.VISIBLE
            arrowUpImg.visibility=View.GONE
            rel_calendar_title.visibility=View.VISIBLE
        }
        arrowDwnImg.setOnClickListener {
            arrowDwnImg.visibility=View.GONE
            arrowUpImg.visibility=View.VISIBLE
            rel_calendar_title.visibility=View.GONE
        }
        spinnnerList.adapter = ListViewSpinnerAdapter(mcontext, mListViewArray)

        list!!.layoutManager = LinearLayoutManager(mcontext)
        val studentlist_adapter =
            CustomLisAdapter(mcontext, mEventArrayListFilterList)
        list!!.adapter = studentlist_adapter

        // spinnnerList.setAdapter(ListViewSpinnerAdapter(mcontext, mListViewArray))

        txtSpinner.setOnClickListener {
            if (daySpinSelect==true)
            {
                listSpinner.visibility=View.VISIBLE
                daySpinSelect = false
            }
            else{
                listSpinner.visibility=View.GONE
                daySpinSelect = true

            }
        }

        list.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mcontext, CalendarDetailsActivity::class.java)
                startActivity(intent)
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysinweek() {

        var count_month2 = count_month!! + 1
        val yearMonthObject: YearMonth = YearMonth.of(count_year!!, count_month2)
        val firstday_date: LocalDate? = yearMonthObject.atDay(1)
        val day_name: DayOfWeek = firstday_date!!.dayOfWeek
        month_total_days = yearMonthObject.lengthOfMonth()


        if (day_name.toString().equals("SUNDAY")) {
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("MONDAY")) {
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("TUESDAY")) {
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("WEDNESDAY")) {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("THURSDAY")) {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("FRIDAY")) {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        } else if (day_name.toString().equals("SATURDAY")) {
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            nums_Array.add("0")
            for (i in 1..month_total_days!!) {
                nums_Array.add(i.toString())
            }
        }

        for (i in 0..41) {
            if (i <= nums_Array.size - 1) {
                dateTextView[i]!!.visibility = View.VISIBLE
                var value = nums_Array.get(i).toString()

                if (value.equals("0")) {
                    dateTextView[i]!!.visibility = View.INVISIBLE
                } else {
                    dateTextView[i]!!.visibility = View.VISIBLE
                    dateTextView[i]!!.text = value.toString()
                }
            } else {
                dateTextView[i]!!.visibility = View.INVISIBLE
            }
        }
    }

    private fun setTextview() {
        for (i in 0..41) {
            val resID: Int =
                mcontext.resources.getIdentifier("textview_$i", "id", mcontext.packageName)
            dateTextView[i] = findViewById(resID)
            dateTextView[i]!!.setBackgroundColor(Color.WHITE)
            dateTextView[i]!!.setTextColor(Color.BLACK)
        }

    }
}