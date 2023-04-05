package com.example.kingsapp.activities.message

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.model.AbsenceLeaveApiModel
import com.example.kingsapp.activities.apps.adapter.AppsAdapter
import com.example.kingsapp.activities.apps.model.AppsModel
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.message.adapter.MessageListAdapter
import com.example.kingsapp.activities.message.model.MessageListModel
import com.example.kingsapp.activities.message.model.NotificationModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.fragment.*
import com.example.kingsapp.manager.AppController
import com.example.kingsapp.manager.NaisClassNameConstants
import com.example.kingsapp.manager.NaisTabConstants
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response

class MessageFragment : Fragment() {
    lateinit var rootView: View
    lateinit var messagerec : RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var menu : ImageView
    lateinit var message_array:ArrayList<MessageListModel>
    //private lateinit var progressDialog: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.fragment_message, container, false)
        mContext =requireContext()
        homeActivity = activity as HomeActivity
        appController = AppController()
        classNameConstants = NaisClassNameConstants()
        naisTabConstants = NaisTabConstants()
        mListItemArray = resources.getStringArray(R.array.home_list_items)
        mListImgArrays = mContext.resources.obtainTypedArray(R.array.home_list_reg_icons)
        mContext =requireContext()
        initFn()
        if(CommonClass.isInternetAvailable(mContext)) {
            NotificationApiCall()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }

        return rootView
    }

    private fun NotificationApiCall() {
        progressBarDialog.show()
        message_array.clear()
        val studentbody= AbsenceLeaveApiModel(PreferenceManager().getStudent_ID(mContext)!!,0,20)
        val call: Call<NotificationModel> = ApiClient.getApiService().notification("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(),
            studentbody
        )
        call.enqueue(object : retrofit2.Callback<NotificationModel> {
            override fun onResponse(
                call: Call<NotificationModel>,
                response: Response<NotificationModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    message_array.addAll(response.body()!!.notifications)
                    if (message_array.size>0)
                    {
                        messagerec.layoutManager = linearLayoutManager
                        val messageListAdapter = MessageListAdapter(mContext,message_array)
                        messagerec.setAdapter(messageListAdapter)
                    }
                    else
                    {
                        CommonClass.showErrorAlert(mContext," No Data Found","Alert")
                    }
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status,mContext)
                }
                }
                    else{
                        val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                        startActivity(intent)
                    }
            }

            override fun onFailure(call: Call<NotificationModel?>, t: Throwable) {
                progressBarDialog.hide()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun initFn() {
        message_array = ArrayList()

        messagerec = (rootView.findViewById<View>(R.id.messagerec) as? RecyclerView)!!
        menu = ((rootView.findViewById<View>(R.id.menu) as? ImageView)!!)
        progressBarDialog = ProgressBarDialog(mContext)
        /*val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)*/
        menu.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        linearLayoutManager = LinearLayoutManager(mContext)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL



        messagerec.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if(message_array.get(position).alert_type.equals(1))
                {
                    val intent = Intent(activity, MessageDetailsActivity::class.java)
                    intent.putExtra("id",message_array.get(position).id)
                    intent.putExtra("title",message_array.get(position).title)
                    intent.putExtra("date",message_array.get(position).created_at)
                    activity?.startActivity(intent)
                }

                else if (message_array.get(position).alert_type.equals(2))
                {
                    val intent = Intent(activity, ImageMessageActivity::class.java)
                    intent.putExtra("id",message_array.get(position).id)
                    intent.putExtra("title",message_array.get(position).title)
                    intent.putExtra("url",message_array.get(position).url)
                    intent.putExtra("createdate",message_array.get(position).created_at)
                    Log.e("image",message_array.get(position).title)
                    Log.e("url",message_array.get(position).url)
                    activity?.startActivity(intent)
                }
                else if (message_array.get(position).alert_type.equals(3))
                {
                    if (message_array.get(position).url.endsWith(".mp3"))
                    {
                        val intent = Intent(activity, AudioPlayerDetail::class.java)
                        intent.putExtra("url", message_array[position].url)
                        Log.e("urll",message_array.get(position).url)
                        intent.putExtra("createdate",message_array.get(position).created_at)

                        //intent.putExtra("audio_id", message_array[position].id)

                        activity?.startActivity(intent)
                    }
                    else{

                        val intent = Intent(activity, VideoMessageActivity::class.java)
                        intent.putExtra("id",message_array.get(position).id)
                        intent.putExtra("title",message_array.get(position).title)
                        intent.putExtra("url",message_array.get(position).url)
                        intent.putExtra("createdate",message_array.get(position).created_at)
                        Log.e("image",message_array.get(position).title)
                        Log.e("url",message_array.get(position).url)
                        activity?.startActivity(intent)
                    }

                }
                    // showChangePasswordPopUp()

            }

        })
    }



}