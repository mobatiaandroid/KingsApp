package com.kingseducation.app.activities.teacher_contact.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.teacherRole.text = teachersList[position].email
        holder.itemView.setOnClickListener {
            openEmail(teachersList[position].email)
        }

    }

    private fun openEmail(email: String?) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(intent, "Send email using:"))
        } else {
            // Handle the case where no email client is available
            // For example, show a Toast or log an error
        }
    }

    override fun getItemCount(): Int {
        return teachersList.size
    }




}