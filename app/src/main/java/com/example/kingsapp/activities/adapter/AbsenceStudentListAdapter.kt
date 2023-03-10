package com.example.kingsapp.activities.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.model.Studentlist_model

class AbsenceStudentListAdapter(private val context: Context, private  val student_name:ArrayList<Studentlist_model>): RecyclerView.Adapter<AbsenceStudentListAdapter.MyViewHolder>()  {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var studentName: TextView = view.findViewById(R.id.studentName)
        var check : ImageView = view.findViewById(R.id.check)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AbsenceStudentListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item_popup, parent, false)
        return AbsenceStudentListAdapter.MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: AbsenceStudentListAdapter.MyViewHolder, position: Int) {
        val namelist = student_name[position].name
        holder.studentName.setText(namelist)
        holder.check.setOnClickListener {


            var foundPosition = -1
            var isFound: Boolean = false

            for (i in 0..student_name.size-1)
            {
                if (student_name.get(i).isclicked)
                {
                    foundPosition=i
                    isFound=true
                }
            }

            if (isFound)
            {
                if (position==foundPosition)
                {
                    // make it as false
                    student_name.get(foundPosition).isclicked=false
                    notifyDataSetChanged()
                }
                else
                {
                    student_name.get(foundPosition).isclicked=false
                    student_name.get(position).isclicked=true
                    notifyDataSetChanged()
                }
            }
            else
            {
                student_name.get(position).isclicked=true
                notifyDataSetChanged()
            }

        }
        if (student_name.get(position).isclicked)
        {
            holder.check.setBackgroundResource(R.drawable.rectangle1)
        }
        else
        {
            holder.check.setBackgroundResource(R.drawable.rectangle)
        }
    }

    override fun getItemCount(): Int {
     return student_name.size
    }
}