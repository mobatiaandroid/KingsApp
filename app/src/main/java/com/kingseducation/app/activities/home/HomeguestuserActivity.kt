package com.kingseducation.app.activities.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.nas_dubai_kotlin.activities.home.adapter.HomeListAdapter
import com.kingseducation.app.R
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.activities.login.model.StudentListResponseModel
import com.kingseducation.app.activities.message.MessageFragment
import com.kingseducation.app.adapter.StudentListAdapter
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.fragment.HomeFragment
import com.kingseducation.app.fragment.contact.ContactFragment
import com.kingseducation.app.fragment.setting.SettingFragment
import com.kingseducation.app.manager.MyDragShadowBuilder
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.OnItemClickListener
import com.kingseducation.app.manager.recyclerviewmanager.addOnItemClickListener
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.system.exitProcess

class HomeguestuserActivity: AppCompatActivity(),AdapterView.OnItemLongClickListener {
    val manager = supportFragmentManager
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var drawerLayout: DrawerLayout
    lateinit var top_navigation_li : RelativeLayout
    lateinit var messageRel : RelativeLayout
    lateinit var settingRel  : RelativeLayout
    lateinit var homeRel : RelativeLayout
    lateinit var profileRel : RelativeLayout
    lateinit var otherImg : ImageView
    lateinit var otherText : TextView
    lateinit var messageImg : ImageView
    lateinit var messageText : TextView
    lateinit var profileImg : ImageView
    lateinit var contactText : TextView
    lateinit var homeImg : ImageView

    lateinit var homeText : TextView
    lateinit var bottomLinear : LinearLayout
    //private lateinit var navView: NavigationView
    lateinit var menu_btn: ImageView
    lateinit var student_profile : ImageView
    lateinit var lang_switch : Switch
    lateinit var studentListRecyclerview : RecyclerView
    lateinit var mHomeListView: ListView
    var mListAdapter: HomeListAdapter? = null
    lateinit var mListItemArray:Array<String>
    lateinit var mContext: Context
    lateinit var linearLayout: LinearLayout
    lateinit var fragment: Fragment
    private var mListImgArray: TypedArray? = null
    var sPosition: Int = 0
    lateinit var list:ArrayList<Int>
    lateinit var name:Array<String>
    lateinit var linearLayoutManager: LinearLayoutManager
    var flag:Boolean = true
    lateinit var student_name: ArrayList<StudentList>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_activity)

        mContext=this
        loadLocate()
        initFn()
        showfragmenthome()

    }

    @SuppressLint("ResourceAsColor")
    private fun initFn() {
        student_name = ArrayList()
        linearLayout = findViewById<View>(R.id.linearLayout) as LinearLayout
        drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        mHomeListView = findViewById<View>(R.id.homeList) as ListView

        contactText = findViewById<View>(R.id.contactText) as TextView
        homeText = findViewById<View>(R.id.homeText) as TextView
        homeImg = findViewById<View>(R.id.homeImg) as ImageView
        messageText = findViewById<View>(R.id.messageText) as TextView
        messageImg = findViewById<View>(R.id.messageImg) as ImageView
        otherText = findViewById<View>(R.id.otherText) as TextView
        otherImg = findViewById<View>(R.id.otherImg) as ImageView
        top_navigation_li = findViewById<View>(R.id.top_navigation_li) as RelativeLayout
        messageRel = findViewById<View>(R.id.messageRel) as RelativeLayout
        settingRel = findViewById<View>(R.id.settingRel) as RelativeLayout
        homeRel = findViewById<View>(R.id.homeRel) as RelativeLayout
        profileRel = findViewById<View>(R.id.profileRel) as RelativeLayout
        profileImg = findViewById<ImageView>(R.id.profileImg)
        bottomLinear = findViewById<View>(R.id.bottomLinear) as LinearLayout
        menu_btn = findViewById<View>(R.id.hambrgr_btn) as ImageView
        student_profile = findViewById<View>(R.id.student_profile) as ImageView
        studentListRecyclerview = findViewById<View>(R.id.studentlistrec) as RecyclerView


        // studentListRecyclerViewArab = findViewById<View>(R.id.studentlistrecc) as RecyclerView
        lang_switch = findViewById<View>(R.id.switchlang) as Switch
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

            homeText.setText(R.string.Home)
            messageText.setText(R.string.Message)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)
        } else {

            homeText.setText(R.string.Home)
            messageText.setText(R.string.Message)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)

        }

        homeRel.setOnClickListener {
            top_navigation_li.visibility = View.VISIBLE
            studentListRecyclerview.visibility = View.VISIBLE
            homeImg.setBackgroundResource(R.drawable.home4)
            homeText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))

            fragment = HomeFragment()
            initializeFragment(fragment)
        }

        messageRel.setOnClickListener {
            top_navigation_li.visibility = View.GONE
            studentListRecyclerview.visibility = View.GONE

            // bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            messageImg.setBackgroundResource(R.drawable.message_white)
            messageText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))

            fragment = MessageFragment()
            initializeFragment(fragment)
            //(mContext as MainActivity).overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up )
        }
        settingRel.setOnClickListener {
            studentListRecyclerview.visibility = View.GONE
            top_navigation_li.visibility = View.GONE
            otherImg.setBackgroundResource(R.drawable.setting_white)
            otherText.setTextColor(Color.parseColor("#FFFFFFFF"))
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"))
            contactText.setTextColor(Color.parseColor("#7F8B93"))

            //  bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            fragment = SettingFragment()
            initializeFragment(fragment)
        }


        profileRel.setOnClickListener {
            top_navigation_li.visibility = View.GONE
            studentListRecyclerview.visibility = View.GONE
            profileImg.setBackgroundResource(R.drawable.contact)
            contactText.setTextColor(Color.parseColor("#FFFFFFFF"))
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"))
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"))
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"))
            fragment = ContactFragment()
            initializeFragment(fragment)
        }
        studentListRecyclerview.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Log.e("id", PreferenceManager().getStudent_ID(mContext).toString())

                Glide.with(mContext) //1
                    .load(R.drawable.profile_icon_grey)
                    .placeholder(R.drawable.profile_icon_grey)
                    .error(R.drawable.profile_icon_grey)
                    .skipMemoryCache(true) //2
                    .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                    .transform(CircleCrop()) //4
                    .into(student_profile)

            }

        })

        menu_btn.setOnClickListener {
            drawerLayout.openDrawer(linearLayout)
            if (drawerLayout.isDrawerOpen(linearLayout)) {

                drawerLayout.closeDrawer(linearLayout)
            } else {
                drawerLayout.openDrawer(linearLayout)
            }
            //

        }
        student_profile.setOnClickListener {
            if (flag) {
                studentListRecyclerview.visibility = View.VISIBLE

                if (CommonClass.isInternetAvailable(mContext)) {
                    student_name.clear()
                    studentListApiCall()
                } else {
                    Toast.makeText(
                        mContext,
                        "Network error occurred. Please check your internet connection and try again later",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            }
            else
            {

                studentListRecyclerview.visibility= View.GONE
                /*linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                studentListRecyclerview.layoutManager = linearLayoutManager
                val studentAdapter = StudentListAdapter(mContext,list,name)
                studentListRecyclerview.setAdapter(studentAdapter)*/
            }
            flag = !flag
/*if(PreferenceManager().getLanguage(mContext).equals("ar"))
{*/
            /*studentListRecyclerview.visibility=View.GONE
            studentListRecyclerViewArab.visibility=View.VISIBLE
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            studentListRecyclerViewArab.layoutManager = linearLayoutManager
            val studentAdapter = StudentListAdapterArab(mContext,list,name)
            studentListRecyclerViewArab.setAdapter(studentAdapter)*/
/*}
  else
{*/

//}

        }



    }
    private fun studentListApiCall() {
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString())
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                Log.e("Response",response.body().toString())
                if (response.body()!!.status.equals(100))
                {
                    student_name.addAll(response.body()!!.student_list)
                    PreferenceManager().setStudent_ID(mContext,
                        response.body()!!.student_list.get(0).id.toString()
                    )
                    Log.e("id", PreferenceManager().getStudent_ID(mContext).toString())
                    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                    studentListRecyclerview.layoutManager = linearLayoutManager
                    val studentAdapter = StudentListAdapter(
                        mContext,
                        student_name,
                        studentListRecyclerview,
                        lang_switch,
                    )
                    studentListRecyclerview.adapter = studentAdapter
                    /*student_name.addAll(response.body()!!.student_list)
                    circleImageView!!.layoutManager = LinearLayoutManager(mContext)
                    val studentlist_adapter =
                        ChildSelectionAdapter(ncontext, student_name)
                    circleImageView!!.adapter = studentlist_adapter*/
                }
                else
                {
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
    override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, p3: Long): Boolean {
        Log.e("Success","Success")
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
    private fun display(position:Int)
    {
        when (position) {
            0 -> {
                fragment = HomeFragment()
                replaceFragmentsSelected(position)
            }
            1 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)


            }
            2 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)
                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            3 -> {


                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)
                //  Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            4 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)

            }
            5 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)

                //Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            6 -> {

                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)

            }
            7 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)
                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            8 -> {

                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)
                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            9 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            10 -> {
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.kingseducation.app.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
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

            lang_switch.isChecked = false
            /* setLocate("ar")
             recreate()*/
        } else if (PreferenceManager().getLanguage(mContext).equals("en")) {
            lang_switch.isChecked = true
            /*setLocate("en")
            recreate()*/
        }
        lang_switch. setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->


            if (lang_switch.isChecked) {
                Log.e("english","english")
                setLocate("en")
                restartActivity()
                lang_switch.isChecked = true

            }
            else
            {
                Log.e("arabic","arabic")
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
        val locale1= Locale.getDefault().language
        Log.e("localelang",locale1)
        PreferenceManager().setLanguage(mContext,locale1)
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


        showSuccessAlert(mContext,"Do you want to exit?")

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

    fun showSuccessAlert(context: Context, msgHead:String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView

        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as TextView
        alertHead.text = msgHead
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