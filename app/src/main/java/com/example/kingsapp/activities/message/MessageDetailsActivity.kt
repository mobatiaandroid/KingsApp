package com.example.kingsapp.activities.message

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.example.kingsapp.manager.PreferenceManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class MessageDetailsActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var message_backarrow: ImageView
    lateinit var dateText: TextView
    lateinit var timeText: TextView
    lateinit var stringList: Array<String>
    lateinit var desc: TextView
    lateinit var datee: String
    lateinit var descrption: String
    lateinit var textview: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_details_activity)
        mContext = this
        initFn()
    }

    fun formatDateWithSuffix(dateString: String): String {
        val dateParts = dateString.split(" ")
        if (dateParts.size == 3) {
            val day = dateParts[0].toInt()
            val month = dateParts[1]
            val year = dateParts[2]

            val dayWithSuffix = when (day) {
                1, 21, 31 -> "${day}st"
                2, 22 -> "${day}nd"
                3, 23 -> "${day}rd"
                else -> "${day}th"
            }

            return "$dayWithSuffix $month $year"
        }
        return dateString
    }

    private fun initFn() {
        title = intent.getStringExtra("title").toString()
        datee = intent.getStringExtra("date").toString()
        descrption = intent.getStringExtra("message").toString()
        dateText = findViewById(R.id.textview1)
        timeText = findViewById(R.id.timetextview)
        textview = findViewById(R.id.textview)
        message_backarrow = findViewById(R.id.message_backarrow)
        desc = findViewById(R.id.desc)
        desc.text = descrption
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/times_new_roman.ttf")
            textview.typeface = face
        }
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
        val outputFormatdate: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = datee
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        val outputDateStr1: String = outputFormatdate.format(date)



        dateText.text = formatDateWithSuffix(outputDateStr1)
        timeText.text = outputDateStr
        message_backarrow.setOnClickListener(View.OnClickListener { finish() })
    }
}