package com.example.kingsapp.activities.apps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.context
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.apps.adapter.AppsAdapter
import com.example.kingsapp.activities.apps.model.AppsList
import com.example.kingsapp.activities.apps.model.AppsModel
import com.example.kingsapp.activities.forms.adapter.FormListAdapter
import com.example.kingsapp.activities.forms.model.FormList
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.activities.student_info.model.StudentInfoResponseModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.PdfReaderActivity
import com.example.kingsapp.constants.WebViewLoaderActivity
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response

class AppsActivity:AppCompatActivity() {
    lateinit var mContext:Context
    lateinit var studentName_Text: TextView
    lateinit var studentName: String
    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var student_class: String
    lateinit var student_name: ArrayList<StudentList>
    lateinit var imagicon: ImageView
    lateinit var studentclass: TextView
    lateinit var parentList: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var list_name: ArrayList<AppsList>
    private lateinit var progressDialog: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)// will hide title
        supportActionBar?.hide() // hide the title bar

        setContentView(R.layout.activity_apps_layout)
        mContext = this
        PreferenceManager().setStudent_ID(mContext,"")

        initFn()
        if(CommonClass.isInternetAvailable(mContext)) {
            studentListApiCall()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }

    }

    private fun studentListApiCall() {
        progressDialog.visibility = View.VISIBLE
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString())
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                progressDialog.visibility = View.GONE
                Log.e("Response",response.body().toString())
                if (response.body()!!.status.equals(100))
                {
                    student_name.addAll(response.body()!!.student_list)
                    Log.e("StudentNameid", PreferenceManager().getStudent_ID(mContext).toString())
                    if ( PreferenceManager().getStudent_ID(mContext).equals(""))
                    {
                        studentName=student_name.get(0).fullname
                        student_class=student_name.get(0).classs
                        Log.e("StudentName",studentName)
                        Log.e("student_class",student_class)
                        studentImg=student_name.get(0).photo
                        studentId= student_name.get(0).id.toString()
                        PreferenceManager().setStudent_ID(mContext,studentId)
                        PreferenceManager().setStudentName(mContext,studentName)
                        PreferenceManager().setStudentPhoto(mContext,studentImg)
                        PreferenceManager().setStudentClass(mContext,student_class)

                        studentName_Text.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_photo)
                                .error(R.drawable.profile_photo)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_photo)
                        }

                    }
                    else{

                        studentName= PreferenceManager().getStudentName(mContext)!!
                        Log.e("StudentName",studentName)
                        studentImg= PreferenceManager().getStudentPhoto(mContext)!!
                        studentId= PreferenceManager().getStudent_ID(mContext)!!
                        student_class=PreferenceManager().getStudentClass(mContext)!!
                        studentName_Text.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(mContext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_photo)
                                .error(R.drawable.profile_photo)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_photo)
                        }
                    }
                    if(CommonClass.isInternetAvailable(mContext)) {
                        appsInfoApiCall()
                    }
                    else{
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
            }

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
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

    private fun appsInfoApiCall() {
        val call: Call<AppsModel> = ApiClient.getApiService().apps("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(),
            PreferenceManager().getStudent_ID(mContext).toString()
        )
        call.enqueue(object : retrofit2.Callback<AppsModel> {
            override fun onResponse(
                call: Call<AppsModel>,
                response: Response<AppsModel>
            ) {
                if (response.body()!!.status.equals(100))
                {
                    list_name.addAll(response.body()!!.apps)
                    parentList.layoutManager = linearLayoutManager
                    val appadapter = AppsAdapter(mContext,list_name)
                    parentList.setAdapter(appadapter)

                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
            }

            override fun onFailure(call: Call<AppsModel?>, t: Throwable) {
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
        student_name= ArrayList()
        list_name= ArrayList()
        studentName_Text = findViewById(R.id.studentName)
        imagicon=findViewById(R.id.imagicon)

        studentclass=findViewById(R.id.studentclass)
        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        parentList =findViewById(R.id.absencelist)
        linearLayoutManager = LinearLayoutManager(mContext)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        parentList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if(list_name.get(position).url.endsWith(".pdf"))
                {
                    val intent = Intent(mContext, PdfReaderActivity::class.java)
                    intent.putExtra("pdf_url", list_name[position].url)
                    intent.putExtra("pdf_title", list_name[position].title)
                    startActivity(intent)
                }
                else
                {
                    val intent = Intent(mContext, WebViewLoaderActivity::class.java)
                    intent.putExtra("webview_url", list_name[position].url)
                    intent.putExtra("title", list_name[position].title)

                    startActivity(intent)
                }
            }

        })
        studentName_Text.setOnClickListener {
            studentlist_popup(student_name)
            /* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*/

        }
    }
    private fun studentlist_popup(student_name: ArrayList<StudentList>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView

        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(mContext)
        val studentlist_adapter =
            AbsenceStudentListAdapter(mContext, student_name)
        recycler_view!!.adapter = studentlist_adapter

        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                var name: String = student_name.get(position).fullname
                var classs: String = student_name.get(position).classs
                var id: Int = student_name.get(position).id
                studentName_Text.setText(name)
                studentclass.text=classs
                PreferenceManager().setStudent_ID(mContext,id.toString())
                appsInfoApiCall()
                dialog.dismiss()
            }

        })
        dialog.show()
    }
}