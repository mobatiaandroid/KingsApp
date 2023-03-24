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
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

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

        //callCalendarListMonth()
       // callCalendarList()
       // onclick()
    }

    private fun callCalendarList() {
        mEventArrayListFilterListMonth.clear()
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
                    mEventArrayListFilterListMonth.addAll(response.body()!!.calendar)
                    Log.e("mEventArrayListYear", mEventArrayListFilterListMonth.toString())
for(i in mEventArrayListFilterListMonth.indices) {
    val date: Date
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    date = sdf.parse(mEventArrayListFilterListMonth.get(i).start_date);

    Log.e("date", date.toString())
    val formatEEE = SimpleDateFormat("EEE", Locale.ENGLISH)
    val formatdd = SimpleDateFormat("dd", Locale.ENGLISH)
    val formatMMM = SimpleDateFormat("MMM", Locale.ENGLISH)
    val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
    val formatyyyy = SimpleDateFormat("yyyy", Locale.ENGLISH)
    val c = Calendar.getInstance().getTime()
    val monthNumber = formatMM.format(date)
    val dayOfTheWeek = formatEEE.format(date) // Thu
    val days = formatdd.format(date) // 20
    val monthString = formatMMM.format(date) // Jun
    val year = formatyyyy.format(date)
    val monthNumber1 = formatMM.format(date)

    mEventArrayListFilterListMonth.get(i).monthNumber=monthNumber1
    mEventArrayListFilterListMonth.get(i).dayOfTheWeekk=dayOfTheWeek
    mEventArrayListFilterListMonth.get(i).dayss=days
    mEventArrayListFilterListMonth.get(i).monthString=monthString
    mEventArrayListFilterListMonth.get(i).yearr=year
    Log.e("monthNumber", monthNumber.toString())
    Log.e("dayOfTheWeek", dayOfTheWeek.toString())
    Log.e("days", days.toString())
    Log.e("monthString", monthString.toString())
    Log.e("year", year.toString())


    //filterWeekArray(monthNumber,dayOfTheWeek,days,monthString,year,mEventArrayListYear)
    //
}
                    filterYearlist(mEventArrayListFilterListMonth)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
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

    private fun filterWeekArray(

    ) {

        mEventArrayListFilterListYear= ArrayList()
        val formatEEE = SimpleDateFormat("EEE", Locale.ENGLISH)
        val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
        val formatDD = SimpleDateFormat("dd", Locale.ENGLISH)
        val formatyyyy = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

        val dayOfTheWeek = arrayOfNulls<String>(7)
        val month = arrayOfNulls<String>(7)
        val year = arrayOfNulls<String>(7)
        val dd = arrayOfNulls<String>(7)

        val date = Date() // your date


        val calendar = java.util.Calendar.getInstance()
        calendar.time = date
        calendar.firstDayOfWeek = java.util.Calendar.SUNDAY
        calendar[java.util.Calendar.DAY_OF_WEEK] = java.util.Calendar.SUNDAY
        println("calendar:" + calendar[java.util.Calendar.DAY_OF_MONTH] + ":" + calendar[java.util.Calendar.MONTH] + ":" + calendar[java.util.Calendar.YEAR])

        val days = arrayOfNulls<String>(7)

        for (i in 0..6) {
            days[i] = sdf.format(calendar.time)
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
            calendar.firstDayOfWeek = java.util.Calendar.SUNDAY
            calendar[java.util.Calendar.DAY_OF_WEEK] = i + 1
            var msgDate = Date()
            try {
                msgDate = sdf.parse(
                    calendar[java.util.Calendar.YEAR].toString() + "-" + currentMonth(
                        calendar[java.util.Calendar.MONTH]
                    ) + "-" + calendar[java.util.Calendar.DAY_OF_MONTH]
                )
            } catch (ex: ParseException) {
                Log.e("Date", "Parsing error")
            }
            dayOfTheWeek[i] = formatEEE.format(msgDate) // Thu
            year[i] = formatyyyy.format(msgDate) // yyyy
            month[i] = formatMM.format(msgDate) // 15
            dd[i] = formatDD.format(msgDate) // 15

            Log.e("dxay", dayOfTheWeek[i].toString())
            Log.e("year", year[i].toString())
            Log.e("month", month[i].toString())
            Log.e("dd", dd[i].toString())
        }
        mEventArrayListFilterListYear=ArrayList()
            for (i in dayOfTheWeek.indices) {
                Log.e("size", mEventArrayListYear.size.toString())
                        for (j in mEventArrayListYear.indices) {

                            if(dd[i].equals(mEventArrayListYear.get(j).dayss)&& dayOfTheWeek[i].equals(mEventArrayListYear.get(j).dayOfTheWeekk)&&
                                month[i].equals(mEventArrayListYear.get(j).monthNumber)&&
                                year[i].equals(mEventArrayListYear.get(j).yearr))
                            {

                                mEventArrayListFilterListYear.add(mEventArrayListYear.get(j))


                            }

                        }

                    }
        Log.e("arrayinside", mEventArrayListFilterListYear.size.toString())
        list!!.layoutManager = LinearLayoutManager(mcontext)
        val studentlist_adapter =
            CustomLisAdapter(mcontext, mEventArrayListFilterListYear)
        list!!.adapter = studentlist_adapter
        Log.e("array", mEventArrayListFilterListYear.size.toString())

    }

    private fun callCalendarListMonth() {

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
                    mEventArrayListYear.addAll(response.body()!!.calendar)
                    //  Log.e("ArrayAudioFile1", String.valueOf(audiofile.get(0)));
                    //   Log.e("ArrayAudioFile2", String.valueOf(audiofile.get(1)));

                    /************************ end of end date new */
                    for (i in mEventArrayListYear.indices )
                    {
                        val date:Date
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                        date=sdf.parse(mEventArrayListYear.get(i).start_date);
                        Log.e("date", date.toString())
                        val formatEEE = SimpleDateFormat("EEE", Locale.ENGLISH)
                        val formatdd = SimpleDateFormat("dd", Locale.ENGLISH)
                        val formatMMM = SimpleDateFormat("MMM", Locale.ENGLISH)
                        val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
                        val formatyyyy = SimpleDateFormat("yyyy", Locale.ENGLISH)
                        val c = Calendar.getInstance().getTime()

                        val monthNumber = formatMM.format(c)
                        PreferenceManager().setMonthView(mcontext,monthNumber)
                        val monthNumber1 = formatMM.format(date)
                        val dayOfTheWeek = formatEEE.format(date) // Thu
                        val days = formatdd.format(date) // 20
                        val monthString = formatMMM.format(date) // Jun
                        val year = formatyyyy.format(date)
                        mEventArrayListYear.get(i).monthNumber=monthNumber1
                        mEventArrayListYear.get(i).dayOfTheWeekk=dayOfTheWeek
                        mEventArrayListYear.get(i).dayss=days
                        mEventArrayListYear.get(i).monthString=monthString
                        mEventArrayListYear.get(i).yearr=year

                        Log.e("monthNumber", monthNumber.toString())
                        Log.e("dayOfTheWeek", dayOfTheWeek.toString())
                        Log.e("days", days.toString())
                        Log.e("monthString", monthString.toString())
                        Log.e("year", year.toString())

                       // filtermonthlist(monthNumber.toString(),mEventArrayListFilterListMonth)

                    }

                    filtermonthlist(PreferenceManager().getMonthView(mcontext),mEventArrayListYear)

                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
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
                    filtermonthlist(count1.toString(),mEventArrayListYear)
                }
                else if(count_month==9)
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    Log.e("new", count1.toString())
                    filtermonthlist(count1.toString(),mEventArrayListYear)
                }

                else
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    val count= "0"+count1
                    Log.e("new", count.toString())
                    filtermonthlist(count.toString(),mEventArrayListYear)
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
                filtermonthlist(count.toString(),mEventArrayListYear)
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




                   /* val formatdd = SimpleDateFormat("dd", Locale.ENGLISH)
                    val formatMMM = SimpleDateFormat("MMM", Locale.ENGLISH)
                    val c = Calendar.getInstance().getTime()
                    val monthNumber = formatMM.format(c)
                    val dayOfTheWeek1 = formatEEE.format(c) // Thu
                    val days1 = formatdd.format(c) // 20
                    val monthString = formatMMM.format(c) // Jun
                    val year1 = formatyyyy.format(c)
                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("currentdate", c.toString())*/


                    filterWeekArray()

                    daySpinner.setText("Week View")
                    emptyImg.visibility=View.GONE

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

              /*  val intent = Intent(mcontext, CalendarDetailsActivity::class.java)
                intent.putExtra("tittle", mEventArrayListFilterMonth.get(position).title)
                intent.putExtra("des", mEventArrayListFilterMonth.get(position).description)
                intent.putExtra("startdate", mEventArrayListFilterMonth.get(position).start_date)
                intent.putExtra("enddate", mEventArrayListFilterMonth.get(position).end_date)
                intent.putExtra("venu", mEventArrayListFilterMonth.get(position).venue)

                startActivity(intent)*/
            }

        })
    }

    private fun filtermonthlist(month:String, mEventArrayListYear : ArrayList<CalendarList>) {
        mEventArrayListFilterMonth= ArrayList()
        Log.e("Monthdate", mEventArrayListYear.toString())
        for(i in mEventArrayListYear.indices)
        {
            val formatMM = SimpleDateFormat("MM", Locale.ENGLISH)
            val c = Calendar.getInstance().getTime()

            val monthNumber = formatMM.format(c)
            val monthdate:String
            monthdate=mEventArrayListYear.get(i).start_date
            Log.e("Monthdate",monthdate)
            Log.e("monthNumber",month)
            val monthdate1=monthdate.split("-")
            val str1=monthdate1[1]
            Log.e("str1", str1.toString())
            if(str1==month)
            {
                Log.e("Success",str1)
                Log.e("Success",month)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterMonth.add(mEventArrayListYear.get(i))
                Log.e("array", mEventArrayListFilterMonth.toString())

            }
            else
            {
                list.visibility=View.GONE
                emptyImg.visibility=View.VISIBLE

            }
            /*if(month.equals(mEventArrayListFilterListMonth.get(i).start_date))
            mEventArrayListFilterMonth.addAll()*/
        }

        val studentlist_adapter =
            CustomLisAdapter(mcontext, mEventArrayListFilterMonth)
        list!!.adapter = studentlist_adapter
    }

    fun filterYearlist(mEventArrayListYear: ArrayList<CalendarList>)
    {
        mEventArrayListFilterListYear= ArrayList()
        Log.e("Success", mEventArrayListYear.toString())
        for(j in mEventArrayListYear.indices)
        {

            val monthdate:String
            val monthNumber=intent.getStringExtra("monthNumber")
            Log.e("Yearmonth", monthNumber.toString())
            monthdate=mEventArrayListYear.get(j).start_date
            Log.e("Monthdate",monthdate)
            val monthdate1=monthdate.split("-")
            val str1:String=monthdate1[1]
            val str2:String=monthdate1[0]
            Log.e("str1", str1.toString())
            Log.e("str2", str2.toString())
            if(str1.equals(monthNumber))
            {

                Log.e("Success",str1)
                Log.e("Success",monthNumber)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterListYear.add(mEventArrayListYear.get(j))
                Log.e("mEventArrayListFilterListYear", mEventArrayListFilterListYear.toString())


            }
           else if(str1.equals(monthNumber)&&str2.equals("2022"))
            {
                Log.e("Success",str1)
                Log.e("Success",monthNumber)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterListYear.add(mEventArrayListYear.get(j))
                Log.e("mEventArrayListFilterListYear", mEventArrayListFilterListYear.toString())
            }


        }
        if(mEventArrayListFilterListYear.size==0)
        {
            list.visibility=View.GONE
            emptyImg.visibility=View.VISIBLE
        }
        else
        {
            val studentlist_adapter =
                CustomLisAdapter(mcontext, mEventArrayListFilterListYear)
            list!!.adapter = studentlist_adapter
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