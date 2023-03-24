package com.example.kingsapp.activities.calender.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.parentessentials.model.ParentessentialModel

class StudentRecyclerCalenderAdapter(private val context: Context, private  val name:ArrayList<StudentList>):
    RecyclerView.Adapter<StudentRecyclerCalenderAdapter.MyViewHolder>() {
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var textview: TextView = view.findViewById(R.id.studName)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentRecyclerCalenderAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_image_name, parent, false)
        return StudentRecyclerCalenderAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentRecyclerCalenderAdapter.MyViewHolder, position: Int) {
        holder.textview.text=name.get(position).fullname
        /*if (PreferenceManager().getLanguage(context).equals("ar"))
        {

            holder.imageView3.visibility=View.GONE
            holder.imageView4.visibility=View.VISIBLE
        }*/
    }

    override fun getItemCount(): Int {
        Log.e("error", name.size.toString())
        return name.size
    }
}