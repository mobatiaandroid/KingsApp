package com.example.kingsapp.fragment.setting

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.RegisterAbsenceActivity
import com.example.kingsapp.activities.login.CreateAccountActivity
import com.example.kingsapp.fragment.setting.adapter.CommonAdapter
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.example.kingsapp.splash.WelcomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingFragment: Fragment() {
    lateinit var rootView: View
    lateinit var recyclerList: RecyclerView
    lateinit var mContext:Context
    lateinit var menu : ImageView
    lateinit var stringList:Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        rootView= inflater.inflate(R.layout.activity_settings, container, false)
        mContext =requireContext()

        initFn()
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun initFn() {
        recyclerList = (rootView.findViewById<View>(R.id.settingsRecycler) as? RecyclerView?)!!
        //recyclerList = findViewById(R.id.settingsRecycler)
        menu = ((rootView.findViewById<View>(R.id.menu) as? ImageView)!!)
        stringList = mContext.resources.getStringArray(
            R.array.setting_list)
        recyclerList.setHasFixedSize(true)

        recyclerList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerList.layoutManager = llm
        val adapter = CommonAdapter(mContext, stringList)
        recyclerList.adapter = adapter

        menu.setOnClickListener {
            val intent = Intent(com.example.kingsapp.fragment.mContext, MainActivity::class.java)
            startActivity(intent)
        }
        recyclerList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if (position == 4){
                    showChangePasswordPopUp()
                }
                if (position == 5){
                    showSuccessAlert(mContext,"Do you want to Logout?")
                }
            }

        })
    }
    private fun showChangePasswordPopUp() {

        val dialog = BottomSheetDialog(mContext, R.style.CustomBottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_change_password, null)
//        val title = view.findViewById<TextView>(R.id.selectTitle)

        dialog.setCancelable(true)
        dialog.setContentView(view)
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
            val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
}
