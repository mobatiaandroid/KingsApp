package com.kingseducation.app.activities.absence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.textfield.TextInputEditText
import com.kingseducation.app.R
import com.kingseducation.app.fragment.mContext
import com.kingseducation.app.manager.PreferenceManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date


class AbsenceDeatilsActivity:AppCompatActivity() {
    lateinit var ccontext:Context
    lateinit var backtolist : TextView
    lateinit var backarrow : ImageView
    lateinit var firstdayofabsencetext: TextInputEditText
    lateinit var returnabsencetext: TextInputEditText
    lateinit var reason_for_absence_text: TextInputEditText
    lateinit var studentNameText: TextView
    lateinit var studentclass: TextView
    lateinit var imageView: ImageView
    var reason: String? = ""
    var studentName: String? = ""
    var studentClass: String? = ""
    var fromDate: String? = ""
    var toDate: String? = ""
    override fun onBackPressed() {
        val intent = Intent(ccontext, AbsenceActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.absence_details_activity)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ccontext = this
        reason = intent.getStringExtra("reason")
        studentName=intent.getStringExtra("studentName")
        studentClass=intent.getStringExtra("studentClass")
        fromDate=intent.getStringExtra("fromDate")
        toDate=intent.getStringExtra("toDate")
        Log.e("Values get",reason +" "+fromDate+" "+toDate)
        initFn()
    }

    private fun initFn() {
        firstdayofabsencetext = findViewById(R.id.firstdayofabsencetext)
        returnabsencetext = findViewById(R.id.returnabsencetext)
        reason_for_absence_text =findViewById(R.id.reason_for_absence_text)
        studentclass = findViewById(R.id.studentclass)
        studentNameText = findViewById(R.id.studentName)
        backtolist = findViewById(R.id.backtolist)
        backarrow = findViewById(R.id.backarrow)
        imageView = findViewById(R.id.imagicon)
        backtolist.setOnClickListener {
            val intent = Intent(ccontext, AbsenceActivity::class.java)
            startActivity(intent)
        }
        //firstdayofabsencetext.setText("gcfdcfksdfhc")

        studentNameText.text = studentName
        studentclass.text = studentClass
        if (!PreferenceManager().getStudentPhoto(mContext).equals("")) {
            studentImg = PreferenceManager().getStudentPhoto(mContext).toString()
            if (!studentImg.isEmpty()) {
                val glideUrl = GlideUrl(
                    studentImg,
                    LazyHeaders.Builder()
                        .addHeader(
                            "Authorization",
                            "Bearer " + PreferenceManager().getAccessToken(mContext)
                                .toString()
                        )
                        .build()
                )
                Glide.with(mContext)
                    .load(glideUrl)
                    .transform(CircleCrop()) // Apply circular transformation
                    .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                    .error(R.drawable.profile_photo) // Image to display in case of error
                    .into(imagicon)
            } else {
                Toast.makeText(mContext, "Image empty", Toast.LENGTH_SHORT).show()
                // Handle the case when studentImg is null or empty
            }
        } else {
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        val fromdate = fromDate
        val todate = toDate
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = fromdate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)




        if (todate!=""){
            val inputFormat1: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val outputFormat1: DateFormat = SimpleDateFormat("dd MMMM yyyy")
            val inputDateStr1 = todate
            val date1: Date = inputFormat1.parse(inputDateStr1)
            val outputDateStr1: String = outputFormat1.format(date1)
            returnabsencetext.setText(outputDateStr1)
            firstdayofabsencetext.setText(outputDateStr)
            reason_for_absence_text.setText (reason)

        }else{
            firstdayofabsencetext.setText(outputDateStr)
            reason_for_absence_text.setText(reason)
            returnabsencetext.setText("-")

        }
    }
}