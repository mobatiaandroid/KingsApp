package com.kingseducation.app.activities.staff_directory

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.kingseducation.app.activities.payments.adapter.PaymentsAdapter
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel
import com.kingseducation.app.activities.staff_directory.model.StaffDirectoryResponseModel
import com.kingseducation.app.activities.teacher_contact.adapter.StaffDirectoryAdapter
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response

class StaffDirectoryListingActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var paymentRecyclerView: RecyclerView
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var studentImage: ImageView
    lateinit var studentName: TextView
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var studentClass: TextView
    lateinit var backButton: ImageView

    //    lateinit var backButton: ImageView
    lateinit var noDataTV: TextView
    var staffList: ArrayList<StaffDirectoryResponseModel.Staff> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_directory_listing)
        context = this
        initializeUI()
        callStaffDirectory()
    }

    private fun callStaffDirectory() {
        progressBarDialog.show()
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
        }
        val call: Call<StaffDirectoryResponseModel> = ApiClient.getApiService().staffDirectory(
            "Bearer " + PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<StaffDirectoryResponseModel> {
            override fun onResponse(
                call: Call<StaffDirectoryResponseModel>,
                response: Response<StaffDirectoryResponseModel>
            ) {
                progressBarDialog.dismiss()

                if (response.body() != null) {
                    if (response.body()!!.status == 100) {
                        staffList = response.body()!!.staffList
                        if (staffList.isEmpty()) {
                            paymentRecyclerView.layoutManager = linearLayoutManager
                            val studentListAdapter = StaffDirectoryAdapter(context, ArrayList())
                            paymentRecyclerView.adapter = studentListAdapter
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            paymentRecyclerView.layoutManager = linearLayoutManager
                            val studentListAdapter = StaffDirectoryAdapter(context, staffList)
                            paymentRecyclerView.adapter = studentListAdapter
                            noDataTV.visibility = View.GONE
                        }
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, context)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<StaffDirectoryResponseModel?>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    context, "Fail to get the data..", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initializeUI() {
        progressBarDialog = ProgressBarDialog(context)
        paymentRecyclerView = findViewById(R.id.paymentRecycler)
        studentImage = findViewById(R.id.studentImage)
        studentName = findViewById(R.id.studentName)
        studentClass = findViewById(R.id.studentClass)
        studentName.text = PreferenceManager().getStudentName(context)
        studentClass.text = PreferenceManager().getStudentClass(context)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        noDataTV = findViewById(R.id.noDataTV)
        backButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
        if (!PreferenceManager().getStudentPhoto(context).equals("")) {
            if (!PreferenceManager().getStudentPhoto(context).equals("")) {
                val studentImg = PreferenceManager().getStudentPhoto(context).toString()
                if (studentImg != null && studentImg.isNotEmpty()) {
                    val glideUrl = GlideUrl(
                        studentImg,
                        LazyHeaders.Builder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + PreferenceManager().getAccessToken(context)
                                    .toString()
                            )
                            .build()
                    )

                    Glide.with(context)
                        .load(glideUrl)
                        .transform(CircleCrop()) // Apply circular transformation
                        .placeholder(R.drawable.profile_photo)
                        .error(R.drawable.profile_photo)
                        .into(studentImage)
                } else {
                    Toast.makeText(context, "Image empty", Toast.LENGTH_SHORT).show()
                }
            } else {
                studentImage.setImageResource(R.drawable.profile_photo)
            }
        } else {
            studentImage.setImageResource(R.drawable.profile_photo)
        }
    }
}