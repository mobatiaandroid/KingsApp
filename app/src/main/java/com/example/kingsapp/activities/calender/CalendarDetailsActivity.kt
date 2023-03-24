package com.example.kingsapp.activities.calender

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.model.CalendarModel
import com.example.kingsapp.constants.CommonClass
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/*
class CalendarDetailsActivity :AppCompatActivity(){
    var txtmsg: TextView? = null
    var mDateTv: TextView? = null
    var mTimeTv: TextView? = null
    var img: ImageView? = null
    var studName: TextView? = null
    var home: ImageView? = null

    var eventArraylist: java.util.ArrayList<CalendarModel>? = null
   // var mStudentModel: java.util.ArrayList<StudentDetailModel>? = null
    var position = 0
    var add_cl: ImageView? = null
    var context: Context = this
    var mActivity: Activity? = null
    var header: RelativeLayout? = null
    var back: ImageView? = null
    var relativeHeader: RelativeLayout? = null

    var studentRecycleUnread: RecyclerView? = null



    //    String date="";
    lateinit var mWebView: WebView
    lateinit var mProgressRelLayout: RelativeLayout
    lateinit var mwebSettings: WebSettings
    private var loadingFlag = true
    private var mLoadUrl: String? = null
    private var mErrorFlag = false
   lateinit var anim: RotateAnimation
    var mContext: Context? = null
    var extras: Bundle? = null
    lateinit var msgTitle: TextView
    var title:String=" "
    var desc:String=" "
    var venu:String=" "
    var startdate:String=" "
    var enddate:String=" "


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calendar_details_layout)
        mContext = this
        initFn()
        getWebViewSettings()

        // onclick()
    }

    private fun getWebViewSettings() {
        mProgressRelLayout.setVisibility(View.GONE)
        anim = RotateAnimation(
            0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim!!.setInterpolator(context, android.R.interpolator.linear)
        anim.setRepeatCount(Animation.INFINITE)
        anim.setDuration(1000)
        mProgressRelLayout.setAnimation(anim)
        mProgressRelLayout.startAnimation(anim)
        mWebView.setFocusable(true)
        mWebView.setFocusableInTouchMode(true)
        mWebView.setBackgroundColor(0X00000000)
        mWebView.setVerticalScrollBarEnabled(false)
        mWebView.setHorizontalScrollBarEnabled(false)
        mWebView.setWebChromeClient(WebChromeClient())
        mwebSettings = mWebView.getSettings()
        mwebSettings!!.setSaveFormData(true)
        mwebSettings.setBuiltInZoomControls(false)
        mwebSettings.setSupportZoom(false)
        mwebSettings.setPluginState(WebSettings.PluginState.ON)
        mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mwebSettings.setJavaScriptCanOpenWindowsAutomatically(true)
        mwebSettings.setDomStorageEnabled(true)
        mwebSettings.setDatabaseEnabled(true)
        mwebSettings.setDefaultTextEncodingName("utf-8")
        mwebSettings.setLoadsImagesAutomatically(true)
        mwebSettings.setAllowFileAccessFromFileURLs(true)
        mWebView.getSettings().setAppCacheMaxSize((10 * 1024 * 1024).toLong()) // 5MB
        mWebView.getSettings().setAppCachePath(
            context.getCacheDir().getAbsolutePath()
        )
        mWebView.getSettings().setAllowFileAccess(true)
        mWebView.getSettings().setAppCacheEnabled(true)
        mWebView.getSettings().setJavaScriptEnabled(true)
        mWebView.getSettings()
            .setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                } else {
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    true
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                mProgressRelLayout.clearAnimation()
                mProgressRelLayout.setVisibility(View.GONE)
                if (CommonClass.isInternetAvailable(context) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    loadingFlag = false
                } else if (!CommonClass.isInternetAvailable(context) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
                    view.loadDataWithBaseURL(
                        "file:///android_asset/fonts/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    println("CACHE LOADING")
                    loadingFlag = false
                }
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            */
/*
             * (non-Javadoc)
             *
             * @see
             * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
             * , int, java.lang.String, java.lang.String)
             *//*

            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
                mProgressRelLayout.clearAnimation()
                mProgressRelLayout.setVisibility(View.GONE)
                if (CommonClass.isInternetAvailable(context)) {

                }
                super.onReceivedError(view, errorCode, description, failingUrl)
            }
        })
        mErrorFlag = mLoadUrl == ""
        if (mLoadUrl != null && !mErrorFlag) {
            println("NAS load url $mLoadUrl")
            mWebView.loadDataWithBaseURL(
                "file:///android_asset/fonts/",
                mLoadUrl!!,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
        } else {
            mProgressRelLayout.clearAnimation()
            mProgressRelLayout.setVisibility(View.GONE)

        }
    }

    private fun initFn() {
        name= ArrayList()
        name.add("Jane Mathewes")
        name.add("Esther Mathewes")
        studentRecycle=findViewById(R.id.studentRecycle)
       // back=findViewById(R.id.back)
        msgTitle = findViewById(R.id.msgTitle)

        linearLayoutManager = LinearLayoutManager(mcontext)
        extras = intent.extras
        title= extras!!.getString("tittle").toString()
        desc= extras!!.getString("des").toString()
        startdate= extras!!.getString("startdate").toString()
        enddate= extras!!.getString("enddate").toString()
        venu= extras!!.getString("venu").toString()

        mLoadUrl =
            "<!DOCTYPE html>\n" +
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
                    ".venue {"+

                    "font-family: SourceSansPro-Regular;"+
                    "font-size:16px;"+
                    "text-align:left;"+
                    "color:	#A9A9A9;"+
                    "}"+
                    ".title {"+
                    "font-family: SourceSansPro-Regular;"+
                    "font-size:16px;"+
                    "text-align:left;"+
                    "color:	#46C1D0;"+
                    "}"+
                    ".description {"+
                    "font-family: SourceSansPro-Semibold;"+
                    "text-align:justify;"+
                    "font-size:14px;"+
                    "color: #000000;"+
                    "}"+".date {"+
                    "font-family: SourceSansPro-Semibold;"+
                    "text-align:right;"+
                    "font-size:12px;"+

                    "color: #A9A9A9;"+
                    "}"+
                    "</style>\n"+"</head>"+
                    "<body>";

        msgTitle.setText(title)

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")

        val mEndDate: String = eventArraylist!![position].getEnddate()
        var date: Date? = null
        var endDate: Date? = null
        try {
            date = inputFormat.parse(mdate)
            endDate = inputFormat.parse(mEndDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val outputDateStr = outputFormat.format(date)
        val outputEndDateStr = outputFormat.format(endDate)

        mDateTv!!.text = outputDateStr

        val startFormat: DateFormat = SimpleDateFormat("H:mm:ss")
        val newStart: DateFormat = SimpleDateFormat("h:mm a")
        val starttime: String = eventArraylist!![position].getStarttime()
        var newSTime: Date? = null
        try {
            newSTime = startFormat.parse(starttime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val newstart = newStart.format(newSTime)
        val endtime: String = eventArraylist!![position].getEndtime()
        var newETime: Date? = null
        try {
            newETime = startFormat.parse(endtime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        if (eventArraylist!![position].getVenue().toString().length() > 0) {
            mLoadUrl =
                mLoadUrl + "<p class='venue'>Venue: " + eventArraylist!![position].getVenue() + "</p>"
        }
        mLoadUrl = mLoadUrl +
                "<p class='description'>" + eventArraylist!![position].getDescription() + "</p>"


        mLoadUrl = if (eventArraylist!![position].getDaterange().equalsIgnoreCase("")) {
            if (mdate.equals(mEndDate, ignoreCase = true)) {
                """
     $mLoadUrl<p class='date'>$outputDateStr</p><p class='date'>${mTimeTv!!.text}</p></body>
     </html>
     """.trimIndent()
            } else {
                """$mLoadUrl<p class='date'>$outputDateStr to $outputEndDateStr</p><p class='date'>${mTimeTv!!.text}</p></body>
</html>"""
            }
        } else {
            if (mdate.equals(mEndDate, ignoreCase = true)) {
                """
     $mLoadUrl<p class='date'>$outputDateStr</p><p class='date'>${mTimeTv!!.text}</p></body>
     </html>
     """.trimIndent()
            } else {
                """$mLoadUrl<p class='date'>$outputDateStr to $outputEndDateStr</p><p class='date'>${eventArraylist!![position].getDayrange()}</p><p class='date'>${mTimeTv!!.text}</p></body>
</html>"""
            }
        }
      */
/*  back.setOnClickListener {
            val intent = Intent(mcontext, SchoolCalendarActivity::class.java)
            startActivity(intent)
        }*//*

        */
/*linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        studentRecycle.layoutManager = linearLayoutManager
        val abseneadapter = StudentRecyclerCalenderAdapter(mcontext,name)
        studentRecycle.setAdapter(abseneadapter)*//*

    }
}*/
