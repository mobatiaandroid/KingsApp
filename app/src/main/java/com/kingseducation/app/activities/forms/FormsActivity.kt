package com.kingseducation.app.activities.forms

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
import com.kingseducation.app.activities.forms.adapter.FormListAdapter
import com.kingseducation.app.activities.forms.model.FormList
import com.kingseducation.app.activities.forms.model.FormsModel
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.PdfReaderActivity
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.WebViewLoaderActivity
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.fragment.mContext
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response

class FormsActivity : AppCompatActivity() {
    lateinit var list_name: ArrayList<FormList>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext: Context
    lateinit var linearLayoutManager: LinearLayoutManager

    //private lateinit var progressDialog: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView: TextView
    lateinit var noDataTV: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forms_layout)
        mcontext = this
        initFn()
        if (CommonClass.isInternetAvailable(mcontext)) {
            formListApiCall()
        } else {
            Toast.makeText(mcontext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }
    }

    private fun formListApiCall() {
        progressBarDialog.show()
        Log.e("type", PreferenceManager().getLanguagetype(mcontext).toString())

        val call: Call<FormsModel> = ApiClient.getApiService().forms("Bearer "+
                PreferenceManager().getAccessToken(mcontext).toString(), PreferenceManager().getStudent_ID(mcontext).toString(),PreferenceManager().getLanguagetype(mContext).toString())
        call.enqueue(object : retrofit2.Callback<FormsModel> {
            override fun onResponse(
                call: Call<FormsModel>,
                response: Response<FormsModel>
            ) {
                progressBarDialog.hide()

                Log.e("Response",response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100)) {
                        list_name.addAll(response.body()!!.forms)
                        if (list_name.isEmpty()) {
                            parentList.layoutManager = linearLayoutManager
                            val formadapter = FormListAdapter(mcontext, ArrayList())
                            parentList.adapter = formadapter
                            Toast.makeText(mContext, "No Data", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            parentList.layoutManager = linearLayoutManager
                            val formadapter = FormListAdapter(mcontext, list_name)
                            parentList.adapter = formadapter
                            noDataTV.visibility = View.GONE
                        }

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

            override fun onFailure(call: Call<FormsModel?>, t: Throwable) {
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
        list_name = ArrayList()


        back = findViewById(R.id.back)
        noDataTV = findViewById(R.id.noDataTV)
        parentList = findViewById(R.id.parentessetialsrec)
        linearLayoutManager = LinearLayoutManager(mcontext)
        textView = findViewById(R.id.textView)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        progressBarDialog = ProgressBarDialog(mcontext)
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }
        /* val aniRotate: Animation =
             AnimationUtils.loadAnimation(mcontext, R.anim.linear_interpolator)
         progressDialog.startAnimation(aniRotate)*/
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
