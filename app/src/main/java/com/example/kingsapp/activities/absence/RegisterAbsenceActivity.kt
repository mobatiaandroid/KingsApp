package com.example.kingsapp.activities.absence

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.model.RequestAbsenceApiModel
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.common.CommonResponse
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.manager.AppUtils
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


lateinit var firstdayofabsence : TextInputEditText
lateinit var returnabsence : TextInputEditText
lateinit var reason_for_absence : TextInputEditText
lateinit var reasonforabsence : TextInputLayout
lateinit var first_day_of_absencetext: TextInputLayout
lateinit var return_absence_text: TextInputLayout
lateinit var studentSpinner: LinearLayout
lateinit var student_Name: TextView
lateinit var studentName: String
lateinit var studentId: String
lateinit var studentImg: String
lateinit var student_class: String
lateinit var imagicon: ImageView
lateinit var studentclass: TextView
lateinit var reasonAPI: String
lateinit var context: Context

var c: Calendar? = null
var mYear = 0
var mMonth = 0
var mDay = 0
var df: SimpleDateFormat? = null
var formattedDate: String? = null
var calendar: Calendar? = null
var fromDate = ""
var toDate: String? = ""
var tomorrowAsString: String? = null
lateinit var sdate: Date
lateinit var edate:Date
var elapsedDays: Long = 0
var outputFormats: SimpleDateFormat? = null
lateinit var student_name:ArrayList<StudentList>
lateinit var backarrow_registerabsence : ImageView
lateinit var relativieabsence:RelativeLayout
private lateinit var progressDialog: RelativeLayout

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
        if(CommonClass.isInternetAvailable(context)) {
            studentListApiCall()
        }
        else{
            Toast.makeText(context,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }
    }

    private fun studentListApiCall() {
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(context).toString())
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                Log.e("Response",response.body().toString())
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    student_name.addAll(response.body()!!.student_list)
                    Log.e("StudentNameid", PreferenceManager().getStudent_ID(context).toString())
                    if ( PreferenceManager().getStudent_ID(context).equals(""))
                    {
                        studentName=student_name.get(0).fullname
                        student_class=student_name.get(0).classs
                        Log.e("StudentName",studentName)
                        Log.e("student_class",student_class)
                        studentImg=student_name.get(0).photo
                        studentId= student_name.get(0).id.toString()
                        PreferenceManager().setStudent_ID(context,studentId)
                        PreferenceManager().setStudentName(context,studentName)
                        PreferenceManager().setStudentPhoto(context,studentImg)
                        PreferenceManager().setStudentClass(context,student_class)
                        student_Name.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(context) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_icon_grey)
                                .error(R.drawable.profile_icon_grey)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_icon_grey)
                        }

                    }
                    else{

                        studentName= PreferenceManager().getStudentName(context)!!
                        Log.e("StudentName",studentName)
                        student_class=PreferenceManager().getStudentClass(context)!!
                        studentImg= PreferenceManager().getStudentPhoto(context)!!
                        studentId= PreferenceManager().getStudent_ID(context)!!
                        student_Name.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(context) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_icon_grey)
                                .error(R.drawable.profile_icon_grey)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_icon_grey)
                        }
                    }

                }
                else if(response.body()!!.status.equals(106))
                {
                    val intent = Intent(context, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
                }
                else{
                    val intent = Intent(context, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    @SuppressLint("ResourceAsColor")
    private fun initFn() {

        student_name = ArrayList()
        imagicon=findViewById(R.id.imagicon)
        studentclass=findViewById(R.id.studentclass)
        relativieabsence=findViewById(R.id.relativieabsence)
        first_day_of_absencetext = findViewById(R.id.first_day_of_absencetext)
        return_absence_text = findViewById(R.id.return_absence_text)
      //  reasonforabsence = findViewById(R.id.reasonforabsence)
        reason_for_absence = findViewById(R.id.reason_for_absence)
        firstdayofabsence = findViewById(R.id.firstdayofabsence)
        returnabsence = findViewById(R.id.returnabsence)
        studentSpinner = findViewById(R.id.studentSpinner)
        student_Name = findViewById(R.id.studentName)
        backarrow_registerabsence = findViewById(R.id.backarrow_registerabsence)
        calendar = Calendar.getInstance()
       // outputFormats = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(context, R.anim.linear_interpolator)
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
        relativieabsence.setOnClickListener {

            if(firstdayofabsence.text.toString().trim().equals(""))
            {
                Toast.makeText(context, "Please select First day of absence", Toast.LENGTH_SHORT).show()
            }

            else{
                if (reason_for_absence.text.toString().trim().equals("")){
                    Toast.makeText(context, "Please enter reason for your absence", Toast.LENGTH_SHORT).show()
                }
                else
                {
            reasonAPI=reason_for_absence.text.toString().trim()
            Log.e("Pass Value",fromDate+"  "+toDate+"   "+reasonAPI)
            val inputFormat2: DateFormat = SimpleDateFormat("d-m-yyyy")
            val outputFormat2: DateFormat = SimpleDateFormat("d-m-yyyy")
            val inputDateStr2 = fromDate
            val date2: Date = inputFormat2.parse(inputDateStr2)
            val f_date: String = outputFormat2.format(date2)
            Log.e("fd",f_date)

            val inputFormat3: DateFormat = SimpleDateFormat("d-m-yyyy")
            val outputFormat3: DateFormat = SimpleDateFormat("d-m-yyyy")
            val inputDateStr3 = toDate
            val date3: Date = inputFormat3.parse(inputDateStr3)
            val t_date: String = outputFormat3.format(date3)
            Log.e("fd",t_date)
            callAbsenceSubmitApi(f_date,t_date,reasonAPI)
                }
            }
        }
        studentSpinner.setOnClickListener {

            studentlist_popup(student_name)
        }
        firstdayofabsence.setOnClickListener {

            val mcurrentTime = android.icu.util.Calendar.getInstance()
            val year = mcurrentTime.get(android.icu.util.Calendar.YEAR)
            val month = mcurrentTime.get(android.icu.util.Calendar.MONTH)
            val day = mcurrentTime.get(android.icu.util.Calendar.DAY_OF_MONTH)
            val minDate = android.icu.util.Calendar.getInstance()
            minDate.set(android.icu.util.Calendar.HOUR_OF_DAY, 0)
            minDate.set(android.icu.util.Calendar.MINUTE, 0)
            minDate.set(android.icu.util.Calendar.SECOND, 0)
            val dpd1 = DatePickerDialog(this, R.style.DialogTheme,
                object : OnDateSetListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        var firstday: String? =String.format("%d/%d/%d", month + 1,dayOfMonth , year)
                        var date_sel: String? = String.format("%d-%d-%d", dayOfMonth, month + 1, year)
                        var date_temp = date_sel.toString()
                        Log.e("date_temp",date_temp)
                        fromDate=date_sel.toString()

                        firstdayofabsence.setText(AppUtils().dateConversionY(date_temp))
                       // firstdayofabsence.text = outputDateStr

                    }
                }, year, month, day)

            dpd1.datePicker.minDate = android.icu.util.Calendar.getInstance().timeInMillis
            dpd1.show()
            dpd1.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.kings_blue);
            dpd1.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.kings_blue);
        }

        returnabsence.setOnClickListener {
            if(firstdayofabsence.text.toString().trim().equals(""))
            {
                Toast.makeText(context, "Please select First day of absence", Toast.LENGTH_SHORT).show()
            }
            else
            {
            val mcurrentTime = android.icu.util.Calendar.getInstance()
            val year = mcurrentTime.get(android.icu.util.Calendar.YEAR)
            val month = mcurrentTime.get(android.icu.util.Calendar.MONTH)
            val day = mcurrentTime.get(android.icu.util.Calendar.DAY_OF_MONTH)
            val minDate = android.icu.util.Calendar.getInstance()
            minDate.set(android.icu.util.Calendar.HOUR_OF_DAY, 0)
            minDate.set(android.icu.util.Calendar.MINUTE, 0)
            minDate.set(android.icu.util.Calendar.SECOND, 0)
            val dpd1 = DatePickerDialog(this, R.style.DialogTheme, object : DatePickerDialog.OnDateSetListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    var firstday: String? =String.format("%d/%d/%d", month + 1,dayOfMonth , year)
                    var date_sel: String? = String.format("%d-%d-%d", dayOfMonth, month + 1, year)
                    var date_temp = date_sel.toString()

                    toDate=date_sel.toString()
                    returnabsence.setText(AppUtils().dateConversionY(date_temp))


                }
            }, year, month, day)

            dpd1.datePicker.minDate = android.icu.util.Calendar.getInstance().timeInMillis
            dpd1.show()
            dpd1.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.navyBlue);
            dpd1.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.navyBlue);

        }
        }


    }

    private fun callAbsenceSubmitApi(from: String, tDate: String, reasonAPI: String) {
        //progressDialog.visibility = View.VISIBLE

        var devicename:String= (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        Log.e("fromto",from + tDate)
        val requestLeaveBody= RequestAbsenceApiModel(PreferenceManager().getStudent_ID(context)!!,from,tDate,reasonAPI,"1",devicename,"1.0")
        val call: Call<CommonResponse> = ApiClient.getApiService().requestleave("Bearer "+
                PreferenceManager().getAccessToken(context).toString(),
            requestLeaveBody
        )
        call.enqueue(object : retrofit2.Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                //progressDialog.visibility = View.GONE
                if (response.body() != null) {
                if(response.body()!!.status.equals(100))
                {
                    showErrorAlert(context,"Successfully submitted your absence.","Success")
                }
                else if(response.body()!!.status.equals(106))
                {
                    val intent = Intent(context, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
                }
                else{
                    val intent = Intent(context, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
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

    fun showErrorAlert(context: Context,message : String,msgHead : String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            val intent = Intent(context, AbsenceActivity::class.java)
            startActivity(intent)
            finish()
        }
        dialog.show()
    }
    private fun studentlist_popup(student_name: ArrayList<StudentList>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView
        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(context)
        val studentlist_adapter =
            AbsenceStudentListAdapter(
                context,
                student_name)
        recycler_view!!.adapter = studentlist_adapter
        crossicon.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()


         recycler_view.addOnItemClickListener(object : OnItemClickListener {
             override fun onItemClicked(position: Int, view: View) {

                 var name: String = student_name.get(position).fullname
                 var classs: String = student_name.get(position).classs
                 var id: Int = student_name.get(position).id
                 student_Name.setText(name)
                 studentclass.text=classs
                 PreferenceManager().setStudent_ID(context,id.toString())
                 dialog.dismiss()
             }

         })

    }
}