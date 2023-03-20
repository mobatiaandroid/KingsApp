package com.example.kingsapp.activities.student_info.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R


/*
internal class StudentInfoAdapter (private var studentInfoList: List<StudentInfoDetail>) :
    RecyclerView.Adapter<StudentInfoAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTxt: TextView = view.findViewById(R.id.nameTxt)
        var valueTxt: TextView = view.findViewById(R.id.valueTxt)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_info_adapter, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = studentInfoList[position]
        holder.nameTxt.text = movie.title
        holder.valueTxt.text = movie.value
    }
    override fun getItemCount(): Int {
        return studentInfoList.size
    }
}*/
