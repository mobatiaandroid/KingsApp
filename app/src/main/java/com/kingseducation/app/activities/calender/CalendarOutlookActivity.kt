package com.kingseducation.app.activities.calender

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.kingseducation.app.R
import com.kingseducation.app.activities.calender.adapter.CalendarDateAdapter
import com.kingseducation.app.activities.calender.adapter.CalendarOutlookAdapter
import com.kingseducation.app.activities.calender.model.CalendarDateModel
import com.kingseducation.app.activities.calender.model.CalendarDetailModel
import com.kingseducation.app.activities.calender.model.CalendarList
import com.kingseducation.app.activities.calender.model.CalendarListModel
import com.kingseducation.app.activities.calender.model.CalendarOutlookResponseModel
import com.kingseducation.app.activities.calender.model.CalendarResponseArray
import com.kingseducation.app.activities.calender.model.CategoryModel
import com.kingseducation.app.activities.calender.model.PrimaryModel
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.fragment.mContext
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.OnItemClickListener
import com.kingseducation.app.manager.recyclerviewmanager.addOnItemClickListener
import com.mobatia.calendardemopro.adapter.CategoryAdapter
import retrofit2.Call
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class CalendarOutlookActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var calendarRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var noEventTxt: TextView
    lateinit var noEventImage: ImageView
    lateinit var back: ImageView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var monthYearTxt: TextView
    lateinit var hideBtn: TextView
    lateinit var showBtn: TextView
    lateinit var previousBtn: ImageView
    lateinit var calendar_relative: RelativeLayout
    lateinit var nextBtn: ImageView
    lateinit var filterLinear: LinearLayout
    lateinit var hidePast: LinearLayout
    lateinit var monthLinear: LinearLayout
    var isPrimarySelected: Boolean = true
    var isSecondarySeleted: Boolean = true
    var isWholeSchoolSelected: Boolean = true
    var isAllSelected: Boolean = true
    var isHide: Boolean = false
    var isShow: Boolean = true
    var start: LocalDate? = null
    var end: LocalDate? = null
    var year: Int = 0
    lateinit var calendarArrayList: ArrayList<CalendarResponseArray>
    lateinit var primaryArrayList: ArrayList<CalendarList>
    lateinit var secondaryArrayList: ArrayList<CalendarList>
    lateinit var wholeSchoolArrayList: ArrayList<CalendarList>
    lateinit var primaryShowArrayList: ArrayList<PrimaryModel>
    lateinit var secondaryShowArrayList: ArrayList<PrimaryModel>
    lateinit var wholeSchoolShowArrayList: ArrayList<PrimaryModel>
    lateinit var calendarShowArrayList: ArrayList<PrimaryModel>
    lateinit var calendarFilterArrayList: ArrayList<PrimaryModel>
    lateinit var mTriggerModelArrayList: ArrayList<CategoryModel>
    lateinit var mCalendarFinalArrayList: ArrayList<CalendarDateModel>
    lateinit var FILTERCALENDARlIST: ArrayList<PrimaryModel>
    lateinit var difference_In_Days: String
    lateinit var progressBarDialog: ProgressBarDialog


    lateinit var calendarCategories: ArrayList<CalendarOutlookResponseModel.CalendarCategory>
    lateinit var calendarItems: ArrayList<CalendarOutlookResponseModel.CalendarEvent>
    lateinit var categoryNames: ArrayList<String>
    lateinit var categorySortList: ArrayList<String>


    var currentMonth: Int = -1
    lateinit var monthTxt: String
    var primaryColor: String = "#DE000F"
    var secondaryColor: String = "#00458C"
    var wholeSchoole: String = "#C4A105"
    private var currentMonthValue = LocalDate.now().monthValue
    private var currentYear = LocalDate.now().year
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar_outlook)
        context = this
        initFn()
        if (CommonClass.isInternetAvailable(context)) {
//            callCalendarApi()
            callCalendarAPIOutlook()
            //    callAPI()
        } else {
            Toast.makeText(
                context,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun callCalendarAPIOutlook() {
        progressBarDialog.show()
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
        }
        val call: Call<CalendarOutlookResponseModel> = ApiClient.getApiService().getOutlookCalendar(
            "Bearer " + PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<CalendarOutlookResponseModel> {
            override fun onResponse(
                call: Call<CalendarOutlookResponseModel>,
                response: Response<CalendarOutlookResponseModel>
            ) {
                progressBarDialog.dismiss()
                calendarCategories = ArrayList()
                calendarItems = ArrayList()
                categoryNames = ArrayList()
                categorySortList = ArrayList()
                calendarCategories = response.body()!!.calendarCategories
                for (i in calendarCategories.indices) {
                    categoryNames.add(calendarCategories.get(i).title)
                }
                calendarItems = response.body()!!.calendar
                categorySortList.addAll(categoryNames)
                mTriggerModelArrayList = ArrayList()
                for (item in categorySortList) {
                    val categoryModel = CategoryModel()
                    categoryModel.color = ""
                    categoryModel.categoryName = item
                    categoryModel.checkedCategory = true
                    mTriggerModelArrayList.add(categoryModel)
                }
                Log.e("mTriggerModelArrayList",mTriggerModelArrayList[0].categoryName.toString())
                showCalendar(calendarItems,categorySortList)


            }

            override fun onFailure(call: Call<CalendarOutlookResponseModel>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    context,
                    resources.getString(R.string.no_data_found),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun showCalendar(
        calendarItems: ArrayList<CalendarOutlookResponseModel.CalendarEvent>,
        categorySortList: ArrayList<String>
    ) {
        displayEventsForCurrentMonth()
    }
    private fun displayEventsForCurrentMonth() {
        val filteredEvents = filterEventsByMonth(calendarItems, categorySortList, currentYear, currentMonthValue)
        // Code to display the events (e.g., update UI components)
//
        for (i in filteredEvents){
            Log.e("fileters",i.key.toString()+i.value.size.toString())
        }
        filteredEvents.forEach { println(it.value.size.toString() + "" + it.key) }
        calendarRecycler.visibility = View.VISIBLE
        val calendarListAdapter = CalendarOutlookAdapter(
            context, filteredEvents, calendarRecycler
        )
        calendarRecycler.adapter = calendarListAdapter
        updateMonthYearTextView(monthYearTxt, currentYear, currentMonthValue)
    }

    private fun updateMonthYearTextView(textView: TextView, year: Int, month: Int) {
        val monthName = LocalDate.of(year, month, 1).month.getDisplayName(TextStyle.FULL, Locale.getDefault())
        textView.text = "$monthName $year"
    }
    private fun showNextMonthEvents() {
        if (currentMonthValue == 12) {
            currentMonthValue = 1
            currentYear++
        } else {
            currentMonthValue++
        }
        displayEventsForCurrentMonth()
    }

    private fun filterEventsByMonth(
        events: ArrayList<CalendarOutlookResponseModel.CalendarEvent>,
        categories: ArrayList<String>,
        year: Int,
        month: Int
    ): Map<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>> {
        val selectedCategoriesSet = categories.toSet()

        val filteredEvents = events.filter { event ->
            val eventDate = LocalDate.parse(event.start.dateTime.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
            event.categories.any { it in selectedCategoriesSet } &&
                    eventDate.year == year &&
                    eventDate.monthValue == month
        }

        val groupedEvents = HashMap<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>>()
        for (event in filteredEvents) {
            val eventDate = LocalDate.parse(event.start.dateTime.substring(0, 10), DateTimeFormatter.ISO_LOCAL_DATE)
            if (groupedEvents.containsKey(eventDate)) {
                groupedEvents[eventDate]?.add(event)
            } else {
                groupedEvents[eventDate] = arrayListOf(event)
            }
        }

        return groupedEvents
    }
    private fun showPreviousMonthEvents() {
        if (currentMonthValue == 1) {
            currentMonthValue = 12
            currentYear--
        } else {
            currentMonthValue--
        }
        displayEventsForCurrentMonth()
    }


    private fun initFn() {
        calendarRecycler = findViewById<RecyclerView>(R.id.calendarRecycler)
        titleTextView = findViewById<TextView>(R.id.titleTextView)
        showBtn = findViewById<TextView>(R.id.showBtn)
        hideBtn = findViewById<TextView>(R.id.hideBtn)
        noEventTxt = findViewById<TextView>(R.id.noEventTxt)
        noEventImage = findViewById<ImageView>(R.id.noEventImage)
        linearLayoutManager = LinearLayoutManager(context)
        calendarRecycler.layoutManager = linearLayoutManager
        calendarRecycler.itemAnimator = DefaultItemAnimator()
        progressBarDialog = ProgressBarDialog(context)
        back = findViewById(R.id.back)
        monthYearTxt = findViewById(R.id.monthYearTxt)
        previousBtn = findViewById(R.id.previousBtn)
        nextBtn = findViewById(R.id.nextBtn)
        filterLinear = findViewById(R.id.filterLinear)
        hidePast = findViewById(R.id.hidePast)
        monthLinear = findViewById(R.id.monthLinear)
        year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
        month(currentMonth, year)


        back.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
        filterLinear.setOnClickListener(View.OnClickListener {


            showFilterMenu(context)


        })
        if (!isHide && isShow) {
            hideBtn.setTextColor(resources.getColor(R.color.black))
            showBtn.setTextColor(resources.getColor(R.color.kings_blue))
            showBtn.setBackgroundResource(R.drawable.edittext_greybg)
            hideBtn.setBackgroundColor(resources.getColor(R.color.white))
            // showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
            //  hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.NORMAL);

        } else {
            hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
            showBtn.setTextColor(resources.getColor(R.color.black))
            hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
            showBtn.setBackgroundColor(resources.getColor(R.color.white))
        }

        nextBtn.setOnClickListener {
            if (currentMonthValue == 12) {
                currentMonthValue = 1
                currentYear++
            } else {
                currentMonthValue++
            }
            displayEventsForCurrentMonth()
        }

//        nextBtn.setOnClickListener({
//            currentMonth = currentMonth + 1
//            if (currentMonth > 11) {
//                currentMonth = currentMonth - 12
//                year = year + 1
//
//            }
//            isHide = false
//            isShow = true
//            if (!isHide && isShow) {
//                hideBtn.setTextColor(resources.getColor(R.color.black))
//                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
//                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
//                //   hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.NORMAL);
//
//            } else {
//                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setTextColor(resources.getColor(R.color.black))
//                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                showBtn.setBackgroundColor(resources.getColor(R.color.white))
//                // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);
//                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.NORMAL);
//
//            }
//            month(currentMonth, year)
//            showCalendarEvent(
//                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
//            )
//
//        })

//        hideBtn.setOnClickListener(View.OnClickListener {
//            hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
//            showBtn.setTextColor(resources.getColor(R.color.black))
//            hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
//            showBtn.setBackgroundColor(resources.getColor(R.color.white))
//
//            // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD)
//            //  showBtn.setTypeface(showBtn.getTypeface(), R.font.font.)
//
//            isHide = true
//            isShow = false
//            showCalendarEvent(
//                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
//            )
//        })

//        showBtn.setOnClickListener(View.OnClickListener {
//            hideBtn.setTextColor(resources.getColor(R.color.black))
//            showBtn.setTextColor(resources.getColor(R.color.kings_blue))
//            showBtn.setBackgroundResource(R.drawable.edittext_greybg)
//            hideBtn.setBackgroundColor(resources.getColor(R.color.white))
//            isHide = false
//            isShow = true
//            showCalendarEvent(
//                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
//            )
//        })

        previousBtn.setOnClickListener {
            if (currentMonthValue == 1) {
                currentMonthValue = 12
                currentYear--
            } else {
                currentMonthValue--
            }
            displayEventsForCurrentMonth()
        }
//        previousBtn.setOnClickListener(View.OnClickListener {
//            isHide = false
//            isShow = true
//            if (!isHide && isShow) {
//                hideBtn.setTextColor(resources.getColor(R.color.black))
//                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
//            } else {
//                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setTextColor(resources.getColor(R.color.black))
//                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                showBtn.setBackgroundColor(resources.getColor(R.color.white))
//
//            }
//            if (currentMonth == 0) {
//                currentMonth = 11 - currentMonth
//                year = year - 1
//            } else {
//                currentMonth = currentMonth - 1
//            }
//            month(currentMonth, year)
//            showCalendarEvent(
//                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
//            )
//
//        })
    }

    fun showCalendarEvent(
        allSeleted: Boolean,
        primarySelected: Boolean,
        secondarySelected: Boolean,
        wholeSchoolSelected: Boolean
    ) {
        primaryShowArrayList = ArrayList()
        secondaryShowArrayList = ArrayList()
        wholeSchoolShowArrayList = ArrayList()
        calendarFilterArrayList = ArrayList()
        if (primaryArrayList.size > 0) {
            for (i in 0..primaryArrayList.size - 1) {
                var pModel = PrimaryModel()
                Log.e("date", primaryArrayList.get(i).dTSTART.length.toString())
                if (primaryArrayList.get(i).dTSTART.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTSTART)
                    var result = outputFormat.format(startdate)
                    outputFormat.format(startdate)
                    val enddate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    outputFormat.format(enddate)
                    pModel.DTSTART = result

                } else if (primaryArrayList.get(i).dTSTART.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTSTART = outputDateStrstart

                } else if (primaryArrayList[i].dTSTART.length == 15) {
                    Log.e("1", "15")
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTSTART = outputDateStrstart
                }
                if (primaryArrayList.get(i).dTEND.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var result = outputFormat.format(startdate)
                    outputFormat.format(startdate)
                    pModel.DTEND = result

                } else if (primaryArrayList.get(i).dTEND.length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTEND = outputDateStrstart

                } else if (primaryArrayList.get(i).dTEND.length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTEND = outputDateStrstart

                }

                pModel.SUMMARY = primaryArrayList.get(i).sUMMARY
                pModel.color = primaryColor
                pModel.type = 1
                primaryShowArrayList.add(pModel)
                Log.e("primary", primaryShowArrayList.toString())

            }
        } else {
            noEventTxt.text = "No data"

        }
        if (secondaryArrayList.size > 0) {
            for (i in 0..secondaryArrayList.size - 1) {
                var sModel = PrimaryModel()
                Log.e("secondaryArrayList", secondaryArrayList.get(i).dTSTART)

                if (secondaryArrayList.get(i).dTSTART.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTSTART)
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTSTART = result
                    Log.e("startdate", result)

                } else if (secondaryArrayList.get(i).dTSTART.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTSTART = outputDateStrstart

                } else if (secondaryArrayList.get(i).dTSTART.toString().length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTSTART = outputDateStrstart

                }
                if (secondaryArrayList.get(i).dTEND.equals("null")) {
                    sModel.DTEND = ""
                } else if (secondaryArrayList.get(i).dTEND.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTEND)
                    // var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    //  outputFormat.timeZone = tz
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTEND = result

                } else if (secondaryArrayList.get(i).dTEND.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTEND = outputDateStrstart

                } else if (secondaryArrayList.get(i).dTEND.toString().length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(secondaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    sModel.DTEND = outputDateStrstart

                }
                sModel.SUMMARY = secondaryArrayList.get(i).sUMMARY
                sModel.color = secondaryColor
                sModel.type = 2
                secondaryShowArrayList.add(sModel)
                Log.e("secondary", secondaryShowArrayList.toString())

            }
        } else {
            noEventTxt.text = "No data"

        }
        if (wholeSchoolArrayList.size > 0) {
            for (i in 0 until wholeSchoolArrayList.size) {
                var wModel = PrimaryModel()
                if (wholeSchoolArrayList.get(i).dTSTART.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTSTART)
                    // var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    // outputFormat.timeZone = tz
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTSTART = result

                } else if (wholeSchoolArrayList.get(i).dTSTART.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTSTART = outputDateStrstart

                } else if (wholeSchoolArrayList.get(i).dTSTART.toString().length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTSTART)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTSTART = outputDateStrstart

                }
                if (wholeSchoolArrayList.get(i).dTEND.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTEND)
                    // var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    // outputFormat.timeZone = tz
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTEND = result

                } else if (wholeSchoolArrayList.get(i).dTEND.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTEND = outputDateStrstart

                } else if (wholeSchoolArrayList.get(i).dTEND.toString().length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(wholeSchoolArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    wModel.DTEND = outputDateStrstart

                }

                wModel.SUMMARY = wholeSchoolArrayList.get(i).sUMMARY.toString()
                // wModel.DESCRIPTION = wholeSchoolArrayList.get(i).description
                //  wModel.LOCATION = wholeSchoolArrayList.get(i).venue
                wModel.color = wholeSchoole
                wModel.type = 3
                wholeSchoolShowArrayList.add(wModel)
                Log.e("wholeshool", wholeSchoolShowArrayList.toString())
            }
        } else {
            noEventTxt.text = "No data"

        }
        if (allSeleted) {
            calendarShowArrayList = ArrayList()
            if (primaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(primaryShowArrayList)

            }
            if (secondaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(secondaryShowArrayList)
            }
            if (wholeSchoolShowArrayList.size > 0) {
                calendarShowArrayList.addAll(wholeSchoolShowArrayList)
            }

        } else if (!allSeleted && !primarySelected && !secondarySelected && !wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            var dummy = ArrayList<PrimaryModel>()
            calendarShowArrayList = dummy
        } else if (!allSeleted && !primarySelected && !secondarySelected && wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (wholeSchoolShowArrayList.size > 0) {
                calendarShowArrayList.addAll(wholeSchoolShowArrayList)
            }

        } else if (!allSeleted && !primarySelected && secondarySelected && !wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (secondaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(secondaryShowArrayList)
            }

        } else if (!allSeleted && !primarySelected && secondarySelected && wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (secondaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(secondaryShowArrayList)
            }
            if (wholeSchoolShowArrayList.size > 0) {
                calendarShowArrayList.addAll(wholeSchoolShowArrayList)
            }

        } else if (!allSeleted && primarySelected && !secondarySelected && !wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (primaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(primaryShowArrayList)
            }

        } else if (!allSeleted && primarySelected && !secondarySelected && wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (primaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(primaryShowArrayList)
            }
            if (wholeSchoolShowArrayList.size > 0) {
                calendarShowArrayList.addAll(wholeSchoolShowArrayList)
            }

        } else if (!allSeleted && primarySelected && secondarySelected && !wholeSchoolSelected) {
            calendarShowArrayList = ArrayList()
            if (primaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(primaryShowArrayList)
            }
            if (secondaryShowArrayList.size > 0) {
                calendarShowArrayList.addAll(secondaryShowArrayList)
            }

        }
        if (calendarShowArrayList.size > 0) {
            Log.e("calshowlist", calendarShowArrayList.size.toString())

            FILTERCALENDARlIST = ArrayList()
            for (n in 0..calendarShowArrayList.size - 1) {

                FILTERCALENDARlIST.add(calendarShowArrayList[n])

            }

            if (FILTERCALENDARlIST.size > 0) {
                Log.e("filtercallist", FILTERCALENDARlIST.size.toString())
                var listMonth: String = ""
                var listYear: String = ""
                for (i in 0..FILTERCALENDARlIST.size - 1) {
                    Log.e("filtercallistsize", FILTERCALENDARlIST.get(i).DTSTART.length.toString())
                    Log.e("filtercalliststart", FILTERCALENDARlIST.get(i).DTSTART)
                    if (FILTERCALENDARlIST.get(i).DTSTART.length == 20) {
                        val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                        val outputFormatYear: DateFormat = SimpleDateFormat("yyyy")
                        val outputFormatMonth: DateFormat = SimpleDateFormat("MMMM")
                        val startdate: Date = inputFormat.parse(FILTERCALENDARlIST.get(i).DTSTART)
                        var outputDateMonth: String = outputFormatMonth.format(startdate)
                        var outputDateYear: String = outputFormatYear.format(startdate)
                        listMonth = outputDateMonth
                        listYear = outputDateYear
                    } else if (FILTERCALENDARlIST.get(i).DTSTART.length == 11) {
                        val inputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                        val outputFormatYear: DateFormat = SimpleDateFormat("yyyy")
                        val outputFormatMonth: DateFormat = SimpleDateFormat("MMMM")
                        val startdate: Date = inputFormat.parse(FILTERCALENDARlIST.get(i).DTSTART)
                        var outputDateMonth: String = outputFormatMonth.format(startdate)
                        var outputDateYear: String = outputFormatYear.format(startdate)
                        listMonth = outputDateMonth
                        listYear = outputDateYear
                    } else if (FILTERCALENDARlIST.get(i).DTSTART.length == 10) {
                        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                        val outputFormatYear: DateFormat = SimpleDateFormat("yyyy")
                        val outputFormatMonth: DateFormat = SimpleDateFormat("MMMM")
                        val startdate: Date = inputFormat.parse(FILTERCALENDARlIST.get(i).DTSTART)
                        var outputDateMonth: String = outputFormatMonth.format(startdate)
                        var outputDateYear: String = outputFormatYear.format(startdate)
                        listMonth = outputDateMonth
                        listYear = outputDateYear
                    }
                    Log.e("listyear", listYear)
                    Log.e("lyear", year.toString())
                    monthLinear.visibility = View.VISIBLE
                    filterLinear.visibility = View.VISIBLE
                    hidePast.visibility = View.VISIBLE
                    if (listYear.equals(year.toString())) {
                        if (monthTxt.equals(listMonth)) {
                            Log.e("month", monthTxt + "  " + listMonth)
                            var model = PrimaryModel()
                            if (FILTERCALENDARlIST.get(i).DTSTART.length == 10) {
                                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                                val outPutFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                                val startdate: Date =
                                    inputFormat.parse(FILTERCALENDARlIST.get(i).DTSTART)
                                var outputStart: String = outPutFormat.format(startdate)
                                model.DTSTART = outputStart

                            } else {
                                model.DTSTART = FILTERCALENDARlIST.get(i).DTSTART
                            }


                            model.DTEND = FILTERCALENDARlIST.get(i).DTEND
                            model.SUMMARY = FILTERCALENDARlIST.get(i).SUMMARY
                            model.DESCRIPTION = FILTERCALENDARlIST.get(i).DESCRIPTION
                            model.LOCATION = FILTERCALENDARlIST.get(i).LOCATION
                            model.color = FILTERCALENDARlIST.get(i).color
                            model.type = FILTERCALENDARlIST.get(i).type
                            calendarFilterArrayList.add(model)
                        }

                    }

                }


                if (calendarFilterArrayList.size > 0) {
                    Log.e("calfilterlisttt", calendarFilterArrayList.size.toString())

                    calendarRecycler.visibility = View.VISIBLE
                    calendarFilterArrayList.sortByDescending { calendarFilterArrayList -> calendarFilterArrayList.DTSTART }
                    calendarFilterArrayList.reverse()
                    mCalendarFinalArrayList = ArrayList()
                    Log.e(
                        "filter last date",
                        calendarFilterArrayList[calendarFilterArrayList.size - 1].DTSTART.toString()
                    )
                    for (n in 0 until calendarFilterArrayList.size) {
                        Log.e(
                            "calfilterlistttstartdate",
                            calendarFilterArrayList.get(n).DTSTART.toString()
                        )

                        if (mCalendarFinalArrayList.size == 0) {
                            Log.e("mcalfinallist", "SIZE 0")
                            var cModel = CalendarDateModel()
                            cModel.startDate = calendarFilterArrayList.get(n).DTSTART
                            cModel.endDate = calendarFilterArrayList.get(n).DTEND
                            var calendarDetaiArray = ArrayList<CalendarDetailModel>()
                            var dModel = CalendarDetailModel()
                            dModel.DTSTART = calendarFilterArrayList.get(n).DTSTART
                            dModel.DTEND = calendarFilterArrayList.get(n).DTEND
                            dModel.SUMMARY = calendarFilterArrayList.get(n).SUMMARY
                            dModel.DESCRIPTION = calendarFilterArrayList.get(n).DESCRIPTION
                            dModel.LOCATION = calendarFilterArrayList.get(n).LOCATION
                            dModel.color = calendarFilterArrayList.get(n).color
                            dModel.type = calendarFilterArrayList.get(n).type
                            calendarDetaiArray.add(dModel)
                            cModel.detailList = calendarDetaiArray
                            mCalendarFinalArrayList.add(cModel)
                        } else {
                            Log.e("mcalfinallist", mCalendarFinalArrayList.size.toString())
                            var isFound: Boolean = false
                            var pos: Int = -1
                            for (o in 0 until mCalendarFinalArrayList.size) {
                                // Log.e("DATE ",calendarFilterArrayList.get(n).DTSTART+"  :: "+mCalendarFinalArrayList.get(o).startDate)
                                if (calendarFilterArrayList.get(n).DTSTART.equals(
                                        mCalendarFinalArrayList.get(o).startDate
                                    )
                                ) {
                                    isFound = true
                                    pos = o
                                }

                            }

                            if (!isFound) {
                                var cModel = CalendarDateModel()
                                cModel.startDate = calendarFilterArrayList.get(n).DTSTART
                                cModel.endDate = calendarFilterArrayList.get(n).DTEND
                                var calendarDetaiArray = ArrayList<CalendarDetailModel>()
                                var dModel = CalendarDetailModel()
                                dModel.DTSTART = calendarFilterArrayList.get(n).DTSTART
                                dModel.DTEND = calendarFilterArrayList.get(n).DTEND
                                dModel.SUMMARY = calendarFilterArrayList.get(n).SUMMARY
                                dModel.DESCRIPTION = calendarFilterArrayList.get(n).DESCRIPTION
                                dModel.LOCATION = calendarFilterArrayList.get(n).LOCATION
                                dModel.color = calendarFilterArrayList.get(n).color
                                dModel.type = calendarFilterArrayList.get(n).type
                                calendarDetaiArray.add(dModel)
                                cModel.detailList = calendarDetaiArray
                                mCalendarFinalArrayList.add(cModel)

                            } else {

                                var dModel = CalendarDetailModel()
                                dModel.DTSTART = calendarFilterArrayList.get(n).DTSTART
                                dModel.DTEND = calendarFilterArrayList.get(n).DTEND
                                dModel.SUMMARY = calendarFilterArrayList.get(n).SUMMARY
                                dModel.DESCRIPTION = calendarFilterArrayList.get(n).DESCRIPTION
                                dModel.LOCATION = calendarFilterArrayList.get(n).LOCATION
                                dModel.color = calendarFilterArrayList.get(n).color
                                dModel.type = calendarFilterArrayList.get(n).type
                                mCalendarFinalArrayList.get(pos).detailList.add(dModel)

                            }
                        }
                    }
                    monthLinear.visibility = View.VISIBLE
                    filterLinear.visibility = View.VISIBLE
                    hidePast.visibility = View.VISIBLE
                    Log.e("mfinal size", mCalendarFinalArrayList.size.toString())
                    if (isHide && !isShow) {
                        var calendar = ArrayList<CalendarDateModel>()
                        if (mCalendarFinalArrayList.size > 0) {

                            for (s in 0 until mCalendarFinalArrayList.size) {
                                Log.e(
                                    "S calstart",
                                    mCalendarFinalArrayList.get(s).startDate.toString()
                                )
                                if (mCalendarFinalArrayList.get(s).startDate.length == 19) {
                                    var sdf = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                                    var strDate =
                                        sdf.parse(mCalendarFinalArrayList.get(s).startDate)
                                    if (System.currentTimeMillis() < strDate.time) {

                                        var model = CalendarDateModel()
                                        model.startDate = mCalendarFinalArrayList.get(s).startDate
                                        model.endDate = mCalendarFinalArrayList.get(s).endDate
                                        model.detailList = mCalendarFinalArrayList.get(s).detailList
                                        calendar.add(model)

                                    }
                                } else if (mCalendarFinalArrayList.get(s).startDate.length == 11) {
                                    var sdf = SimpleDateFormat("MMM dd,yyyy")
                                    var strDate =
                                        sdf.parse(mCalendarFinalArrayList.get(s).startDate)
                                    if (System.currentTimeMillis() < strDate.time) {

                                        var model = CalendarDateModel()
                                        model.startDate = mCalendarFinalArrayList.get(s).startDate
                                        model.endDate = mCalendarFinalArrayList.get(s).endDate
                                        model.detailList = mCalendarFinalArrayList.get(s).detailList
                                        calendar.add(model)

                                    }
                                } else if (mCalendarFinalArrayList.get(s).startDate.length == 10) {
                                    var sdf = SimpleDateFormat("yyyy-MM-dd")
                                    var strDate =
                                        sdf.parse(mCalendarFinalArrayList.get(s).startDate)
                                    if (System.currentTimeMillis() < strDate.time) {

                                        var model = CalendarDateModel()
                                        model.startDate = mCalendarFinalArrayList.get(s).startDate
                                        model.endDate = mCalendarFinalArrayList.get(s).endDate
                                        model.detailList = mCalendarFinalArrayList.get(s).detailList
                                        calendar.add(model)

                                    }
                                }

                            }
                        }
                        Log.e("calendar size", calendar.size.toString())
                        if (calendar.size > 0) {
                            Log.e("cal", calendar.size.toString())
                            noEventImage.visibility = View.GONE
                            noEventTxt.visibility = View.GONE
                            calendarRecycler.visibility = View.VISIBLE
                            val calendarListAdapter =
                                CalendarDateAdapter(context, calendar, calendarRecycler)
                            calendarRecycler.adapter = calendarListAdapter
                        } else {
                            noEventImage.visibility = View.VISIBLE
                            noEventTxt.visibility = View.VISIBLE
                            calendarRecycler.visibility = View.GONE
                        }
                    } else {
                        Log.e("else hide SIZE::::", mCalendarFinalArrayList.size.toString())
                        if (mCalendarFinalArrayList.size > 0) {
                            Log.e(
                                "lastdate",
                                mCalendarFinalArrayList[mCalendarFinalArrayList.size - 1].startDate.toString()
                            )
                            noEventImage.visibility = View.GONE
                            noEventTxt.visibility = View.GONE
                            calendarRecycler.visibility = View.VISIBLE
                            val calendarListAdapter = CalendarDateAdapter(
                                context, mCalendarFinalArrayList, calendarRecycler
                            )
                            //   Log.e("::::",mCalendarFinalArrayList[mCalendarFinalArrayList.size].startDate.toString())

                            calendarRecycler.adapter = calendarListAdapter
                        } else {
                            noEventImage.visibility = View.VISIBLE
                            noEventTxt.visibility = View.VISIBLE
                            calendarRecycler.visibility = View.GONE
                        }
                    }


                } else {
                    noEventImage.visibility = View.VISIBLE
                    noEventTxt.visibility = View.VISIBLE
                    calendarRecycler.visibility = View.GONE
                }
            } else {
                noEventImage.visibility = View.VISIBLE
                noEventTxt.visibility = View.VISIBLE
                calendarRecycler.visibility = View.GONE
            }
        } else {
            noEventImage.visibility = View.VISIBLE
            noEventTxt.visibility = View.VISIBLE
            calendarRecycler.visibility = View.GONE
        }
    }

    fun month(month: Int, year: Int) {
        when (month) {
            0 -> {
                monthTxt = "January"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            1 -> {
                monthTxt = "February"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            2 -> {
                monthTxt = "March"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            3 -> {
                monthTxt = "April"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            4 -> {
                monthTxt = "May"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            5 -> {
                monthTxt = "June"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            6 -> {
                monthTxt = "July"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            7 -> {
                monthTxt = "August"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            8 -> {
                monthTxt = "September"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            9 -> {
                monthTxt = "October"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            10 -> {
                monthTxt = "November"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

            11 -> {
                monthTxt = "December"
                monthYearTxt.text = monthTxt + " " + year.toString()
            }

        }
    }

//    fun callCalendarApi() {
//        calendarArrayList = ArrayList()
//        primaryArrayList = ArrayList()
//        secondaryArrayList = ArrayList()
//        wholeSchoolArrayList = ArrayList()
//        progressBarDialog.show()
//
//        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar(
//            "Bearer " + PreferenceManager().getAccessToken(context).toString(),
//            PreferenceManager().getStudent_ID(context).toString(),
//            PreferenceManager().getLanguagetype(mContext).toString()
//        )
//        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
//            override fun onResponse(
//                call: Call<CalendarListModel>, response: Response<CalendarListModel>
//            ) {
//                progressBarDialog.hide()
//                if (response.body()!!.status == 100) {
//                    calendarArrayList.addAll(response.body()!!.calendar)
//
//                    if (calendarArrayList.size > 0) {
//                        for (i in calendarArrayList.indices) {
//                            Log.e(
//                                "title", calendarArrayList.get(i).details.get(0).dTSTART
//                            )
//
//                            if (calendarArrayList.get(i).title.equals("Primary")) {
//                                if (calendarArrayList.get(i).details.size > 0) {
//                                    primaryArrayList.addAll(calendarArrayList.get(i).details)
//                                    Log.e(
//                                        "primary",
//                                        calendarArrayList.get(i).details.get(i).dTSTART.toString()
//                                    )
//                                    Log.e("primaryst", primaryArrayList.get(i).dTSTART.toString())
//                                    // primaryColor = calendarArrayList.get(i).color
//                                }
//
//                            } else if (calendarArrayList.get(i).title.equals("Secondary")) {
//                                if (calendarArrayList.get(i).details.size > 0) {
//
//                                    secondaryArrayList.addAll(calendarArrayList.get(i).details)
//                                    Log.e("Secondary", secondaryArrayList.toString())
//                                    //secondaryColor = calendarArrayList.get(i).color
//                                }
//
//                            } else if (calendarArrayList.get(i).title.equals("WholeSchool")) {
//                                if (calendarArrayList.get(i).details.size > 0) {
//                                    wholeSchoolArrayList.addAll(calendarArrayList.get(i).details)
//                                    Log.e("WholeSchool", wholeSchoolArrayList.toString())
//                                    //wholeSchoole = calendarArrayList.get(i).color
//                                }
//
//                            }
//
//                            isAllSelected = true
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                            showCalendarEvent(
//                                isAllSelected,
//                                isPrimarySelected,
//                                isSecondarySeleted,
//                                isWholeSchoolSelected
//                            )
//                        }
//                        var categoryList = ArrayList<String>()
//                        categoryList.add("Select all/none")
//                        categoryList.add("Primary Event")
//                        categoryList.add("Secondary Event")
//                        categoryList.add("Whole School Event")
//
//                        mTriggerModelArrayList = ArrayList()
//                        for (i in 0..categoryList.size - 1) {
//                            var model = CategoryModel(categoryName, true, "")
//                            model.categoryName = categoryList.get(i)
//                            model.checkedCategory = true
//                            if (i == 0) {
//                                var whiteColor = "#E6E7E8"
//                                model.color = whiteColor
//                            } else {
//
//                                if (i == 1) {
//                                    model.color = primaryColor
//                                }
//                                if (i == 2) {
//                                    model.color = secondaryColor
//                                }
//                                if (i == 3) {
//                                    model.color = wholeSchoole
//
//                                }
//                            }
//
//                            mTriggerModelArrayList.add(model)
//
//                        }
//
//                    } else {
//                        noEventTxt.visibility = View.VISIBLE
//                        noEventTxt.text = "No data"
//
//                    }
//                } else {
//
//                }
//            }
//
//            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
//                progressBarDialog.hide()
//                Toast.makeText(
//                    context, "Fail to get the data..", Toast.LENGTH_SHORT
//                ).show()
//                Log.e("succ", t.message.toString())
//            }
//        })
//
//    }

    fun showFilterMenu(context: Context){
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_calendar_category)
        var iconImageView: ImageView = dialog.findViewById(R.id.iconImageView)
        var checkRecycler: RecyclerView = dialog.findViewById(R.id.checkRecycler)
        var btn_Cancel: TextView = dialog.findViewById(R.id.btn_Cancel)
        var btn_Ok: TextView = dialog.findViewById(R.id.btn_Ok)
        var linearLayoutManagerM: LinearLayoutManager = LinearLayoutManager(mContext)
        checkRecycler.layoutManager = linearLayoutManagerM
        checkRecycler.itemAnimator = DefaultItemAnimator()



        var triggerAdapter = CategoryAdapter(mContext, mTriggerModelArrayList)
        checkRecycler.adapter = triggerAdapter
        btn_Cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        btn_Ok.setOnClickListener(View.OnClickListener {
            isHide = false
            isShow = true
            if (!isHide && isShow) {
                hideBtn.setTextColor(resources.getColor(R.color.black))
                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
            } else {
                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setTextColor(resources.getColor(R.color.black))
                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
                showBtn.setBackgroundColor(resources.getColor(R.color.white))
                // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);

            }
//            showCalendarEvent(
//                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
//            )
            dialog.dismiss()
        })
        checkRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var selectedPosition: Int = 0
//                var triggerAdapter = CategoryAdapter(mContext, mTriggerModelArrayList)
//                checkRecycler.adapter = triggerAdapter

            }
        })
        dialog.show()
    }

//    fun showFilterMenu(context: Context) {
//        val dialog = Dialog(context)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.dialog_calendar_category)
//        var iconImageView: ImageView = dialog.findViewById(R.id.iconImageView)
//        var checkRecycler: RecyclerView = dialog.findViewById(R.id.checkRecycler)
//        var btn_Cancel: TextView = dialog.findViewById(R.id.btn_Cancel)
//        var btn_Ok: TextView = dialog.findViewById(R.id.btn_Ok)
//        var linearLayoutManagerM: LinearLayoutManager = LinearLayoutManager(mContext)
//        checkRecycler.layoutManager = linearLayoutManagerM
//        checkRecycler.itemAnimator = DefaultItemAnimator()
//
//
//
//        var triggerAdapter = CategoryAdapter(mContext, mTriggerModelArrayList)
//        checkRecycler.adapter = triggerAdapter
//        btn_Cancel.setOnClickListener(View.OnClickListener {
//            dialog.dismiss()
//        })
//        btn_Ok.setOnClickListener(View.OnClickListener {
//            isHide = false
//            isShow = true
//            if (!isHide && isShow) {
//                hideBtn.setTextColor(resources.getColor(R.color.black))
//                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
//                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
//            } else {
//                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
//                showBtn.setTextColor(resources.getColor(R.color.black))
//                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
//                showBtn.setBackgroundColor(resources.getColor(R.color.white))
//                // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);
//
//            }
////            showCalendarEvent(
////                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
////            )
//            dialog.dismiss()
//        })
//        checkRecycler.addOnItemClickListener(object : OnItemClickListener {
//            override fun onItemClicked(position: Int, view: View) {
//                var selectedPosition: Int = 0
//                if (position == 0) {
//                    var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
//                    if (pos0) {
//                        isAllSelected = false
//                        isPrimarySelected = false
//                        isSecondarySeleted = false
//                        isWholeSchoolSelected = false
//                        mTriggerModelArrayList.get(0).checkedCategory = false
//                        mTriggerModelArrayList.get(1).checkedCategory = false
//                        mTriggerModelArrayList.get(2).checkedCategory = false
//                        mTriggerModelArrayList.get(3).checkedCategory = false
//                    } else {
//                        isAllSelected = true
//                        isPrimarySelected = true
//                        isSecondarySeleted = true
//                        isWholeSchoolSelected = true
//                        mTriggerModelArrayList.get(0).checkedCategory = true
//                        mTriggerModelArrayList.get(1).checkedCategory = true
//                        mTriggerModelArrayList.get(2).checkedCategory = true
//                        mTriggerModelArrayList.get(3).checkedCategory = true
//                    }
//
//                } else {
//                    if (position == 1) {
//                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
//                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
//                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
//                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
//                        //0000
//                        if (!pos0 && !pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//                        //0001
//                        else if (!pos0 && !pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                        //0010
//                        else if (!pos0 && !pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//                        //0011
//                        else if (!pos0 && !pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = true
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = true
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//                        //0100
//                        else if (!pos0 && pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//                        //0101
//                        else if (!pos0 && pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                        //0110
//                        else if (!pos0 && pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//                        //1111
//                        else if (pos0 && pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                        }
//
//                    } else if (position == 2) {
//                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
//                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
//                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
//                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
//                        //0000
//                        if (!pos0 && !pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//
//                        //0001
//                        else if (!pos0 && !pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                        }
//                        //0010
//                        else if (!pos0 && !pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//                        //0011
//                        else if (!pos0 && !pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                        //0100
//                        else if (!pos0 && pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//
//                        //0101
//                        else if (!pos0 && pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = true
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = true
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                        }
//                        //0110
//                        else if (!pos0 && pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//
//                        //1111
//                        else if (pos0 && pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                    } else if (position == 3) {
//                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
//                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
//                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
//                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
//                        //0000
//                        if (!pos0 && !pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                        //0001
//                        else if (!pos0 && !pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//                        //0010
//                        else if (!pos0 && !pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                        }
//                        //0011
//                        else if (!pos0 && !pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = false
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = false
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//                        //0100
//                        else if (!pos0 && pos1 && !pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = true
//                        }
//                        //0101
//                        else if (!pos0 && pos1 && !pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = false
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = false
//                            isWholeSchoolSelected = false
//                        }
//                        //0110
//                        else if (!pos0 && pos1 && pos2 && !pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = true
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = true
//                            isAllSelected = true
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = true
//                        }
//                        //1111
//                        else if (pos0 && pos1 && pos2 && pos3) {
//                            mTriggerModelArrayList.get(0).checkedCategory = false
//                            mTriggerModelArrayList.get(1).checkedCategory = true
//                            mTriggerModelArrayList.get(2).checkedCategory = true
//                            mTriggerModelArrayList.get(3).checkedCategory = false
//                            isAllSelected = false
//                            isPrimarySelected = true
//                            isSecondarySeleted = true
//                            isWholeSchoolSelected = false
//                        }
//                    }
//                }
//
//                var triggerAdapter = CategoryAdapter(mContext, mTriggerModelArrayList)
//                checkRecycler.adapter = triggerAdapter
//
//            }
//        })
//        dialog.show()
//    }


}