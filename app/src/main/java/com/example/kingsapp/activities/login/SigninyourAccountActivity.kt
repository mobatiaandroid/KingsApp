package com.example.kingsapp.activities.login

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.splash.WelcomeActivity
import com.google.android.material.textfield.TextInputEditText

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
        emailSupport.setOnClickListener {
            showEmailHelpAlert(ncontext)
        }
        joinGuestTxt.setOnClickListener {
            val intent = Intent(ncontext, MainActivity::class.java)
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
                startActivity(Intent(this, ChildSelectionActivity::class.java))

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
        dialogCancelButton.setOnClickListener { //   AppUtils.hideKeyBoard(mContext);


            dialog.dismiss()
        }
        dialog.show()
    }
}