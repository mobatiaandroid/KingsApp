package com.kingseducation.app.activities.teacher_contact.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.kingseducation.app.R
import com.kingseducation.app.activities.staff_directory.model.StaffDirectoryResponseModel


class StaffDirectoryAdapter(context: Context, teachersList: ArrayList<StaffDirectoryResponseModel.Staff>) :
    RecyclerView.Adapter<StaffDirectoryAdapter.ViewHolder>() {
    var context = context
    var teachersList = teachersList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var teacherPhoto: ImageView
        var teacherName: TextView
        var teacherRole: TextView


        init {
            teacherPhoto = view.findViewById<ImageView>(R.id.teacherPhoto)
            teacherName = view.findViewById(R.id.teacherName)
            teacherRole = view.findViewById(R.id.teacherDesignation)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_staff_directory_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.teacherName.text = teachersList[position].fullName
        holder.teacherRole.text = teachersList[position].subject
        holder.itemView.setOnClickListener {
            showDetailsPopUp(teachersList[position])
//            openEmail(teachersList[position].email)
        }

    }

    private fun showDetailsPopUp(staff: StaffDirectoryResponseModel.Staff) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_staff_directory_detail)
        val close = dialog.findViewById<ImageView>(R.id.close_btn)
        close.setOnClickListener {
            dialog.dismiss()
        }
        val teacherName = dialog.findViewById<TextView>(R.id.teacherName)
        val teacherDesignation = dialog.findViewById<TextView>(R.id.teacherDesignation)
        val teacherPhoto = dialog.findViewById<ImageView>(R.id.teacherPhoto)
        val emailIDText = dialog.findViewById<TextInputEditText>(R.id.emailIDText)
        val phoneNumberText = dialog.findViewById<TextInputEditText>(R.id.phoneNumberText)
        if (staff.fullName.isNotEmpty()) {
            teacherName.text = staff.fullName
        }
            teacherDesignation.text = staff.subject

        if (staff.email.isNotEmpty()) {
            emailIDText.setText(staff.email)
        }
        if (staff.mobile!!.isNotEmpty()) {
            phoneNumberText.setText(staff.mobile)
        }
        emailIDText.setOnClickListener {
            openEmail(emailIDText.text.toString())
        }
        phoneNumberText.setOnClickListener {
            val mobile = staff.mobile
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$mobile")
            }
            context.startActivity(intent)
        }
        dialog.show()
    }

    private fun openEmail(email: String?) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            // No email client installed

        }
    }

    override fun getItemCount(): Int {
        return teachersList.size
    }




}