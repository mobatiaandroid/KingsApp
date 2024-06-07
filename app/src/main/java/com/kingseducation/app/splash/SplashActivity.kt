package com.kingseducation.app.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.home.model.HomeUserResponseModel
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response

class SplashActivity : AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT: Long = 3000
    var firebaseid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// will hide title
        supportActionBar?.hide() // hide the title bar

        setContentView(R.layout.splash_screen_layout)
        mContext = this
        PreferenceManager().setDataCollectionsShown(mContext,"n")
        PreferenceManager().setReEnrolShown(mContext,"n")
        Handler().postDelayed({
            Log.e("Username", PreferenceManager().getuser_id(mContext).toString())
            if (PreferenceManager().getAccessToken(mContext).equals("")) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                Log.e("Failed", "Success")
                callhomeuserApi()
            }

        }, SPLASH_TIME_OUT)
    }

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

                        val intent = Intent(mContext, HomeActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                } else {
                    val intent = Intent(mContext, WelcomeActivity::class.java)
                    startActivity(intent)
                    finish()
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
}