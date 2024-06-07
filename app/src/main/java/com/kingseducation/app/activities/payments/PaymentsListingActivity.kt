package com.kingseducation.app.activities.payments

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
import com.kingseducation.app.activities.absence.AbsenceDeatilsActivity
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.payments.adapter.PaymentsAdapter
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response

class PaymentsListingActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var paymentRecyclerView: RecyclerView
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var studentImage: ImageView
    lateinit var studentName: TextView
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var studentClass: TextView

    lateinit var backButton: ImageView
    lateinit var noDataTV: TextView
    var paymentList: ArrayList<PendingInvoiceResponseModel.Invoice> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_listing)
        context = this
        initializeUI()
        callPayments()

    }

    private fun callPayments() {

        progressBarDialog.show()
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
        }
        val call: Call<PendingInvoiceResponseModel> = ApiClient.getApiService().pendingInvoices(
            "Bearer " + PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<PendingInvoiceResponseModel> {
            override fun onResponse(
                call: Call<PendingInvoiceResponseModel>,
                response: Response<PendingInvoiceResponseModel>
            ) {
                progressBarDialog.dismiss()

                if (response.body() != null) {
                    if (response.body()!!.status == 100) {
                        paymentList = response.body()!!.invoices
                        if (paymentList.isEmpty()) {
                            paymentRecyclerView.layoutManager = linearLayoutManager
                            val studentListAdapter = PaymentsAdapter(context, ArrayList())
                            paymentRecyclerView.adapter = studentListAdapter
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            paymentRecyclerView.layoutManager = linearLayoutManager
                            val studentListAdapter = PaymentsAdapter(context, paymentList)
                            paymentRecyclerView.adapter = studentListAdapter
                            noDataTV.visibility = View.GONE
                        }
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, context)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<PendingInvoiceResponseModel?>, t: Throwable) {
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
        backButton = findViewById(R.id.back)
        noDataTV = findViewById(R.id.noDataTV)
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
        backButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
        paymentRecyclerView.addOnItemTouchListener(
            RecyclerItemListener(
                context, paymentRecyclerView,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {

                        val intent = Intent(context, PaymentDetailActivity::class.java).apply {
                            putExtra("attribute_type", paymentList[position].attributes.type)
                            putExtra("attribute_url", paymentList[position].attributes.url)
                            putExtra("Id", paymentList[position].id)
                            putExtra("name", paymentList[position].name)
                            putExtra("Invoice_Number__c", paymentList[position].invoiceNumber)
                            putExtra("Outstanding__c", paymentList[position].outstanding)
                            putExtra("Component__c", paymentList[position].component)
                            putExtra("Academic_Year__c", paymentList[position].academicYear)
                            putExtra("Description__c", paymentList[position].description)
                            putExtra("Student__c", paymentList[position].Student__c)
                            putExtra("Grade__c", paymentList[position].grade)
                            putExtra("Invoice_Date__c", paymentList[position].invoiceDate)
                            putExtra("Invoice_Due_Date__c", paymentList[position].invoiceDueDate)
                            putExtra("Invoice_Unique_Number__c", paymentList[position].invoiceUniqueNumber)
                            putExtra("Student__r_pupil_code", paymentList[position].student.pupilCode)
                            putExtra("Total_Amount__c", paymentList[position].totalAmount)
                            putExtra("Unique_Invoice__c", paymentList[position].uniqueInvoice)
                            putExtra("Total_Amount_Before_Tax__c", paymentList[position].totalAmountBeforeTax)
                        }
                        startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
    }
}