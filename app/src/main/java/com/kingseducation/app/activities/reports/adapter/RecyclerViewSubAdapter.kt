package com.kingseducation.app.activities.reports.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.reports.model.ReportsList
import com.kingseducation.app.constants.PdfReaderActivity
import com.kingseducation.app.constants.WebViewLoaderActivity


class RecyclerViewSubAdapter(private val context: Context, private val name: ArrayList<ReportsList>):
    RecyclerView.Adapter<RecyclerViewSubAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var relativelayout: ConstraintLayout = view.findViewById(R.id.relativelayout)

//        var recycler_view_list: RecyclerView = view.findViewById(R.id.recycler_view_list)
        // var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_us, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("name","Success")

        //val accYr = name
        val url = name[position].file
        //Log.e("name",accYr)
        Log.e("url",url)
        holder.titleTextView.text = name[position].report_cycle

        holder.relativelayout.setOnClickListener {
            if(name.get(position).file.endsWith(".pdf"))
            {
                val intent = Intent(context, PdfReaderActivity::class.java)
                intent.putExtra("pdf_url", name[position].file)
                intent.putExtra("pdf_title", name[position].report_cycle)
                context. startActivity(intent)
            }
            else
            {
                val intent = Intent(context, WebViewLoaderActivity::class.java)
                intent.putExtra("webview_url", name[position].file)
                intent.putExtra("title", name[position].report_cycle)

                context. startActivity(intent)
            }
        }

        /*val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(context, name.get(position).mDataModel)
        holder.recycler_view_list.setAdapter(mRecyclerViewSubAdapter)*/

    }

    override fun getItemCount(): Int {
        return name.size

    }
}