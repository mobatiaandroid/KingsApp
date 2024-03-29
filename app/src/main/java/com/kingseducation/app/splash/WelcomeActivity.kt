package com.kingseducation.app.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.kingseducation.app.R
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.CreateAccountActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity

class WelcomeActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var createAccountBtn: Button
    lateinit var signInBtn:AppCompatButton
    lateinit var joinGuestTxt: TextView
    lateinit var guestBgImg:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// will hide title
        supportActionBar?.hide() // hide the title bar
        setContentView(R.layout.activity_welcome)

        mContext=this
        initUI()
    }

    fun initUI()
    {
        createAccountBtn=findViewById(R.id.createAccountBtn)
        signInBtn = findViewById(R.id.signInBtn)
        joinGuestTxt = findViewById(R.id.joinGuestTxt)
        guestBgImg = findViewById(R.id.guestBgImg)
        guestBgImg.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        joinGuestTxt.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        createAccountBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, CreateAccountActivity::class.java))
        })
        signInBtn.setOnClickListener {
            startActivity(Intent(this, SigninyourAccountActivity::class.java))
        }
    }
}
