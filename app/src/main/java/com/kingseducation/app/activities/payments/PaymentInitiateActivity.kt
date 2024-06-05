package com.kingseducation.app.activities.payments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.kingseducation.app.R
import com.kingseducation.app.activities.payments.adapter.PaymentsAdapter
import com.kingseducation.app.activities.payments.model.PaymentInitiateModel
import com.kingseducation.app.activities.payments.model.PaymentInitiateResponseModel
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response

class PaymentInitiateActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var extras: Bundle
    lateinit var paymentInitiate: PaymentInitiateModel
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var webView: WebView
    val PAYMENT_TIME_OUT:Long = 13000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_initiate)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        context = this
        extras = intent.extras!!
        paymentInitiate = extras.getParcelable("paymentInitiate")!!
        initializeUI()
        Log.e("paymentInitiate.app_version", paymentInitiate.app_version)
        callInitiatePayment()
    }

    private fun initializeUI() {
        progressBarDialog = ProgressBarDialog(context)
        webView = findViewById(R.id.webView)
        webView.webViewClient = MyWebViewClient()
        setWebViewSettingsPrint()
    }
    private fun setWebViewSettingsPrint() {
        progressBarDialog.show()
        webView.settings.javaScriptEnabled = true
        webView.clearCache(true)
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.setSupportMultipleWindows(true)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                // Log.e("IT WORKING", "PAYMENT")
//                if(url.contains("http://naisakweb.mobatia.in:5000/payment/credit/callback"))
//                {
//                    Handler(Looper.myLooper()!!).postDelayed({
//                        finish()
//
//                    }, PAYMENT_TIME_OUT)
//                }
//                if(url.contains("http://gama.mobatia.in:8080/kingseducation/public/payment/verify")){
//                    // Log.e("PAYMEBNT", "SUCCESS" + url)
//                    var uri: Uri = Uri.parse(url)
//                    var reference:String? =  uri.getQueryParameter("reference")
//                    var PUN:String? =  uri.getQueryParameter("PUN")
//                    return true
//                }
//                else{
//                    return false
//                }
                return false
            }

            override fun onPageFinished(view: WebView, url: String)
            {
                super.onPageFinished(view, url)
               progressBarDialog.dismiss()
            }

        }
    }

    class MyWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            // This method is called when the page has finished loading.
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            // This method is called when an error occurs while loading the page.
        }
    }

    private fun callInitiatePayment() {
        progressBarDialog.show()
        val call: Call<PaymentInitiateResponseModel> = ApiClient.getApiService().initiatePayment(
            "Bearer " + PreferenceManager().getAccessToken(context).toString(), paymentInitiate
        )
        call.enqueue(object : retrofit2.Callback<PaymentInitiateResponseModel> {
            override fun onResponse(
                call: Call<PaymentInitiateResponseModel>,
                response: Response<PaymentInitiateResponseModel>
            ) {
                progressBarDialog.dismiss()

                if (response.body() != null) {
                    if (response.body()!!.status == 100) {
                        webView.loadUrl(response.body()!!.paymentUrl)
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, context)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<PaymentInitiateResponseModel?>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    context, "Fail to get the data..", Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, PaymentsListingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, PaymentsListingActivity::class.java)
        startActivity(intent)

    }
}