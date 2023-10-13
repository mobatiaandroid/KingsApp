package com.example.kingsapp.activities.teacher_contact

import android.content.Context
import android.os.Bundle
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
import com.example.kingsapp.activities.teacher_contact.adapter.TeachersListAdapterRecyclerView
import com.example.kingsapp.activities.teacher_contact.model.TeacherModel
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog

class TeacherContactActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var teachersList: ArrayList<TeacherModel>
    lateinit var teachersRecycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_contact)
        context = this
        initialiseUI()
        // TODO call staff API
        addValuesToArrayList()
        teachersRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                showSendEmailPopUp(teachersList[position])
            }

        })
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
        emailID!!.text = teacherItem.email

        sendEmailButton.setOnClickListener {
            Toast.makeText(context, "Email sent successfully.", Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        crossicon.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
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
    }
}