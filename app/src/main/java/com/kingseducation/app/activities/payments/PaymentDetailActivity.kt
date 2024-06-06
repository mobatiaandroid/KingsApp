package com.kingseducation.app.activities.payments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kingseducation.app.R
import com.kingseducation.app.activities.payments.model.AttributesModel
import com.kingseducation.app.activities.payments.model.PaymentInitiateModel
import com.kingseducation.app.activities.payments.model.StudentPaymentModel
import com.kingseducation.app.manager.PreferenceManager
import java.text.SimpleDateFormat
import java.util.Locale

class PaymentDetailActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var extra: Bundle
    lateinit var titleTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var dueDateTextView: TextView
    lateinit var amountTextView: TextView
    lateinit var invoiceNumberTextView: TextView
    lateinit var studentName: TextView
    lateinit var studentClass: TextView
    lateinit var studentImage: ImageView
    lateinit var backButton: ImageView
    lateinit var payTotalButton: Button
    lateinit var totalLinear: LinearLayout
    lateinit var amount: TextView

    var attributesData: AttributesModel = AttributesModel("","")
    var studentData: StudentPaymentModel = StudentPaymentModel(attributesData,"")
    var paymentInitiate: PaymentInitiateModel =
        PaymentInitiateModel("","",
        "","",attributesData,
        "","","",0.0,
        "","","","",
        "","","","",studentData,
        0.0,"",0.0)

    var attribute_type: String = ""
    var attribute_url: String = ""
    var Id: String = ""
    var Invoice_Number__c = ""
    var name = ""
    var Outstanding__c: Double = 0.0
    var Component__c: String = ""
    var Academic_Year__c = ""
    var Description__c = ""
    var Grade__c = ""
    var Invoice_Date__c = ""
    var Invoice_Due_Date__c = ""
    var Invoice_Unique_Number__c = ""
    var Student__r_pupil_code = ""
    var Total_Amount__c: Double = 0.0
    var Unique_Invoice__c = ""
    var Student__c = ""
    var Total_Amount_Before_Tax__c: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)
        context = this
        extra = intent.extras!!
        attribute_type = extra.getString("attribute_type").toString()
        attribute_url = extra.getString("attribute_url").toString()
        Id = extra.getString("Id").toString()
        name = extra.getString("name").toString()
        Invoice_Number__c = extra.getString("Invoice_Number__c").toString()
        Outstanding__c = extra.getDouble("Outstanding__c")
        Component__c = extra.getString("Component__c").toString()
        Academic_Year__c = extra.getString("Academic_Year__c").toString()
        Description__c = extra.getString("Description__c").toString()
        Student__c = extra.getString("Student__c").toString()
        Grade__c = extra.getString("Grade__c").toString()
        Invoice_Date__c = extra.getString("Invoice_Date__c").toString()
        Invoice_Due_Date__c = extra.getString("Invoice_Due_Date__c").toString()
        Invoice_Unique_Number__c = extra.getString("Invoice_Unique_Number__c").toString()
        Student__r_pupil_code = extra.getString("Student__r_pupil_code").toString()
        Total_Amount__c = extra.getDouble("Total_Amount__c")
        Unique_Invoice__c = extra.getString("Unique_Invoice__c").toString()
        Total_Amount_Before_Tax__c = extra.getDouble("Total_Amount_Before_Tax__c")
        attributesData.type = attribute_type
        attributesData.url = attribute_url
        studentData.Pupil_Code__c = Student__r_pupil_code
        studentData.attributes = attributesData
        paymentInitiate.Id = Id
        paymentInitiate.attributes = attributesData
        paymentInitiate.Invoice_Number__c = Invoice_Number__c
        paymentInitiate.Outstanding__c = Outstanding__c
        paymentInitiate.Component__c = Component__c
        paymentInitiate.Academic_Year__c = Academic_Year__c
        paymentInitiate.Description__c = Description__c
        paymentInitiate.Grade__c = Grade__c
        paymentInitiate.Invoice_Date__c = Invoice_Date__c
        paymentInitiate.Invoice_Due_Date__c = Invoice_Due_Date__c
        paymentInitiate.Student__c = Student__c
        paymentInitiate.Invoice_Unique_Number__c = Invoice_Unique_Number__c
        paymentInitiate.Student__r = studentData
        paymentInitiate.Total_Amount__c = Total_Amount__c
        paymentInitiate.Unique_Invoice__c = Unique_Invoice__c
        paymentInitiate.Total_Amount_Before_Tax__c = Total_Amount_Before_Tax__c
        paymentInitiate.name = name
        paymentInitiate.student_id = PreferenceManager().getStudent_ID(context).toString()
        paymentInitiate.app_version = "1.0"
        paymentInitiate.device_name = "Android"
        paymentInitiate.device_type = "2"
        initialiseUI()
    }

    private fun initialiseUI() {
        titleTextView = findViewById(R.id.descriptionTitle)
        descriptionTextView = findViewById(R.id.description)
        dueDateTextView = findViewById(R.id.duedate)
        amountTextView = findViewById(R.id.totalAmount)
        invoiceNumberTextView = findViewById(R.id.invoiceNumber)
        studentName = findViewById(R.id.studentName)
        studentClass = findViewById(R.id.studentClass)
        studentImage = findViewById(R.id.studentImage)
        studentName.text = PreferenceManager().getStudentName(context)
        studentClass.text = PreferenceManager().getStudentClass(context)
        payTotalButton = findViewById(R.id.payTotalButton)
        backButton = findViewById(R.id.back)
        totalLinear = findViewById(R.id.totalLinear)
        amount = findViewById(R.id.amount)

        if (Outstanding__c > 0) {
            payTotalButton.text = "Pay"
        } else {
            payTotalButton.isEnabled = false
            payTotalButton.text = "Paid"
        }
        if (!PreferenceManager().getStudentPhoto(context).equals("")) {
            if (!PreferenceManager().getStudentPhoto(context).equals("")) {
                val studentImg = PreferenceManager().getStudentPhoto(context).toString()
                if (studentImg.isNotEmpty()) {
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
        descriptionTextView.text = Description__c
        invoiceNumberTextView.text = Invoice_Unique_Number__c
        dueDateTextView.text = formatDate(Invoice_Due_Date__c)
        amountTextView.text = Total_Amount__c.toString()
        amount.text = Total_Amount__c.toString() + " AED"
        backButton.setOnClickListener {
            finish()
        }
        payTotalButton.setOnClickListener {
            initiatePayment()
        }

    }

    private fun initiatePayment() {
        if (paymentInitiate != null) {
            val intent = Intent(context, PaymentInitiateActivity::class.java)
            intent.putExtra("paymentInitiate", paymentInitiate)
            startActivity(intent)
        }
    }

    fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

    fun getColoredDescription(
        context: Context,
        fullText: String,
        partToColor: String,
        colorResId: Int
    ): SpannableString {
        val spannableString = SpannableString(fullText)
        val start = fullText.indexOf(partToColor)
        if (start != -1) {
            val end = start + partToColor.length
            val color = resources.getColor(colorResId)
            val colorSpan = ForegroundColorSpan(color)
            spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }
}