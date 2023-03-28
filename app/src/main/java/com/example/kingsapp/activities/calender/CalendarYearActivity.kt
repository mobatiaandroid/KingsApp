package com.example.kingsapp.activities.calender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.adapter.CalendarYearAdapter
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import java.text.SimpleDateFormat
import java.util.*

class CalendarYearActivity : AppCompatActivity() {
    lateinit var mcontext: Context
    lateinit var header: TextView
    //var adapter: MyRecyclerViewAdapter? = null
    var isCalendarVisible = false
    var titleTxt: TextView? = null
    var closeCalendarYear: ImageView? = null
    lateinit var dataArray: ArrayList<String>
    var yearValue:String=""
    private val dayNames =
        arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
    private val monthNames = arrayOf(
        "JANUARY",
        "FEBRUARY",
        "MARCH",
        "APRIL",
        "MAY",
        "JUNE",
        "JULY",
        "AUGUST",
        "SEPTEMBER",
        "OCTOBER",
        "NOVEMBER",
        "DECEMBER"
    )
    private val monthNamesTitle = arrayOf(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recycle_main_activity)
        mcontext=this
        initFn()

    }
    private fun initFn(){
        dataArray= ArrayList()

        dataArray.add("07")
        dataArray.add("08")
        dataArray.add("09")
        dataArray.add("10")
        dataArray.add("11")
        dataArray.add("0")
        dataArray.add("01")
        dataArray.add("02")
        dataArray.add("03")
        dataArray.add("04")
        dataArray.add("05")
        dataArray.add("06")
        titleTxt = findViewById<View>(R.id.titleTxt) as TextView
        closeCalendarYear = findViewById<View>(R.id.closeCalendarYear) as ImageView
        val cal = Calendar.getInstance()
        val year_date = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val yearName = year_date.format(cal.time)
        val currentYearNumber = yearName.toInt()

        val month_date = SimpleDateFormat("MM", Locale.ENGLISH)
        val currentMonthName = month_date.format(cal.time)
        val currentMonthNumber = currentMonthName.toInt()

        closeCalendarYear!!.setOnClickListener {
            PreferenceManager().setFromYearView(mcontext,"0")
            val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
            startActivity(intent)
            //finish()
            //onBackPressed()
        }
        var titleString = ""
        if (currentMonthNumber < 7) { // JAN - JUL
            titleString = (currentYearNumber - 1).toString() + " - " + currentYearNumber
            titleTxt!!.text = titleString
        } else {                     // AUG - DEC
            titleString = currentYearNumber.toString() + " - " + (currentYearNumber + 1)
            titleTxt!!.text = titleString
        }
        //  Log.d("LOG11","titleString= "+titleString);
        //val data = arrayOf("7", "8", "9", "10", "11", "0", "1", "2", "3", "4", "5", "6")
        //Bundle args = intent.getBundleExtra("BUNDLE");
        //Bundle args = intent.getBundleExtra("BUNDLE");
        val holidaysArray = intent.getSerializableExtra("BUNDLE") as ArrayList<String>?

        val recyclerView = findViewById<View>(R.id.rvNumbers) as RecyclerView
        //recyclerView.setAlpha(0.0f);
        //recyclerView.setAlpha(0.0f);

        //recyclerView.setAlpha(0.0f);
        val numberOfColumns = 3
        recyclerView.layoutManager = GridLayoutManager(mcontext, numberOfColumns)
        //recyclerView.layoutManager=LinearLayoutManager(mcontext)
        var adapter= CalendarYearAdapter(mcontext,dataArray)
        recyclerView.adapter=adapter
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                val monthNumber = dataArray[position].toInt()
                val monthName = monthNamesTitle[monthNumber]
                val count1=monthNumber!!+1
                if(count1.toString()=="08"||count1.toString()=="09"||count1.toString()=="10"
                    ||count1.toString()=="11"||count1.toString()=="12"){
                    yearValue="2022"
            }else{
                yearValue="2023"
            }
                Log.e("count1", count1.toString())
                if (count1==10)
                {
                    PreferenceManager().setFromYearView(mcontext,"1")
                    PreferenceManager().setMonthSelected(mcontext,count1.toString())
                    val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
                    intent.putExtra("monthNumber",count1.toString())
                    intent.putExtra("monthName",monthName.toString())
                    intent.putExtra("yearValue",yearValue.toString())
                    Log.e("monthNumber",count1.toString())
                    Log.e("monthName",monthName.toString())
                    startActivity(intent)
                }
                else if(count1==11)
                {
                    PreferenceManager().setFromYearView(mcontext,"1")
                    PreferenceManager().setMonthSelected(mcontext,count1.toString())
                    val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
                    intent.putExtra("monthNumber",count1.toString())
                    intent.putExtra("monthName",monthName.toString())
                    intent.putExtra("yearValue",yearValue.toString())
                    Log.e("monthNumber",count1.toString())
                    Log.e("monthName",monthName.toString())
                    startActivity(intent)
                }
                else if (count1==12)
                {
                    Log.e("inside12", count1.toString())
                    PreferenceManager().setFromYearView(mcontext,"1")
                    PreferenceManager().setMonthSelected(mcontext,count1.toString())
                    val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
                    intent.putExtra("monthNumber",count1.toString())
                    intent.putExtra("monthName",monthName.toString())
                    intent.putExtra("yearValue",yearValue.toString())
                    Log.e("monthNumber",count1.toString())
                    Log.e("monthName",monthName.toString())
                    startActivity(intent)
                }
                else
                {
                    val count= "0"+count1
                    PreferenceManager().setFromYearView(mcontext,"1")
                    PreferenceManager().setMonthSelected(mcontext,count1.toString())
                    val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
                    intent.putExtra("monthNumber",count)
                    intent.putExtra("monthName",monthName.toString())
                    intent.putExtra("yearValue",yearValue.toString())
                    Log.e("monthNumber",count.toString())
                    Log.e("monthName",monthName.toString())


                    startActivity(intent)
                }

                /*Log.e("m number",monthNumber.toString())
                Log.e("m name",monthName.toString())*/




            }

        })



    }
}