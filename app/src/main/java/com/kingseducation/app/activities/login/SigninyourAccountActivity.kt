package com.kingseducation.app.activities.login

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kingseducation.app.R
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.model.LoginResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.splash.WelcomeActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.lang.System.exit


class SigninyourAccountActivity:AppCompatActivity() ,View.OnTouchListener{
    lateinit var ncontext: Context
    lateinit var createAccountTxt:TextView
    lateinit var signInBtn:AppCompatButton
    lateinit var forgetPassword:TextView
    lateinit var backImg:ImageView
    lateinit var emailTextInputEditText:TextInputEditText
    lateinit var passwordTextInputEditText:TextInputEditText
    lateinit var joinGuestTxt: TextView
    lateinit var emailSupport:TextView
    lateinit var haveaccount:TextView
    lateinit var donthaveaccount:TextView
    lateinit var rememeberMeImg:ImageView
    lateinit var passwordTextInputLayout:TextInputLayout
   // private lateinit var progressDialog: RelativeLayout
   lateinit var progressBarDialog: ProgressBarDialog
    var tokenM:String=""

    var flag:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.kings_sign_in)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        PreferenceManager().setAccessToken(ncontext, "")
        initFn()
    }

    override fun onBackPressed() {
        exit(0)
//        super.onBackPressed()
    }

    private fun initFn() {
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        createAccountTxt = findViewById(R.id.createAccountTxt)
        signInBtn = findViewById(R.id.signInBtn)
        haveaccount = findViewById(R.id.haveaccount)
        forgetPassword = findViewById(R.id.forgetPassword)
        donthaveaccount = findViewById(R.id.donthaveaccount)
        rememeberMeImg = findViewById(R.id.rememeberMeImg)
        emailTextInputEditText = findViewById(R.id.emailTextInputEditText)
        passwordTextInputEditText=findViewById(R.id.passwordTextInputEditText)
        backImg=findViewById(R.id.backImg)
        joinGuestTxt = findViewById(R.id.joinGuestTxt)
        emailSupport = findViewById(R.id.emailSupport)
        progressBarDialog = ProgressBarDialog(ncontext)
        // edtUserName.setText("9946063677");
        /*set underline for forgot password text*/
        passwordTextInputLayout.setEndIconOnClickListener {
            if(flag)
            {

                passwordTextInputLayout.setEndIconDrawable(R.drawable.ic_baseline_remove_red_eye_24)
                passwordTextInputEditText.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            else
            {
                passwordTextInputLayout.setEndIconDrawable(R.drawable.ic_baseline_remove_red_eye)
                passwordTextInputEditText.inputType= InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            flag = !flag

        }
        //Remeberme click
        if (PreferenceManager().getUsernametext(ncontext)
                .equals("") && PreferenceManager().getUserpasswrdtext(ncontext).equals("")
        ) {
            //  Toast.makeText(ncontext, "Please enter the field !", Toast.LENGTH_SHORT).show()
        } else {
            emailTextInputEditText.setText(PreferenceManager().getUsernametext(ncontext))
            passwordTextInputEditText.setText(PreferenceManager().getUserpasswrdtext(ncontext))
        }
       // emailTextInputEditText.setOnTouchListener(this)
       // passwordTextInputEditText.setOnTouchListener(this)
       /* emailTextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                emailTextInputEditText?.isFocusable=false
                emailTextInputEditText?.isFocusableInTouchMode=false
                false
            } else {
                emailTextInputEditText?.isFocusable=false
                emailTextInputEditText?.isFocusableInTouchMode=false
                false
            }
        }*/

        //Keyboard done button click password
       /* passwordTextInputEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                passwordTextInputEditText.isFocusable =false
                passwordTextInputEditText.isFocusableInTouchMode =false
                false
            } else {
                passwordTextInputEditText?.isFocusable=false
                passwordTextInputEditText?.isFocusableInTouchMode=false
                false
            }
        }*/
        rememeberMeImg.setOnClickListener(View.OnClickListener {
            if(flag)
            {
            if(emailTextInputEditText.text.toString().trim().equals("")&&passwordTextInputEditText.text.toString().trim().equals(""))
            {
                Toast.makeText(ncontext, "Please enter the field !", Toast.LENGTH_SHORT).show()
            }
            else
            {
                rememeberMeImg.setImageDrawable(ContextCompat.getDrawable(ncontext, R.drawable.ic_baseline_check_24))
                PreferenceManager().setUsernametext(ncontext, emailTextInputEditText.text.toString())
                PreferenceManager().setUserpasswrdtext(ncontext, passwordTextInputEditText.text.toString())
            }
            }
            else
            {
                rememeberMeImg.setImageResource(0)
                PreferenceManager().setUsernametext(ncontext, "")
                PreferenceManager().setUserpasswrdtext(ncontext, "")
            }
            flag = !flag
            /*rememeberMeImg.setImageDrawable(ContextCompat.getDrawable(ncontext, R.drawable.ic_baseline_check_24))
if(PreferenceManager().getUsernametext(ncontext).equals("")&&PreferenceManager().getUserpasswrdtext(ncontext).equals(""))
{
   Toast.makeText(ncontext, "Please enter the field !", Toast.LENGTH_SHORT).show()
}
            else
{
    emailTextInputEditText.setText(PreferenceManager().getUsernametext(ncontext))
    passwordTextInputEditText.setText(PreferenceManager().getUserpasswrdtext(ncontext))
}*/
         /*   if(flag)
            {
if(passwordTextInputEditText.text.toString().trim().equals(""))
{

    Toast.makeText(ncontext, "Please enter the Password !", Toast.LENGTH_SHORT).show()


}
    else
{
    rememeberMeImg.setImageDrawable(ContextCompat.getDrawable(ncontext, R.drawable.ic_baseline_check_24))
    //passwordTextInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())

    passwordTextInputEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
}

}
            else
            {
                passwordTextInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance())
                rememeberMeImg.setImageDrawable(ContextCompat.getDrawable(ncontext, R.drawable.check))
            }
            flag = !flag*/
        })
        haveaccount.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf("info@kings-edu.com")
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }
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
                    FirebaseApp.initializeApp(ncontext)
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isComplete) {
                            val token: String = task.result.toString()
                            tokenM = token
                            callLoginApi(
                         emailTextInputEditText.text.toString(),
                         passwordTextInputEditText.text.toString()
                     )
                            //callChangePasswordStaffAPI(URL_STAFF_CHANGE_PASSWORD, token)
                        }
                    }

                }
                else{
                    Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
                }

            }
        }
        donthaveaccount.setOnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))

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
      //  progressDialog.startAnimation(aniRotate)
        progressBarDialog.show()

        var androidID = Settings.Secure.getString(this.contentResolver,
            Settings.Secure.ANDROID_ID)
        Log.e("android_id",androidID)
        val call: Call<LoginResponseModel> = ApiClient.getApiService().login(username,paswwd,"2",
            tokenM, androidID)
        call.enqueue(object : retrofit2.Callback<LoginResponseModel> {
            override fun onResponse(
                call: Call<LoginResponseModel>,
                response: Response<LoginResponseModel>
            ) {
                progressBarDialog.hide()

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
                progressBarDialog.hide()

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

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (v) {
            emailTextInputEditText -> {
                when (event!!.action){
                    MotionEvent.ACTION_DOWN -> {
                        emailTextInputEditText.isFocusable = true
                        emailTextInputEditText.isFocusableInTouchMode = true
                    }
                    MotionEvent.ACTION_UP -> {
                        v.performClick()
                        emailTextInputEditText.isFocusable = true
                        emailTextInputEditText.isFocusableInTouchMode = true
                    }
                }
            }
            passwordTextInputEditText -> {
                when (event!!.action){
                    MotionEvent.ACTION_DOWN -> {
                        passwordTextInputEditText.isFocusable = true
                        passwordTextInputEditText.isFocusableInTouchMode = true
                    }
                    MotionEvent.ACTION_UP -> {
                        v.performClick()
                        passwordTextInputEditText.isFocusable = true
                        passwordTextInputEditText.isFocusableInTouchMode = true
                    }
                }
            }
        }
        return false
    }
}