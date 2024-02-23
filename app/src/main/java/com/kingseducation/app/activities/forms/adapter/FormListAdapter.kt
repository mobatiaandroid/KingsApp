package com.kingseducation.app.activities.forms.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.forms.model.FormList


class FormListAdapter(private val context: Context, private  val name:ArrayList<FormList>):
    RecyclerView.Adapter<FormListAdapter.MyViewHolder>() {
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var textview: TextView = view.findViewById(R.id.titleTextView)
        var imageView3: ImageView = view.findViewById(R.id.imageView3)
        var imageView4: ImageView = view.findViewById(R.id.imageView4)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parent_essentials, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textview.text = name[position].title
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