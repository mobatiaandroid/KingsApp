package com.example.kingsapp

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
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.activities.AbsenceActivity
import com.example.kingsapp.activities.calender.SchoolCalendarActivity
import com.example.kingsapp.activities.forms.FormsActivity
import com.example.kingsapp.activities.message.MessageFragment
import com.example.kingsapp.activities.parentessentials.ParentEssentialsActivity
import com.example.kingsapp.activities.reports.ReportsActivity
import com.example.kingsapp.activities.student_planner.StudentPlannerActivity
import com.example.kingsapp.activities.timetable.TimeTableActivity
import com.example.kingsapp.adapter.StudentListAdapter
import com.example.kingsapp.calender.CalenderActivity
import com.example.kingsapp.fragment.HomeFragment
import com.example.kingsapp.fragment.about_us.AboutusFragment
import com.example.kingsapp.fragment.contact.ContactFragment
import com.example.kingsapp.fragment.setting.SettingFragment
import com.example.kingsapp.manager.MyDragShadowBuilder
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.splash.WelcomeActivity
import com.example.nas_dubai_kotlin.activities.home.adapter.HomeListAdapter
import com.mobatia.bisad.fragment.contact_us.ContactUsFragment
import java.util.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(),AdapterView.OnItemLongClickListener {

    val manager = supportFragmentManager
    lateinit var shadowBuilder: MyDragShadowBuilder
    lateinit var drawerLayout: DrawerLayout
    lateinit var top_navigation_li : RelativeLayout
    lateinit var messageRel : RelativeLayout
    lateinit var settingRel  : RelativeLayout
    lateinit var aboutRel  : RelativeLayout
    lateinit var homeRel : RelativeLayout
    lateinit var profileRel : RelativeLayout
     lateinit var aboutImg : ImageView
     lateinit var aboutText : TextView
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
        aboutText = findViewById<View>(R.id.aboutText) as TextView
        aboutImg = findViewById<View>(R.id.aboutImg) as ImageView
        top_navigation_li = findViewById<View>(R.id.top_navigation_li) as RelativeLayout
        messageRel = findViewById<View>(R.id.messageRel) as RelativeLayout
        settingRel = findViewById<View>(R.id.settingRel) as RelativeLayout
        aboutRel = findViewById<View>(R.id.aboutRel) as RelativeLayout
        homeRel = findViewById<View>(R.id.homeRel) as RelativeLayout
        profileRel = findViewById<View>(R.id.profileRel) as RelativeLayout
        profileImg = findViewById(R.id.profileImg)as ImageView
        bottomLinear = findViewById<View>(R.id.bottomLinear) as LinearLayout
        menu_btn = findViewById<View>(R.id.hambrgr_btn) as ImageView
        student_profile = findViewById<View>(R.id.student_profile) as ImageView
        studentListRecyclerview = findViewById<View>(R.id.studentlistrec) as RecyclerView


       // studentListRecyclerViewArab = findViewById<View>(R.id.studentlistrecc) as RecyclerView
        lang_switch = findViewById<View>(R.id.switchlang) as Switch
        list= ArrayList()

        list.add(R.drawable.pic1)
        list.add(R.drawable.pic)
        list.add(R.drawable.pic3)
        name = mContext.resources.getStringArray(
            R.array.student_list)

        studentListRecyclerview = findViewById(R.id.studentlistrec)
        linearLayoutManager = LinearLayoutManager(mContext)

        top_navigation_li.visibility=View.VISIBLE
        bottomLinear.visibility = View.VISIBLE

        mListItemArray = mContext.resources.getStringArray(
            R.array.home_list_items)
        Log.e("home_list_items", mListItemArray.get(0).toString())
        mListImgArray = mContext!!.resources.obtainTypedArray(
            R.array.home_list_reg_icons)
        val width = (resources.displayMetrics.widthPixels / 1.7).toInt()
        val params = linearLayout
            .layoutParams as DrawerLayout.LayoutParams
        params.width = width

        linearLayout.layoutParams = params

        mListAdapter = HomeListAdapter(
            mContext, mListItemArray,mListImgArray!!,
            R.layout.custom_list_adapter, true)
        mHomeListView.adapter = mListAdapter
        mHomeListView.setBackgroundColor(
            resources.getColor(
                R.color.kings_blue
            ))

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
        if(PreferenceManager().getLanguage(mContext).equals("ar"))
        {

            homeText.setText(R.string.Home)
            messageText.setText(R.string.Message)
            aboutText.setText(R.string.About)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)
        }
        else
        {

            homeText.setText(R.string.Home)
            messageText.setText(R.string.Message)
            aboutText.setText(R.string.About)
            otherText.setText(R.string.Settings)
            contactText.setText(R.string.Contact)

        }

        homeRel.setOnClickListener {
            top_navigation_li.visibility=View.VISIBLE
            studentListRecyclerview.visibility=View.VISIBLE
            homeImg.setBackgroundResource(R.drawable.home4)
            homeText.setTextColor(Color.parseColor("#FFFFFFFF"));
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"));
            aboutImg.setBackgroundResource(R.drawable.about)
            aboutText.setTextColor(Color.parseColor("#7F8B93"));
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"));
            contactText.setTextColor(Color.parseColor("#7F8B93"));

            fragment = HomeFragment()
            initializeFragment(fragment)
        }

        messageRel.setOnClickListener {
            top_navigation_li.visibility=View.GONE
            studentListRecyclerview.visibility=View.GONE

            // bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            messageImg.setBackgroundResource(R.drawable.message_white)
            messageText.setTextColor(Color.parseColor("#FFFFFFFF"));
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"));
            aboutImg.setBackgroundResource(R.drawable.about)
            aboutText.setTextColor(Color.parseColor("#7F8B93"));
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"));
            contactText.setTextColor(Color.parseColor("#7F8B93"));

            fragment = MessageFragment()
            initializeFragment(fragment)
            //(mContext as MainActivity).overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up )
        }
        settingRel.setOnClickListener {
            studentListRecyclerview.visibility=View.GONE
            top_navigation_li.visibility=View.GONE
            otherImg.setBackgroundResource(R.drawable.setting_white)
            otherText.setTextColor(Color.parseColor("#FFFFFFFF"));
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"));
            aboutImg.setBackgroundResource(R.drawable.about)
            aboutText.setTextColor(Color.parseColor("#7F8B93"));
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"));
            contactText.setTextColor(Color.parseColor("#7F8B93"));

            //  bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            fragment = SettingFragment()
            initializeFragment(fragment)
        }

        aboutRel.setOnClickListener {
            top_navigation_li.visibility=View.GONE
            studentListRecyclerview.visibility=View.GONE
            aboutImg.setBackgroundResource(R.drawable.about_white)
            aboutText.setTextColor(Color.parseColor("#FFFFFFFF"));
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"));
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"));
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"));
            contactText.setTextColor(Color.parseColor("#7F8B93"));

            // bottomLinear.setBackgroundColor(R.drawable.bottom_bg)
            fragment = AboutusFragment()
            initializeFragment(fragment)

        }
        profileRel.setOnClickListener {
            top_navigation_li.visibility=View.GONE
            studentListRecyclerview.visibility=View.GONE
            profileImg.setBackgroundResource(R.drawable.contact)
            contactText.setTextColor(Color.parseColor("#FFFFFFFF"));
            aboutImg.setBackgroundResource(R.drawable.about)
            aboutText.setTextColor(Color.parseColor("#7F8B93"));
            otherImg.setBackgroundResource(R.drawable.settings)
            otherText.setTextColor(Color.parseColor("#7F8B93"));
            messageImg.setBackgroundResource(R.drawable.message4)
            messageText.setTextColor(Color.parseColor("#7F8B93"));
            homeImg.setBackgroundResource(R.drawable.home_icon_grey)
            homeText.setTextColor(Color.parseColor("#7F8B93"));
            fragment = ContactFragment()
            initializeFragment(fragment)
        }
        //navView = findViewById(R.id.navView)
        menu_btn.setOnClickListener(){
            drawerLayout.openDrawer(linearLayout)
            if (drawerLayout.isDrawerOpen(linearLayout)) {

                drawerLayout.closeDrawer(linearLayout)
            } else {
                drawerLayout.openDrawer(linearLayout)
            }
            //

        }
        student_profile.setOnClickListener() {
            if(flag)
            {
                studentListRecyclerview.visibility=View.VISIBLE
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                studentListRecyclerview.layoutManager = linearLayoutManager
                val studentAdapter = StudentListAdapter(mContext,list,name,studentListRecyclerview)
                studentListRecyclerview.setAdapter(studentAdapter)
            }
            else
            {

                studentListRecyclerview.visibility=View.GONE
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
                val intent = Intent(mContext, SchoolCalendarActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)


            }
            2 -> {
                val intent = Intent(mContext, AbsenceActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)
                //Toast.makeText(com.example.kingsapp.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            3 -> {
                val intent = Intent(mContext, TimeTableActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                //  Toast.makeText(com.example.kingsapp.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            4 -> {
                val intent = Intent(mContext, ParentEssentialsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

            }
            5 -> {
                val intent = Intent(mContext, StudentPlannerActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                //Toast.makeText(com.example.kingsapp.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            6 -> {
                val intent = Intent(mContext, ReportsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

            }
            7 -> {
                val intent = Intent(mContext, FormsActivity::class.java)
                startActivity(intent)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.example.kingsapp.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
            8 -> {
               showEmailHelpAlert(mContext)
                drawerLayout.closeDrawer(linearLayout)

                // Toast.makeText(com.example.kingsapp.fragment.mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
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
                    R.id.frame_container, fragment!!,
                    mListItemArray[position]
                )
                .addToBackStack(mListItemArray[position]).commitAllowingStateLoss()
            mHomeListView.setItemChecked(position, true)
            mHomeListView.setSelection(position)
            supportActionBar?.setTitle(R.string.null_value)
            if (drawerLayout.isDrawerOpen(linearLayout!!)) {
                drawerLayout.closeDrawer(linearLayout!!)
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
                    R.id.frame_container, fragment!!)
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
        val intent = Intent(mContext, MainActivity::class.java)
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
                           "class com.example.kingsapp.fragment.HomeFragment",
                           ignoreCase = true
                       )
                      )
               ) {
                   finish()
               } else if ((currentFragment
                       .javaClass
                       .toString()
                       .equals(
                           "class com.example.kingsapp.fragment.about_us.AboutusFragment",
                           ignoreCase = true
                       )
                           || currentFragment
                       .javaClass
                       .toString()
                       .equals(
                           "class com.example.kingsapp.fragment.setting.SettingFragment",
                           ignoreCase = true
                       )
                           || currentFragment
                       .javaClass
                       .toString()
                       .equals(
                           "class com.example.kingsapp.activities.message.MessageFragment",
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
                com.example.kingsapp.fragment.mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
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

    fun showSuccessAlert(context: Context,msgHead:String)
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
            moveTaskToBack(true);
            exitProcess(-1)
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}