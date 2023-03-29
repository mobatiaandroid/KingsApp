package com.example.kingsapp.activities.login

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.home.HomeguestuserActivity
import com.example.kingsapp.activities.login.model.LoginResponseModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.fragment.homeActivity
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.splash.WelcomeActivity
import com.google.android.material.textfield.TextInputEditText
import com.mobatia.nasmanila.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class SigninyourAccountActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var createAccountTxt:TextView
    lateinit var signInBtn:AppCompatButton
    lateinit var forgetPassword:TextView
    lateinit var backImg:ImageView
    lateinit var emailTextInputEditText:TextInputEditText
    lateinit var passwordTextInputEditText:TextInputEditText
    lateinit var joinGuestTxt: TextView
    lateinit var emailSupport:TextView
    private lateinit var progressDialog: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.kings_sign_in)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
    }

    private fun initFn() {
        createAccountTxt=findViewById(R.id.createAccountTxt)
        signInBtn=findViewById(R.id.signInBtn)
        forgetPassword=findViewById(R.id.forgetPassword)
        emailTextInputEditText=findViewById(R.id.emailTextInputEditText)
        passwordTextInputEditText=findViewById(R.id.passwordTextInputEditText)
        backImg=findViewById(R.id.backImg)
        joinGuestTxt = findViewById(R.id.joinGuestTxt)
        emailSupport = findViewById(R.id.emailSupport)
       progressDialog = findViewById(R.id.progressDialog)

        emailSupport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf("info@kings-edu.com")
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }
        joinGuestTxt.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)
        }
        signInBtn.setOnClickListener {
            var emailPattern = isEmailValid(emailTextInputEditText.text.toString().trim())
            if(emailTextInputEditText.text.isNullOrBlank())
            {
                Toast.makeText(ncontext, "Please enter the field !", Toast.LENGTH_SHORT).show()

            }
            else if(passwordTextInputEditText.text.isNullOrBlank())
            {
                Toast.makeText(ncontext, "Please enter the Password !", Toast.LENGTH_SHORT).show()

            }
            else if(!emailPattern)
            {
                Toast.makeText(ncontext, "Please enter a valid email !", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if(CommonClass.isInternetAvailable(ncontext)) {
                    callLoginApi(
                        emailTextInputEditText.text.toString(),
                        passwordTextInputEditText.text.toString()
                    )
                }
                else{
                    Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                }

            }
        }
        createAccountTxt.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        }
        forgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }
        backImg.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }
    }

    private fun callLoginApi(username: String, paswwd: String) {
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(ncontext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        progressDialog.visibility = View.VISIBLE
        var androidID = Settings.Secure.getString(this.contentResolver,
            Settings.Secure.ANDROID_ID)
        Log.e("android_id",androidID)
        val call: Call<LoginResponseModel> = ApiClient.getApiService().login(username,paswwd,"1",
            androidID,"123456789")
        call.enqueue(object : retrofit2.Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                progressDialog.visibility = View.GONE

                if(response.body()!!.status.equals(100))
               {
                   PreferenceManager().setAccessToken(ncontext,response.body()!!.token)
                   Log.e("token",PreferenceManager().getAccessToken(ncontext).toString())
                   startActivity(Intent(ncontext, ChildSelectionActivity::class.java))
               }
                else
               {
                   CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
               }
            }

            override fun onFailure(call: Call<LoginResponseModel?>, t: Throwable) {
                Toast.makeText(
                    ncontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
            dialog.dismiss()
           // callSendMailApi(text_dialog.text.toString(),text_dialog.text.toString(),context,dialog)
        }
        dialogCancelButton.setOnClickListener { //   AppUtils.hideKeyBoard(mContext);
            val imm =
                context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
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
        val call: Call<ResponseBody> = ApiClient.getApiService().feedback("Bearer "+PreferenceManager().getAccessToken(context)
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
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }
}