package com.kingseducation.app.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R

class WebViewActivity: AppCompatActivity() {
    lateinit var back: ImageView

    lateinit var context: Context
    lateinit var webview: WebView
    var urltoshow: String = ""
    var titleToShow: String = ""
    lateinit var logoclick: ImageView
    lateinit var titleTextView: TextView
    lateinit var progressBarDialog: ProgressBarDialog

    @SuppressLint("SetJavaScriptEnabled", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_loader)
        context = this
        titleTextView = findViewById(R.id.titleTextView)
        logoclick = findViewById(R.id.logoclick)
        urltoshow = intent.getStringExtra("webview_url").toString()
        urltoshow = "$urltoshow"
//        urltoshow = "https://www.google.com/"
        Log.e("urltoshow", urltoshow)
        titleToShow = intent.getStringExtra("title").toString()
        back = findViewById(R.id.back)
        titleTextView.text = titleToShow
        progressBarDialog = ProgressBarDialog(context)
        webview = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.settings.loadsImagesAutomatically = true
        webview.setBackgroundColor(Color.TRANSPARENT)
        webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        webview.webViewClient = MyWebViewClient(this)

        back.setOnClickListener {
            finish()
        }


        webview.loadUrl(urltoshow)
        Log.e("LOADINGURL==>",urltoshow)

        webview.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressBarDialog.show()
                if (newProgress == 100) {
                    progressBarDialog.hide()
                    back.visibility = View.VISIBLE
                    Log.e("web_view", view.toString())

                }
            }
        }
        val settings = webview.settings
        settings.domStorageEnabled = true

    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString()
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            System.out.print("url"+url)
            return true
        }
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            Log.e("error", error.toString())
            // Toast.makeText(activity, "Got Error! $error", Toast.LENGTH_SHORT).show()

        }

    }
}