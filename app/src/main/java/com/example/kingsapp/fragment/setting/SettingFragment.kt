package com.example.kingsapp.fragment.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.AbsenceActivity
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.settings.TermsOfServiceActivity
import com.example.kingsapp.activities.student_info.StudentInfoActivity
import com.example.kingsapp.common.CommonResponse
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.fragment.setting.adapter.SettingAdapter
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
        val adapter = SettingAdapter(mContext, stringList)
        recyclerList.adapter = adapter

        menu.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        recyclerList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if (PreferenceManager().getAccessToken(mContext).equals(""))
                {

                    if(position==0)
                    {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    if (position == 1) {
                        Toast.makeText(mContext,"This feature is only available for registered users.",
                            Toast.LENGTH_SHORT).show()
                    }
                    if (position == 2) {
                        Toast.makeText(mContext,"This feature is only available for registered users.",
                            Toast.LENGTH_SHORT).show()
                    }
                    if (position == 3) {
                        Toast.makeText(mContext,"This feature is only available for registered users.",
                            Toast.LENGTH_SHORT).show()
                    }
                    if (position == 4) {
                        Toast.makeText(mContext,"This feature is only available for registered users.",
                            Toast.LENGTH_SHORT).show()
                    }
                    if (position == 5) {
                        Toast.makeText(mContext,"This feature is only available for registered users.",
                            Toast.LENGTH_SHORT).show()
                    }

                    if (position == 6) {

                        showSuccessAlertGuest(mContext, "Do you want to Logout?")
                    }

                }
                else
                {
                    if(position==0)
                    {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    if (position == 1) {
                        val intent = Intent(mContext, TermsOfServiceActivity::class.java)
                        startActivity(intent)
                    }
                    if (position == 2) {
                        showEmailHelpAlert(mContext)
                    }
                    if (position == 3) {
                        Toast.makeText(
                            mContext,
                            "This feature will be available soon.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    if (position == 4) {
                        showSuccessAlertForDelete(mContext, "Do you want to Delete Account?")
                    }
                    if (position == 5) {
                        showChangePasswordPopUp()
                    }

                    if (position == 6) {
                        showSuccessAlert(mContext, "Do you want to Logout?")
                    }
                }

            }

        })
    }

    private fun showSuccessAlertGuest(mContext: Context, s: String) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_layout)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView

        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as TextView
        alertHead.text = s
        btn_Ok.setOnClickListener()
        {

            val intent = Intent(mContext, WelcomeActivity::class.java)
            startActivity(intent)
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
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
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun callChangePasswdApi(currentpswd: String, newpswd: String, confrmpswd: String) {
        val call: Call<CommonResponse> = ApiClient.getApiService().changepswd("Bearer "+
            PreferenceManager().getAccessToken(mContext).toString(),
            newpswd,confrmpswd,currentpswd)
        call.enqueue(object : retrofit2.Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
           Log.e("responsedata",response.body().toString())
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    Log.e("Response",response.body().toString())
                    showErrorAlert(mContext,"Successfully submitted your password","Success")
                }
              else if(response.body()!!.status.equals(106))
                {
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }

               else{
                    CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                }

                }
                else
                {
                    Log.e("failed","Failed")
                    CommonClass.checkApiStatusError(300, mContext
                    )
                }
                }

            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
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
    fun showErrorAlert(context: Context,message : String,msgHead : String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        var iconImageView=dialog.findViewById(R.id.iconImageView) as ImageView
        text_dialog.text = message
        alertHead.text = msgHead
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
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
            if(CommonClass.isInternetAvailable(mContext)) {
                callLogoutApi()
            }
            else
            {
                Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

            }
            dialog.dismiss()
        }
        btn_Cancel.setOnClickListener()
        {
            dialog.dismiss()
        }
        dialog.show()
    }
fun showSuccessAlertForDelete(mContext: Context, s: String)
{
    val dialog = Dialog(mContext)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(false)
    dialog.setContentView(R.layout.alert_dialogue_layout)
    var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView

    var alertHead = dialog.findViewById(R.id.alertHead) as TextView
    var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
    var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
    var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as TextView
    alertHead.text = s
    btn_Ok.setOnClickListener()
    {
        if(CommonClass.isInternetAvailable(this.mContext)) {
           callDeleteApicall()
        }
        else
        {
            Toast.makeText(this.mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }
        dialog.dismiss()
    }
    btn_Cancel.setOnClickListener()
    {
        dialog.dismiss()
    }
    dialog.show()
}

    private fun callDeleteApicall() {
        val call: Call<ResponseBody> = ApiClient.getApiService().delete("Bearer "+PreferenceManager().getAccessToken(mContext)
            .toString())
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Response",response.body().toString())
                PreferenceManager().setuser_id(mContext,"")
                PreferenceManager().setAccessToken(mContext,"")
                val intent = Intent(mContext, WelcomeActivity::class.java)
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

    private fun callLogoutApi() {
        val call: Call<ResponseBody> = ApiClient.getApiService().logout("Bearer "+PreferenceManager().getAccessToken(mContext)
            .toString())
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Response",response.body().toString())
                PreferenceManager().setuser_id(mContext,"")
                PreferenceManager().setAccessToken(mContext,"")
                val intent = Intent(mContext, WelcomeActivity::class.java)
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

        submitButton.setOnClickListener {
            if(text_dialog.text.toString().trim().equals(""))
            {
                Toast.makeText(mContext,"Please Enter The Subject ",Toast.LENGTH_SHORT).show()
            }
            else if(text_content.text.toString().trim().equals(""))
            {
                Toast.makeText(mContext,getResources().getString(R.string.please_enter_the_subject),Toast.LENGTH_SHORT).show()

            }
            else
            {
                if(CommonClass.isInternetAvailable(mContext)) {
                    callSendMailApi(text_dialog.text.toString(),text_content.text.toString(),context,dialog)
                }
                else
                {
                    Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

                }

            }

        }
        dialogCancelButton.setOnClickListener { //   AppUtils.hideKeyBoard(mContext);
            val imm =
                com.example.kingsapp.fragment.mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(text_dialog.windowToken, 0)
            imm.hideSoftInputFromWindow(text_content.windowToken, 0)

            dialog.dismiss()
        }
        dialog.show()
    }

    private fun callSendMailApi(
        textDialog: String,
        textContent: String,
        context: Context,
        dialog: Dialog
    ) {
        val call: Call<ResponseBody> = ApiClient.getApiService().feedback("Bearer "+PreferenceManager().getAccessToken(mContext)
            .toString(),textDialog,textContent, PreferenceManager().getUserCode(context).toString(),
            PreferenceManager().getuser_id(context).toString())
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                Log.e("Response",response.body().toString())
                dialog.dismiss()
                /*val intent = Intent(context, WelcomeActivity::class.java)
                startActivity(intent)*/

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
