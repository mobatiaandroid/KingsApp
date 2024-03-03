package com.kingseducation.app.activities.reports

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.JsonObject
import com.kingseducation.app.R
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.activities.reports.adapter.ReportsAdapterList
import com.kingseducation.app.activities.reports.model.ReportModelFiltered
import com.kingseducation.app.activities.reports.model.Reports
import com.kingseducation.app.activities.reports.model.ReportsResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.fragment.mContext
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response

class ReportsActivity : AppCompatActivity() {
    lateinit var ncontext: Context

    //    lateinit var linearlayoutstudentlist:LinearLayout
    lateinit var reportrec: RecyclerView
    lateinit var student_name: ArrayList<StudentList>

    lateinit var report_array: ArrayList<Reports>
    lateinit var report_array_filtered: ArrayList<ReportModelFiltered>
    lateinit var student_Name: TextView
    lateinit var studentclass: TextView
    lateinit var imagicon: ImageView
    lateinit var backarrow: ImageView
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView: TextView
    lateinit var noDataTV: TextView

    //lateinit var progressDialog: ProgressBarDialog
    var studentName: String = ""
    var student_class: String = ""
    var studentImg: String = ""
    var studentId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_report)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        // progressDialog = ProgressBarDialog(ncontext)
        initFn()
        if (CommonClass.isInternetAvailable(ncontext)) {
            // studentListApiCall()
            //reportslisting()
            reportslisting()
        } else {
            Toast.makeText(
                ncontext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }

    }

    private fun initFn() {
//        linearlayoutstudentlist=findViewById(R.id.linearlayoutstudentlist)
        student_name = ArrayList()
        reportrec = findViewById(R.id.reportrec)
        report_array_filtered = ArrayList()
        student_Name = findViewById(R.id.studentName)
        studentclass = findViewById(R.id.studentclass)
        imagicon = findViewById(R.id.imagicon)
        backarrow = findViewById(R.id.back)
        progressBarDialog = ProgressBarDialog(ncontext)
        noDataTV = findViewById(R.id.noDataTV)

        textView = findViewById(R.id.textView)
        if (PreferenceManager().getLanguage(ncontext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(ncontext.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }
        student_Name.text = PreferenceManager().getStudentName(ncontext)
        studentclass.text = PreferenceManager().getStudentClass(ncontext)
        if (!PreferenceManager().getStudentPhoto(ncontext).equals("")) {
            if (!PreferenceManager().getStudentPhoto(mContext).equals("")) {
                studentImg = PreferenceManager().getStudentPhoto(mContext).toString()
                if (studentImg != null && !studentImg.isEmpty()) {
                    val glideUrl = GlideUrl(
                        studentImg,
                        LazyHeaders.Builder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + PreferenceManager().getAccessToken(mContext)
                                    .toString()
                            )
                            .build()
                    )

                    Glide.with(mContext)
                        .load(glideUrl)
                        .transform(CircleCrop()) // Apply circular transformation
                        .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                        .error(R.drawable.profile_photo) // Image to display in case of error
                        .into(imagicon)
                } else {
                    Toast.makeText(mContext, "Image empty", Toast.LENGTH_SHORT).show()
                    // Handle the case when studentImg is null or empty
                }
            } else {
                imagicon.setImageResource(R.drawable.profile_photo)
            }
        } else {
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        backarrow.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)
        }


        /* linearlayoutstudentlist.setOnClickListener {
             studentlist_popup(student_name)
             *//* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*//*

        }*/
    }


    private fun reportslisting() {
        report_array = ArrayList()
        report_array_filtered = ArrayList()
        progressBarDialog.show()

        Log.e("id", PreferenceManager().getStudent_ID(ncontext).toString())
        Log.e("type", PreferenceManager().getLanguagetype(ncontext).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(ncontext).toString())
            addProperty("language_type", PreferenceManager().getLanguagetype(ncontext).toString())

        }
        val call: Call<ReportsResponseModel> = ApiClient.getApiService().reportss(
            "Bearer " + PreferenceManager().getAccessToken(ncontext).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<ReportsResponseModel> {
            override fun onResponse(
                call: Call<ReportsResponseModel>, response: Response<ReportsResponseModel>
            ) {
                progressBarDialog.hide()

                Log.e("Response", response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status == 100) {

                        report_array.addAll(response.body()!!.reports)
                        if (report_array.isEmpty()) {
                            reportrec.layoutManager = LinearLayoutManager(ncontext)
                            val report_rec_adapter = ReportsAdapterList(ncontext, ArrayList())
                            reportrec.adapter = report_rec_adapter
                            Toast.makeText(mContext, "No data.", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            noDataTV.visibility = View.GONE
                            reportrec.layoutManager = LinearLayoutManager(ncontext)
                            val report_rec_adapter = ReportsAdapterList(ncontext, report_array)
                            reportrec.adapter = report_rec_adapter
                        }

                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                    }
                } else {/* val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
                     startActivity(intent)*/
                }
            }

            override fun onFailure(call: Call<ReportsResponseModel?>, t: Throwable) {
                progressBarDialog.hide()

                Toast.makeText(
                    ncontext, "Fail to get the data..", Toast.LENGTH_SHORT
                ).show()
                Log.e("succ", t.message.toString())
            }
        })/*Log.e("id", PreferenceManager().getStudent_ID(ncontext).toString())
        if (PreferenceManager().getStudent_ID(ncontext)!!.equals("1")){
            Log.e("Sucesee","successs")
            for (i in report_array.indices){
                if (report_array[i].student==1){
                    var nmodel=ReportModelFiltered(report_array[i].date,report_array[i].report_list,
                    report_array[i].report_list_url)
                    report_array_filtered.add(nmodel)
                }
            }
        }else{
            Log.e("Sucesee","failed")
            for (i in report_array.indices){
                if (report_array[i].student==2){
                    var nmodel=ReportModelFiltered(report_array[i].date,report_array[i].report_list,
                        report_array[i].report_list_url)
                    report_array_filtered.add(nmodel)
                }
            }
        }*/

        /* reportrec!!.layoutManager = LinearLayoutManager(ncontext)
         val report_rec_adapter =
             ReportsAdapterList(ncontext, report_array_filtered)
         reportrec!!.adapter = report_rec_adapter*/

    }
}