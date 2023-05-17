package com.example.kingsapp.activities.early_pickup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.AbsenceActivity
import com.google.android.material.textfield.TextInputEditText
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EarlyPickupdetailsActivity:AppCompatActivity() {
    lateinit var status_text: TextInputEditText
    lateinit var reason_for_earlypickup:TextInputEditText
    lateinit var rejection_text:TextInputEditText
    lateinit var timepick:TextInputEditText
    lateinit var pickup_by_text:TextInputEditText
    lateinit var date_pickup:TextInputEditText
    lateinit var mContext: Context
    lateinit var studentNameText: TextView
    lateinit var studentclass: TextView
    lateinit var backtolist : TextView

    var reason: String? = ""
    var studentName: String? = ""
    var studentClass: String? = ""
    var fromDate: String? = ""
    var status: String? =""
    var pickupby: String? = ""
    var reason_for_rejection:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.early_pickup_details)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
       // ncontext = this
        mContext = this
        reason = intent.getStringExtra("reason")
        studentName=intent.getStringExtra("studentName")
        studentClass=intent.getStringExtra("studentClass")
        fromDate=intent.getStringExtra("fromDate")
        status=intent.getIntExtra("status",0).toString()
        pickupby=intent.getStringExtra("pickupby")
        reason_for_rejection=intent.getStringExtra("reason_for_rejection").toString()

        Log.e("Values get",reason +" "+fromDate+" "+status)
        initFn()
}

    private fun initFn() {

        status_text=findViewById(R.id.status_text)
        reason_for_earlypickup=findViewById(R.id.reason_for_absence_text)
        rejection_text=findViewById(R.id.rejection_text)
        pickup_by_text=findViewById(R.id.pickup_by_text)
        timepick=findViewById(R.id.returnabsencetext)
        date_pickup=findViewById(R.id.firstdayofabsencetext)
        studentclass = findViewById(R.id.studentclass)
        studentNameText = findViewById(R.id.studentName)
        backtolist = findViewById(R.id.backtolist)

        studentNameText.text = studentName
        studentclass.text = studentClass

        val substr: String = fromDate!!.substring(0, 10)
        val substr1: String = fromDate!!.substring(11, 18)
        Log.e("Values get",substr1 +" "+substr)

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        val inputDateStr = substr
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        Log.e("date",outputDateStr)
        date_pickup.setText(outputDateStr)

        val inFormat: DateFormat = SimpleDateFormat("hh:mm:ss")
        val outFormat: DateFormat = SimpleDateFormat("hh:mm aa")
        val inputTimeStr = substr1
        val time: Date = inFormat.parse(inputTimeStr)
        val outputTimeStr: String = outFormat.format(time)
        timepick. setText( outputTimeStr)

        if (status.equals("1")){
            status_text.setText("PENDING")
            rejection_text.visibility= View.GONE
           // reasonRejectionScroll.visibility= View.GONE
        }
        else if(status.equals("2")){
            status_text.setText ("APPROVED")
            rejection_text.visibility= View.GONE
           // reasonRejectionScroll.visibility= View.GONE
        }
        else{
            status_text.setText("REJECTED")
            rejection_text.visibility= View.VISIBLE
           // reasonRejectionScroll.visibility= View.VISIBLE
            rejection_text.setText(reason_for_rejection)

        }

        reason_for_earlypickup.setText (reason)
        pickup_by_text.setText(pickupby)
        //status_text.setText(status!!)
        backtolist.setOnClickListener {
            val intent = Intent(mContext, EarlyPickupListActivity::class.java)
            startActivity(intent)
        }

    }
}