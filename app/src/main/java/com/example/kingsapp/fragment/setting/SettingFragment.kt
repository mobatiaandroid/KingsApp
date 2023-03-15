package com.example.kingsapp.fragment.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.RegisterAbsenceActivity
import com.example.kingsapp.activities.login.CreateAccountActivity
import com.example.kingsapp.fragment.setting.adapter.CommonAdapter
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.example.kingsapp.splash.WelcomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.mobatia.nasmanila.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

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
    @SuppressLint("MissingInflatedId")
    private fun showChangePasswordPopUp() {

        val dialog = BottomSheetDialog(mContext, R.style.CustomBottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_change_password, null)
        val current_passwrd = view.findViewById<TextInputEditText>(R.id.currentpasswd)
        val new_passwrd = view.findViewById<TextInputEditText>(R.id.newpasswd)
        val confirm_passwd = view.findViewById<TextInputEditText>(R.id.confirmpasswd)
        val submit_button = view.findViewById<Button>(R.id.button)

        dialog.setCancelable(true)
        dialog.setContentView(view)

        submit_button.setOnClickListener {
            if(current_passwrd.text.isNullOrBlank())
            {
                Toast.makeText(mContext, "Please enter the field !", Toast.LENGTH_SHORT).show()

            }
            else if(new_passwrd.text.isNullOrBlank())
            {
                Toast.makeText(mContext, "Please enter the Password !", Toast.LENGTH_SHORT).show()

            }
            else if(confirm_passwd.text.isNullOrBlank())
            {
                Toast.makeText(mContext, "Please enter the Password !", Toast.LENGTH_SHORT).show()

            }
            else
            {
                callChangePasswdApi(current_passwrd.text.toString(),new_passwrd.text.toString(),confirm_passwd.text.toString())
            }
        }
        dialog.show()
    }

    private fun callChangePasswdApi(currentpswd: String, newpswd: String, confrmpswd: String) {
        val call: Call<ResponseBody> = ApiClient.getApiService().changepswd("Bearer "+
            PreferenceManager().getAccessToken(mContext).toString(),
            newpswd,confrmpswd,currentpswd)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Response",response.body().toString())

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
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

            callLogoutApi()
            dialog.dismiss()
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun callLogoutApi() {
        val call: Call<ResponseBody> = ApiClient.getApiService().logout("Bearer "+PreferenceManager().getAccessToken(mContext)
            .toString())
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Response",response.body().toString())
                val intent = Intent(context, WelcomeActivity::class.java)
            startActivity(intent)

            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
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
}
