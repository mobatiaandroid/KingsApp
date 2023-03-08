package com.example.kingsapp.activities.message

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.message.adapter.MessageListAdapter
import com.example.kingsapp.activities.message.model.MessageListModel
import com.example.kingsapp.fragment.*
import com.example.kingsapp.manager.AppController
import com.example.kingsapp.manager.NaisClassNameConstants
import com.example.kingsapp.manager.NaisTabConstants
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener

class MessageFragment : Fragment() {
    lateinit var rootView: View
    lateinit var messagerec : RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var menu : ImageView
    lateinit var message_array:ArrayList<MessageListModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.fragment_message, container, false)
        mContext =requireContext()
        homeActivity = activity as MainActivity
        appController = AppController()
        classNameConstants = NaisClassNameConstants()
        naisTabConstants = NaisTabConstants()
        mListItemArray = resources.getStringArray(R.array.home_list_items)
        mListImgArrays = mContext.resources.obtainTypedArray(R.array.home_list_reg_icons)
        mContext =requireContext()
        initFn()


        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun initFn() {
        message_array = ArrayList()
        var model=MessageListModel("13-sep-2022","9:30Pm","We are getting ready for the 2022/23 scholl year portraits.Photographers from Fotorex will be on campus nest weeek")
        message_array.add(model)
        var xmodel=MessageListModel("13-sep-2022","9:30Pm","We are getting ready for the 2022/23 scholl year portraits.Photographers from Fotorex will be on campus nest weeek")
        message_array.add(xmodel)
        var nmodel=MessageListModel("13-sep-2022","9:30Pm","We are getting ready for the 2022/23 scholl year portraits.Photographers from Fotorex will be on campus nest weeek")
        message_array.add(nmodel)
        var emodel=MessageListModel("13-sep-2022","9:30Pm","We are getting ready for the 2022/23 scholl year portraits.Photographers from Fotorex will be on campus nest weeek")
        message_array.add(emodel)
        messagerec = (rootView.findViewById<View>(R.id.messagerec) as? RecyclerView)!!
        menu = ((rootView.findViewById<View>(R.id.menu) as? ImageView)!!)

        menu.setOnClickListener {
            val intent = Intent(mContext, MainActivity::class.java)
            startActivity(intent)
        }
        linearLayoutManager = LinearLayoutManager(mContext)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        messagerec.layoutManager = linearLayoutManager
        val messageListAdapter = MessageListAdapter(mContext,message_array)
        messagerec.setAdapter(messageListAdapter)


        messagerec.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                    Log.e("sucesss","Success")
                    val intent = Intent(mContext, MessageDetailsActivity::class.java)
                    startActivity(intent)
                    // showChangePasswordPopUp()

            }

        })
    }



}