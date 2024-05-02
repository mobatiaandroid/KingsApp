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
import com.kingseducation.app.activities.reports.model.ReportsNewResponseModel
import com.kingseducation.app.constants.WebViewLoaderActivity


class RecyclerViewSubAdapter(
    private val context: Context,
    private val name: ArrayList<ReportsNewResponseModel.Report>,
) :
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
        val url = name[position].reportUrl
        //Log.e("name",accYr)
        Log.e("url",url)
        holder.titleTextView.text = name[position].academicYear.toString()

        holder.relativelayout.setOnClickListener {

            val intent = Intent(context, WebViewLoaderActivity::class.java)
            intent.putExtra("webview_url", name[position].reportUrl)
            intent.putExtra("title", name[position].academicLabel)

            context.startActivity(intent)

        }

        /*val mRecyclerViewSubAdapter =
            RecyclerViewSubAdapter(context, name.get(position).mDataModel)
        holder.recycler_view_list.setAdapter(mRecyclerViewSubAdapter)*/

    }

    override fun getItemCount(): Int {
        return name.size

    }
}