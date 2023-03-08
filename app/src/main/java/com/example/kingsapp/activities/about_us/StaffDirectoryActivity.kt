package com.example.kingsapp.activities.about_us

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.fragment.setting.adapter.CommonAdapter
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener

class StaffDirectoryActivity : AppCompatActivity() {
    lateinit var recyclerList: RecyclerView
    lateinit var mContext: Context
    lateinit var imageView_staff : ImageView
   // lateinit var menu : ImageView
    lateinit var stringList:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_directory)
        mContext = this
        initFn()
    }

    private fun initFn() {
        recyclerList = findViewById(R.id.staffDirectoryList)
        imageView_staff = findViewById(R.id.imageView_staff)
        //recyclerList = findViewById(R.id.settingsRecycler)
       // menu = ((rootView.findViewById<View>(R.id.menu) as? ImageView)!!)
        stringList = mContext.resources.getStringArray(
            R.array.staff_dir_list)
        recyclerList.setHasFixedSize(true)

        recyclerList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerList.layoutManager = llm
        val adapter = CommonAdapter(mContext, stringList)
        recyclerList.adapter = adapter
        imageView_staff.setOnClickListener(View.OnClickListener { finish() })

        /*imageView_staff.setOnClickListener {
            val intent = Intent(mContext, StaffDirectoryDetailActivity::class.java)
            startActivity(intent)
        }*/
        /*menu.setOnClickListener {
            val intent = Intent(com.example.kingsapp.fragment.mContext, MainActivity::class.java)
            startActivity(intent)
        }*/
        recyclerList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if (position == 0){
                    val intent = Intent(mContext, StaffDirectoryDetailActivity::class.java)
                    startActivity(intent)
                    // showChangePasswordPopUp()
                }
            }

        })
    }

}