package com.example.kingsapp.activities.absence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AbsenceDeatilsActivity:AppCompatActivity() {
    lateinit var ccontext:Context
    lateinit var backtolist : TextView
    lateinit var backarrow : ImageView
    lateinit var firstdayofabsencetext:TextInputEditText
    lateinit var returnabsencetext:TextInputEditText
    lateinit var reason_for_absence_text:TextInputEditText
    lateinit var studentNameText:TextView
    lateinit var studentclass:TextView
    var reason:String?=""
    var studentName:String?=""
    var studentClass:String?=""
    var fromDate:String?=""
    var toDate:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.absence_details_activity)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ccontext = this
        reason=intent.getStringExtra("reason")
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
        backarrow.setOnClickListener {
            val intent = Intent(ccontext, AbsenceActivity::class.java)
            startActivity(intent)
        }
        backtolist.setOnClickListener {
            val intent = Intent(ccontext, AbsenceActivity::class.java)
            startActivity(intent)
        }
        //firstdayofabsencetext.setText("gcfdcfksdfhc")

        studentNameText.text = studentName
        studentclass.text = studentClass
        val fromdate=fromDate
        val todate=toDate
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