package com.example.kingsapp.activities.early_pickup

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.*
import com.example.kingsapp.activities.absence.model.RequestAbsenceApiModel
import com.example.kingsapp.activities.early_pickup.model.RequestEarlyApiModel
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.common.CommonResponse
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.manager.AppUtils
import com.example.kingsapp.manager.PreferenceManager
import com.google.android.material.textfield.TextInputEditText
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EarlyPickupRegisterActivity :AppCompatActivity(){
lateinit var mContext: Context
lateinit var timepikup:TextInputEditText
lateinit var dayofearlypikup:TextInputEditText
lateinit var student_Name: TextView
lateinit var studentclass: TextView
lateinit var imagicon: ImageView
lateinit var pickup_by_text:TextInputEditText
lateinit var pickup_reason:TextInputEditText
lateinit var relativieabsence:RelativeLayout
lateinit var progressBarDialog: ProgressBarDialog
    lateinit var back:ImageView

    var totime: String =""
    var pickup_date_time: String =""
    var pickupby:String=""
    var reasonpickup:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.register_early_pickup)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        mContext=this

        initFn()
    }

    @SuppressLint("ResourceAsColor")
    private fun initFn() {
        timepikup=findViewById(R.id.returnabsence)
        dayofearlypikup=findViewById(R.id.firstdayofabsence)
       studentclass =findViewById(R.id.studentclass)
        student_Name = findViewById(R.id.studentName)
        imagicon=findViewById(R.id.imagicon)
        pickup_by_text=findViewById(R.id.pickup_by_text)
        pickup_reason=findViewById(R.id.reason_for_absence)
        relativieabsence =findViewById(R.id.relativieabsence)
        progressBarDialog = ProgressBarDialog(mContext)
        back = findViewById(R.id.backarrow_registerabsence)
        back.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        relativieabsence.setOnClickListener {

            if(dayofearlypikup.text.toString().trim().equals(""))
            {
                Toast.makeText(mContext, "Please select Date of Early Pickup", Toast.LENGTH_SHORT).show()
            }

            else{
                if (timepikup.text.toString().trim().equals("")){
                    Toast.makeText(mContext, "Please select Time of Early Pickup", Toast.LENGTH_SHORT).show()
                }
                else
                {
            pickupby=pickup_by_text.text.toString().trim()
            reasonpickup=pickup_reason.text.toString().trim()
            val inputFormat2: DateFormat = SimpleDateFormat("d-m-yyyy")
            val outputFormat2: DateFormat = SimpleDateFormat("d-m-yyyy")
            val inputDateStr2 = fromDate
            val date2: Date = inputFormat2.parse(inputDateStr2)
            val f_date: String = outputFormat2.format(date2)
            Log.e("fd",f_date)
            Log.e("f_date",f_date)
            Log.e("time",totime)
            pickup_date_time=f_date+" "+totime
                    callearlypickupSubmitApi(pickup_date_time,pickupby,reasonpickup)
                }   }
        }


        student_Name.text= PreferenceManager().getStudentName(mContext)
        studentclass.text= PreferenceManager().getStudentClass(mContext)
        if(!PreferenceManager().getStudentPhoto(mContext).equals(""))
        {
            Glide.with(mContext) //1
                .load(studentImg)
                .placeholder(R.drawable.profile_photo)
                .error(R.drawable.profile_photo)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(imagicon)
        }
        else{
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        dayofearlypikup.setOnClickListener {
            val mcurrentTime = android.icu.util.Calendar.getInstance()
            val year = mcurrentTime.get(android.icu.util.Calendar.YEAR)
            val month = mcurrentTime.get(android.icu.util.Calendar.MONTH)
            val day = mcurrentTime.get(android.icu.util.Calendar.DAY_OF_MONTH)
            val minDate = android.icu.util.Calendar.getInstance()
            minDate.set(android.icu.util.Calendar.HOUR_OF_DAY, 0)
            minDate.set(android.icu.util.Calendar.MINUTE, 0)
            minDate.set(android.icu.util.Calendar.SECOND, 0)
            val dpd1 = DatePickerDialog(this, R.style.DialogTheme,
                object : DatePickerDialog.OnDateSetListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        var firstday: String? =String.format("%d/%d/%d", month + 1,dayOfMonth , year)
                        var date_sel: String? = String.format("%d-%d-%d", dayOfMonth, month + 1, year)
                        var date_temp = date_sel.toString()
                        Log.e("date_temp",date_temp)
                        fromDate =date_sel.toString()

                        dayofearlypikup.setText(AppUtils().dateConversionY(date_temp))
                        // firstdayofabsence.text = outputDateStr

                    }
                }, year, month, day)

            dpd1.datePicker.minDate = android.icu.util.Calendar.getInstance().timeInMillis
            dpd1.show()
            dpd1.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.kings_blue);
            dpd1.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.kings_blue);
        }
        timepikup.setOnClickListener {

            if (dayofearlypikup.text!!.equals("")){
                Toast.makeText(mContext, "mContext", Toast.LENGTH_SHORT).show()
                //showerror(mContext,"Please select Date of Early Pickup","Alert")
            }
            else
            {
                val mTimePicker: TimePickerDialog
                val mcurrentTime = Calendar.getInstance()
                val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                val minute = mcurrentTime.get(Calendar.MINUTE)
                //var am_pm = mcurrentTime.get(Calendar.AM_PM)
                var am_pm:String=""

                mTimePicker = TimePickerDialog(mContext, R.style.TimePickerTheme,object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        var AM_PM: String
                        var hour=hourOfDay
                        var min=minute.toString()
                        if (minute<10){
                            min="0"+min
                        }

                        if(hour ==0) {
                            Log.e("h","0")
                            hour = 12
                            AM_PM="AM"
                        } else if(hour<12){
                            hour = hourOfDay
                            AM_PM = "AM"
                        }
                        else if (hour >12) {
                            hour -= 12
                            AM_PM = "PM"
                        } else if (hour == 12) {
                            Log.e("h","12")
                            hour = 12
                            AM_PM = "PM"
                        } else
                            AM_PM = "AM"
                        timepikup.setText( hour.toString() + ":" + min + ":" + "00"+ AM_PM)
                        totime=hour.toString() + ":" + min + ":" + "00"
                    }
                }, hour, minute,false)

                timepikup.setOnClickListener({ v ->
                    mTimePicker.show()
                    mTimePicker.getButton(TimePickerDialog.BUTTON_NEGATIVE).setTextColor(R.color.kings_blue);
                    mTimePicker.getButton(TimePickerDialog.BUTTON_POSITIVE).setTextColor(R.color.kings_blue);

                })
            }

        }
    }

     fun callearlypickupSubmitApi(fDate: String, pickupby: String, reasonAPI: String) {
       progressBarDialog.show()
        var devicename:String= (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT]
            .name)
        val requestLeaveBody= RequestEarlyApiModel(PreferenceManager().getStudent_ID(mContext)!!,fDate,reasonAPI,pickupby,"1",devicename,"1.0")
        val call: Call<CommonResponse> = ApiClient.getApiService().request_early_pickup("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(),
            requestLeaveBody
        )
        call.enqueue(object : retrofit2.Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                progressBarDialog.hide()
                //progressDialog.visibility = View.GONE
                if (response.body() != null) {
                    if(response.body()!!.status.equals(100))
                    {
                        showErrorAlert(mContext,"Successfully submitted your absence.","Success")
                    }
                    else if(response.body()!!.status.equals(106))
                    {
                        val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
                progressBarDialog.hide()

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

    fun showErrorAlert(context: Context,message : String,msgHead : String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        //  var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        text_dialog.text = message
        //alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            val intent = Intent(context, AbsenceActivity::class.java)
            startActivity(intent)
            finish()
        }
        dialog.show()
    }
}