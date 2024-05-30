package com.kingseducation.app.activities.re_enrolment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.re_enrolment.model.ReEnrolmentListResponseModel


class ReEnrolStudentListAdapter(
    private val context: Context,
    private val studentList: ArrayList<ReEnrolmentListResponseModel.ReEnrollment>
) :
    RecyclerView.Adapter<ReEnrolStudentListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var studentNameTV: TextView = view.findViewById(R.id.studentNameTV)
        var reEnrolStatusTV: TextView = view.findViewById(R.id.reEnrolStatusTV)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reenrol_student, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.studentNameTV.text = studentList[position].fullName
        if (studentList[position].selectedOption.equals("")) {
            holder.reEnrolStatusTV.text =
                context.resources.getString(R.string.reenrol_status) + " " + context.resources.getString(
                    R.string.nil
                )
        } else {
            holder.reEnrolStatusTV.text =
                context.resources.getString(R.string.reenrol_status) + " " + studentList[position].selectedOption
        }


    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}