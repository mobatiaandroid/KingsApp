package com.example.kingsapp.activities.parentessentials.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceListAdapter
import com.example.kingsapp.activities.parentessentials.model.ParentessentialModel
import com.example.kingsapp.manager.PreferenceManager

class ParentListAdapter(private val context: Context, private  val name:ArrayList<ParentessentialModel>):
    RecyclerView.Adapter<ParentListAdapter.MyViewHolder>() {
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var textview: TextView = view.findViewById(R.id.titleTextView)
        var imageView3: ImageView = view.findViewById(R.id.imageView3)
        var imageView4: ImageView = view.findViewById(R.id.imageView4)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParentListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parent_essentials, parent, false)
        return ParentListAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ParentListAdapter.MyViewHolder, position: Int) {
        holder.textview.text = name!![position].title
        /*if (PreferenceManager().getLanguage(context).equals("ar"))
        {

            holder.imageView3.visibility=View.GONE
            holder.imageView4.visibility=View.VISIBLE
        }*/
    }

    override fun getItemCount(): Int {
        return name.size
    }
}