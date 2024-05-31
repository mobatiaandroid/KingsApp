package com.kingseducation.app.activities.student_info

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.kingseducation.app.R
import com.kingseducation.app.activities.absence.adapter.AbsenceStudentListAdapter
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.activities.login.model.StudentListResponseModel
import com.kingseducation.app.activities.student_info.model.StudentInfoResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.OnItemClickListener
import com.kingseducation.app.manager.recyclerviewmanager.addOnItemClickListener
import retrofit2.Call
import retrofit2.Response


class StudentInfoActivity:AppCompatActivity (){
    lateinit var mContext: Context
    lateinit var name: TextInputEditText
    lateinit var classs: TextInputEditText
    lateinit var address: TextInputEditText
    lateinit var tutoremail: TextInputEditText
    lateinit var studentName_Text: TextView
    lateinit var studentName: String
    lateinit var studentId: String
    var studentImg: String = ""
    lateinit var student_class: String
    lateinit var student_name: ArrayList<StudentList>
    lateinit var imagicon: ImageView
    lateinit var studentclass: TextView
    lateinit var backarrow: ImageView

    //    lateinit var studentLinear: LinearLayout
    lateinit var textView: TextView

    // private lateinit var progressDialog: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_student_info)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        mContext = this


        initFn()
        if(CommonClass.isInternetAvailable(mContext)) {
           // studentListApiCall()
            studentInfoApiCall()
        }
        else{
            Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }


    }

    private fun studentListApiCall() {

        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString())

        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {


                Log.e("Response", response.body().toString())

                if (response.body() != null) {
                if (response.body()!!.status.equals(100)) {

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
                        if(!studentImg.equals("")) {
//                            Glide.with(mContext) //1
//                                .load(studentImg)
//                                .placeholder(R.drawable.profile_photo)
//                                .error(R.drawable.profile_photo)
//                                .skipMemoryCache(true) //2
//                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                                .transform(CircleCrop()) //4
//                                .into(imagicon)

                            val base64String = studentImg
                            val decodedString: ByteArray =
                                Base64.decode(base64String, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            Glide.with(mContext)
                                .load(decodedBitmap)
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_photo)
                        }

                    }
                    else{

                        studentName= PreferenceManager().getStudentName(mContext)!!
                        Log.e("StudentName",studentName)
                        student_class=PreferenceManager().getStudentClass(mContext)!!
                        studentImg= PreferenceManager().getStudentPhoto(mContext)!!
                        studentId= PreferenceManager().getStudent_ID(mContext)!!
                        studentName_Text.text=studentName
                        studentclass.text=student_class
                        if(!studentImg.equals("")) {
//                            Glide.with(mContext) //1
//                                .load(studentImg)
//                                .placeholder(R.drawable.profile_icon_grey)
//                                .error(R.drawable.profile_icon_grey)
//                                .skipMemoryCache(true) //2
//                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//                                .transform(CircleCrop()) //4
//                                .into(imagicon)
                            val base64String = studentImg
                            val decodedString: ByteArray =
                                Base64.decode(base64String, Base64.DEFAULT)
                            val decodedBitmap =
                                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                            Glide.with(mContext)
                                .load(decodedBitmap)
                                .into(imagicon)
                        }
                        else{
                            imagicon.setImageResource(R.drawable.profile_icon_grey)
                        }
                    }
                    if(CommonClass.isInternetAvailable(mContext)) {

                    }
                    else{
                        Toast.makeText(mContext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

                    }
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
    private fun studentInfoApiCall() {
        progressBarDialog.show()
        val call: Call<StudentInfoResponseModel> = ApiClient.getApiService().studentinfo("Bearer "+
                PreferenceManager().getAccessToken(mContext).toString(),
            PreferenceManager().getStudent_ID(mContext).toString()
        )
        call.enqueue(object : retrofit2.Callback<StudentInfoResponseModel> {
            override fun onResponse(
                call: Call<StudentInfoResponseModel>,
                response: Response<StudentInfoResponseModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
               if (response.body()!!.status.equals(100)) {
                   name.setText(response.body()!!.student_info.fullname)
                   address.setText(response.body()!!.student_info.tutorName)
                   classs.setText(response.body()!!.student_info.classs)
                   tutoremail.setText(response.body()!!.student_info.tutor_email)
                   studentImg = response.body()!!.student_info.photo
                   if (studentImg != null && !studentImg.isEmpty()) {
                       val glideUrl = GlideUrl(
                           studentImg,
                           LazyHeaders.Builder()
                               .addHeader("Authorization",
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
            }

            override fun onFailure(call: Call<StudentInfoResponseModel?>, t: Throwable) {
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

    fun initFn() {
        student_name = ArrayList()
//        studentLinear = findViewById(R.id.studentSpinner)
        studentName_Text = findViewById(R.id.studentName)
        imagicon = findViewById(R.id.imagicon)
        name = findViewById(R.id.name)
        classs = findViewById(R.id.classs)
        address = findViewById(R.id.address)
        tutoremail = findViewById(R.id.tutoremailtext)
        studentclass = findViewById(R.id.studentClass)
        backarrow = findViewById(R.id.backarrow)
        progressBarDialog = ProgressBarDialog(mContext)
        textView = findViewById(R.id.textView)
        if (PreferenceManager().getLanguage(com.kingseducation.app.fragment.mContext)
                .equals("ar")
        ) {
            val face: Typeface =
                Typeface.createFromAsset(
                    com.kingseducation.app.fragment.mContext.assets,
                    "font/times_new_roman.ttf"
                )
            textView.typeface = face
        }
        /* val aniRotate: Animation =
             AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
         progressDialog.startAnimation(aniRotate)*/
        backarrow.setOnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            startActivity(intent)
        }
        studentName_Text.text=PreferenceManager().getStudentName(mContext)
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
        /*studentLinear.setOnClickListener {
            studentlist_popup(student_name)
            *//* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*//*

        }*/

    }
    private fun studentlist_popup(student_name: ArrayList<StudentList>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(mContext, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!!

        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(mContext)
        val studentlist_adapter =
            AbsenceStudentListAdapter(
                mContext,
                student_name)
        recycler_view.adapter = studentlist_adapter

        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                var name: String = student_name.get(position).fullname
                var classs: String = student_name.get(position).classs
                var id: Int = student_name.get(position).id
                studentName_Text.text = name
                studentclass.text = classs
                PreferenceManager().setStudent_ID(mContext, id.toString())

                studentInfoApiCall()

                dialog.dismiss()
            }

        })
        dialog.show()
    }
}
