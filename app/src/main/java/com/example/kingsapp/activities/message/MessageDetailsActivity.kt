package com.example.kingsapp.activities.message

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import java.util.*

class MessageDetailsActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var message_backarrow : ImageView
    lateinit var dateText:TextView
    lateinit var timeText:TextView
    lateinit var stringList:Array<String>
    lateinit var desc:TextView
    lateinit var date:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_details_activity)
        mContext = this
        initFn()
    }

    private fun initFn() {
        title=intent.getStringExtra("title").toString()
        date=intent.getStringExtra("date").toString()
        dateText=findViewById(R.id.textview1)
        timeText=findViewById(R.id.timetextview)

        message_backarrow = findViewById(R.id.message_backarrow)
        desc=findViewById(R.id.desc)
        desc.setText(title)

       /* val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSz")
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
        val outputFormatdate: DateFormat = SimpleDateFormat("dd-MMM-yyyy")
        val inputDateStr = date
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        val outputDateStr1: String = outputFormatdate.format(date)*/
        val substr: String = date.substring(0, 9)
        val substr23: String = date.substring(11, 15)
        val substr3:String=date.substring(date.indexOf("T") + 8)

        
        dateText.setText(substr)
        timeText.setText(substr3)
        message_backarrow.setOnClickListener(View.OnClickListener { finish() })
    }
}