package com.example.kingsapp.activities.teacher_contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.teacher_contact.adapter.TeachersListAdapterRecyclerView
import com.example.kingsapp.activities.teacher_contact.model.ContactTeacherResponseModel
import com.example.kingsapp.activities.teacher_contact.model.SubjectTeachersResponseModel
import com.example.kingsapp.activities.teacher_contact.model.TeacherModel
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.constants.api.ApiClient
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response

class TeacherContactActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var teachersList: ArrayList<TeacherModel>
    lateinit var teachersRecycler: RecyclerView
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var studentNameTextView: TextView
    lateinit var studentClassTextView: TextView
    lateinit var backButton: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_contact)
        context = this
        initialiseUI()
        // TODO call staff API
        callSubjectTeacher()
//        addValuesToArrayList()

    }

    private fun callSubjectTeacher() {
        progressBarDialog.show()

        Log.e("type", PreferenceManager().getLanguagetype(context).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
        }
        val call: Call<SubjectTeachersResponseModel> = ApiClient.getApiService().getSubjectTeachers(
            "Bearer " +
                    PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<SubjectTeachersResponseModel> {
            override fun onResponse(
                call: Call<SubjectTeachersResponseModel>,
                response: Response<SubjectTeachersResponseModel>
            ) {
                progressBarDialog.hide()
                var teachersList = response.body()!!.teachersList
                if (teachersList.size > 0) {
                    val adapter =
                        TeachersListAdapterRecyclerView(context, teachersList)
                    teachersRecycler.adapter = adapter
                    teachersRecycler.addOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClicked(position: Int, view: View) {
                            showSendEmailPopUp(teachersList[position])
                        }

                    })

                } else {
                    Toast.makeText(context, "No teachers available", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SubjectTeachersResponseModel>, t: Throwable) {
                progressBarDialog.hide()
            }
        }
        )
    }

    private fun showSendEmailPopUp(teacherItem: TeacherModel) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.bottom_sheet_contact_teacher)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        var sendEmailButton = dialog.findViewById<Button>(R.id.sendEmailButton)!! as Button
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView
        var emailID = dialog.findViewById<TextView>(R.id.emailID)
        var subjectTextView = dialog.findViewById<TextView>(R.id.subjectTextInputEditText)
        var contentTextView = dialog.findViewById<TextView>(R.id.contentInputEditText)
        emailID!!.text = teacherItem.staff_email

        sendEmailButton.setOnClickListener {
            if (subjectTextView!!.text.isEmpty() || contentTextView!!.text.isEmpty()) {
                Toast.makeText(context, "Please do not leave any field empty.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                contactTeacherAPICall(
                    context, dialog, teacherItem.id,
                    subjectTextView.text.toString(), contentTextView.text.toString()
                )
            }
//            Toast.makeText(context, "Email sent successfully.", Toast.LENGTH_LONG).show()
//            dialog.dismiss()
        }
        crossicon.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun contactTeacherAPICall(
        context: Context,
        dialog: BottomSheetDialog,
        teacherID: String,
        subjectText: String,
        contentText: String
    ) {
        progressBarDialog.show()

        Log.e("type", PreferenceManager().getLanguagetype(context).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(context).toString())
            addProperty("subject", subjectText)
            addProperty("message", contentText)
            addProperty("staff_id", teacherID)
        }
        val call: Call<ContactTeacherResponseModel> = ApiClient.getApiService().postContactTeacher(
            "Bearer " +
                    PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<ContactTeacherResponseModel> {
            override fun onResponse(
                call: Call<ContactTeacherResponseModel>,
                response: Response<ContactTeacherResponseModel>
            ) {
                progressBarDialog.hide()
                if (response.body()!!.status.equals("100")) {
                    Toast.makeText(context, "Email sent successfully.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(context, "Email not sent.", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }

            override fun onFailure(call: Call<ContactTeacherResponseModel>, t: Throwable) {
                progressBarDialog.hide()
            }
        }
        )
    }

    private fun addValuesToArrayList() {
        for (i in 1..3) {
            val randomName = "Teacher $i"
            val randomPhoto = "teacher$i.jpg"
            val randomDesignation = "Role $i"
            val randomEmail = "teacher$i@gmail.com"

            val teacher = TeacherModel(randomName, randomPhoto, randomDesignation, randomEmail)

            teachersList.add(teacher)
        }
        val adapter =
            TeachersListAdapterRecyclerView(context, teachersList)
        teachersRecycler!!.adapter = adapter
    }

    private fun initialiseUI() {
        teachersList = ArrayList()
        teachersRecycler = findViewById(R.id.recyclerTeacher)
        teachersRecycler.layoutManager = LinearLayoutManager(context)
        progressBarDialog = ProgressBarDialog(context)
        studentNameTextView = findViewById(R.id.studentName)
        studentClassTextView = findViewById(R.id.studentclass)
        backButton = findViewById(R.id.back)
        studentNameTextView.text = PreferenceManager().getStudentName(context)
        studentClassTextView.text = PreferenceManager().getStudentClass(context)
        backButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}