package com.kingseducation.app.activities.early_pickup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kingseducation.app.R
import com.kingseducation.app.activities.absence.model.AbsenceLeaveApiModel
import com.kingseducation.app.activities.absence.studentImg
import com.kingseducation.app.activities.early_pickup.adapter.PickuplistAdapter
import com.kingseducation.app.activities.early_pickup.model.EarlyList
import com.kingseducation.app.activities.early_pickup.model.EarlyPickupListModel
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response

class EarlyPickupListActivity:AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var student_Name: TextView
    lateinit var studentclass: TextView
    lateinit var imagicon: ImageView
    lateinit var earlylistlist: RecyclerView
    lateinit var pickup_list:ArrayList<EarlyList>
    lateinit var pickupListSort:ArrayList<EarlyList>
    lateinit var registerabsence: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var back:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.early_pickup_list_adapter)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        // ncontext = this
        mContext = this
        initFn()
        if(CommonClass.isInternetAvailable(mContext)) {
            // studentListApiCall()
            callEarlyPickupListApi()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }
}

    private fun callEarlyPickupListApi() {

        progressBarDialog.show()
        pickup_list= ArrayList()
        pickupListSort= ArrayList()
        val studentbody= AbsenceLeaveApiModel(PreferenceManager().getStudent_ID(mContext)!!,0,20)
        val call: Call<EarlyPickupListModel> = ApiClient.getApiService().earlyPickupList("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(),
            studentbody
        )
        call.enqueue(object : retrofit2.Callback<EarlyPickupListModel> {
            override fun onResponse(
                call: Call<EarlyPickupListModel>,
                response: Response<EarlyPickupListModel>
            ) {
                progressBarDialog.hide()
//                progressDialog.visibility = View.GONE
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100)) {
                        pickup_list.addAll(response.body()!!.early_pickups)
                        earlylistlist.visibility = View.VISIBLE
                        var list_size=pickup_list.size-1
                        pickupListSort=ArrayList()
                        for (i in pickup_list.indices){
                            pickupListSort.add(pickup_list[list_size-i])
                        }
                        if (pickupListSort.size>0){
                            earlylistlist.layoutManager= LinearLayoutManager(mContext)
                            var pickuplistAdapter= PickuplistAdapter(mContext,pickupListSort)
                            earlylistlist.adapter=pickuplistAdapter
                        }else{
                            earlylistlist.layoutManager= LinearLayoutManager(mContext)
                            var pickuplistAdapter= PickuplistAdapter(mContext,pickupListSort)
                            earlylistlist.adapter=pickuplistAdapter
                            Toast.makeText(mContext, "No Registered Early Pickup Found", Toast.LENGTH_SHORT).show()
                        }

                    }
                    else if(response.body()!!.status.equals(106))
                    {
                        val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                        startActivity(intent)
                    }

                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
                //  showErrorAlert(ncontext,"Successfully submitted your absence.","Success")
            }

            override fun onFailure(call: Call<EarlyPickupListModel?>, t: Throwable) {
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

    private fun initFn() {
        pickup_list= ArrayList()
        pickupListSort=ArrayList()
        back = findViewById(R.id.backarrow_absense)
        progressBarDialog = ProgressBarDialog(mContext)
        earlylistlist = findViewById(R.id.absencelist)
        registerabsence = findViewById(R.id.featureLinear)
        studentclass = findViewById(R.id.studentClass)
        student_Name = findViewById(R.id.studentName)
        imagicon=findViewById(R.id.imagicon)
        back.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        student_Name.text= PreferenceManager().getStudentName(mContext)
        studentclass.text= PreferenceManager().getStudentClass(mContext)
        if(!PreferenceManager().getStudentPhoto(mContext).equals("")) {
            if (!PreferenceManager().getStudentPhoto(mContext).equals("")) {
                studentImg = PreferenceManager().getStudentPhoto(mContext).toString()
                if (studentImg != null && !studentImg.isEmpty()) {
                    val glideUrl = GlideUrl(
                        studentImg,
                        LazyHeaders.Builder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + PreferenceManager().getAccessToken(mContext)
                                    .toString()
                            )
                            .build()
                    )
                    Glide.with(mContext)
                        .load(glideUrl)
                        .transform(CircleCrop()) // Apply circular transformation
                        .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                        .error(R.drawable.profile_photo) // Image to display in case of error
                        .into(imagicon)
                } else {
                    Toast.makeText(mContext, "Image empty", Toast.LENGTH_SHORT).show()
                    // Handle the case when studentImg is null or empty
                }
            } else {
                imagicon.setImageResource(R.drawable.profile_photo)
            }
        }
        else{
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        earlylistlist.addOnItemTouchListener(
            RecyclerItemListener(
                mContext, earlylistlist,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {

                        val intent = Intent(mContext, EarlyPickupdetailsActivity::class.java)
                        intent.putExtra("studentName",PreferenceManager().getStudentName(mContext))
                        intent.putExtra("studentClass",PreferenceManager().getStudentClass(mContext))
                        intent.putExtra("fromDate",pickupListSort.get(position).pickup_time)
                        intent.putExtra("pickupby",pickupListSort.get(position).pickup_by_whom)
                        intent.putExtra("reason",pickupListSort.get(position).reason)
                        intent.putExtra("status",pickupListSort.get(position).status)
                        intent.putExtra("reason_for_rejection",pickupListSort.get(position).reason_for_rejection)


                        startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        registerabsence.setOnClickListener {
            showPopUp()
        }
    }

    private fun showPopUp() {
        val intent = Intent(mContext, EarlyPickupRegisterActivity::class.java)
        startActivity(intent)
    }
}