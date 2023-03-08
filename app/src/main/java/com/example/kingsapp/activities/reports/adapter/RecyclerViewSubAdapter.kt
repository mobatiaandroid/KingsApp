package com.example.kingsapp.activities.reports.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.reports.model.DataModel
import com.example.kingsapp.activities.reports.model.ReportModel
import com.example.kingsapp.constants.PdfReaderActivity

class RecyclerViewSubAdapter(private val context: Context, private val name: String,private  val url : String):
    RecyclerView.Adapter<RecyclerViewSubAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
//        var recycler_view_list: RecyclerView = view.findViewById(R.id.recycler_view_list)
        // var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_us, parent, false)
        return RecyclerViewSubAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("name","Success")

        val accYr = name
        val url = url
        Log.e("name",accYr)
        Log.e("url",url)
        holder.titleTextView.setText(accYr)

        holder.titleTextView.setOnClickListener {
            context.startActivity(
                Intent(context, PdfReaderActivity::class.java).
                putExtra("pdf_url",url).
                putExtra("pdf_title",accYr))
        }

        /*val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(context, name.get(position).mDataModel)
        holder.recycler_view_list.setAdapter(mRecyclerViewSubAdapter)*/

    }

    override fun getItemCount(): Int {
        return name!!.length
        return url!!.length
    }
}