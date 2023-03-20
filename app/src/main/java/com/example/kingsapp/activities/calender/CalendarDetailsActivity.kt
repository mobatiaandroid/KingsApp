package com.example.kingsapp.activities.calender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.adapter.StudentRecyclerCalenderAdapter

class CalendarDetailsActivity :AppCompatActivity(){
    lateinit var mcontext:Context
    lateinit var studentRecycle:RecyclerView
    lateinit var name:ArrayList<String>
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calendar_details_layout)
        mcontext = this
        initFn()

        // onclick()
    }

    private fun initFn() {
        name= ArrayList()
        name.add("Jane Mathewes")
        name.add("Esther Mathewes")
        studentRecycle=findViewById(R.id.studentRecycle)
        back=findViewById(R.id.back)
        linearLayoutManager = LinearLayoutManager(mcontext)
        back.setOnClickListener {
            val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
            startActivity(intent)
        }
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycle.layoutManager = linearLayoutManager
        val abseneadapter = StudentRecyclerCalenderAdapter(mcontext,name)
        studentRecycle.setAdapter(abseneadapter)
    }
}