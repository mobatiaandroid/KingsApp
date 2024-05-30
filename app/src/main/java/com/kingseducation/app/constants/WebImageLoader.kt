package com.kingseducation.app.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kingseducation.app.R

class WebImageLoader : AppCompatActivity() {
    lateinit var back: ImageView

    lateinit var context: Context
    lateinit var webview: ImageView
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
//        urltoshow = "https://docs.google.com/gview?embedded=true&url=$urltoshow"
//        urltoshow = "https://www.google.com/"
        Log.e("urltoshow", urltoshow)
        titleToShow = intent.getStringExtra("title").toString()
        back = findViewById(R.id.back)
        titleTextView.text = titleToShow
        progressBarDialog = ProgressBarDialog(context)
        webview = findViewById(R.id.webview)

        Glide.with(context)
            .load(urltoshow)
            .into(webview)

        back.setOnClickListener {
            finish()
        }


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
            System.out.print("url" + url)
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