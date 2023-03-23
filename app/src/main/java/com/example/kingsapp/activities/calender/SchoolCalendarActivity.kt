package com.example.kingsapp.activities.calender

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.*
import com.example.kingsapp.activities.calender.adapter.CustomLisAdapter
import com.example.kingsapp.activities.calender.adapter.ListViewSpinnerAdapter
import com.example.kingsapp.activities.calender.model.CalendarList
import com.example.kingsapp.activities.calender.model.CalendarListModel
import com.example.kingsapp.activities.calender.model.ListViewSpinnerModel
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var mEventArrayListYear : ArrayList<CalendarList>
    lateinit var mEventArrayListFilterListYear : ArrayList<CalendarList>
    lateinit var mEventArrayListFilterListMonth : ArrayList<CalendarList>
    lateinit var mEventArrayListFilterMonth : ArrayList<CalendarList>
    lateinit var emptyImg:RelativeLayout

    lateinit var txtSpinner:LinearLayout
    private var daySpinSelect = true
    lateinit var listSpinner: RelativeLayout
    lateinit var  spinnnerList: ListView
    lateinit var list: RecyclerView
    lateinit var arrowUpImg:ImageView
    lateinit var arrowDwnImg:ImageView
    lateinit var rel_calendar_title:RelativeLayout
    lateinit var back:ImageView
    lateinit var txtMYW:TextView
    lateinit var daySpinner:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_school_calendar_layout)
        mcontext = this
        initFn()
        headerfnc()
        daysinweek()


       // onclick()
    }

    private fun callCalendarList() {
        mEventArrayListYear.clear()
        Log.e("callcal", PreferenceManager().getStudent_ID(mcontext).toString())
        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                if (response.body()!!.status.equals(100))
                {
                    mEventArrayListYear.addAll(response.body()!!.calendar)
                    Log.e("mEventArrayListYear", mEventArrayListYear.toString())
                    filterYearlist(mEventArrayListYear)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun callCalendarListMonth() {
        mEventArrayListFilterListMonth.clear()
        Log.e("callcalList", PreferenceManager().getStudent_ID(mcontext).toString())

        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendarList("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                if (response.body()!!.status.equals(100))

                {
                    mEventArrayListFilterListMonth.addAll(response.body()!!.calendar)

                    val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
                    val c = Calendar.getInstance().getTime()
                    val monthNumber = formatMM.format(c)

                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("currentdate", c.toString())

                    filtermonthlist(monthNumber.toString(),mEventArrayListFilterListMonth)

                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
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
     Log.e("count_month", count_month.toString())
        arrow_prev.setOnClickListener() {
            nums_Array = ArrayList()
            if (count_month != 0) {
                count_month = count_month!! - 1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                daysinweek()
                setTextview()
                if(count_month==10)
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    Log.e("new", count1.toString())
                    filtermonthlist(count1.toString(),mEventArrayListFilterListMonth)
                }
                else if(count_month==9)
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    Log.e("new", count1.toString())
                    filtermonthlist(count1.toString(),mEventArrayListFilterListMonth)
                }

                else
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    val count= "0"+count1
                    Log.e("new", count.toString())
                    filtermonthlist(count.toString(),mEventArrayListFilterListMonth)
                }

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
                Log.e("countmont", count_month.toString())
                Log.e("m", m.toString())
                val count1=count_month!!+1
                val count= "0"+count1
                Log.e("new", count.toString())
                filtermonthlist(count.toString(),mEventArrayListFilterListMonth)
                // holiday()
            } else {
                count_year = count_year!! + 1
                val m = monthlist[0]
                count_month = 0
                header.text = m + count_year
                daysinweek()
                setTextview()
               /* val count1=count_month!!+1
                val count= "0"+count1
                Log.e("new", count.toString())
                filtermonthlist(count.toString(),mEventArrayListFilterListMonth)*/
                // holiday()
            }
        }
    }

    private fun initFn() {
        nums_Array= ArrayList()
        mEventArrayListYear= ArrayList()
        mListViewArray= ArrayList()
        mEventArrayListFilterListMonth = ArrayList()
        mEventArrayListFilterMonth = ArrayList()
        mEventArrayListFilterListYear = ArrayList()

        var modell= ListViewSpinnerModel("Year View"," ",",")
        mListViewArray.add(modell)
        var xmodel= ListViewSpinnerModel("Month View"," "," ")
        mListViewArray.add(xmodel)
        var nmodel= ListViewSpinnerModel("Week View"," "," ")
        mListViewArray.add(nmodel)
        list = findViewById<RecyclerView>(R.id.mEventList)
        txtMYW=findViewById(R.id.txtMYW)
        daySpinner=findViewById(R.id.daySpinner)
        emptyImg=findViewById(R.id.alertRelative)

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





        if (PreferenceManager().getFromYearView(mcontext).equals("1")){
            Log.e("year","1")

            callCalendarList()
        }else{
            Log.e("year","0")
            callCalendarListMonth()
        }
        back.setOnClickListener {
            val intent = Intent(mcontext, HomeActivity::class.java)
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


        // spinnnerList.setAdapter(ListViewSpinnerAdapter(mcontext, mListViewArray))

        spinnnerList.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, l ->
                Log.e("positiontime1", "positiontime1 = $position")

                // TODO Auto-generated method stub
                if (position == 1) {
                    txtMYW.setText("This Month")
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinner.setText("Month View")
                    callCalendarListMonth()

                } else if (position == 2) {
                    txtMYW.setText("This Week")
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinner.setText("Week View")
                    list!!.visibility=View.GONE
                    emptyImg.visibility=View.VISIBLE




                   /* list!!.layoutManager = LinearLayoutManager(mcontext)
                    val studentlist_adapter =
                        CustomLisAdapter(mcontext, mEventArrayListFilterList)
                    list!!.adapter = studentlist_adapter*/

                } else if (position == 0) {
                    listSpinner.setVisibility(
                        View.GONE
                    )

                    val intent = Intent(mcontext, CalendarYearActivity::class.java)
                    //intent.putExtra("BUNDLE", AppController.holidayArrayListYear as Serializable)
                    startActivity(intent)


                }
                if (daySpinSelect == false) {
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinSelect = true
                }
            }
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

    private fun filtermonthlist(month:String, mEventArrayListFilterListMonth : ArrayList<CalendarList>) {
        mEventArrayListFilterMonth.clear()
        for(i in mEventArrayListFilterListMonth.indices)
        {
            val monthdate:String
            monthdate=mEventArrayListFilterListMonth.get(i).start_date
            Log.e("Monthdate",monthdate)
            val monthdate1=monthdate.split("-")
            val str1=monthdate1[1]
            Log.e("str1", str1.toString())
            if(str1==month)
            {
                /*Log.e("Success",str1)
                Log.e("Success",month)*/
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterMonth.add(mEventArrayListFilterListMonth.get(i))
                val studentlist_adapter =
                    CustomLisAdapter(mcontext, mEventArrayListFilterMonth)
                list!!.adapter = studentlist_adapter
            }
            else
            {
                list.visibility=View.GONE
                emptyImg.visibility=View.VISIBLE

            }
            /*if(month.equals(mEventArrayListFilterListMonth.get(i).start_date))
            mEventArrayListFilterMonth.addAll()*/
        }

    }

    fun filterYearlist(mEventArrayListYear: ArrayList<CalendarList>)
    {
        Log.e("Success", mEventArrayListYear.toString())
        mEventArrayListFilterListYear.clear()
        for(i in mEventArrayListYear.indices)
        {
            val monthdate:String
            var monthNumber=intent.getStringExtra("monthNumber")
            Log.e("Yearmonth", monthNumber.toString())
            monthdate=mEventArrayListYear.get(i).start_date
            Log.e("Monthdate",monthdate)
            val monthdate1=monthdate.split("-")
            val str1=monthdate1[1]
            Log.e("str1", str1.toString())
            if(str1==monthNumber)
            {
                Log.e("Success",str1)
                Log.e("Success",monthNumber)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterListYear.add(mEventArrayListYear.get(i))
                val studentlist_adapter =
                    CustomLisAdapter(mcontext, mEventArrayListFilterListYear)
                list!!.adapter = studentlist_adapter
            }
            else
            {
                list.visibility=View.GONE
                emptyImg.visibility=View.VISIBLE

            }
        }

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
    fun currentMonth(month: Int): Int {
        var currentMonth = "01"
        when (month) {
            0 -> currentMonth = "01"
            1 -> currentMonth = "02"
            2 -> currentMonth = "03"
            3 -> currentMonth = "04"
            4 -> currentMonth = "05"
            5 -> currentMonth = "06"
            6 -> currentMonth = "07"
            7 -> currentMonth = "08"
            8 -> currentMonth = "09"
            9 -> currentMonth = "10"
            10 -> currentMonth = "11"
            11 -> currentMonth = "12"
        }
        return Integer.valueOf(currentMonth)
    }

}