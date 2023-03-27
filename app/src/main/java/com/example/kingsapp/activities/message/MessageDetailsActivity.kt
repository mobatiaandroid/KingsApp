package com.example.kingsapp.activities.message

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MessageDetailsActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var message_backarrow : ImageView
    lateinit var dateText:TextView
    lateinit var timeText:TextView
    lateinit var stringList:Array<String>
    lateinit var desc:TextView
    lateinit var datee:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_details_activity)
        mContext = this
        initFn()
    }

    private fun initFn() {
        title=intent.getStringExtra("title").toString()
        datee=intent.getStringExtra("date").toString()
        dateText=findViewById(R.id.textview1)
        timeText=findViewById(R.id.timetextview)

        message_backarrow = findViewById(R.id.message_backarrow)
        desc=findViewById(R.id.desc)
        desc.setText(title)

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
        val outputFormatdate: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val inputDateStr = datee
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        val outputDateStr1: String = outputFormatdate.format(date)


        
        dateText.setText(outputDateStr1)
        timeText.setText(outputDateStr)
        message_backarrow.setOnClickListener(View.OnClickListener { finish() })
    }
}