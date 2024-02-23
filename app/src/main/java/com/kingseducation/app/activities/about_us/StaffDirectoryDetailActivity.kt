package com.kingseducation.app.activities.about_us

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.about_us.adapter.StaffDirectoryDetails
import com.kingseducation.app.manager.recyclerviewmanager.OnItemClickListener
import com.kingseducation.app.manager.recyclerviewmanager.addOnItemClickListener

class StaffDirectoryDetailActivity : AppCompatActivity() {
    lateinit var recyclerList: RecyclerView
    lateinit var mContext: Context
    // lateinit var menu : ImageView
    lateinit var imageView_staff_details : ImageView
    lateinit var stringList:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_directory_detail)
        mContext = this
        initFn()
    }

    private fun initFn() {
        recyclerList = findViewById(R.id.staffdirectorydetailList)
        imageView_staff_details = findViewById(R.id.imageView_staff_details)
        //recyclerList = findViewById(R.id.settingsRecycler)
        // menu = ((rootView.findViewById<View>(R.id.menu) as? ImageView)!!)
        stringList = mContext.resources.getStringArray(
            R.array.staff_dir_list)
        recyclerList.setHasFixedSize(true)

        recyclerList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerList.layoutManager = llm
        val adapter = StaffDirectoryDetails(mContext, stringList)
        recyclerList.adapter = adapter

        imageView_staff_details.setOnClickListener {
            val intent = Intent(mContext, StaffDirectoryActivity::class.java)
            startActivity(intent)
        }
        /*menu.setOnClickListener {
            val intent = Intent(com.kingseducation.app.fragment.mContext, MainActivity::class.java)
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