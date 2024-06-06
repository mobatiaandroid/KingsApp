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
import com.kingseducation.app.activities.calender.model.CalendarDateModel
import com.kingseducation.app.activities.calender.model.CalendarDetailModel
import com.kingseducation.app.activities.calender.model.CalendarList
import com.kingseducation.app.activities.calender.model.CalendarListModel
import com.kingseducation.app.activities.calender.model.CalendarOutlookResponseModel
import com.kingseducation.app.activities.calender.model.CalendarResponseArray
import com.kingseducation.app.activities.calender.model.CalendarResponseModel
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
import java.util.Date

class SchoolCalendarActivity : AppCompatActivity() {
    lateinit var mcontext: Context

    // lateinit var jsonConstans: JsonConstants
    //  lateinit var sharedprefs: PreferenceData
    lateinit var calendarRecycler: RecyclerView
    lateinit var titleTextView: TextView
    lateinit var noEventTxt: TextView
    lateinit var noEventImage: ImageView
    lateinit var back: ImageView

    //  lateinit var mContext: Context
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

    // lateinit var TempCALENDARlIST: ArrayList<CalendarDateModel>
    lateinit var FILTERCALENDARlIST: ArrayList<PrimaryModel>

    //val liveArray: ArrayList<CalendarDateModel> = ArrayList()
    lateinit var difference_In_Days: String
    lateinit var progressBarDialog: ProgressBarDialog


    var currentMonth: Int = -1
    lateinit var monthTxt: String
    var primaryColor: String = "#DE000F"
    var secondaryColor: String = "#00458C"
    var wholeSchoole: String = "#C4A105"

    /* var primaryColor: String = ""
     var secondaryColor: String = ""
     var wholeSchoole: String = ""*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calendar_fragment)
        mcontext = this
        initFn()
        if (CommonClass.isInternetAvailable(mcontext)) {
            callCalendarApi()
            callCalendarAPIOutlook()
            //    callAPI()
        } else {
            Toast.makeText(
                mcontext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }


    }

    private fun callCalendarAPIOutlook() {
        progressBarDialog.show()
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(mcontext).toString())
        }
        val call: Call<CalendarOutlookResponseModel> = ApiClient.getApiService().getOutlookCalendar(
            "Bearer " + PreferenceManager().getAccessToken(mcontext).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<CalendarOutlookResponseModel> {
            override fun onResponse(
                call: Call<CalendarOutlookResponseModel>,
                response: Response<CalendarOutlookResponseModel>
            ) {
                calendarArrayList = ArrayList()
            }

            override fun onFailure(call: Call<CalendarOutlookResponseModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    private fun initFn() {
        calendarRecycler = findViewById<RecyclerView>(R.id.calendarRecycler)
        titleTextView = findViewById<TextView>(R.id.titleTextView)
        showBtn = findViewById<TextView>(R.id.showBtn)
        hideBtn = findViewById<TextView>(R.id.hideBtn)
        noEventTxt = findViewById<TextView>(R.id.noEventTxt)
        noEventImage = findViewById<ImageView>(R.id.noEventImage)
        linearLayoutManager = LinearLayoutManager(mcontext)
        calendarRecycler.layoutManager = linearLayoutManager
        calendarRecycler.itemAnimator = DefaultItemAnimator()
        progressBarDialog = ProgressBarDialog(mcontext)
        back = findViewById(R.id.back)

        //progressDialog.visibility = View.VISIBLE
        /* val aniRotate: Animation =
             AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
         progressDialog.startAnimation(aniRotate)*/
        monthYearTxt = findViewById(R.id.monthYearTxt)
        previousBtn = findViewById(R.id.previousBtn)
        nextBtn = findViewById(R.id.nextBtn)
        filterLinear = findViewById(R.id.filterLinear)
        //calendar_relative = view!!.findViewById(R.id.calendar_relative)
        hidePast = findViewById(R.id.hidePast)
        monthLinear = findViewById(R.id.monthLinear)
        year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
        month(currentMonth, year)
        // monthLinear.visibility=View.VISIBLE

        // filterLinear.visibility=View.VISIBLE
        // hidePast.visibility=View.VISIBLE

        back.setOnClickListener {
            val intent = Intent(mcontext, HomeActivity::class.java)
            startActivity(intent)
        }
        filterLinear.setOnClickListener(View.OnClickListener {


            showTriggerDataCollection(mcontext)


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
            // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);
            // showBtn.setTypeface(showBtn.getTypeface(), Typeface.NORMAL);

        }

        nextBtn.setOnClickListener({
            currentMonth = currentMonth + 1
            if (currentMonth > 11) {
                currentMonth = currentMonth - 12
                year = year + 1

            }
            isHide = false
            isShow = true
            if (!isHide && isShow) {
                hideBtn.setTextColor(resources.getColor(R.color.black))
                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
                //   hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.NORMAL);

            } else {
                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setTextColor(resources.getColor(R.color.black))
                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
                showBtn.setBackgroundColor(resources.getColor(R.color.white))
                // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);
                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.NORMAL);

            }
            month(currentMonth, year)
            showCalendarEvent(
                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
            )

        })

        hideBtn.setOnClickListener(View.OnClickListener {
            hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
            showBtn.setTextColor(resources.getColor(R.color.black))
            hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
            showBtn.setBackgroundColor(resources.getColor(R.color.white))

            // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD)
            //  showBtn.setTypeface(showBtn.getTypeface(), R.font.font.)

            isHide = true
            isShow = false
            showCalendarEvent(
                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
            )
        })

        showBtn.setOnClickListener(View.OnClickListener {
            hideBtn.setTextColor(resources.getColor(R.color.black))
            showBtn.setTextColor(resources.getColor(R.color.kings_blue))
            showBtn.setBackgroundResource(R.drawable.edittext_greybg)
            hideBtn.setBackgroundColor(resources.getColor(R.color.white))
            // showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
            // hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.NORMAL);

            isHide = false
            isShow = true
            showCalendarEvent(
                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
            )
        })


        previousBtn.setOnClickListener(View.OnClickListener {
            isHide = false
            isShow = true
            if (!isHide && isShow) {
                hideBtn.setTextColor(resources.getColor(R.color.black))
                showBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setBackgroundResource(R.drawable.edittext_greybg)
                hideBtn.setBackgroundColor(resources.getColor(R.color.white))
                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.BOLD);
                //  hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.NORMAL);


            } else {
                hideBtn.setTextColor(resources.getColor(R.color.kings_blue))
                showBtn.setTextColor(resources.getColor(R.color.black))
                hideBtn.setBackgroundResource(R.drawable.edittext_greybg)
                showBtn.setBackgroundColor(resources.getColor(R.color.white))
                //  hideBtn.setTypeface(hideBtn.getTypeface(), Typeface.BOLD);
                //  showBtn.setTypeface(showBtn.getTypeface(), Typeface.NORMAL);


            }
            if (currentMonth == 0) {
                currentMonth = 11 - currentMonth
                year = year - 1
            } else {
                currentMonth = currentMonth - 1
            }
            month(currentMonth, year)
            showCalendarEvent(
                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
            )

        })
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
                    // var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    // outputFormat.timeZone = tz
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTSTART)
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    val enddate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var outputDateStrend: String = outputFormat.format(enddate)
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
                    Log.e("time", outputDateStrstart)
                    Log.e("time", outputDateStrstart)
                }
                if (primaryArrayList.get(i).dTEND.toString().length == 19) {
                    val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    //var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    // outputFormat.timeZone = tz
                    var result = outputFormat.format(startdate)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTEND = result

                } else if (primaryArrayList.get(i).dTEND.toString().length == 8) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTEND = outputDateStrstart

                } else if (primaryArrayList.get(i).dTEND.toString().length == 15) {
                    val inputFormat: DateFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss")
                    val outputFormat: DateFormat = SimpleDateFormat("MMM dd,yyyy hh:mm a")
                    val startdate: Date = inputFormat.parse(primaryArrayList.get(i).dTEND)
                    var outputDateStrstart: String = outputFormat.format(startdate)
                    pModel.DTEND = outputDateStrstart

                }

                pModel.SUMMARY = primaryArrayList.get(i).sUMMARY.toString()
                // pModel.DESCRIPTION = primaryArrayList.get(i).description
                //pModel.LOCATION = primaryArrayList.get(i).venue
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
                    //  var tz: TimeZone = TimeZone.getTimeZone("GMT+09:30")
                    //  outputFormat.timeZone = tz
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
                sModel.SUMMARY = secondaryArrayList.get(i).sUMMARY.toString()
                //  sModel.DESCRIPTION = secondaryArrayList.get(i).description
                //  sModel.LOCATION = secondaryArrayList.get(i).venue
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
                                CalendarDateAdapter(mcontext, calendar, calendarRecycler)
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
                                mcontext, mCalendarFinalArrayList, calendarRecycler
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

    private fun callAPI() {
        val categoryCalendarArrayList: ArrayList<CalendarResponseModel.Calendar> = ArrayList()
        val call: Call<CalendarResponseModel> = ApiClient.getApiService().schoolcalendarNew(
            "Bearer " + PreferenceManager().getAccessToken(mcontext).toString(),
            PreferenceManager().getStudent_ID(mcontext).toString(),
            PreferenceManager().getLanguagetype(mContext).toString()
        )
        call.enqueue(object : retrofit2.Callback<CalendarResponseModel> {
            override fun onResponse(
                call: Call<CalendarResponseModel>, response: Response<CalendarResponseModel>
            ) {
                if (response != null) {
                    for (i in response.body()!!.calendar!!.indices) {
                        categoryCalendarArrayList.add(response.body()!!.calendar!![i]!!)
                    }
                    for (i in categoryCalendarArrayList.indices) {
                        if (categoryCalendarArrayList[i].title.equals("Primary")) {
                            if (categoryCalendarArrayList[i].details!!.isNotEmpty()) {
                                // primaryArrayList.addAll(categoryCalendarArrayList[i].details)
                                Log.e(
                                    "primary",
                                    categoryCalendarArrayList[i].details!!.size.toString()
                                )
                                //  primaryColor = calendarArrayList.get(i).color
                            }

                        } else if (categoryCalendarArrayList[i].title.equals("Secondary")) {
                            if (categoryCalendarArrayList[i].details!!.isNotEmpty()) {

                                Log.e(
                                    "Secondary",
                                    categoryCalendarArrayList[i].details!!.size.toString()
                                )
                                //  secondaryColor = calendarArrayList.get(i).color
                            }

                        } else if (categoryCalendarArrayList[i].title.equals("WholeSchool")) {
                            if (categoryCalendarArrayList[i].details!!.isNotEmpty()) {
                                Log.e(
                                    "WholeSchool",
                                    categoryCalendarArrayList[i].details!!.size.toString()
                                )
                                //  wholeSchoole = calendarArrayList.get(i).color
                            }

                        }

                    }


                }
            }

            override fun onFailure(call: Call<CalendarResponseModel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun callCalendarApi() {
        calendarArrayList = ArrayList()
        primaryArrayList = ArrayList()
        secondaryArrayList = ArrayList()
        wholeSchoolArrayList = ArrayList()
        progressBarDialog.show()

//        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar(
//            "Bearer " + PreferenceManager().getAccessToken(mcontext).toString(),
//            PreferenceManager().getStudent_ID(mcontext).toString(),
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
//                                "title", calendarArrayList.get(i).details.get(0).dTSTART.toString()
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
//                    mcontext, "Fail to get the data..", Toast.LENGTH_SHORT
//                ).show()
//                Log.e("succ", t.message.toString())
//            }
//        })

    }

    fun showTriggerDataCollection(context: Context) {
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
        // iconImageView.setBackgroundResource(bgIcon)
        //iconImageView.setImageResource(ico)

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
            showCalendarEvent(
                isAllSelected, isPrimarySelected, isSecondarySeleted, isWholeSchoolSelected
            )
            dialog.dismiss()
        })
        checkRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var selectedPosition: Int = 0
                if (position == 0) {
                    var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
                    if (pos0) {
                        isAllSelected = false
                        isPrimarySelected = false
                        isSecondarySeleted = false
                        isWholeSchoolSelected = false
                        mTriggerModelArrayList.get(0).checkedCategory = false
                        mTriggerModelArrayList.get(1).checkedCategory = false
                        mTriggerModelArrayList.get(2).checkedCategory = false
                        mTriggerModelArrayList.get(3).checkedCategory = false
                    } else {
                        isAllSelected = true
                        isPrimarySelected = true
                        isSecondarySeleted = true
                        isWholeSchoolSelected = true
                        mTriggerModelArrayList.get(0).checkedCategory = true
                        mTriggerModelArrayList.get(1).checkedCategory = true
                        mTriggerModelArrayList.get(2).checkedCategory = true
                        mTriggerModelArrayList.get(3).checkedCategory = true
                    }

                } else {
                    if (position == 1) {
                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
                        //0000
                        if (!pos0 && !pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }
                        //0001
                        else if (!pos0 && !pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                        //0010
                        else if (!pos0 && !pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }
                        //0011
                        else if (!pos0 && !pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = true
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = true
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }
                        //0100
                        else if (!pos0 && pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }
                        //0101
                        else if (!pos0 && pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                        //0110
                        else if (!pos0 && pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }
                        //1111
                        else if (pos0 && pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = true
                        }

                    } else if (position == 2) {
                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
                        //0000
                        if (!pos0 && !pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }

                        //0001
                        else if (!pos0 && !pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = true
                        }
                        //0010
                        else if (!pos0 && !pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }
                        //0011
                        else if (!pos0 && !pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                        //0100
                        else if (!pos0 && pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }

                        //0101
                        else if (!pos0 && pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = true
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = true
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = true
                        }
                        //0110
                        else if (!pos0 && pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }

                        //1111
                        else if (pos0 && pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                    } else if (position == 3) {
                        var pos0: Boolean = mTriggerModelArrayList.get(0).checkedCategory
                        var pos1: Boolean = mTriggerModelArrayList.get(1).checkedCategory
                        var pos2: Boolean = mTriggerModelArrayList.get(2).checkedCategory
                        var pos3: Boolean = mTriggerModelArrayList.get(3).checkedCategory
                        //0000
                        if (!pos0 && !pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                        //0001
                        else if (!pos0 && !pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }
                        //0010
                        else if (!pos0 && !pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = true
                        }
                        //0011
                        else if (!pos0 && !pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = false
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = false
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }
                        //0100
                        else if (!pos0 && pos1 && !pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = true
                        }
                        //0101
                        else if (!pos0 && pos1 && !pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = false
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = false
                            isWholeSchoolSelected = false
                        }
                        //0110
                        else if (!pos0 && pos1 && pos2 && !pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = true
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = true
                            isAllSelected = true
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = true
                        }
                        //1111
                        else if (pos0 && pos1 && pos2 && pos3) {
                            mTriggerModelArrayList.get(0).checkedCategory = false
                            mTriggerModelArrayList.get(1).checkedCategory = true
                            mTriggerModelArrayList.get(2).checkedCategory = true
                            mTriggerModelArrayList.get(3).checkedCategory = false
                            isAllSelected = false
                            isPrimarySelected = true
                            isSecondarySeleted = true
                            isWholeSchoolSelected = false
                        }
                    }
                }

                var triggerAdapter = CategoryAdapter(mContext, mTriggerModelArrayList)
                checkRecycler.adapter = triggerAdapter

            }
        })
        dialog.show()
    }
}


/*
class SchoolCalendarActivity:AppCompatActivity() {
    lateinit var mcontext: Context
    lateinit var header:TextView
    lateinit var arrow_prev:ImageView
    lateinit var arrow_nxt:ImageView
    lateinit var monthlist:Array<String>
    lateinit var week_day:Array<String>
    lateinit var nums_Array:ArrayList<String>
   // private lateinit var progressDialog: RelativeLayout
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
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_school_calendar_layout)
        mcontext = this
        initFn()
        headerfnc()
        daysinweek()
        holiday()

        //callCalendarListMonth()
       // callCalendarList()
       // onclick()
    }

    private fun callCalendarList() {
        progressBarDialog.show()
        mEventArrayListYear= ArrayList()
        Log.e("callcal", PreferenceManager().getStudent_ID(mcontext).toString())
        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    mEventArrayListYear.addAll(response.body()!!.calendar)
                    Log.e("mEventArrayListYear", mEventArrayListYear.toString())
for(i in mEventArrayListYear.indices) {
    datesToPlot.add(mEventArrayListYear.get(i).start_date)
    PreferenceManager().saveArrayList(mcontext,datesToPlot)
    Log.e("datesToPlot2", PreferenceManager().getArrayList(mcontext).toString())
    holiday()
    val date: Date
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    date = sdf.parse(mEventArrayListYear.get(i).start_date);

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


    //filterWeekArray(monthNumber,dayOfTheWeek,days,monthString,year,mEventArrayListYear)
    //
}
                    filterYearlist(mEventArrayListYear)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                progressBarDialog.hide()
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
                            Log.e("dayscall",mEventArrayListYear.get(j).dayss)
                            if(dd[i].equals(mEventArrayListYear.get(j).dayss)&& dayOfTheWeek[i].equals(mEventArrayListYear.get(j).dayOfTheWeekk)&&
                                month[i].equals(mEventArrayListYear.get(j).monthNumber)&&
                                year[i].equals(mEventArrayListYear.get(j).yearr))
                            {

                                mEventArrayListFilterListYear.add(mEventArrayListYear.get(j))


                            }

                        }

                    }
        Log.e("empty1", mEventArrayListFilterListYear.size.toString())
        if(mEventArrayListFilterListYear.size==0)
        {
            Log.e("empty", mEventArrayListFilterListYear.size.toString())
            list.visibility=View.GONE
            emptyImg.visibility=View.VISIBLE
        }
        else
        {
            emptyImg.visibility=View.GONE
            list.visibility=View.VISIBLE
            Log.e("arrayinside", mEventArrayListFilterListYear.size.toString())
            list!!.layoutManager = LinearLayoutManager(mcontext)
            val studentlist_adapter =
                CustomLisAdapter(mcontext, mEventArrayListFilterListYear)
            list!!.adapter = studentlist_adapter
            Log.e("array", mEventArrayListFilterListYear.size.toString())
        }


    }

    private fun callCalendarListMonth() {
        progressBarDialog.show()
        mEventArrayListYear= ArrayList()
        Log.e("callcalList", PreferenceManager().getStudent_ID(mcontext).toString())

        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendarList("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))

                {
                    mEventArrayListYear.addAll(response.body()!!.calendar)
                    //  Log.e("ArrayAudioFile1", String.valueOf(audiofile.get(0)));
                    //   Log.e("ArrayAudioFile2", String.valueOf(audiofile.get(1)));

                    */
/************************ end of end date new *//*

                    if (mEventArrayListYear.size>0)
                    {
                    for (i in mEventArrayListYear.indices )
                    {
                        datesToPlot.add(mEventArrayListYear.get(i).start_date)
                        PreferenceManager().saveArrayList(mcontext,datesToPlot)
                        Log.e("datesToPlot1", PreferenceManager().getArrayList(mcontext).toString())
                        holiday()
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
                        callfiltermonthApi()
                        *//*list.visibility=View.GONE
                        emptyImg.visibility=View.VISIBLE*//*

                    }
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                }
                }
                else{
                    val intent = Intent(mcontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                progressBarDialog.hide()
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

    private fun callfiltermonthApi() {
        progressBarDialog.show()
        mEventArrayListYear= ArrayList()
        Log.e("callcalList", PreferenceManager().getStudent_ID(mcontext).toString())

        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100))

                    {
                        mEventArrayListYear.addAll(response.body()!!.calendar)
                        //  Log.e("ArrayAudioFile1", String.valueOf(audiofile.get(0)));
                        //   Log.e("ArrayAudioFile2", String.valueOf(audiofile.get(1)));

                        */
/************************ end of end date new *//*

                        if (mEventArrayListYear.size>0)
                        {
                            for (i in mEventArrayListYear.indices )
                            {
                                datesToPlot.add(mEventArrayListYear.get(i).start_date)
                                PreferenceManager().saveArrayList(mcontext,datesToPlot)
                                Log.e("datesToPlot2", PreferenceManager().getArrayList(mcontext).toString())
                                holiday()
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
                            list.visibility=View.GONE
                            emptyImg.visibility=View.VISIBLE
                        }
                    }
                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                    }
                }
                else{
                    val intent = Intent(mcontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                progressBarDialog.hide()
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
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            var month= Calendar.getInstance()

            var simpleDateFormat = SimpleDateFormat("MM", Locale("ar"))
            var s=SimpleDateFormat("MMMM", Locale("ar"))
            var month_tt=s.format(month.time)
            var dateMonth = simpleDateFormat.format(month.time).toString()
            var simpleDateFormat2 = SimpleDateFormat("yyyy", Locale("ar"))
            var dateYear = simpleDateFormat2.format(month.time).toString()
            count_month=dateMonth.toInt()-1
            count_year=dateYear.toInt()
            header.text = month_tt +" " +count_year
            Log.e("month",month_tt)
            Log.e("count_year", count_year.toString())

            //header.text=PreferenceManager().getMonthSelected(mcontext)
            Log.e("count_month", count_month.toString())
        }
        else
        {
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
            Log.e("month",month_tt)
            Log.e("count_year", count_year.toString())

            //header.text=PreferenceManager().getMonthSelected(mcontext)
            Log.e("count_month", count_month.toString())
        }

        //PreferenceManager().setMonthSelected(mcontext,month_tt)

        arrow_prev.setOnClickListener() {
            nums_Array = ArrayList()
            if (count_month != 0) {
                count_month = count_month!! - 1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                txtMYW.setText(header.text)
                daysinweek()
                setTextview()
                holiday()
                if(count_month==10)
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    Log.e("new", count1.toString())
                   // filtermonthlist(count1.toString(),mEventArrayListYear)
                    callCalendeyearApi(count1.toString(),count_year.toString())

                  //
                    //filterYearlist(mEventArrayListFilterListMonth)
                }
                else if(count_month==9)
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    Log.e("new", count1.toString())
                    callCalendeyearApi(count1.toString(),count_year.toString())

                   // filterYearmonthList(count1.toString(),mEventArrayListYear)
                    //  filtermonthlist(count1.toString(),mEventArrayListYear)
                    //filterYearlist(mEventArrayListFilterListMonth)
                }

                else
                {
                    Log.e("countmont", count_month.toString())
                    Log.e("m", m.toString())
                    val count1=count_month!!+1
                    val count= "0"+count1
                    Log.e("new", count.toString())
                    callCalendeyearApi(count.toString(),count_year.toString())

                    //filterYearmonthList(count1.toString(),mEventArrayListYear)
                    //filtermonthlist(count.toString(),mEventArrayListYear)
                    //filterYearlist(mEventArrayListFilterListMonth)
                }

                //  holiday()
            } else {
                count_year = count_year!! - 1
                val m = monthlist[11]
                count_month = 11
                header.text = m + count_year
                txtMYW.setText(header.text)
                daysinweek()
                setTextview()
                holiday()

                //  holiday()
            }
        }
        arrow_nxt.setOnClickListener() {

            nums_Array = ArrayList()
            if (count_month != 11) {
                count_month = count_month!! + 1
                val m = monthlist[count_month!!]
                header.text = m + count_year
                txtMYW.setText(header.text)
                daysinweek()
                setTextview()
                holiday()
                Log.e("countmont", count_month.toString())
                Log.e("m", m.toString())
                val count1=count_month!!+1
                val count= "0"+count1
                Log.e("new", count.toString())
                callCalendeyearApi(count.toString(),count_year.toString())
               // filterYearmonthList(count1.toString(),mEventArrayListYear)
                // filtermonthlist(count.toString(),mEventArrayListYear)
                //filterYearlist(mEventArrayListFilterListMonth)
                // holiday()
            } else {
                count_year = count_year!! + 1
                val m = monthlist[0]
                count_month = 0
                header.text = m + count_year
                txtMYW.setText(header.text)
                daysinweek()
                setTextview()
                holiday()
                //filtermonthlist(count.toString(),mEventArrayListYear)
                val count1=count_month!!+1
                val count= "0"+count1
                Log.e("new", count.toString())
                callCalendeyearApi(count.toString(),count_year.toString())
                //filterYearmonthList(count1.toString(),mEventArrayListYear)

                // filtermonthlist(count.toString(),mEventArrayListFilterListMonth)
                // holiday()
            }
        }
    }

    private fun filterYearmonthList(month: String, mEventArrayListYear: ArrayList<CalendarList>,count_year:String) {
        mEventArrayListFilterMonth= ArrayList()
        Log.e("Success", "Success")
        Log.e("Monthdate", mEventArrayListYear.toString())
        Log.e("size", mEventArrayListYear.size.toString())
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
            val str2=monthdate1[0]
            Log.e("str1", str1.toString())
            Log.e("str2", str2.toString())
            Log.e("count_year", count_year.toString())
            if(str1.equals(month)&&str2.equals(count_year))
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
            *//*if(month.equals(mEventArrayListFilterListMonth.get(i).start_date))
            mEventArrayListFilterMonth.addAll()*//*

            Log.e("array2", mEventArrayListFilterMonth.toString())
        }
        if(mEventArrayListFilterMonth.size==0)
        {
            Log.e("empty","empty")
            list.visibility=View.GONE
            emptyImg.visibility=View.VISIBLE
        }
        else
        {
            Log.e("full","full")
            list.visibility=View.VISIBLE
            emptyImg.visibility=View.GONE
            val studentlist_adapter =
                CustomLisAdapter(mcontext, mEventArrayListFilterMonth)
            list!!.adapter = studentlist_adapter
        }

    }

    private fun callCalendeyearApi(month:String,count_year:String) {
        Log.e("monthnumbernew",month)
        progressBarDialog.show()
        mEventArrayListYear= ArrayList()
        Log.e("callcal", PreferenceManager().getStudent_ID(mcontext).toString())
        val call: Call<CalendarListModel> = ApiClient.getApiService().schoolcalendar("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<CalendarListModel> {
            override fun onResponse(
                call: Call<CalendarListModel>,
                response: Response<CalendarListModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100))
                    {
                        mEventArrayListYear.addAll(response.body()!!.calendar)
                        Log.e("mEventArrayListYear", mEventArrayListYear.toString())
                        for(i in mEventArrayListYear.indices) {
                            val date: Date
                            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

                            date = sdf.parse(mEventArrayListYear.get(i).start_date);

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


                            //filterWeekArray(monthNumber,dayOfTheWeek,days,monthString,year,mEventArrayListYear)
                            //
                        }
                        filterYearmonthList(month,mEventArrayListYear,count_year)
                    }
                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                    }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CalendarListModel?>, t: Throwable) {
                progressBarDialog.hide()
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

    private fun initFn() {
        nums_Array= ArrayList()
        mEventArrayListYear= ArrayList()
        mListViewArray= ArrayList()
        mEventArrayListFilterListMonth = ArrayList()
        mEventArrayListFilterMonth = ArrayList()
        mEventArrayListFilterListYear = ArrayList()
        datesToPlot = ArrayList()


        var modell= ListViewSpinnerModel(getResources().getString(R.string.yearview)," ",",")
        mListViewArray.add(modell)
        var xmodel= ListViewSpinnerModel(getResources().getString(R.string.monthview)," "," ")
        mListViewArray.add(xmodel)
        var nmodel= ListViewSpinnerModel(getResources().getString(R.string.weekview)," "," ")
        mListViewArray.add(nmodel)
        progressBarDialog = ProgressBarDialog(mcontext)
       // progressDialog = findViewById(R.id.progressDialog)
        list = findViewById<RecyclerView>(R.id.mEventList)
        txtMYW=findViewById(R.id.txtMYW)
        txtMYW.setText(getResources().getString(R.string.thismonth))
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

        textView=findViewById(R.id.textView)
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mContext.getAssets(), "font/times_new_roman.ttf")
            textView.setTypeface(face);
        }
        monthlist=getResources().getStringArray(R.array.Months)
        week_day=getResources().getStringArray(R.array.Weeks)
        setTextview()

        if (PreferenceManager().getFromYearView(mcontext).equals("1")){
            Log.e("year","1")
             callCalendarList()

        }else{
            Log.e("year","0")
            callfiltermonthApi()

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
                    txtMYW.setText(getResources().getString(R.string.thismonth))
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinner.setText(getResources().getString(R.string.monthview))

                    callCalendarListMonth()

                } else if (position == 2) {
                    txtMYW.setText(getResources().getString(R.string.thisweek))
                    listSpinner.setVisibility(
                        View.GONE
                    )




                   *//* val formatdd = SimpleDateFormat("dd", Locale.ENGLISH)
                    val formatMMM = SimpleDateFormat("MMM", Locale.ENGLISH)
                    val c = Calendar.getInstance().getTime()
                    val monthNumber = formatMM.format(c)
                    val dayOfTheWeek1 = formatEEE.format(c) // Thu
                    val days1 = formatdd.format(c) // 20
                    val monthString = formatMMM.format(c) // Jun
                    val year1 = formatyyyy.format(c)
                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("monthNumber", monthNumber.toString())
                    Log.e("currentdate", c.toString())*//*



                    filterWeekArray()

                    daySpinner.setText(getResources().getString(R.string.weekview))
                   // emptyImg.visibility=View.GONE

                   *//* list!!.layoutManager = LinearLayoutManager(mcontext)
                    val studentlist_adapter =
                        CustomLisAdapter(mcontext, mEventArrayListFilterList)
                    list!!.adapter = studentlist_adapter*//*


                } else if (position == 0) {
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinner.setText(getResources().getString(R.string.yearview))

                    val intent = Intent(mcontext, CalendarYearActivity::class.java)
                  //  intent.putExtra("BUNDLE", AppController().datesToPlot as Serializable)
                    startActivity(intent)


                }
                if (daySpinSelect == false) {
                    listSpinner.setVisibility(
                        View.GONE
                    )
                    daySpinSelect = true
                }
            }
        daySpinner.setOnClickListener {

            if (daySpinSelect==true)
            {
                Log.e("true","click")
                listSpinner.visibility=View.VISIBLE
                daySpinSelect = false
            }
            else{
                Log.e("false","click")
                listSpinner.visibility=View.GONE
                daySpinSelect = true

            }
        }

        list.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
Log.e("detailarray",mEventArrayListFilterMonth.size.toString())
Log.e("detailarray",mEventArrayListFilterMonth[position].title)
                val intent = Intent(mcontext, CalendarDetailsActivity::class.java)
                intent.putExtra("tittle", mEventArrayListFilterMonth.get(position).title)
                intent.putExtra("des", mEventArrayListFilterMonth.get(position).description)
                intent.putExtra("startdate", mEventArrayListFilterMonth.get(position).start_date)
                intent.putExtra("enddate", mEventArrayListFilterMonth.get(position).end_date)
                intent.putExtra("venu", mEventArrayListFilterMonth.get(position).venue)

                startActivity(intent)
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
            *//*if(month.equals(mEventArrayListFilterListMonth.get(i).start_date))
            mEventArrayListFilterMonth.addAll()*//*

        }
        if(mEventArrayListFilterMonth.size==0)
        {
            list.visibility=View.GONE
            emptyImg.visibility=View.VISIBLE
        }
        else
        {
            list.visibility=View.VISIBLE
            emptyImg.visibility=View.GONE
            val studentlist_adapter =
                CustomLisAdapter(mcontext, mEventArrayListFilterMonth)
            list!!.adapter = studentlist_adapter
        }

    }

    fun filterYearlist(mEventArrayListYear: ArrayList<CalendarList>)
    {
        mEventArrayListFilterMonth= ArrayList()
        val Monthname=intent.getStringExtra("monthName")
        var dateyear:String="2023"
       // header.text = Monthname+" " +dateyear
        for(j in mEventArrayListYear.indices)
        {

            val monthdate:String
            val monthNumber=intent.getStringExtra("monthNumber")
            var monthName=intent.getStringExtra("monthName")
            var yearValue=intent.getStringExtra("yearValue")

            Log.e("Yearmonth", monthNumber.toString())
            Log.e("yearValue", yearValue.toString())
            txtMYW.setText(monthName+"  " +yearValue)
            header.text =monthName+"  " +yearValue
            monthdate=mEventArrayListYear.get(j).start_date
            Log.e("Monthdate",monthdate)
            val monthdate1=monthdate.split("-")
            val str1:String=monthdate1[1]
            val str2:String=monthdate1[0]
            Log.e("str1", str1.toString())
            Log.e("str2", str2.toString())
            if(str1.equals(monthNumber)&&str2.equals("2023"))
            {

                Log.e("Success",str1)
                Log.e("Success",monthNumber)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterMonth.add(mEventArrayListYear.get(j))
                Log.e("mEventArrayListFilterListYear if", mEventArrayListFilterMonth.toString())


            }
           else if(str1.equals(monthNumber)&&str2.equals("2022"))
            {
                Log.e("Success",str1)
                Log.e("Success",monthNumber)
                list.visibility=View.VISIBLE
                emptyImg.visibility=View.GONE
                mEventArrayListFilterMonth.add(mEventArrayListYear.get(j))
                Log.e("mEventArrayListFilterListYear elseif", mEventArrayListFilterMonth.toString())
            }


        }
        if(mEventArrayListFilterMonth.size==0)
        {
            list.visibility=View.GONE
            emptyImg.visibility=View.VISIBLE
        }
        else
        {
            val studentlist_adapter =
                CustomLisAdapter(mcontext, mEventArrayListFilterMonth)
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
                    if (PreferenceManager().getLanguage(mContext).equals("ar")) {
                        //calendararabictextview(value)
                        if(value.equals("1"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            dateTextView[i]!!.text = str.toString()

                        }

                       else if(value.equals("2"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            dateTextView[i]!!.text = str.toString()

                        }
                        else if(value.equals("3"))
                        {
                            val str:String=getResources().getString(R.string.three)
                            dateTextView[i]!!.text = str.toString()

                        }else if(value.equals("4"))
                        {
                            val str:String=getResources().getString(R.string.four)
                            dateTextView[i]!!.text = str.toString()

                        }else if(value.equals("5"))
                        {
                            val str:String=getResources().getString(R.string.five)
                            dateTextView[i]!!.text = str.toString()

                        }else if(value.equals("6"))
                        {
                            val str:String=getResources().getString(R.string.six)
                            dateTextView[i]!!.text = str.toString()

                        }else if(value.equals("7"))
                        {
                            val str:String=getResources().getString(R.string.seven)
                            dateTextView[i]!!.text = str.toString()

                        }else if(value.equals("8"))
                        {
                            val str:String=getResources().getString(R.string.eight)
                            dateTextView[i]!!.text = str.toString()

                        }
                        else if(value.equals("9"))
                        {
                            val str:String=getResources().getString(R.string.nine)
                            dateTextView[i]!!.text = str.toString()

                        }
                        else if(value.equals("10"))
                        {
                            val str:String=getResources().getString(R.string.ten)
                            dateTextView[i]!!.text = str.toString()

                        }
                        else if(value.equals("11"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.one)

                            dateTextView[i]!!.text = str.toString()+str1

                        }

                        else if(value.equals("12"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.tw0)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("13"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.three)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("14"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.four)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("15"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.five)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("16"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.six)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("17"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.seven)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("18"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.eight)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("19"))
                        {
                            val str:String=getResources().getString(R.string.one)
                            val str1:String=getResources().getString(R.string.nine)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("20"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.zero)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("21"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.one)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("22"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.tw0)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("23"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.three)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("24"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.four)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("25"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.five)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("26"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.six)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("27"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.seven)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("28"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.eight)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("29"))
                        {
                            val str:String=getResources().getString(R.string.tw0)
                            val str1:String=getResources().getString(R.string.nine)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("30"))
                        {
                            val str:String=getResources().getString(R.string.three)
                            val str1:String=getResources().getString(R.string.zero)

                            dateTextView[i]!!.text = str.toString()+str1

                        }else if(value.equals("31"))
                        {
                            val str:String=getResources().getString(R.string.three)
                            val str1:String=getResources().getString(R.string.one)

                            dateTextView[i]!!.text = str.toString()+str1

                        }
                    }
                    else
                    {
                        dateTextView[i]!!.text = value.toString()

                    }
                }
            } else {
                dateTextView[i]!!.visibility = View.INVISIBLE
            }
        }
    }
   *//* private fun calendararabictextview(value: String)
    {
        if(value.equals("1"))
        {
            val str:String=getResources().getString(R.string.one)
            dateTextView[i]!!.text = value.toString()

        }

    }*//*

    private fun setTextview() {
        for (i in 0..41) {
            val resID: Int =
                mcontext.resources.getIdentifier("textview_$i", "id", mcontext.packageName)
            Log.e("textview",resID.toString())
            Log.e("i", i.toString())
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
    private fun holiday(){
Log.e("datestoplot",datesToPlot.size.toString())
        for (i in 0..datesToPlot.size-1){
            var days_s=datesToPlot.get(i)
            Log.e("days_s",days_s)
            val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-M-d")
            val inputDateStr = days_s
            val date: Date = inputFormat.parse(inputDateStr)
            val outputDateStr: String = outputFormat.format(date)
            Log.e("days_sformat",outputDateStr)
            for (i in 0..nums_Array.size-1) {

                Log.e("td", month_total_days.toString())
                var c_day=nums_Array.get(i)
                var c_month= count_month!! +1
                var c_year=count_year
                var c_date=c_year.toString() + "-"+c_month+"-"+c_day
                //var c_date=c_day+"/"+c_month+"/"+c_year
                Log.e("c_date",c_date)

                if (outputDateStr==c_date){

                    Log.e("match","match")
                    dateTextView[i]!!.setBackgroundResource(R.drawable.circle_calendar)

                }

                else{
                    //Log.e("no_match","no_match")

                }
            }
        }
    }

}*/
