package com.example.kingsapp.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import de.hdodenhof.circleimageview.CircleImageView

class ChildSelectionActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var circleImageView:CircleImageView
    lateinit var circleImageView2:CircleImageView
    lateinit var circleImageView3:CircleImageView
    lateinit var imageView18:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.kings_child_selection)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
    }

    private fun initFn() {
        circleImageView=findViewById(R.id.circleImageView)
        circleImageView2=findViewById(R.id.circleImageView2)
        circleImageView3=findViewById(R.id.circleImageView3)
        imageView18=findViewById(R.id.imageView18)
        imageView18.setOnClickListener {
            startActivity(Intent(this, SigninyourAccountActivity::class.java))
        }
        circleImageView.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        circleImageView2.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        circleImageView3.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}