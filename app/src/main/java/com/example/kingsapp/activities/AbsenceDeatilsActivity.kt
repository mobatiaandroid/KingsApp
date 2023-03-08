package com.example.kingsapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.google.android.material.textfield.TextInputEditText


class AbsenceDeatilsActivity:AppCompatActivity() {
    lateinit var ccontext:Context
    lateinit var backtolist : TextView
    lateinit var backarrow : ImageView
    lateinit var firstdayofabsencetext:TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.absence_details_activity)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ccontext = this
        initFn()
    }

    private fun initFn() {
        firstdayofabsencetext = findViewById(R.id.firstdayofabsencetext)
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
    }
}