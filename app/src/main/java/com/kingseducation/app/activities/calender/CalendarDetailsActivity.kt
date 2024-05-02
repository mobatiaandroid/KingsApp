package com.kingseducation.app.activities.calender

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.calender.adapter.StudentRecyclerCalenderAdapter
import com.kingseducation.app.activities.calender.model.CalendarList
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.activities.login.model.StudentListResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class CalendarDetailsActivity : AppCompatActivity() {
    var txtmsg: TextView? = null
    var mDateTv: TextView? = null
    lateinit var mTimeTv: TextView
    var img: ImageView? = null
    var studName: TextView? = null
    var home: ImageView? = null
    lateinit var studentList: ArrayList<StudentList>

    var eventArraylist: ArrayList<CalendarList>? = null

    // var mStudentModel: java.util.ArrayList<StudentDetailModel>? = null
    var position = 0
    var add_cl: ImageView? = null
    var mActivity: Activity? = null
    var header: RelativeLayout? = null
    lateinit var back: ImageView
    var relativeHeader: RelativeLayout? = null
    lateinit var mContext: Context
    var studentRecycleUnread: RecyclerView? = null

    //    String date="";
    lateinit var mWebView: WebView

    //lateinit var mProgressRelLayout: RelativeLayout
    lateinit var mwebSettings: WebSettings
    private var loadingFlag = true
    private var mLoadUrl: String? = null
    private var mErrorFlag = false
    lateinit var anim: RotateAnimation
    lateinit var studentRecycle: RecyclerView
    lateinit var datee: TextView
    var extras: Bundle? = null
    lateinit var msgTitle: TextView
    var title: String = " "
    var desc: String = " "
    var venu: String = " "
    var startdate: String = " "
    var enddate: String = " "
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.calendar_details_layout)
        mContext = this

        initFn()

        if (CommonClass.isInternetAvailable(mContext)) {
            studentListApiCall()
            Log.e("dckhsdchj", studentList.toString())

        } else {
            Toast.makeText(
                mContext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }
        // onclick()
    }

    private fun studentListApiCall() {
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list(
            "Bearer " +
                    PreferenceManager().getAccessToken(mContext).toString()
        )
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {

                Log.e("Response", response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100)) {

                        studentList.addAll(response.body()!!.student_list)
                        Log.e("StudentNameid", studentList.toString())
                        studentRecycle.layoutManager = linearLayoutManager
                        val abseneadapter = StudentRecyclerCalenderAdapter(mContext, studentList)
                        studentRecycle.adapter = abseneadapter
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                } else {
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
                Toast.makeText(
                    mContext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun getWebViewSettings() {
        // mProgressRelLayout.setVisibility(View.GONE)
        anim = RotateAnimation(
            0F, 360F, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.setInterpolator(mContext, android.R.interpolator.linear)
        anim.repeatCount = Animation.INFINITE
        anim.duration = 1000
        // mProgressRelLayout.setAnimation(anim)
        // mProgressRelLayout.startAnimation(anim)
        mWebView.isFocusable = true
        mWebView.isFocusableInTouchMode = true
        mWebView.setBackgroundColor(0X00000000)
        mWebView.isVerticalScrollBarEnabled = false
        mWebView.isHorizontalScrollBarEnabled = false
        mWebView.webChromeClient = WebChromeClient()
        mwebSettings = mWebView.settings
        mwebSettings.saveFormData = true
        mwebSettings.builtInZoomControls = false
        mwebSettings.setSupportZoom(false)
        mwebSettings.pluginState = WebSettings.PluginState.ON
        mwebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        mwebSettings.javaScriptCanOpenWindowsAutomatically = true
        mwebSettings.domStorageEnabled = true
        mwebSettings.databaseEnabled = true
        mwebSettings.defaultTextEncodingName = "utf-8"
        mwebSettings.loadsImagesAutomatically = true
        mwebSettings.allowFileAccessFromFileURLs = true
        //  mWebView.getSettings().setAppCacheMaxSize((10 * 1024 * 1024).toLong()) // 5MB
        /* mWebView.getSettings().setAppCachePath(
             mContext.getCacheDir().getAbsolutePath()
         *//*)*/
        mWebView.settings.allowFileAccess = true
        //  mWebView.getSettings().setAppCacheEnabled(true)
        mWebView.settings.javaScriptEnabled = true
        mWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        mWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.startsWith("http:") || url.startsWith("https:") || url.startsWith("www.")) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    true
                } else {
                    view.loadDataWithBaseURL(
                        "file:///android_asset/font/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    true
                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                // mProgressRelLayout.clearAnimation()
                // mProgressRelLayout.setVisibility(View.GONE)
                if (CommonClass.isInternetAvailable(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    view.loadDataWithBaseURL(
                        "file:///android_asset/font/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    loadingFlag = false
                } else if (!CommonClass.isInternetAvailable(mContext) && loadingFlag) {
                    view.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
                    view.loadDataWithBaseURL(
                        "file:///android_asset/font/",
                        mLoadUrl!!, "text/html; charset=utf-8", "utf-8", "about:blank"
                    )
                    println("CACHE LOADING")
                    loadingFlag = false
                }
            }


            /*
                 * (non-Javadoc)
                 *
                 * @see
                 * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView
                 * , int, java.lang.String, java.lang.String)
                 */
            override fun onReceivedError(
                view: WebView, errorCode: Int,
                description: String, failingUrl: String
            ) {
                // mProgressRelLayout.clearAnimation()
                // mProgressRelLayout.setVisibility(View.GONE)
                if (CommonClass.isInternetAvailable(mContext)) {

                }
                super.onReceivedError(view, errorCode, description, failingUrl)
            }
        }
        mErrorFlag = mLoadUrl == ""
        if (mLoadUrl != null && !mErrorFlag) {
            println("NAS load url $mLoadUrl")
            mWebView.loadDataWithBaseURL(
                "file:///android_asset/font/",
                mLoadUrl!!,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
        } else {
            //mProgressRelLayout.clearAnimation()
            // mProgressRelLayout.setVisibility(View.GONE)

        }
    }

    private fun initFn() {
        studentList = ArrayList()
        studentRecycle = findViewById(R.id.studentRecycle)
        /*name= ArrayList()
        name.add("Jane Mathewes")
        name.add("Esther Mathewes")*/

        back = findViewById(R.id.back)
        datee = findViewById(R.id.datee)
        msgTitle = findViewById(R.id.msgTitle)
        add_cl = findViewById(R.id.add_cl)
        studName = findViewById(R.id.studName)
        msgTitle = findViewById(R.id.msgTitle)
        studentRecycleUnread = findViewById(R.id.studentRecycle)
        txtmsg = findViewById(R.id.txt)
        mDateTv = findViewById(R.id.mDateTv)
        mTimeTv = findViewById(R.id.mTimeTv)
        mWebView = findViewById(R.id.webView)
        //  mProgressRelLayout = findViewById(R.id.progressDialog)

        extras = intent.extras
        title = extras!!.getString("tittle").toString()
        desc = extras!!.getString("des").toString()
        startdate = extras!!.getString("startdate").toString()
        enddate = extras!!.getString("enddate").toString()
        venu = extras!!.getString("venu").toString()
        linearLayoutManager = LinearLayoutManager(mContext)

        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        back.setOnClickListener {
            finish()
        }

        mLoadUrl =
            "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "\n" +
                    "@font-face {\n" +
                    "font-family:verdana_regular;" +
                    "src: url(verdana_regular.ttf);" +

                    "font-family:verdana_regular;" +
                    "src: url(verdana_regular.ttf);" +
                    "}" +
                    ".venue {" +

                    "font-family:verdana_regular;" +
                    "font-size:16px;" +
                    "text-align:left;" +
                    "color:	#A9A9A9;" +
                    "}" +
                    ".title {" +
                    "font-family:verdana_regular;" +
                    "font-size:16px;" +
                    "text-align:left;" +
                    "color:	#46C1D0;" +
                    "}" +
                    ".description {" +
                    "font-family:verdana_regular;" +
                    "text-align:justify;" +
                    "font-size:14px;" +
                    "color: #000000;" +
                    "}" + ".date {" +
                    "font-family:verdana_regular;" +
                    "text-align:right;" +
                    "font-size:12px;" +

                    "color: #A9A9A9;" +
                    "}" +
                    "</style>\n" + "</head>" +
                    "<body>"

        msgTitle.text = title

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy")
        //val mdate: String = eventArraylist!![position].getDateCalendar()

        var date: Date? = null
        var endDate: Date? = null
        try {
            date = inputFormat.parse(startdate)
            endDate = inputFormat.parse(enddate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        Log.e("date", date.toString())
        Log.e("endDate", endDate.toString())
        //val outputDateStr = outputFormat.format(date)
        val outputDateStr = outputFormat.format(date)
        val outputEndDateStr = outputFormat.format(endDate)
        Log.e("date", outputDateStr.toString())
        Log.e("endDate", outputEndDateStr.toString())
        datee.text = outputEndDateStr
        //mDateTv!!.text = "2023-02-23"

        /* val startFormat: DateFormat = SimpleDateFormat("H:mm:ss")
         val newStart: DateFormat = SimpleDateFormat("h:mm a")
         val starttime: String = eventArraylist!![position].start_date
         var newSTime: Date? = null
         try {
             newSTime = startFormat.parse(starttime)
         } catch (e: ParseException) {
             e.printStackTrace()
         }
         val newstart = newStart.format(newSTime)
         val endtime: String = eventArraylist!![position].end_date
         var newETime: Date? = null
         try {
             newETime = startFormat.parse(endtime)
         } catch (e: ParseException) {
             e.printStackTrace()
         }*/

        mLoadUrl =
            mLoadUrl + "<p class='venue'>Venue: " + venu + "</p>"

        mLoadUrl = mLoadUrl +
                "<p class='description'>" + desc + "</p>"




        mLoadUrl =
            """$mLoadUrl<p class='date'>$outputDateStr </p><p class='date'>${mTimeTv.text}</p></body>
</html>"""
        getWebViewSettings()
    }


}
