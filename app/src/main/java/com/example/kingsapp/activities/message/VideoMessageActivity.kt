package com.example.kingsapp.activities.message

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebSettings.PluginState
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

private lateinit var relativeHeader: RelativeLayout
private lateinit var backRelative: RelativeLayout
private lateinit var logoClickImgView: ImageView
private lateinit var btn_left: ImageView
private lateinit var heading: TextView
private lateinit var textcontent: WebView
private lateinit var webView: WebView
//private lateinit var proWebView: ProgressBar
class VideoMessageActivity : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var dateText:TextView
    lateinit var timeText:TextView
    var id:String=""
    var title:String=""
    var idApi:String=""
    var titleApi:String=""
    var message:String=""
    var url:String=""
    var date:String=""
    var createdate:String=""

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.messge_activity_video_layout)
        mContext=this

       // id=intent.getStringExtra("id").toString()
        title=intent.getStringExtra("title").toString()
        createdate=intent.getStringExtra("createdate").toString()
        url=intent.getStringExtra("url").toString()
        initUI()
        callMessageDetailAPI()
       // getSettings()
    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_info)
        textcontent = findViewById(R.id.txtContent)
        webView = findViewById(R.id.webView)
        dateText=findViewById(R.id.textview1)
        timeText=findViewById(R.id.timetextview)
        //proWebView = findViewById(R.id.proWebView)
       // backRelative = findViewById(R.id.backRelative)
        textcontent.visibility= View.INVISIBLE
        heading.text = "Messages"

        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })


//        val aniRotate: Animation =
//            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
//        progressDialog.startAnimation(aniRotate)
    }
    fun callMessageDetailAPI()
    {
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
        val outputFormatdate: DateFormat = SimpleDateFormat("dd-MMMM-yyyy")
        val inputDateStr = createdate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = outputFormat.format(date)
        val outputDateStr1: String = outputFormatdate.format(date)
        dateText.setText(outputDateStr1)
        timeText.setText(outputDateStr)
                    var pushNotificationDetail="<!DOCTYPE html>\n"+
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
                            ".title {" +
                            "font-family: verdana_regular;" +
                            "font-size:16px;" +
                            "text-align:left;" +
                            "color:	#46C1D0;" +
                            "text-align: ####TEXT_ALIGN####;" +
                            "}" +
                            ".description {" +
                            "font-family:verdana_regular;" +
                            "text-align:justify;" +
                            "font-size:14px;" +
                            "color: #000000;" +
                            "text-align: ####TEXT_ALIGN####;" +
                            "}" +
                            "</style>\n" + "</head>" +
                            "<body>" +
                            "<p class='title'>"+title

                    pushNotificationDetail=pushNotificationDetail+ "<p class='description'>" +outputDateStr1 +" "+outputDateStr+  "</p>"
                    if (!url.equals(""))
                    {
                        pushNotificationDetail=pushNotificationDetail+"<center><img src='" + url + "'width='100%', height='auto'>"
                    }
                    pushNotificationDetail=pushNotificationDetail+"</body>\n</html>"
                  //  webView.webViewClient = HelloWebViewClient()
                        webView.settings.javaScriptEnabled = true
                    webView.settings.javaScriptEnabled = true
                    webView.settings.pluginState =PluginState.ON
                    webView.settings.builtInZoomControls = false
                    webView.settings.displayZoomControls = true
                  //  webView.webViewClient = HelloWebViewClient()
                    textcontent.settings.javaScriptEnabled = true
                    textcontent.settings.pluginState = PluginState.ON
                    textcontent.settings.builtInZoomControls = false
                    textcontent.settings.displayZoomControls = true

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }
                    textcontent.loadData(
                        pushNotificationDetail,
                        "text/html; charset=utf-8",
                        "utf-8"
                    )
                    /*var frameVideo= "<html>" + "<br><iframe width=\"320\" height=\"250\" src=\""
                    var url_Video= frameVideo+url+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
                    var urlYoutube=url_Video
                   Log.e("urlYoutube",urlYoutube)
                    webView.loadData(urlYoutube, "text/html", "utf-8")*/


        var frameVideo= "<html>" + "<br><iframe width=\"350\" height=\"250\" src=\""
                    var url_Video= frameVideo+url+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>"
                    var urlYoutube=url_Video.replace("watch?v=", "embed/")
                   Log.e("urlYoutube",urlYoutube)
        //val urlstring="<html><br><iframe width=\"320\" height=\"250\" src=\"https://www.youtube.com/embed/E5S9UZ8Fd_s\" frameborder=\"5\" allowfullscreen></iframe></body></html>"
        webView.loadData(urlYoutube,"text/html", "utf-8");


        //  webView.loadUrl("https://www.youtube.com/watch?v=E5S9UZ8Fd_s")


        // proWebView.visibility = View.VISIBLE

                }


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
               // proWebView.visibility = View.VISIBLE
                println("testing2")
                if (newProgress == 100)
                {
                    println("testing1")
                  //  proWebView.visibility = View.GONE

                }
            }
        }
    }

    private class HelloWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url)
           // proWebView.visibility = View.GONE
          //  textcontent.visibility = View.VISIBLE
        }
    }
