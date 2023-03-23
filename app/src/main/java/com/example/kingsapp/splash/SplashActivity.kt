package com.example.kingsapp.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.manager.PreferenceManager

class SplashActivity: AppCompatActivity() {
    lateinit var mContext: Context
    private val SPLASH_TIME_OUT: Long = 3000
    var firebaseid: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// will hide title
        supportActionBar?.hide() // hide the title bar

        setContentView(R.layout.splash_screen_layout)
        mContext = this

        Handler().postDelayed({
            Log.e("Username", PreferenceManager().getuser_id(mContext).toString())
            if (PreferenceManager().getuser_id(mContext).equals(""))
            {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            else
            {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

        }, SPLASH_TIME_OUT)
    }

}