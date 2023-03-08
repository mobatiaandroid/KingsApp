package com.example.kingsapp.activities.message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.about_us.StaffDirectoryActivity

class MessageDetailsActivity: AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var message_backarrow : ImageView
    lateinit var stringList:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.message_details_activity)
        mContext = this
        initFn()
    }

    private fun initFn() {
        message_backarrow = findViewById(R.id.message_backarrow)

        message_backarrow.setOnClickListener(View.OnClickListener { finish() })
    }
}