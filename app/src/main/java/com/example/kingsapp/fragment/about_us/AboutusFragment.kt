package com.example.kingsapp.fragment.about_us

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.about_us.StaffDirectoryActivity
import com.example.kingsapp.fragment.setting.adapter.CommonAdapter
import com.example.kingsapp.manager.IOnBackPressed
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener

class AboutusFragment: Fragment()  {
    lateinit var recyclerList: RecyclerView
    lateinit var mContext: Context
    lateinit var menu : ImageView
    lateinit var stringList:Array<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.activity_about_us, container, false)
        //rootView= inflater.inflate(R.layout.activity_about_us, container, false)



    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFn()


    }
    private fun initFn() {
        mContext =requireContext()
        recyclerList = view?.findViewById(R.id.aboutusRec)!!
        //recyclerList = findViewById(R.id.settingsRecycler)
        menu = view?.findViewById(R.id.menu) !!
        stringList = mContext.resources.getStringArray(
            R.array.about_us_list)
        recyclerList.setHasFixedSize(true)

        recyclerList.setHasFixedSize(true)
        val llm = LinearLayoutManager(mContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerList.layoutManager = llm
        val adapter = CommonAdapter(mContext, stringList)
        recyclerList.adapter = adapter

        menu.setOnClickListener {
            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)
        }
        recyclerList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if (position == 0){
                    val intent = Intent(mContext, StaffDirectoryActivity::class.java)
                    startActivity(intent)
                   // showChangePasswordPopUp()
                }
            }

        })
    }

    


}