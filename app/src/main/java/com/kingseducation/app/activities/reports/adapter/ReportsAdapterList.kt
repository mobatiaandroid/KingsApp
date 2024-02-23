package com.kingseducation.app.activities.reports.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.reports.model.Reports


class ReportsAdapterList(private val mcontext: Context, private  val name:ArrayList<Reports>):
    RecyclerView.Adapter<ReportsAdapterList.MyViewHolder>() {


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var accYr: TextView = view.findViewById(R.id.accYr)
        var recycler_view_list: RecyclerView = view.findViewById(R.id.recycler_view_list)
        lateinit var linearlayoutstudentlist: LinearLayout

       // var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_report_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val accYr = name[position].Acyear
        // var detailArray:ArrayList<ReportsList>
        holder.accYr.text = accYr
        //detailArray.addAll(name[position].data)

        holder.recycler_view_list.layoutManager = LinearLayoutManager(mcontext)

        val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(mcontext, name[position].data)
        holder.recycler_view_list.adapter = mRecyclerViewSubAdapter


    }

    override fun getItemCount(): Int {
        return name.size
    }
}