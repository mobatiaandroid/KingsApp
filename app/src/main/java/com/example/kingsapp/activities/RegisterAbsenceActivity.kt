package com.example.kingsapp.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.manager.AppUtils
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


lateinit var firstdayofabsence : TextInputEditText
lateinit var returnabsence : TextInputEditText
lateinit var reason_for_absence : TextInputEditText
lateinit var reasonforabsence : TextInputLayout
lateinit var first_day_of_absencetext : TextInputLayout
lateinit var return_absence_text : TextInputLayout
lateinit var studentSpinner : LinearLayout
lateinit var context: Context
lateinit var studentName:TextView


var c: Calendar? = null
var mYear = 0
var mMonth = 0
var mDay = 0
var df: SimpleDateFormat? = null
var formattedDate: String? = null
var calendar: Calendar? = null
var fromDate = ""
var toDate:String? = ""
var tomorrowAsString: String? = null
lateinit var sdate: Date
lateinit var edate:Date
var elapsedDays: Long = 0
var outputFormats: SimpleDateFormat? = null
lateinit var student_name:ArrayList<Studentlist_model>
lateinit var backarrow_registerabsence : ImageView

class RegisterAbsenceActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.register_absence_layout)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        context = this
        initFn()
    }

    @SuppressLint("ResourceAsColor")
    private fun initFn() {

        student_name = ArrayList()
        var model= Studentlist_model("Jane Mathewes",false)
        student_name.add(model)
        var xmodel= Studentlist_model("Esther Mathews",false)
        student_name.add(xmodel)
        var nmodel= Studentlist_model("Gay Mathewes",false)
        student_name.add(nmodel)
        var emodel= Studentlist_model("Jane Mathewes",false)
        student_name.add(emodel)
        first_day_of_absencetext = findViewById(R.id.first_day_of_absencetext)
        return_absence_text = findViewById(R.id.return_absence_text)
      //  reasonforabsence = findViewById(R.id.reasonforabsence)
        reason_for_absence = findViewById(R.id.reason_for_absence)
        firstdayofabsence = findViewById(R.id.firstdayofabsence)
        returnabsence = findViewById(R.id.returnabsence)
        studentSpinner = findViewById(R.id.studentSpinner)
        studentName = findViewById(R.id.studentName)
        backarrow_registerabsence = findViewById(R.id.backarrow_registerabsence)
        calendar = Calendar.getInstance()
        outputFormats = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))

        if (PreferenceManager().getLanguage(context).equals("ar"))

        {
            first_day_of_absencetext.setStartIconDrawable(R.drawable.ic_baseline_calendar_today_24);
            return_absence_text.setStartIconDrawable(R.drawable.ic_baseline_calendar_today_24);

          //  reasonforabsence.gravity= Gravity.LEFT
           // reason_for_absence.gravity= Gravity.RIGHT

        }
        else
        {
            first_day_of_absencetext.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24);
            return_absence_text.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24);

        }
        /*else
        {
            reason_for_absence.gravity = Gravity.LEFT
        }*/
        backarrow_registerabsence.setOnClickListener {
            val intent = Intent(context, AbsenceActivity::class.java)
            startActivity(intent)
        }
        studentSpinner.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            studentlist_popup(student_name)
        }
        firstdayofabsence.setOnClickListener {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.calender_activity_example)
            dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

            var date_view = dialog.findViewById<TextView>(R.id.date_view)!! as TextView
            var calendar = dialog.findViewById<CalendarView>(R.id.calendar)!! as CalendarView

            calendar
                .setOnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    // Store the value of date with
                    // format in String type Variable
                    // Add 1 in month because month
                    // index is start with 0
                    val Date = (year.toString()+ "-"
                            + (month + 1) + "-" + dayOfMonth.toString())
                   Log.e("Date",Date)

                    if (PreferenceManager().getLanguage(context).equals("ar"))
                    {
                        Log.e("Language", PreferenceManager().getLanguage(context).toString())
                        firstdayofabsence.setText(AppUtils().dateConversionArabic(Date))
                    }
                    else
                    {
                        Log.e("Language", Date)
                        firstdayofabsence.setText(AppUtils().dateConversionY(Date))
                    }
                    dialog.dismiss()
                    // set this date in TextView for Display
                    date_view.text = Date
                }



            dialog.show()
                /*firstdayofabsence.setFocusable(false)
                firstdayofabsence.clearFocus()
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                var strDate: Date? = null
                val items1: List<String> = AppUtils().getCurrentDateToday()!!.split("-")
                val date1 = items1[0]
                //if()
                val month = items1[1]
                val year = items1[2]
                try {
                    strDate = sdf.parse(AppUtils().getCurrentDateToday())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val strDatePicker = DatePickerDialog(
                    context,R.style.DialogTheme, startDate, year.toInt(),
                    month.toInt() - 1, date1.toInt()
                )
                strDatePicker.datePicker.minDate = strDate!!.time
                strDatePicker.show()
            strDatePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.navyBlue);
            strDatePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.navyBlue);*/

        }

        returnabsence.setOnClickListener {


            returnabsence.setFocusable(false)
            returnabsence.clearFocus()
            if (firstdayofabsence.getText().toString() == "") {
                Toast.makeText(context, "Please select first day of absence", Toast.LENGTH_SHORT)
                    .show()
            } else {
//                    pickerIOSEndDate.setVisibility(View.VISIBLE);
//                    pickerIOS.setVisibility(View.GONE);
                /*val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                var strDate: Date? = null
                val items1: List<String> = AppUtils().getCurrentDateToday()!!.split("-")
                val date1 = items1[0]
                //if()
                val month = items1[1]
                val year = items1[2]
                try {
                    strDate = sdf.parse(AppUtils().getCurrentDateToday())
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val endDatePicker = DatePickerDialog(
                    context,R.style.DialogTheme, endDate, year.toInt(),
                    month.toInt() - 1, date1.toInt()
                )
                endDatePicker.datePicker.minDate = strDate!!.time
                endDatePicker.show()
                endDatePicker.setTitle("");
                endDatePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.navyBlue);
                endDatePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.navyBlue);*/
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.calender_activity_example)
                dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

                var date_view = dialog.findViewById<TextView>(R.id.date_view)!! as TextView
                var calendar = dialog.findViewById<CalendarView>(R.id.calendar)!! as CalendarView

                calendar
                    .setOnDateChangeListener { view, year, month, dayOfMonth ->
                        // In this Listener have one method
                        // and in this method we will
                        // get the value of DAYS, MONTH, YEARS
                        // Store the value of date with
                        // format in String type Variable
                        // Add 1 in month because month
                        // index is start with 0
                        val Date = (year.toString()+ "-"
                                + (month + 1) + "-" + dayOfMonth.toString())
                        Log.e("Date",Date)

                        if (PreferenceManager().getLanguage(context).equals("ar"))
                        {
                            Log.e("Language", PreferenceManager().getLanguage(context).toString())
                            Log.e("Language",fromDate)
                            returnabsence.setText(AppUtils().dateConversionArabic(Date))
                        }
                        else
                        {
                            returnabsence.setText(AppUtils().dateConversionY(Date))
                        }
                        dialog.dismiss()
                        // set this date in TextView for Display
                        date_view.text = Date
                    }



                dialog.show()




            }

        }

    }

    var startDate =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            fromDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
            if (toDate != "") {
                val dateFormatt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                try {
                    sdate = dateFormatt.parse(fromDate)
                    edate = dateFormatt.parse(toDate)
                    printDifference(sdate, edate)
                } catch (e: Exception) {
                }
            }
            if (elapsedDays < 0 && toDate != "") {
                fromDate = AppUtils().dateConversionYToD(firstdayofabsence.getText().toString()).toString()
                /*AppUtils().showDialogAlertDismiss(
                    context as Activity?,
                    getString(R.string.alert_heading),
                    "Choose first day of absence(date) less than or equal to selected return to school(date)",
                    R.drawable.infoicon,
                    R.drawable.round
                )*/
                //break;
            } else {

                if (PreferenceManager().getLanguage(context).equals("ar"))
                {
                    Log.e("Language", PreferenceManager().getLanguage(context).toString())
                    firstdayofabsence.setText(AppUtils().dateConversionArabic(fromDate))
                }
                else
                {
                    Log.e("Language", fromDate)
                    firstdayofabsence.setText(AppUtils().dateConversionY(fromDate))
                }
               //

               // val txtPrice = String.format(locale "%d", item.getPrice())
                Log.e("Date", AppUtils().dateConversionY(fromDate).toString())
                try {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    val strDate = sdf.parse(fromDate)
                    c = Calendar.getInstance()
                    mYear = c!!.get(Calendar.YEAR)
                    mMonth = c!!.get(Calendar.MONTH)
                    mDay = c!!.get(Calendar.DAY_OF_MONTH)
                    df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                    formattedDate = df!!.format(c!!.getTime())
                    val endDate = sdf.parse(formattedDate)
                    val dateFormatt = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                    var convertedDate: Date? = Date()
                    try {
                        convertedDate = dateFormatt.parse(firstdayofabsence.getText().toString())
                    } catch (e: ParseException) {
                        // TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                    calendar!!.setTime(convertedDate)
                    calendar!!.add(Calendar.DAY_OF_YEAR, 1)
                    val tomorrow: Date = calendar!!.getTime()
                    tomorrowAsString = dateFormatt.format(tomorrow)

                    //System.out.println(todayAsString);
                    println("Tomorrow--$tomorrowAsString")
                    //  enterEndDate.setText(tomorrowAsString);
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }

    var endDate =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            toDate = year.toString() + "-" + (monthOfYear + 1) + "-" + dayOfMonth
            if (toDate != "") {
                val dateFormatt = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                try {
                    sdate = dateFormatt.parse(fromDate)
                    edate = dateFormatt.parse(toDate)
                    printDifference(sdate, edate)
                } catch (e: java.lang.Exception) {
                }
                if (elapsedDays < 0) {
                    toDate = AppUtils().dateConversionYToD(returnabsence.getText().toString())
                    /*AppUtils.showDialogAlertDismiss(
                        mContext as Activity?,
                        getString(R.string.alert_heading),
                        "Choose return to school(date) greater than or equal to first day of absence(date)",
                        R.drawable.infoicon,
                        R.drawable.round
                    )*/

                    //break;
                } else {


                    if (PreferenceManager().getLanguage(context).equals("ar"))
                    {
                        Log.e("Language", PreferenceManager().getLanguage(context).toString())
                        Log.e("Language",fromDate)
                        returnabsence.setText(AppUtils().dateConversionArabic(fromDate))
                    }
                    else
                    {
                        returnabsence.setText(AppUtils().dateConversionY(fromDate))
                    }
                    //returnabsence.setText(AppUtils().dateConversionY(toDate))
                }

                //AppUtils.showDialogAlertDismiss((Activity) mContext, getString(R.string.alert_heading), getString(R.string.enter_enddate), R.drawable.infoicon,  R.drawable.round);
            }
            /*
                    enterEndDate.setText(AppUtils.dateConversionY(toDate));
        */try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val strDate = sdf.parse(toDate)
            c = Calendar.getInstance()
            mYear = c!!.get(Calendar.YEAR)
            mMonth = c!!.get(Calendar.MONTH)
            mDay = c!!.get(Calendar.DAY_OF_MONTH)
            df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            formattedDate = df!!.format(c!!.getTime())
            val endDate = sdf.parse(formattedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        }
    fun printDifference(startDate: Date, endDate: Date) {

        //milliseconds
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        elapsedDays = different / daysInMilli
        different = different % daysInMilli
        val elapsedHours = different / hoursInMilli
        different = different % hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different = different % minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays,
            elapsedHours, elapsedMinutes, elapsedSeconds
        )
    }
    private fun studentlist_popup(student_name: ArrayList<Studentlist_model>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView
        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(context)
        /*val studentlist_adapter =
            AbsenceStudentListAdapter(context, student_name)
        recycler_view!!.adapter = studentlist_adapter*/
        crossicon.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()


         recycler_view.addOnItemClickListener(object : OnItemClickListener {
             override fun onItemClicked(position: Int, view: View) {

                 var name: String = student_name.get(position).name
                 studentName.setText(name)
                 dialog.dismiss()
             }

         })

    }
}