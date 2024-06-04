package com.kingseducation.app.activities.payments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel

class PaymentDetailActivity : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)
        context = this
        val invoice = intent.getParcelableExtra<PendingInvoiceResponseModel.Invoice>("invoice")
        invoice?.let {
            Log.e("invoice", it.description.toString())
        }
    }
}