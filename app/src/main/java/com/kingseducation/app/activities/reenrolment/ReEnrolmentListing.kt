package com.kingseducation.app.activities.reenrolment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R

class ReEnrolmentListing : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_enrolment_listing)
        context = this
        initialiseUI()
    }

    private fun initialiseUI() {
        TODO("Not yet implemented")
    }
}