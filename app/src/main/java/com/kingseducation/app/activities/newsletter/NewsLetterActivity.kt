package com.kingseducation.app.activities.newsletter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.kingseducation.app.activities.newsletter.model.NewsLetterResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.PdfReaderActivity
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.WebImageLoader
import com.kingseducation.app.constants.WebViewActivity
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response

class NewsLetterActivity : AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var reportrec: RecyclerView
    lateinit var student_name: ArrayList<StudentList>
    lateinit var report_array: ArrayList<NewsLetterResponseModel.Newsletter>
    lateinit var report_array_filtered: ArrayList<NewsLetterResponseModel.Newsletter>
    lateinit var student_Name: TextView
    lateinit var studentclass: TextView
    lateinit var imagicon: ImageView
    lateinit var backarrow: ImageView
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView: TextView
    lateinit var noDataTV: TextView
    var studentName: String = ""
    var student_class: String = ""
    var studentImg: String = ""
    var studentId: String = ""
    var webLoadBaseUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_letter)
        ncontext = this
        initFn()
        if (CommonClass.isInternetAvailable(ncontext)) {

            callNewsletterListingAPI()
        } else {
            Toast.makeText(
                ncontext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    private fun callNewsletterListingAPI() {
        report_array = ArrayList()
        report_array_filtered = ArrayList()
        progressBarDialog.show()

        Log.e("id", PreferenceManager().getStudent_ID(ncontext).toString())
        Log.e("type", PreferenceManager().getLanguagetype(ncontext).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(ncontext).toString())
            addProperty("language_type", PreferenceManager().getLanguagetype(ncontext).toString())

        }
        val call: Call<NewsLetterResponseModel> = ApiClient.getApiService().newsletter(
            "Bearer " + PreferenceManager().getAccessToken(ncontext).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<NewsLetterResponseModel> {
            override fun onResponse(
                call: Call<NewsLetterResponseModel>, response: Response<NewsLetterResponseModel>
            ) {
                progressBarDialog.hide()

                Log.e("Response", response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status == 100) {

                        report_array.addAll(response.body()!!.newsletter)
                        if (report_array.isEmpty()) {
                            reportrec.layoutManager = LinearLayoutManager(ncontext)
                            val report_rec_adapter =
                                NewsletterAdapter(ncontext, ArrayList())
                            reportrec.adapter = report_rec_adapter
                            Toast.makeText(ncontext, "No data.", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            noDataTV.visibility = View.GONE
                            reportrec.layoutManager = LinearLayoutManager(ncontext)
                            val report_rec_adapter =
                                NewsletterAdapter(ncontext, report_array)
                            reportrec.adapter = report_rec_adapter
                        }

                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<NewsLetterResponseModel?>, t: Throwable) {
                progressBarDialog.hide()

                Toast.makeText(
                    ncontext, "Fail to get the data..", Toast.LENGTH_SHORT
                ).show()
                Log.e("succ", t.message.toString())
            }
        })

    }


    private fun initFn() {
        student_name = ArrayList()
        reportrec = findViewById(R.id.reportrec)
        report_array_filtered = ArrayList()
        student_Name = findViewById(R.id.studentName)
        studentclass = findViewById(R.id.studentClass)
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
            if (!PreferenceManager().getStudentPhoto(ncontext).equals("")) {
                studentImg = PreferenceManager().getStudentPhoto(ncontext).toString()
                if (studentImg != null && !studentImg.isEmpty()) {
                    val glideUrl = GlideUrl(
                        studentImg,
                        LazyHeaders.Builder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + PreferenceManager().getAccessToken(ncontext)
                                    .toString()
                            )
                            .build()
                    )

                    Glide.with(ncontext)
                        .load(glideUrl)
                        .transform(CircleCrop()) // Apply circular transformation
                        .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                        .error(R.drawable.profile_photo) // Image to display in case of error
                        .into(imagicon)
                } else {
                    Toast.makeText(ncontext, "Image empty", Toast.LENGTH_SHORT).show()
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

        reportrec.addOnItemTouchListener(
            RecyclerItemListener(
                ncontext, reportrec,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (report_array[position].url.endsWith(".pdf")) {
                            val intent = Intent(ncontext, PdfReaderActivity::class.java)
                            intent.putExtra("pdf_url", report_array[position].url)
                            intent.putExtra("pdf_title", report_array[position].title)
                            startActivity(intent)
                        } else if (report_array[position].url.endsWith(".png")) {
                            val intent = Intent(ncontext, WebImageLoader::class.java)
                            intent.putExtra("webview_url", report_array[position].url)
                            intent.putExtra("title", report_array[position].title)
                            startActivity(intent)
                        } else {
                            val intent = Intent(ncontext, WebViewActivity::class.java)
                            intent.putExtra("webview_url", report_array[position].url)
                            intent.putExtra("title", report_array[position].title)
                            startActivity(intent)
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )


        /* linearlayoutstudentlist.setOnClickListener {
             studentlist_popup(student_name)
             *//* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*//*

        }*/
    }
}