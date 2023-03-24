package com.example.kingsapp.activities.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfServiceActivity : AppCompatActivity(){
    lateinit var mContext: Context
    var message:String=""
    var url:String=""
    var date:String=""
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    private lateinit var webView: WebView
    var myFormatCalende:String="yyyy-MM-dd HH:mm:ss"
    private lateinit var progressDialog: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_message_detail)
        mContext=this

        initUI()
        callMessageDetailAPI()
        getSettings()

    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_info)
       // backRelative = findViewById(R.id.backRelative)
      //  logoClickImgView = findViewById(R.id.logoClickImgView)
        progressDialog = findViewById(R.id.progressDialog)
        webView = findViewById(R.id.webView)
        heading.text = "Terms of Services"
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })


        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
    }
    fun callMessageDetailAPI()
    {

                    var termsTitle="Intellectual Property"
                    var termsDescription="The design of this website and its content is © Kings Education Limited , all rights are reserved. Nothing in this website should be taken as conferring any licence or right to use any trademark displayed on this website without the prior written approval of the trademark owner. You may print off or download content as permitted under the fair dealing provisions of the Copyright Designs and Patents Act 1988 (as amended) (sections 28 to 30) for the purposes of viewing it on your computer, research for non-commercial purposes, private study, criticism, review and news reporting, provided that you do not alter it in any way and acknowledge us as the source of the content and the copyright owners. All other use or copying of any of the contents of this site, other than as expressly permitted by us or permitted by law, is prohibited."

                    var pushNotificationDetail="<!DOCTYPE html>\n" +
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
                            ".title {"+
                            "font-family: SourceSansPro-Regular;"+
                            "font-size:16px;"+
                            "text-align:left;"+
                            "color:	#001c53;"+
                            "text-align: ####TEXT_ALIGN####;"+
                            "}"+

                            ".description {"+
                            "font-family: SourceSansPro-Light;"+
                            "text-align:justify;"+
                            "font-size:14px;"+
                            "color: #000000;"+
                            "text-align: ####TEXT_ALIGN####;"+
                            "}"+
                            "</style>\n"+"</head>"+
                            "<body>"+
                            "<p class='title'>"+termsTitle+"</p>"+"<p class='description'>"+termsDescription+"</p>"+
                            "</body>\n</html>"
                    var htmlData=pushNotificationDetail
                    Log.e("HTML DATA",htmlData)
                    //  webView.loadData(htmlData,"text/html; charset=utf-8","utf-8")
                    webView.loadDataWithBaseURL("file:///android_asset/fonts/",htmlData,"text/html; charset=utf-8", "utf-8", "about:blank")


    }

    fun getSettings()
    {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.cacheMode= WebSettings.LOAD_NO_CACHE
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.allowFileAccess = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)


        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialog.visibility = View.VISIBLE
                println("testing2")
                if (newProgress == 100)
                {
                    println("testing1")
                    progressDialog.visibility = View.GONE

                }
            }
        }
    }
}

