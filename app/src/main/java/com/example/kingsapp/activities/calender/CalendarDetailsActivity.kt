package com.example.kingsapp.activities.calender

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    private var mLoadUrl: String? = null
    var extras: Bundle? = null
    lateinit var msgTitle: TextView

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
       // back=findViewById(R.id.back)
        msgTitle = findViewById(R.id.msgTitle)

        linearLayoutManager = LinearLayoutManager(mcontext)
        extras = intent.extras
        title = extras!!.getString("tittle")

        mLoadUrl =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "\n" +
                    "@font-face {\n" +
                    "font-family: SourceSansPro-Semibold;"+
                    "src: url(SourceSansPro-Semibold.ttf);"+

                    "font-family: SourceSansPro-Regular;"+
                    "src: url(SourceSansPro-Regular.ttf);"+
                    "}"+
                    ".venue {"+

                    "font-family: SourceSansPro-Regular;"+
                    "font-size:16px;"+
                    "text-align:left;"+
                    "color:	#A9A9A9;"+
                    "}"+
                    ".title {"+
                    "font-family: SourceSansPro-Regular;"+
                    "font-size:16px;"+
                    "text-align:left;"+
                    "color:	#46C1D0;"+
                    "}"+
                    ".description {"+
                    "font-family: SourceSansPro-Semibold;"+
                    "text-align:justify;"+
                    "font-size:14px;"+
                    "color: #000000;"+
                    "}"+".date {"+
                    "font-family: SourceSansPro-Semibold;"+
                    "text-align:right;"+
                    "font-size:12px;"+

                    "color: #A9A9A9;"+
                    "}"+
                    "</style>\n"+"</head>"+
                    "<body>";

        msgTitle.setText(title)
      /*  back.setOnClickListener {
            val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
            startActivity(intent)
        }*/
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycle.layoutManager = linearLayoutManager
        val abseneadapter = StudentRecyclerCalenderAdapter(mcontext,name)
        studentRecycle.setAdapter(abseneadapter)
    }
}