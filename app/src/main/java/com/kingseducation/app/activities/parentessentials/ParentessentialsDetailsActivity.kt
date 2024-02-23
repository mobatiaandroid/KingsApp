package com.kingseducation.app.activities.parentessentials

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R

class ParentessentialsDetailsActivity: AppCompatActivity() {
    lateinit var list_name:ArrayList<String>
    lateinit var parentList: RecyclerView
    lateinit var mcontext: Context
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parentessentails)
        mcontext =this
        initFn()
    }

    private fun initFn() {
        TODO("Not yet implemented")
    }
}