package com.example.kingsapp.activities.about_us.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.fragment.setting.adapter.CommonAdapter

class StaffDirectoryDetails(mContext: Context,
                            assignedDealerShowArrayList: Array<String>): RecyclerView.Adapter<StaffDirectoryDetails.MyViewHolder>() {
    var context = mContext
    var listString: Array<String>? = assignedDealerShowArrayList
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var titleTextView: TextView = view.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_staff_detail, parent, false)

        return StaffDirectoryDetails.MyViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.titleTextView.text = listString!![position]
    }

    override fun getItemCount(): Int {
        return listString!!.size
    }
}