package com.kingseducation.app.activities.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.nas_dubai_kotlin.activities.home.adapter.HomeListAdapter
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kingseducation.app.R
import com.kingseducation.app.activities.absence.AbsenceActivity
import com.kingseducation.app.activities.apps.AppsActivity
import com.kingseducation.app.activities.calender.SchoolCalendarActivity
import com.kingseducation.app.activities.early_pickup.EarlyPickupListActivity
import com.kingseducation.app.activities.forms.FormsActivity
import com.kingseducation.app.activities.home.model.HomeGuestrResponseModel
import com.kingseducation.app.activities.home.model.HomeUserResponseModel
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.activities.login.model.StudentListResponseModel
import com.kingseducation.app.activities.message.MessageFragment
import com.kingseducation.app.activities.parentessentials.ParentEssentialsActivity
import com.kingseducation.app.activities.reports.ReportsActivity
import com.kingseducation.app.activities.social_media.SocialMediaActivity
import com.kingseducation.app.activities.student_info.StudentInfoActivity
import com.kingseducation.app.activities.timetable.TimeTableActivity
import com.kingseducation.app.adapter.StudentListAdapter
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.fragment.HomeFragment
import com.kingseducation.app.fragment.contact.ContactFragment
import com.kingseducation.app.fragment.currentversion
import com.kingseducation.app.fragment.setting.SettingFragment
import com.kingseducation.app.fragment.versionfromapi
import com.kingseducation.app.manager.MyDragShadowBuilder
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.OnItemClickListener
import com.kingseducation.app.manager.recyclerviewmanager.addOnItemClickListener
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.system.exitProcess


class HomeActivity : AppCompatActivity(), AdapterView.OnItemLongClickListener {

    val manager = supportFragmentManager
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var drawerLayout: DrawerLayout
    lateinit var top_navigation_li: RelativeLayout
    lateinit var messageRel: ConstraintLayout
    lateinit var settingRel: ConstraintLayout
    lateinit var homeRel: ConstraintLayout
    lateinit var profileRel: ConstraintLayout
    lateinit var otherImg: ImageView
    lateinit var otherText: TextView
    lateinit var messageImg: ImageView
    lateinit var messageText: TextView
    lateinit var profileImg: ImageView
    lateinit var contactText: TextView
    lateinit var homeImg: ImageView
    lateinit var logo: ImageView

    lateinit var homeText: TextView
    lateinit var bottomLinear: ConstraintLayout
    val PERMISSION_REQUEST_CODE = 112

    //private lateinit var navView: NavigationView
    lateinit var menu_btn: ImageView
    lateinit var student_profile: ImageView
    lateinit var lang_switch: Switch
    lateinit var studentListRecyclerview: RecyclerView
    lateinit var mHomeListView: ListView
    var mListAdapter: HomeListAdapter? = null
    lateinit var mListItemArray: Array<String>
    lateinit var mContext: Context
    lateinit var linearLayout: LinearLayout
    lateinit var fragment: Fragment
    private var mListImgArray: TypedArray? = null
    var sPosition: Int = 0
    lateinit var list: ArrayList<Int>
    lateinit var name: Array<String>
    lateinit var linearLayoutManager: LinearLayoutManager
    var flag: Boolean = true
    lateinit var student_name: ArrayList<StudentList>
    lateinit var menuicon: ImageView

    //    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    var tokenM: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity)

        mContext = this
        PreferenceManager().setvalue(mContext, "")
        if (Build.VERSION.SDK_INT > 32) {
            if (!shouldShowRequestPermissionRationale("112")) {
                getNotificationPermission()
            }
        }
        loadLocate()
        initFn()
        showfragmenthome()
        studentListApiCall()

        if (PreferenceManager().getAccessToken(mContext).equals("")) {
            Log.e("Sucess", "Success")
        } else {
            Log.e("Failed", "Success")
            callhomeuserApi()
        }


    }

    private fun getNotificationPermission() {
        try {
            if (Build.VERSION.SDK_INT > 32) {
                ActivityCompat.requestPermissions(
                    this, arrayOf<String>(Manifest.permission.POST_NOTIFICATIONS),
                    PERMISSION_REQUEST_CODE
                )
            }
        } catch (e: Exception) {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                } else {
//                    Toast.makeText(mContext, "Please allow notifications permission to receive school notices.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initFn() {
        student_name = ArrayList()
        linearLayout = findViewById<View>(R.id.linearLayout) as LinearLayout
        drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        mHomeListView = findViewById<View>(R.id.homeList) as ListView
        menuicon = findViewById<View>(R.id.menuicon) as ImageView
        contactText = findViewById<View>(R.id.contactText) as TextView
        homeText = findViewById<View>(R.id.homeText) as TextView
        homeImg = findViewById<View>(R.id.homeImg) as ImageView
        messageText = findViewById<View>(R.id.messageText) as TextView
        messageImg = findViewById<View>(R.id.messageImg) as ImageView
        otherText = findViewById<View>(R.id.otherText) as TextView
        otherImg = findViewById<View>(R.id.otherImg) as ImageView
        top_navigation_li = findViewById<View>(R.id.top_navigation_li) as RelativeLayout
        messageRel = findViewById<View>(R.id.messageRel) as ConstraintLayout
        settingRel = findViewById<View>(R.id.settingRel) as ConstraintLayout
        homeRel = findViewById<View>(R.id.homeRel) as ConstraintLayout
        profileRel = findViewById<View>(R.id.profileRel) as ConstraintLayout
        profileImg = findViewById<ImageView>(R.id.profileImg)
        bottomLinear = findViewById<View>(R.id.bottomLinear) as ConstraintLayout
        menu_btn = findViewById<View>(R.id.hambrgr_btn) as ImageView
        student_profile = findViewById<View>(R.id.student_profile) as ImageView
        studentListRecyclerview = findViewById<View>(R.id.studentlistrec) as RecyclerView
        logo = findViewById(R.id.logo)
        if (PreferenceManager().getSchoolIdentifier(mContext).equals("KSD")) {
            logo.setBackgroundResource(R.drawable.kings_school_dubai_logo)
        } else if (PreferenceManager().getSchoolIdentifier(mContext).equals("KSAB")) {
            logo.setBackgroundResource(R.drawable.kings_school_al_barsha_logo)
        } else if (PreferenceManager().getSchoolIdentifier(mContext)
                .equals("KSNAS")
        ) {
            logo.setBackgroundResource(R.drawable.kings_school_nad_al_sheba_logo)
        } else {
            logo.setBackgroundResource(R.drawable.kings_school_dubai_logo)
        }
        /*FirebaseApp.initializeApp(mContext)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            Log.e("token Firebase", token.toString())
        })*/
        // studentListRecyclerViewArab = findViewById<View>(R.id.studentlistrecc) as RecyclerView
        lang_switch = findViewById<View>(R.id.switchlang) as Switch
        // PreferenceManager().setLanguagetype(mContext, "1")

        if (PreferenceManager().getLanguageschool(mContext).equals("2")) {
            lang_switch.visibility = View.VISIBLE
        } else {
            lang_switch.visibility = View.GONE
        }

        list = ArrayList()

        list.add(R.drawable.pic1)
        list.add(R.drawable.pic)
        list.add(R.drawable.pic3)
        name = mContext.resources.getStringArray(
            R.array.student_list
        )

        studentListRecyclerview = findViewById(R.id.studentlistrec)
        linearLayoutManager = LinearLayoutManager(mContext)

        top_navigation_li.visibility = View.VISIBLE
        bottomLinear.visibility = View.VISIBLE

        mListItemArray = mContext.resources.getStringArray(
            R.array.home_list_items
        )
        Log.e("home_list_items", mListItemArray.get(0).toString())
        mListImgArray = mContext.resources.obtainTypedArray(
            R.array.home_list_reg_icons
        )
        val width = (resources.displayMetrics.widthPixels / 1.7).toInt()
        val params = linearLayout
            .layoutParams as DrawerLayout.LayoutParams
        params.width = width

        linearLayout.layoutParams = params

        mListAdapter = HomeListAdapter(
            mContext, mListItemArray, mListImgArray!!,
            R.layout.custom_list_adapter, true
        )
        mHomeListView.adapter = mListAdapter
        mHomeListView.setBackgroundColor(
            resources.getColor(
                R.color.kings_blue
            )
        )

        mHomeListView.onItemLongClickListener = this

//        requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission(),
//            ActivityResultCallback<Boolean> { result ->
//                Log.e("result", result.toString())
//                if (result) {
//                    // PERMISSION GRANTED
//                    Log.e("result", result.toString())
//                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
//                } else {
//                    // PERMISSION NOT GRANTED
//                    Log.e("denied", result.toString())
//                    val snackbar = Snackbar.make(
//                        drawerLayout,
//                        "Notification Permission Denied",
//                        Snackbar.LENGTH_LONG
//                    )
//                        .setAction("Settings") {
//                            val intent = Intent()
//                            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.putExtra("app_package", mContext.packageName)
//                            intent.putExtra("app_uid", mContext.applicationInfo.uid)
//                            intent.putExtra(
//                                "android.provider.extra.APP_PACKAGE",
//                                mContext.packageName
//                            )
//                            startActivity(intent)
//                        }
//                    snackbar.setActionTextColor(Color.RED)
//
//                    val view = snackbar.view
//                    val tv = view
//                        .findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
//                    tv.setTextColor(Color.WHITE)
//                    snackbar.show()
//
//
//                    // Toast.makeText(mContext, String.valueOf(result), Toast.LENGTH_SHORT).show();
//                }
//            }
//        )
//        askForNotificationPermission()
        mHomeListView.setOnItemClickListener { parent, view, position, id ->
            display(position)

        }

        /*supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.title_bar)
        supportActionBar!!.elevation = 0f
        val view = supportActionBar!!.customView
        val toolbar = view.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0, 0)*/

        showChangeLang()
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            PreferenceManager().setLanguagetype(mContext, "2")

            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/times_new_roman.ttf")
            homeText.typeface = face
            messageText.typeface = face
            otherText.typeface = face
            contactText.typeface = face
            homeText.setText(R.string.Home)
            messageText.setText(R.string.parentcomms)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)
        } else {
            PreferenceManager().setLanguagetype(mContext, "1")
            homeText.setText(R.string.Home)
            messageText.setText(R.string.parentcomms)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)

        }

        homeRel.setOnClickListener {
            PreferenceManager().setvalue(mContext, "")
            homeImg.setBackgroundResource(R.drawable.home_clicked)
            homeText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            messageImg.setBackgroundResource(R.drawable.email)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))
            profileImg.setBackgroundResource(R.drawable.chatting)

            fragment = HomeFragment()
            initializeFragment(fragment)
            top_navigation_li.visibility = View.VISIBLE
            studentListRecyclerview.visibility = View.VISIBLE
        }

        messageRel.setOnClickListener {

            Log.e("setvalue", PreferenceManager().getvalue(mContext))
            // bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            messageImg.setBackgroundResource(R.drawable.email_clicked)
            messageText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home)
            homeText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))
            profileImg.setBackgroundResource(R.drawable.chatting)

            if (PreferenceManager().getAccessToken(mContext).equals("")) {
                if (PreferenceManager().getvalue(mContext).equals("0")) {
                    studentListRecyclerview.visibility = View.GONE
                    top_navigation_li.visibility = View.GONE
                } else {
                    studentListRecyclerview.visibility = View.VISIBLE
                    top_navigation_li.visibility = View.VISIBLE
                }
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                top_navigation_li.visibility = View.GONE
                studentListRecyclerview.visibility = View.GONE

                fragment = MessageFragment()
                initializeFragment(fragment)
            }

            //(mContext as MainActivity).overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up )
        }
        settingRel.setOnClickListener {
            PreferenceManager().setvalue(mContext, "0")
            studentListRecyclerview.visibility = View.GONE
            top_navigation_li.visibility = View.GONE
            otherImg.setBackgroundResource(R.drawable.settings_clicked)
            otherText.setTextColor(Color.parseColor("#FFFFFFFF"))
            messageImg.setBackgroundResource(R.drawable.email)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home)
            homeText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))
            profileImg.setBackgroundResource(R.drawable.chatting)


            //  bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            fragment = SettingFragment()
            initializeFragment(fragment)

        }


        profileRel.setOnClickListener {

            profileImg.setBackgroundResource(R.drawable.chatting_clicked)
            contactText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            messageImg.setBackgroundResource(R.drawable.email)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home)
            homeText.setTextColor(Color.parseColor("#7F8B93"))


            if (PreferenceManager().getAccessToken(mContext).equals("")) {
                if (PreferenceManager().getvalue(mContext).equals("0")) {
                    studentListRecyclerview.visibility = View.GONE
                    top_navigation_li.visibility = View.GONE
                } else {
                    studentListRecyclerview.visibility = View.VISIBLE
                    top_navigation_li.visibility = View.VISIBLE
                }
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    checkPermissionContactUs()


                } else {
                    top_navigation_li.visibility = View.GONE
                    studentListRecyclerview.visibility = View.GONE

                    fragment = ContactFragment()
                    initializeFragment(fragment)
                }

            }
        }
        studentListRecyclerview.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Log.e("id", PreferenceManager().getStudent_ID(mContext).toString())
                Log.e("schoolrec", PreferenceManager().getLanguageschool(mContext).toString())

                /*if(!PreferenceManager().getStudentPhoto(mContext).equals(""))
                {
                    Glide.with(mContext) //1
                        .load(" ")
                        .placeholder(R.drawable.profile_photo)
                        .error(R.drawable.profile_photo)
                        .skipMemoryCache(true) //2
                        .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                        .transform(CircleCrop()) //4
                        .into(student_profile)
                }
                else{
                    student_profile.setImageResource(R.drawable.profile_photo)
                }*/
            }

        })

        menuicon.setOnClickListener {
            drawerLayout.openDrawer(linearLayout)
            if (drawerLayout.isDrawerOpen(linearLayout)) {

                drawerLayout.closeDrawer(linearLayout)
            } else {
                drawerLayout.openDrawer(linearLayout)
            }
        }
        menu_btn.setOnClickListener {
            drawerLayout.openDrawer(linearLayout)
            if (drawerLayout.isDrawerOpen(linearLayout)) {

                drawerLayout.closeDrawer(linearLayout)
            } else {
                drawerLayout.openDrawer(linearLayout)
            }
            //

        }
        if (!PreferenceManager().getStudentPhoto(mContext).equals("")) {
            val glideUrl = GlideUrl(
                PreferenceManager().getStudentPhoto(mContext),
                LazyHeaders.Builder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + PreferenceManager().getAccessToken(mContext)
                            .toString()
                    )
                    .build()
            )

            Glide.with(mContext)
                .load(glideUrl)
                .transform(CircleCrop()) // Apply circular transformation
                .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                .error(R.drawable.profile_photo) // Image to display in case of error
                .into(student_profile)

        } else {
            student_profile.setImageResource(R.drawable.profile_photo)
        }
        student_profile.setOnClickListener {
            if (flag) {
                studentListRecyclerview.visibility = View.VISIBLE

                if (CommonClass.isInternetAvailable(mContext)) {
//                    student_name.clear()
                    if (PreferenceManager().getAccessToken(mContext).equals("")) {
                        Toast.makeText(
                            mContext, "This feature is only available for registered users.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

                        studentListRecyclerview.layoutManager = linearLayoutManager
                        val studentAdapter = StudentListAdapter(
                            mContext,
                            student_name,
                            studentListRecyclerview,
                            lang_switch
                        )
                        studentListRecyclerview.adapter = studentAdapter
                    }

                } else {
                    Toast.makeText(
                        mContext,
                        "Network error occurred. Please check your internet connection and try again later",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            } else {
                studentListRecyclerview.visibility = View.GONE
            }
            flag = !flag


        }
        if (CommonClass.isInternetAvailable(mContext)) {
            FirebaseApp.initializeApp(mContext)
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isComplete) {
                    val token: String = task.result.toString()
                    tokenM = token
                    calldeviceRegistrion(tokenM)
                    //callChangePasswordStaffAPI(URL_STAFF_CHANGE_PASSWORD, token)
                }
            }

        } else {
            Toast.makeText(
                mContext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun calldeviceRegistrion(tokenM: String) {
        var androidID = Settings.Secure.getString(
            this.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        Log.e("android_id", androidID)
        val call: Call<ResponseBody> = ApiClient.getApiService().devicereg(
            "Bearer " +
                    PreferenceManager().getAccessToken(mContext).toString(), "2",
            tokenM, androidID
        )
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                // progressBarDialog.hide()


            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                //  progressBarDialog.hide()

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

    //    private fun askForNotificationPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }
    private fun callhomeuserApi() {
        Log.e("token", PreferenceManager().getAccessToken(mContext).toString())
        val call: Call<HomeUserResponseModel> = ApiClient.getApiService().homeuser(
            "Bearer " + PreferenceManager().getAccessToken(
                mContext
            )
                .toString()
        )
        call.enqueue(object : retrofit2.Callback<HomeUserResponseModel> {
            override fun onResponse(
                call: Call<HomeUserResponseModel>,
                response: Response<HomeUserResponseModel>
            ) {
                Log.e("respon", response.body().toString())
                if (response.body() != null) {

                    if (response.body()!!.status.equals("100")) {
                        val username = response.body()!!.home.user_details.name
                        PreferenceManager().setuser_id(mContext, username)
                        Log.e(
                            "Username",
                            PreferenceManager().getuser_id(com.kingseducation.app.fragment.mContext)
                                .toString()
                        )
                        val useremail = response.body()!!.home.user_details.email
                        PreferenceManager().setUserCode(mContext, useremail)
                        PreferenceManager().setAppversion(
                            mContext,
                            response.body()!!.home.android_version
                        )
                        versionfromapi =
                            PreferenceManager().getAppVersion(mContext)!!.replace(".", "")
                        currentversion = currentversion.replace(".", "")

                        Log.e("APPVERSIONAPI:", versionfromapi)
                        Log.e("CURRENTVERSION:", currentversion)


                        if (!PreferenceManager().getAppVersion(mContext).equals("", true)) {
                            if (versionfromapi > currentversion) {
                                showforceupdate(mContext)

                            }
                        } else if (response.body()!!.status.equals("106")) {
                            val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        // CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                } else {
                    callguestApiCall()
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)

                }
            }

            override fun onFailure(call: Call<HomeUserResponseModel?>, t: Throwable) {
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

    private fun callguestApiCall() {
        val call: Call<HomeGuestrResponseModel> = ApiClient.getApiService().homeguest()
        call.enqueue(object : retrofit2.Callback<HomeGuestrResponseModel> {
            override fun onResponse(
                call: Call<HomeGuestrResponseModel>,
                response: Response<HomeGuestrResponseModel>
            ) {
                Log.e("respon", response.body().toString())
                if (response.body() != null) {

                    if (response.body()!!.status.equals("100")) {
                        PreferenceManager().setAccessToken(mContext, "")
                        PreferenceManager().setAppversion(
                            mContext,
                            response.body()!!.home.android_version
                        )
                        versionfromapi =
                            PreferenceManager().getAppVersion(mContext)!!.replace(".", "")
                        currentversion = currentversion.replace(".", "")

                        Log.e("APPVERSIONAPI:", versionfromapi)
                        Log.e("CURRENTVERSION:", currentversion)


                        if (!PreferenceManager().getAppVersion(mContext).equals("", true)) {
                            if (versionfromapi > currentversion) {
                                showforceupdate(mContext)

                            }
                        } else if (response.body()!!.status.equals("106")) {
                            val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                            startActivity(intent)
                        }

                    } else {
                        // CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                } else {
                    // callguestApiCall()
                }
            }

            override fun onFailure(call: Call<HomeGuestrResponseModel?>, t: Throwable) {
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
                if (response.body()!!.status.equals(100)) {
                    student_name.addAll(response.body()!!.student_list)


                    /*student_name.addAll(response.body()!!.student_list)
                    circleImageView!!.layoutManager = LinearLayoutManager(mContext)
                    val studentlist_adapter =
                        ChildSelectionAdapter(ncontext, student_name)
                    circleImageView!!.adapter = studentlist_adapter*/
                } else {
                    CommonClass.checkApiStatusError(response.body()!!.status, mContext)
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

    fun showforceupdate(mContext: Context) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_updateversion)
        val btnUpdate =
            dialog.findViewById<View>(R.id.btnUpdate) as Button

        btnUpdate.setOnClickListener {
            dialog.dismiss()
            val appPackageName =
                mContext.packageName
            try {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )

            } catch (e: android.content.ActivityNotFoundException) {
                mContext.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }

        }
        dialog.show()
    }

    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        p3: Long
    ): Boolean {
        Log.e("Success", "Success")
        shadowBuilder = MyDragShadowBuilder(view)
        sPosition = position
        val selecteditem = parent?.getItemIdAtPosition(position)
        view?.setBackgroundColor(Color.parseColor("#001c53"))
        val data = ClipData.newPlainText("", "")
        view?.startDrag(data, shadowBuilder, view, 0)
        view!!.visibility = View.VISIBLE
        drawerLayout.closeDrawer(linearLayout)
        return false
    }

    private fun display(position: Int) {

        if (PreferenceManager().getAccessToken(mContext).equals("")) {
            if (position == 0) {
                fragment = HomeFragment()
                replaceFragmentsSelected(position)
            } else if (position == 1) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)


            } else if (position == 2) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)
                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 3) {


                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)
                //  Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 4) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)

            } else if (position == 5) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)

                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 6) {

                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)

            } else if (position == 7) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)
                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 8) {

                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)
                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 9) {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    mContext, "This feature is only available for registered users.",
                    Toast.LENGTH_SHORT
                ).show()
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (position == 0) {
                fragment = HomeFragment()
                replaceFragmentsSelected(position)
            } else if (position == 1) {
                val intent = Intent(mContext, StudentInfoActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)


            } else if (position == 2) {
                PreferenceManager().setFromYearView(mContext, "0")
                val intent = Intent(mContext, SchoolCalendarActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)
                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 3) {
                studentListRecyclerview.visibility = View.GONE
                top_navigation_li.visibility = View.GONE
                fragment = MessageFragment()
                replaceFragmentsSelected(position)
                drawerLayout.closeDrawer(linearLayout)


                //  Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
//            else if (position == 4) {
//                val intent = Intent(mContext, TeacherContactActivity::class.java)
//                startActivity(intent)
//                drawerLayout.closeDrawer(linearLayout)
//
//                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
//            }
            else if (position == 4) {

                val intent = Intent(mContext, AbsenceActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)


            } else if (position == 5) {

                val intent = Intent(mContext, EarlyPickupListActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)


                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 6) {

                val intent = Intent(mContext, TimeTableActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)


            } else if (position == 7) {
                val intent = Intent(mContext, ParentEssentialsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 8) {
                val intent = Intent(mContext, ReportsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 9) {
                val intent = Intent(mContext, FormsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 10) {
                val intent = Intent(mContext, AppsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else if (position == 11) {
                val intent = Intent(mContext, SocialMediaActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            } else {
                if (ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        mContext,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    checkPermissionContactUs()


                } else {
                    studentListRecyclerview.visibility = View.GONE
                    top_navigation_li.visibility = View.GONE
                    fragment = ContactFragment()
                    replaceFragmentsSelected(position)
                    drawerLayout.closeDrawer(linearLayout)
                }


                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkPermissionContactUs() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
//            || ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NOTIFICATION_POLICY) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
//                    ,
//                    Manifest.permission.ACCESS_NOTIFICATION_POLICY
                ),
                123
            )
        }
    }

    fun showfragmenthome() {
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }

    private fun replaceFragmentsSelected(position: Int) {
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.frame_container, fragment,
                    mListItemArray[position]
                )
                .addToBackStack(mListItemArray[position]).commitAllowingStateLoss()
            mHomeListView.setItemChecked(position, true)
            mHomeListView.setSelection(position)
            supportActionBar?.setTitle(R.string.null_value)
            if (drawerLayout.isDrawerOpen(linearLayout)) {
                drawerLayout.closeDrawer(linearLayout)
            }
        }
    }

    fun initializeFragment(fragment: Fragment?) {
        if (fragment != null) {
            val fragmentManager = supportFragmentManager
            fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up)
                .replace(
                    R.id.frame_container, fragment
                )
                .addToBackStack(null).commitAllowingStateLoss()

        }
    }

    fun showChangeLang() {


        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            PreferenceManager().setLanguagetype(mContext, "2")

            lang_switch.isChecked = false
            /* setLocate("ar")
             recreate()*/
        } else if (PreferenceManager().getLanguage(mContext).equals("en")) {
            PreferenceManager().setLanguagetype(mContext, "1")

            lang_switch.isChecked = true
            /*setLocate("en")
            recreate()*/
        }
        lang_switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->


            if (lang_switch.isChecked) {
                Log.e("english", "english")
                PreferenceManager().setLanguagetype(mContext, "1")
                setLocate("en")
                restartActivity()
                lang_switch.isChecked = true

            } else {
                Log.e("arabic", "arabic")
                PreferenceManager().setLanguagetype(mContext, "2")
                setLocate("ar")
                restartActivity()
                lang_switch.isChecked = false
            }
        })


    }

    @SuppressLint("ObsoleteSdkInt")
    fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val locale1 = Locale.getDefault().language
        Log.e("localelang", locale1)
        PreferenceManager().setLanguage(mContext, locale1)
        if (locale1 == "ar") {
            /* name.gravity = Gravity.RIGHT
             password.gravity = Gravity.RIGHT*/
        } else {
            /* name.gravity = Gravity.LEFT
             password.gravity = Gravity.LEFT*/
        }


        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()

    }

    fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        setLocate(language.toString())
    }

    private fun restartActivity() {
        val intent = Intent(mContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
        startActivity(intent)
    }

    /* override fun onStart() {
         // TODO Auto-generated method stub
         super.onStart()
         if (flag) {
             Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show()
         } else {
             Toast.makeText(getApplicationContext(), "Restart 2",Toast.LENGTH_SHORT).show();
             val i = Intent(mContext, MainActivity::class.java)
             finish()
             startActivity(i)
         }
     }*/
    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(linearLayout)
        } else {

            val fm = supportFragmentManager
            val currentFragment = fm
                .findFragmentById(R.id.frame_container)
            println(
                "nas current fragment "
                        + currentFragment!!.javaClass.toString()
            )
            if (currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.kingseducation.app.fragment.HomeFragment",
                        ignoreCase = true
                    )

            ) {
                if (PreferenceManager().getAccessToken(mContext).equals("")) {
                    finish()
                } else {
                    showSuccessAlert(
                        mContext,
                        resources.getString(R.string.do_you_want_account)
                    )
                }

            } else if (currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.kingseducation.app.fragment.contact.ContactFragment",
                        ignoreCase = true
                    )
                || currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.kingseducation.app.fragment.setting.SettingFragment",
                        ignoreCase = true
                    )
                || currentFragment
                    .javaClass
                    .toString()
                    .equals(
                        "class com.kingseducation.app.activities.message.MessageFragment",
                        ignoreCase = true
                    )
            ) {

            } else {
                supportFragmentManager.popBackStack()
            }

        }


        //

        /*if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(linearLayout)
        } else {
            if (supportFragmentManager.backStackEntryCount > 1) {
                val fm = supportFragmentManager
                val currentFragment = fm
                    .findFragmentById(R.id.frame_container)
                println(
                    "nas current fragment "
                            + currentFragment!!.javaClass.toString()
                )
                if ((currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.kingseducation.app.fragment.HomeFragment",
                            ignoreCase = true
                        )
                       )
                ) {
                    finish()
                } else if ((currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.kingseducation.app.fragment.about_us.AboutusFragment",
                            ignoreCase = true
                        )
                            || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.kingseducation.app.fragment.setting.SettingFragment",
                            ignoreCase = true
                        )
                            || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.kingseducation.app.activities.message.MessageFragment",
                            ignoreCase = true
                        )
                            || currentFragment
                        .javaClass
                        .toString()
                        .equals(
                            "class com.mobatia.naisapp.fragments.cca.CcaFragmentMain",
                            ignoreCase = true
                        )


                        )
                ) {
 //                    imageButton.setImageResource(R.drawable.hamburgerbtn);

                        val intent = Intent(mContext, MainActivity::class.java)
                        startActivity(intent)

                } else {
                    println("working *** * *  8")
                    supportFragmentManager.popBackStack()
                    //                    getSupportFragmentManager().popBackStackImmediate();
                }
            } else {

            }
        }*/


    }

    fun showEmailHelpAlert(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_send_email_dialog)
        //  var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        val dialogCancelButton = dialog.findViewById<View>(R.id.cancelButton) as TextView
        val submitButton = dialog.findViewById<View>(R.id.submitButton) as TextView
        val text_dialog = dialog.findViewById<View>(R.id.text_dialog) as EditText
        val text_content = dialog.findViewById<View>(R.id.text_content) as EditText

        //  text_dialog.text = message
        // alertHead.text = msgHead
        // iconImageView.setImageResource(R.color.white)
        dialogCancelButton.setOnClickListener { //   AppUtils.hideKeyBoard(mContext);
            val imm =
                com.kingseducation.app.fragment.mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(text_dialog.windowToken, 0)
            imm.hideSoftInputFromWindow(text_content.windowToken, 0)

            dialog.dismiss()
        }
        dialog.show()
    }

    fun showAlert(
        activity: Context, okBtnVisibility: Boolean
    ) {
        // custom dialog
        val dialog = Dialog(activity, R.style.NewDialog)
        dialog.setContentView(R.layout.custom_alert_dialog)
        dialog.setCancelable(false)

        // set the custom dialog components - text, image, button
        val text = dialog.findViewById<View>(R.id.text) as TextView
        text.text = "Do you want to exit"
        val sdk = Build.VERSION.SDK_INT
        val dialogCancelButton = dialog
            .findViewById<View>(R.id.dialogButtonCancel) as TextView
        dialogCancelButton.text = "cancel"
        //		if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
//			dialogCancelButton.setBackgroundDrawable(AppUtils
//					.getButtonDrawableByScreenCathegory(activity,
//							R.color.split_bg, R.color.list_selector));
//		} else {
//			dialogCancelButton.setBackground(AppUtils
//					.getButtonDrawableByScreenCathegory(activity,
//							R.color.split_bg, R.color.list_selector));
//		}
        // if button is clicked, close the custom dialog
        dialogCancelButton.setOnClickListener { dialog.dismiss() }
        val dialogOkButton = dialog
            .findViewById<View>(R.id.dialogButtonOK) as TextView
        dialogOkButton.visibility = View.GONE
        dialogOkButton.text = "ok"
        if (okBtnVisibility) {
            dialogOkButton.visibility = View.VISIBLE
            //			if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
//				dialogOkButton.setBackgroundDrawable(AppUtils
//						.getButtonDrawableByScreenCathegory(activity,
//								R.color.split_bg, R.color.list_selector));
//			} else {
//				dialogOkButton.setBackground(AppUtils
//						.getButtonDrawableByScreenCathegory(activity,
//								R.color.split_bg, R.color.list_selector));
//			}
            // if button is clicked, close the custom dialog
            dialogOkButton.setOnClickListener {
                dialog.dismiss()
                finish()
            }
        }
        dialog.show()
    }

    fun showSuccessAlert(context: Context, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView

        // var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as TextView
        text_dialog.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            moveTaskToBack(true)
            exitProcess(-1)
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}