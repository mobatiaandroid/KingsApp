package com.kingseducation.app.activities.early_pickup.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.early_pickup.model.EarlyList
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

internal class PickuplistAdapter (private var Context: Context,var pickup_list:ArrayList<EarlyList>) :
    RecyclerView.Adapter<PickuplistAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listDate: TextView = view.findViewById(R.id.listDate)
        var listStatus: TextView = view.findViewById(R.id.listStatus)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_absence_leave_recycelr, parent, false)
        return MyViewHolder(itemView)
    }

    fun formatDateWithSuffix(dateString: String): String {
        val dateParts = dateString.split(" ")
        if (dateParts.size == 3) {
            val day = dateParts[0].toInt()
            val month = dateParts[1]
            val year = dateParts[2]

            val dayWithSuffix = when (day) {
                1, 21, 31 -> "${day}st"
                2, 22 -> "${day}nd"
                3, 23 -> "${day}rd"
                else -> "${day}th"
            }

            return "$dayWithSuffix $month $year"
        }
        return dateString
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = pickup_list[position]

        val fromDate = list.pickup_time
        Log.e("Pickuptime", fromDate)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val outputFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = fromDate
        val date: Date = inputFormat.parse(inputDateStr)
        val outputDateStr: String = formatDateWithSuffix(outputFormat.format(date))
        holder.listDate.text = outputDateStr

        var status = list.status
        if (status == 1) {
            holder.listStatus.visibility = VISIBLE
            holder.listStatus.text = "PENDING"
            holder.listStatus.setTextColor(Context.resources.getColor(R.color.orange))
           //label pending
        }
        else if (status==2){
            holder.listStatus.visibility = VISIBLE
            holder.listStatus.text = "APPROVED"
            holder.listStatus.setTextColor(Context.resources.getColor(R.color.green))
            //label approved
        }
        else{
            holder.listStatus.visibility = VISIBLE
            holder.listStatus.setTextColor(Context.resources.getColor(R.color.red))
            holder.listStatus.text = "REJECTED"

            //label rejected
        }

    }
    override fun getItemCount(): Int {
        return pickup_list.size
    }
}