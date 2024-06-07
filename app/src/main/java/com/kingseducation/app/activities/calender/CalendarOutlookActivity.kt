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
                val categoryModel = CategoryModel()
                categoryModel.color = ""
                categoryModel.categoryName = "All"
                categoryModel.checkedCategory = true
                mTriggerModelArrayList.add(categoryModel)
                for (item in categorySortList) {
                    val categoryModel = CategoryModel()
                    categoryModel.color = ""
                    categoryModel.categoryName = item
                    categoryModel.checkedCategory = true
                    mTriggerModelArrayList.add(categoryModel)
                }
                Log.e("mTriggerModelArrayList", mTriggerModelArrayList[0].categoryName.toString())
                showCalendar(calendarItems, mTriggerModelArrayList)


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
        categorySortList: ArrayList<CategoryModel>
    ) {
        displayEventsForCurrentMonth()
    }

    private fun displayEventsForCurrentMonth() {
        val filteredEvents =
            filterEventsByMonth(calendarItems, mTriggerModelArrayList, currentYear, currentMonthValue)
        // Code to display the events (e.g., update UI components)
        for (i in filteredEvents) {
            Log.e("fileters", i.key.toString() + i.value.size.toString())
        }
        filteredEvents.forEach { println(it.value.size.toString() + "" + it.key) }
        calendarRecycler.visibility = View.VISIBLE
        val originalMap: Map<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>> =
            filteredEvents
        val reversedMap = originalMap.entries.reversed().associate { it.key to it.value }
        val calendarListAdapter = CalendarOutlookAdapter(
            context, reversedMap, calendarRecycler
        )
        calendarRecycler.adapter = calendarListAdapter
        updateMonthYearTextView(monthYearTxt, currentYear, currentMonthValue)
    }

    private fun <K, V> reverseMap(originalMap: Map<K, V>): Map<K, V> {
        val reversedMap: MutableMap<K, V> = LinkedHashMap()
        for ((key, value) in originalMap) {
            reversedMap[key] = value
        }
        return reversedMap
    }

    private fun updateMonthYearTextView(textView: TextView, year: Int, month: Int) {
        val monthName =
            LocalDate.of(year, month, 1).month.getDisplayName(TextStyle.FULL, Locale.getDefault())
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
        categories: ArrayList<CategoryModel>,
        year: Int,
        month: Int
    ): Map<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>> {
        val selectedCategoriesSet = categories.filter { it.checkedCategory }.map { it.categoryName }.toSet()

        val filteredEvents = events.filter { event ->
            val eventDate = LocalDate.parse(
                event.start.dateTime.substring(0, 10),
                DateTimeFormatter.ISO_LOCAL_DATE
            )
            event.categories.any { it in selectedCategoriesSet } &&
                    eventDate.year == year &&
                    eventDate.monthValue == month
        }

        val groupedEvents =
            HashMap<LocalDate, ArrayList<CalendarOutlookResponseModel.CalendarEvent>>()
        for (event in filteredEvents) {
            val eventDate = LocalDate.parse(
                event.start.dateTime.substring(0, 10),
                DateTimeFormatter.ISO_LOCAL_DATE
            )
            if (groupedEvents.containsKey(eventDate)) {
                groupedEvents[eventDate]?.add(event)
            } else {
                groupedEvents[eventDate] = arrayListOf(event)
            }
        }

        return groupedEvents
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


    fun showFilterMenu(context: Context) {
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
                if (position == 0) {
                    var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
                    if (pos0) {
                        for (i in 0..mTriggerModelArrayList.size - 1) {
                            mTriggerModelArrayList.get(i).checkedCategory = false
                        }
                        triggerAdapter.notifyDataSetChanged()

                    } else {
                        for (i in 0..mTriggerModelArrayList.size - 1) {
                            mTriggerModelArrayList.get(i).checkedCategory = true
                        }
                        triggerAdapter.notifyDataSetChanged()

                    }
                } else {
                    if (mTriggerModelArrayList.get(position).checkedCategory) {
                        mTriggerModelArrayList.get(position).checkedCategory = false
                    } else {
                        mTriggerModelArrayList.get(position).checkedCategory = true
                    }
                    triggerAdapter.notifyDataSetChanged()
                }

            }
        })
        btn_Cancel.setOnClickListener {
            dialog.dismiss()
        }
        btn_Ok.setOnClickListener {
            dialog.dismiss()

            displayEventsForCurrentMonth()
        }

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