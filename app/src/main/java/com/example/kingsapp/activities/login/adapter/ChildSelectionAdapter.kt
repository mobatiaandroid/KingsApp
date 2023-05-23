package com.example.kingsapp.activities.login.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.manager.PreferenceManager

class ChildSelectionAdapter(private val mcontext: Context,
                            private  val student_name:ArrayList<StudentList>):
    RecyclerView.Adapter<ChildSelectionAdapter.MyViewHolder>()  {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var studentName: TextView = view.findViewById(R.id.student_name)
        var stud_profile : ImageView = view.findViewById(R.id.stud_profile)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item_adapter, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val namelist = student_name[position].fullname
        val startAnimation: Animation =
            AnimationUtils.loadAnimation(mcontext, R.anim.blinking_animation)
        holder.stud_profile.startAnimation(startAnimation)

        holder.studentName.setText(namelist)

    }

    override fun getItemCount(): Int {
        return student_name.size
    }
}