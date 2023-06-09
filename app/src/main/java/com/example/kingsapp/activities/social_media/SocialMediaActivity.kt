package com.example.kingsapp.activities.social_media

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.social_media.adapter.SocialMediaRecyclerAdapter
import com.example.kingsapp.activities.social_media.model.SocialMediaDetailModel
import com.example.kingsapp.activities.social_media.model.SocialMediaResponseModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.constants.WebViewLoaderActivity
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.gson.JsonObject
import com.example.kingsapp.constants.api.ApiClient
import retrofit2.Call
import retrofit2.Response

class SocialMediaActivity:AppCompatActivity() {
    lateinit var mContext:Context
    lateinit var bannerImageViewPager: ImageView
    lateinit var backImage: ImageView
    lateinit var progressBarDialog: ProgressBarDialog

   lateinit var socialMediaArrayList : ArrayList<SocialMediaDetailModel>
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var socialMediaRecycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// will hide title
        supportActionBar?.hide() // hide the title bar

        setContentView(R.layout.socialmedia_layout)
        mContext = this
        initFn()
        if(CommonClass.isInternetAvailable(mContext)) {
           socialMediaApiCall()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }
}

    private fun socialMediaApiCall() {
        socialMediaArrayList=ArrayList()
        progressBarDialog.show()

        Log.e("type", PreferenceManager().getLanguagetype(mContext).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(mContext).toString())
            addProperty("language_type", PreferenceManager().getLanguagetype(mContext).toString())

        }
        val call: Call<SocialMediaResponseModel> = ApiClient.getApiService().socialmedia("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(), paramObject)
        call.enqueue(object : retrofit2.Callback<SocialMediaResponseModel> {
            override fun onResponse(
                call: Call<SocialMediaResponseModel>,
                response: Response<SocialMediaResponseModel>
            ) {
                progressBarDialog.hide()

                Log.e("Response",response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100))
                    {
                        socialMediaArrayList.addAll(response.body()!!.socialmedia)
                        val socialMediaRecyclerAdapter = SocialMediaRecyclerAdapter(mContext,socialMediaArrayList)
                        socialMediaRecycler.adapter = socialMediaRecyclerAdapter
                    }
                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, mContext)
                    }
                }
                else{
                    val intent = Intent(com.example.kingsapp.fragment.mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<SocialMediaResponseModel?>, t: Throwable) {
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

        linearLayoutManager = LinearLayoutManager(mContext)
        socialMediaRecycler = findViewById(R.id.parentessetialsrec) as RecyclerView
        socialMediaRecycler.layoutManager = linearLayoutManager
        socialMediaRecycler.itemAnimator = DefaultItemAnimator()
        backImage = findViewById(R.id.back)

        progressBarDialog = ProgressBarDialog(mContext)

        backImage.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        socialMediaRecycler.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                var mPackage: String =""
                when(socialMediaArrayList.get(position).title){

                    "Linkedin" -> mPackage = "com.linkedin.android"
                    "Youtube" -> mPackage = "com.google.android.youtube"
                    "Instagram" -> mPackage = "com.instagram.android"
                    "Twitter" -> mPackage = "com.twitter.android"
                    "Facebook" -> mPackage = "fb"
                    "Linkedin" -> mPackage = "com.linkedin.android"

                }


                if (mPackage == "fb"){

                   // Log.d("ASD",socialMediaArrayList[position].page_id)

                    val facebookAppIntent: Intent
                    try {
                        facebookAppIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("fb://page/${socialMediaArrayList[position].page_id}")
                        )
                        startActivity(facebookAppIntent)
                    } catch (e: ActivityNotFoundException) {

                        val url = socialMediaArrayList[position].url
                        val intent = Intent(mContext, WebViewLoaderActivity::class.java)
                        intent.putExtra("webview_url", socialMediaArrayList.get(position).url)
                        intent.putExtra("title", socialMediaArrayList.get(position).title)
                        startActivity(intent)

                    }

                }else {

                    val uri = Uri.parse(socialMediaArrayList.get(position).url)
                    val likeIng = Intent(Intent.ACTION_VIEW, uri)
                    likeIng.setPackage(mPackage)
                    try {
                        startActivity(likeIng)
                    } catch (e: ActivityNotFoundException) {
                        val url = socialMediaArrayList.get(position).url
                        val intent = Intent(mContext, WebViewLoaderActivity::class.java)
                        intent.putExtra("webview_url", socialMediaArrayList.get(position).url)
                        intent.putExtra("title", socialMediaArrayList.get(position).title)
                        startActivity(intent)

                    }
                }


            }
        })

    }
}