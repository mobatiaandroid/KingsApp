package com.example.kingsapp.activities.parentessentials

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.forms.adapter.FormListAdapter
import com.example.kingsapp.activities.forms.model.FormList
import com.example.kingsapp.activities.forms.model.FormsModel
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.parentessentials.adapter.ParentListAdapter
import com.example.kingsapp.activities.parentessentials.model.ParentModel
import com.example.kingsapp.activities.parentessentials.model.ParentessentialModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.PdfReaderActivity
import com.example.kingsapp.constants.WebViewLoaderActivity
import com.example.kingsapp.fragment.mContext
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.RecyclerItemListener
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response


class ParentEssentialsActivity: AppCompatActivity() {
   // lateinit var list_name:ArrayList<String>
    lateinit var list_name:ArrayList<FormList>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext:Context
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var progressDialog: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parentessentails)
        mcontext =this
        initFn()
        if(CommonClass.isInternetAvailable(mcontext)) {
            progressDialog.visibility = View.VISIBLE
            parrentessentialApiCall()
        }
        else{
            Toast.makeText(mcontext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }
    }

    private fun parrentessentialApiCall() {
        progressDialog.visibility = View.VISIBLE
        val call: Call<ParentModel> = ApiClient.getApiService().parentessentials("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(),PreferenceManager().getStudent_ID(mcontext).toString())
        call.enqueue(object : retrofit2.Callback<ParentModel> {
            override fun onResponse(
                call: Call<ParentModel>,
                response: Response<ParentModel>
            ) {
                progressDialog.visibility = View.GONE
                if (response.body() != null) {
                Log.e("Response",response.body().toString())
                if (response.body()!!.status.equals(100))
                {
                    list_name.addAll(response.body()!!.parent_essentials)
                    parentList.layoutManager = linearLayoutManager
                    val parentadapter = ParentListAdapter(mcontext,list_name)
                    parentList.setAdapter(parentadapter)
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                }
                }
                else{
                    val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun initFn() {
       // list_name = mcontext.resources.getStringArray(R.array.parent_list)
        list_name= ArrayList()

        back = findViewById(R.id.back)

        parentList =findViewById(R.id.parentessetialsrec)
        linearLayoutManager = LinearLayoutManager(mcontext)
        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mcontext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        parentList.layoutManager = linearLayoutManager

        parentList.addOnItemTouchListener(
            RecyclerItemListener(
                mcontext, parentList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if(list_name.get(position).url.endsWith(".pdf"))
                        {
                            val intent = Intent(mcontext, PdfReaderActivity::class.java)
                            intent.putExtra("pdf_url", list_name[position].url)
                            intent.putExtra("pdf_title", list_name[position].title)
                            startActivity(intent)
                        }
                        else
                        {
                            val intent = Intent(mcontext, WebViewLoaderActivity::class.java)
                            intent.putExtra("webview_url", list_name[position].url)
                            intent.putExtra("title", list_name[position].title)

                            startActivity(intent)
                        }

                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )

        back.setOnClickListener {
            val intent = Intent(mcontext, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}