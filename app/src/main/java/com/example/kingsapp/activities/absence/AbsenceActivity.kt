package com.example.kingsapp.activities.absence

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
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
import com.example.kingsapp.activities.absence.model.AbsenceLeaveApiModel
import com.example.kingsapp.activities.absence.model.AbsenceList
import com.example.kingsapp.activities.absence.model.AbsenceListModel
import com.example.kingsapp.activities.adapter.AbsenceListAdapter
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.constants.api.ApiClient
import com.example.kingsapp.fragment.mContext
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.RecyclerItemListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Response


class AbsenceActivity: AppCompatActivity() {
    lateinit var absencelist: RecyclerView
    lateinit var ncontext: Context
    lateinit var student_Name: TextView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var registerabsence: RelativeLayout
    lateinit var studentSpinner:LinearLayout
    lateinit var backarrow_absense: ImageView
    lateinit var progressBarDialog: ProgressBarDialog
    var mListAdapter: AbsenceListAdapter? = null
    lateinit var absenceList: ArrayList<AbsenceList>
    lateinit var studentInfoCopy :ArrayList<AbsenceList>

    lateinit var student_name: ArrayList<StudentList>

    //    private lateinit var progressDialog: RelativeLayout
    override fun onBackPressed() {
        val intent = Intent(ncontext, HomeActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.fragment_absence_main)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this

        initFn()
        if(CommonClass.isInternetAvailable(ncontext)) {
           // studentListApiCall()
            callStudentLeaveInfo()
        }
        else{
            Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }
    }

    private fun initFn() {
        absenceList = ArrayList()
        student_name = ArrayList()
        studentclass = findViewById(R.id.studentclass)
        imagicon = findViewById(R.id.imagicon)
        studentSpinner = findViewById(R.id.studentSpinner)
//        progressDialog = findViewById(R.id.progressDialog)
        progressBarDialog = ProgressBarDialog(ncontext)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(ncontext, R.anim.linear_interpolator)
//        progressDialog.startAnimation(aniRotate)
        student_Name = findViewById(R.id.studentName)
        absencelist = findViewById(R.id.absencelist)
        linearLayoutManager = LinearLayoutManager(ncontext)
        registerabsence = findViewById(R.id.featureLinear)
        backarrow_absense = findViewById(R.id.backarrow_absense)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        absencelist.layoutManager = linearLayoutManager


        student_Name.text=PreferenceManager().getStudentName(mContext)
        studentclass.text=PreferenceManager().getStudentClass(mContext)
        if(!PreferenceManager().getStudentPhoto(mContext).equals(""))
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
        backarrow_absense.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)

        }
        /*studentSpinner.setOnClickListener {
            studentlist_popup(student_name)
           *//* val intent = Intent(mContext, StudentListActivity::class.java)
            startActivity(intent)*//*

        }*/
        //mAbsenceListView.setLayoutManager(recyclerViewLayoutManager);
        absencelist.addOnItemTouchListener(
            RecyclerItemListener(
                ncontext, absencelist,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {

                        val intent = Intent(ncontext, AbsenceDeatilsActivity::class.java)
                        intent.putExtra("studentName",PreferenceManager().getStudentName(ncontext))
                        intent.putExtra("studentClass",PreferenceManager().getStudentClass(ncontext))
                        intent.putExtra("fromDate",absenceList.get(position).from_date)
                        intent.putExtra("toDate",absenceList.get(position).to_date)
                        intent.putExtra("reason",absenceList.get(position).reason)
                        startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        registerabsence.setOnClickListener {
            val intent = Intent(ncontext, RegisterAbsenceActivity::class.java)
            startActivity(intent)
        }
    }
//    private fun studentListApiCall() {
////        progressDialog.visibility = View.VISIBLE
//        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
//                PreferenceManager().getAccessToken(ncontext).toString())
//        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
//            override fun onResponse(
//                call: Call<StudentListResponseModel>,
//                response: Response<StudentListResponseModel>
//            ) {
////                progressDialog.visibility = View.GONE
//
//                Log.e("Response",response.body().toString())
//                if (response.body() != null) {
//                if (response.body()!!.status.equals(100))
//                {
//                    student_name.addAll(response.body()!!.student_list)
//                    Log.e("StudentNameid", PreferenceManager().getStudent_ID(ncontext).toString())
//                    if ( PreferenceManager().getStudent_ID(ncontext).equals(""))
//                    {
//                        studentName=student_name.get(0).fullname
//                        student_class=student_name.get(0).classs
//                        Log.e("StudentName",studentName)
//                        Log.e("student_class",student_class)
//                        studentImg=student_name.get(0).photo
//                        studentId= student_name.get(0).id.toString()
//                        PreferenceManager().setStudent_ID(ncontext,studentId)
//                        PreferenceManager().setStudentName(ncontext,studentName)
//                        PreferenceManager().setStudentPhoto(ncontext,studentImg)
//                        PreferenceManager().setStudentClass(ncontext,student_class)
//                        student_Name.text=studentName
//                        studentclass.text=student_class
//                        if(!studentImg.equals(""))
//                        {
//                            Glide.with(ncontext) //1
//                                .load(studentImg)
//                                .placeholder(R.drawable.profile_photo)
//                                .error(R.drawable.profile_photo)
//                                .skipMemoryCache(true) //2
//                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                                .transform(CircleCrop()) //4
//                                .into(imagicon)
//                        }
//                        else{
//                            imagicon.setImageResource(R.drawable.profile_photo)
//                        }
//
//                    }
//                    else{
//
//                        studentName= PreferenceManager().getStudentName(ncontext)!!
//                        Log.e("StudentName",studentName)
//                        student_class= PreferenceManager().getStudentClass(ncontext)!!
//                        studentImg= PreferenceManager().getStudentPhoto(ncontext)!!
//                        studentId= PreferenceManager().getStudent_ID(ncontext)!!
//                        student_Name.text=studentName
//                        studentclass.text=student_class
//                        if(!studentImg.equals(""))
//                        {
//                            Glide.with(ncontext) //1
//                                .load(studentImg)
//                                .placeholder(R.drawable.profile_photo)
//                                .error(R.drawable.profile_photo)
//                                .skipMemoryCache(true) //2
//                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                                .transform(CircleCrop()) //4
//                                .into(imagicon)
//                        }
//                        else{
//                            imagicon.setImageResource(R.drawable.profile_photo)
//                        }
//                    }
//                    if(CommonClass.isInternetAvailable(ncontext)) {
////                        progressDialog.visibility = View.VISIBLE
//                        callStudentLeaveInfo()
//                    }
//                    else{
//                        Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//                else if(response.body()!!.status.equals(106))
//                {
//                    val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
//                    startActivity(intent)
//                }
//                else
//                {
//                    CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
//                }
//
//            }
//            else{
//                val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
//                startActivity(intent)
//            }
//            }
//
//            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
//                Toast.makeText(
//                    ncontext,
//                    "Fail to get the data..",
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                Log.e("succ", t.message.toString())
//            }
//        })
//    }

    private fun callStudentLeaveInfo() {
//        progressDialog.visibility = View.VISIBLE
        studentInfoCopy = ArrayList<AbsenceList>()
        absenceList.clear()
        absencelist.visibility = View.GONE
        val abseneadapter = AbsenceListAdapter(ncontext, absenceList)
        absencelist.setAdapter(abseneadapter)
        progressBarDialog.show()
        val studentbody = AbsenceLeaveApiModel(PreferenceManager().getStudent_ID(ncontext)!!, 0, 20)
        val call: Call<AbsenceListModel> = ApiClient.getApiService().absenceList("Bearer "+
                PreferenceManager().getAccessToken(ncontext).toString(),
            studentbody
        )
        call.enqueue(object : retrofit2.Callback<AbsenceListModel> {
            override fun onResponse(
                call: Call<AbsenceListModel>,
                response: Response<AbsenceListModel>
            ) {
                progressBarDialog.hide()
//                progressDialog.visibility = View.GONE
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100)) {
                        absenceList.addAll(response.body()!!.leave_requests)
                        studentInfoCopy = absenceList
                        if (studentInfoCopy.size > 0) {
                            absencelist.visibility = View.VISIBLE
                            Log.e("name", absenceList.toString())
                            val abseneadapter = AbsenceListAdapter(ncontext, absenceList)
                        absencelist.setAdapter(abseneadapter)
                    }
                    else
                    {
                        Toast.makeText(ncontext, "No Registered Absence Found", Toast.LENGTH_SHORT).show()
                        absencelist.visibility=View.GONE
                    }

                }
                else if(response.body()!!.status.equals(106))
                {
                    val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }

                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                }
                }
                    else{
                        val intent = Intent(mContext, SigninyourAccountActivity::class.java)
                        startActivity(intent)
                    }
              //  showErrorAlert(ncontext,"Successfully submitted your absence.","Success")
            }

            override fun onFailure(call: Call<AbsenceListModel?>, t: Throwable) {
                progressBarDialog.hide()
                Toast.makeText(
                    ncontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun studentlist_popup(student_name: ArrayList<StudentList>) {
       // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(ncontext, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView

        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(ncontext)
        val studentlist_adapter =
            AbsenceStudentListAdapter(
                ncontext,
                student_name)
        recycler_view!!.adapter = studentlist_adapter

        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
Log.e("view", view.toString())

                var name: String = student_name.get(position).fullname
                var classs: String = student_name.get(position).classs
                var id: Int = student_name.get(position).id
                student_Name.setText(name)
                studentclass.text=classs
                PreferenceManager().setStudent_ID(ncontext,id.toString())
                PreferenceManager().setStudentName(ncontext,name)
//                progressDialog.visibility = View.VISIBLE

                callStudentLeaveInfo()
                dialog.dismiss()
            }

        })
        dialog.show()
    }
}