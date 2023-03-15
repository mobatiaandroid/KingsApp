package com.example.kingsapp.activities.reports.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.reports.model.ReportModel


class ReportsAdapterList(private val mcontext: Context, private  val name:ArrayList<ReportModel>):
    RecyclerView.Adapter<ReportsAdapterList.MyViewHolder>() {
    lateinit var detailArray:ArrayList<ReportModel>

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var accYr: TextView = view.findViewById(R.id.accYr)
        var recycler_view_list: RecyclerView = view.findViewById(R.id.recycler_view_list)
        lateinit var linearlayoutstudentlist: LinearLayout

       // var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_report_items, parent, false)
        return ReportsAdapterList.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val accYr = name[position]!!.date
        holder.accYr.setText(accYr)
        detailArray=ArrayList()
        val reportlist= name[position].report_list
        val report_list_url=name[position].report_list_url
        Log.e("date",accYr)
        Log.e("reportlist",reportlist)
        Log.e("report_list_url",report_list_url)


        holder. recycler_view_list!!.layoutManager = LinearLayoutManager(mcontext)

        val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(mcontext, reportlist, name.get(position)!!.report_list_url)
        holder.recycler_view_list.setAdapter(mRecyclerViewSubAdapter)

    }

    override fun getItemCount(): Int {
        return name.size
    }
}