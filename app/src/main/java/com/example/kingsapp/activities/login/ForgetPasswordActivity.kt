package com.example.kingsapp.activities.login

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.example.kingsapp.common.CommonResponse
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.mobatia.nasmanila.api.ApiClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class ForgetPasswordActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var signin: MaterialTextView
    lateinit var txtProgress: TextView
    lateinit var cross_image: ImageView
    lateinit var checkbtn: Button
    lateinit var crossbtn: Button
    private var progressBar: ProgressBar? = null
    private var progressBar2: ProgressBar? = null
    private var pStatus = 0
    private val handler: Handler = Handler()
    lateinit var image: ImageView
    lateinit var passwordTextInputLayout: TextInputLayout
    lateinit var passwordTextInputEditText: TextInputEditText

    lateinit var createacconttextview: TextView
    lateinit var textView24: TextView
    lateinit var back_arrow:ImageView
    lateinit var progressBarDialog: ProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.kings_forgot_password)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
    }

    private fun initFn() {
        signin = findViewById(R.id.signin)
        progressBar = findViewById(R.id.progressBar);
        createacconttextview = findViewById(R.id.textView23)
        textView24 = findViewById(R.id.textView24)
        txtProgress = findViewById(R.id.textView);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        passwordTextInputEditText = findViewById(R.id.passwordTextInputEditText)
        progressBarDialog = ProgressBarDialog(ncontext)

        image = findViewById(R.id.imageView)
        back_arrow = findViewById(R.id.imageView18)


        back_arrow.setOnClickListener {
            startActivity(Intent(this, SigninyourAccountActivity::class.java))
        }
        signin.setOnClickListener {
            var emailPattern = isEmailValid(passwordTextInputEditText.text.toString().trim())
            if (passwordTextInputEditText.text.isNullOrBlank()) {
                Toast.makeText(ncontext, "Please enter the field !", Toast.LENGTH_SHORT).show()
            } else if (!emailPattern) {
                Toast.makeText(ncontext, "Please enter the valid email !", Toast.LENGTH_SHORT).show()
            } else {
                if(CommonClass.isInternetAvailable(ncontext)) {
                    callForgetPasswd(passwordTextInputEditText.text.toString())
                }
                else{
                    Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

                }


            }
        }
    }

    private fun callForgetPasswd(email: String) {
        progressBarDialog.show()
        val call: Call<CommonResponse> = ApiClient.getApiService().forgetpwsd(email)
        call.enqueue(object : retrofit2.Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                progressBarDialog.hide()
                if (response.body()!!.status.equals(100)) {
                    Log.e("Response", response.body().toString())
                    val animZoomIn = AnimationUtils.loadAnimation(ncontext, R.anim.zoom_in)
                    val animZoomInP =
                        AnimationUtils.loadAnimation(ncontext, R.anim.zoom_in_progress)
                    createacconttextview.setText("We have reset your password")
                    textView24.setText("We have send your username and password to your given email id")
                    signin.visibility = View.GONE
                    passwordTextInputLayout.visibility = View.GONE
                    Thread {
                        while (pStatus <= 100) {
                            handler.post {
                                progressBar?.setProgress(pStatus)
                                txtProgress?.setText("$pStatus %")
                                while (pStatus == 100) {
                                    (image.getDrawable() as Animatable).start()
                                }
                            }
                            try {
                                Thread.sleep(25)

                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                            pStatus++
                        }
                        /*while(pStatus==100){
                    progressBar?.getProgressDrawable()?.setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                }*/
                    }.start()

                    image.startAnimation(animZoomIn)
                    progressBar?.startAnimation(animZoomInP)

                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                }
            }
            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
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
}
