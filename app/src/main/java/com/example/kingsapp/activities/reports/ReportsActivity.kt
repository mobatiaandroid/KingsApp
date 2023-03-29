package com.example.kingsapp.activities.reports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.*
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.activities.reports.adapter.ReportsAdapterList
import com.example.kingsapp.activities.reports.model.ReportModel
import com.example.kingsapp.activities.reports.model.ReportModelFiltered
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response

class ReportsActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var linearlayoutstudentlist:LinearLayout
    lateinit var reportrec:RecyclerView
    lateinit var student_name:ArrayList<StudentList>

    lateinit var report_array:ArrayList<ReportModel>
    lateinit var report_array_filtered:ArrayList<ReportModelFiltered>
    lateinit var student_Name : TextView
    lateinit var studentclass:TextView
    lateinit var imagicon:ImageView
    lateinit var backarrow : ImageView
    private lateinit var progressDialog: RelativeLayout
    //lateinit var progressDialog: ProgressBarDialog
    var studentName:String=""
    var student_class:String=""
    var studentImg:String=""
    var studentId:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_report)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
       // progressDialog = ProgressBarDialog(ncontext)
        PreferenceManager().setStudent_ID(ncontext,"")
        initFn()
        if(CommonClass.isInternetAvailable(ncontext)) {
            studentListApiCall()
            //reportslisting()
        }
        else{
            Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT).show()

        }

    }

    private fun initFn() {
        linearlayoutstudentlist=findViewById(R.id.linearlayoutstudentlist)
        student_name = ArrayList()
        reportrec = findViewById(R.id.reportrec)
        report_array_filtered= ArrayList()
        student_Name = findViewById(R.id.studentName)
        studentclass=findViewById(R.id.studentclass)
        imagicon=findViewById(R.id.imagicon)
        backarrow = findViewById(R.id.back)
        progressDialog = findViewById(R.id.progressDialog)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(ncontext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)


        backarrow.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)
        }


        linearlayoutstudentlist.setOnClickListener {
            studentlist_popup(student_name)
            /* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*/

        }
    }
    private fun studentListApiCall() {
        progressDialog.visibility=View.VISIBLE
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(ncontext).toString())
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                progressDialog.visibility=View.GONE

                Log.e("Response",response.body().toString())

                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    progressDialog.visibility=View.GONE
                    student_name.addAll(response.body()!!.student_list)
                    Log.e("StudentNameid", PreferenceManager().getStudent_ID(ncontext).toString())
                    if ( PreferenceManager().getStudent_ID(ncontext).equals(""))
                    {
                        studentName=student_name.get(0).fullname
                        student_class=student_name.get(0).classs
                        Log.e("StudentName",studentName)
                        Log.e("student_class",student_class)
                        studentImg=student_name.get(0).photo
                        studentId= student_name.get(0).id.toString()
                        PreferenceManager().setStudent_ID(ncontext,studentId)
                        PreferenceManager().setStudentName(ncontext,studentName)
                        PreferenceManager().setStudentPhoto(ncontext,studentImg)
                        PreferenceManager().setStudentClass(ncontext,student_class)
                        student_Name.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(ncontext) //1
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

                        studentName = PreferenceManager().getStudentName(ncontext)!!
                        Log.e("StudentName", studentName)
                        student_class = PreferenceManager().getStudentClass(ncontext)!!
                        studentImg = PreferenceManager().getStudentPhoto(ncontext)!!
                        studentId = PreferenceManager().getStudent_ID(ncontext)!!
                        student_Name.text= studentName
                        studentclass.text= student_class
                        if(!studentImg.equals(""))
                        {
                            Glide.with(ncontext) //1
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
                    if(CommonClass.isInternetAvailable(ncontext)) {
                        reportslisting()
                    }
                    else{
                        Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",
                            Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    progressDialog.visibility=View.GONE
                    CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                }
                }
                    else{
                        val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
                        startActivity(intent)
                    }
            }

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
                progressDialog.visibility=View.GONE
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
        val dialog = BottomSheetDialog(ncontext)
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

                 //var id: String = student_name.get(position).id.toString()
                 var name: String = student_name.get(position).fullname
                 var classs: String = student_name.get(position).classs
                 var id: Int = student_name.get(position).id
                 student_Name.setText(name)
                 studentclass.text=classs
                 PreferenceManager().setStudent_ID(ncontext,id.toString())
                 PreferenceManager().setStudentName(ncontext,name)

                 reportslisting()
                 dialog.dismiss()
             }

         })
        dialog.show()
    }
    private fun reportslisting(){
        report_array = ArrayList()
        var reportmodel=ReportModel(1,"2021-2022","Test report cycle 2021-2022","https:\\/\\/file.io\\/386klufU8FEr")
        report_array.add(reportmodel)
        var reportmodel1=ReportModel(1,"2022-2023","Test report cycle 2022-2023","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        report_array.add(reportmodel1)
        var reportmodel2=ReportModel(2,"2019-2020","Test report information 2019-2020","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        report_array.add(reportmodel2)
        /*var reportmodel3=ReportModel(2,"2022-2023","Test report information 2022-2023","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        report_array.add(reportmodel3)*/
        report_array_filtered=ArrayList()
        if (PreferenceManager().getStudent_ID(ncontext)==student_name[0].id.toString()){
            for (i in report_array.indices){
                if (report_array[i].student==1){
                    var nmodel=ReportModelFiltered(report_array[i].date,report_array[i].report_list,
                    report_array[i].report_list_url)
                    report_array_filtered.add(nmodel)
                }
            }
        }else{
            for (i in report_array.indices){
                if (report_array[i].student==2){
                    var nmodel=ReportModelFiltered(report_array[i].date,report_array[i].report_list,
                        report_array[i].report_list_url)
                    report_array_filtered.add(nmodel)
                }
            }
        }

        reportrec!!.layoutManager = LinearLayoutManager(ncontext)
        val report_rec_adapter =
            ReportsAdapterList(ncontext, report_array_filtered)
        reportrec!!.adapter = report_rec_adapter

    }
}