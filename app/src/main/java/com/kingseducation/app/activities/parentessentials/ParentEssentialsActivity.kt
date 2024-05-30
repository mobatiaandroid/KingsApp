package com.kingseducation.app.activities.parentessentials

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.forms.model.FormList
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.activities.parentessentials.adapter.ParentListAdapter
import com.kingseducation.app.activities.parentessentials.model.ParentModel
import com.kingseducation.app.activities.re_enrolment.ReEnrolmentListingActivity
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.PdfReaderActivity
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.WebViewLoaderActivity
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response


class ParentEssentialsActivity : AppCompatActivity() {
    // lateinit var list_name:ArrayList<String>
    lateinit var list_name: ArrayList<FormList>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext: Context
    lateinit var linearLayoutManager: LinearLayoutManager

    // private lateinit var progressDialog: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parentessentails)
        mcontext = this
        initFn()
        if (CommonClass.isInternetAvailable(mcontext)) {
            // progressDialog.visibility = View.VISIBLE
            parrentessentialApiCall()
        } else {
            Toast.makeText(
                mcontext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun parrentessentialApiCall() {
        progressBarDialog.show()
        Log.e("languagetype", PreferenceManager().getLanguagetype(mcontext).toString())
        val call: Call<ParentModel> = ApiClient.getApiService().parentessentials(
            "Bearer " +
                    PreferenceManager().getAccessToken(mcontext).toString(),
            PreferenceManager().getStudent_ID(mcontext).toString(),
            PreferenceManager().getLanguagetype(mcontext).toString()
        )
        call.enqueue(object : retrofit2.Callback<ParentModel> {
            override fun onResponse(
                call: Call<ParentModel>,
                response: Response<ParentModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    Log.e("Response", response.body().toString())
                    if (response.body()!!.status.equals(100)) {
                        list_name.addAll(response.body()!!.parent_essentials)
                        parentList.layoutManager = linearLayoutManager
                        val parentadapter = ParentListAdapter(mcontext, list_name)
                        parentList.adapter = parentadapter
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                    }
                } else {
                    val intent = Intent(mcontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                progressBarDialog.hide()
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
        list_name = ArrayList()

        back = findViewById(R.id.back)
        textView = findViewById(R.id.textView)
        parentList = findViewById(R.id.parentessetialsrec)
        linearLayoutManager = LinearLayoutManager(mcontext)
        progressBarDialog = ProgressBarDialog(mcontext)
        /* val aniRotate: Animation =
             AnimationUtils.loadAnimation(mcontext, R.anim.linear_interpolator)
         progressDialog.startAnimation(aniRotate)*/
        if (PreferenceManager().getLanguage(mcontext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mcontext.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        parentList.layoutManager = linearLayoutManager

        parentList.addOnItemTouchListener(
            RecyclerItemListener(
                mcontext, parentList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {

                        if (list_name.get(position).url.endsWith(".pdf")) {
                            val intent = Intent(mcontext, PdfReaderActivity::class.java)
                            intent.putExtra("pdf_url", list_name[position].url)
                            intent.putExtra("pdf_title", list_name[position].title)
                            startActivity(intent)
                        } else if (
                            list_name[position].title.contains("enrol", ignoreCase = true)
                        ) {
                            val intent = Intent(mcontext, ReEnrolmentListingActivity::class.java)
                            startActivity(intent)
                        } else {
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