package com.example.kingsapp.activities.teacher_contact.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.teacher_contact.model.TeacherModel

class TeachersListAdapterRecyclerView(context: Context, teachersList: ArrayList<TeacherModel>) :
    RecyclerView.Adapter<TeachersListAdapterRecyclerView.ViewHolder>() {
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
            .inflate(R.layout.adapter_teachers_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.teacherName.text = teachersList[position].staff_name
        holder.teacherRole.text = teachersList[position].subject
//        Glide.with(context) //1
//            .load("")
//            .placeholder(null)
//            .error(R.drawable.user)
//            .skipMemoryCache(true) //2
//            .diskCacheStrategy(DiskCacheStrategy.NONE) //3
//            .transform(CircleCrop()) //4
//            .into(holder.teacherPhoto)        //detailArray.addAll(name[position].data)

    }

    override fun getItemCount(): Int {
        return teachersList.size
    }


}