package com.kingseducation.app.fragment.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.manager.PreferenceManager

class CommonAdapter (
    mContext: Context,
    assignedDealerShowArrayList: Array<String>,
) : RecyclerView.Adapter<CommonAdapter.MyViewHolder>() {
    var context = mContext
    var listString: Array<String>? = assignedDealerShowArrayList

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var imageView3: ImageView = view.findViewById(R.id.imageView3)
        var imageView4: ImageView = view.findViewById(R.id.imageView4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_about_us, parent, false)

        return MyViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.titleTextView.text = listString!![position]
        if (PreferenceManager().getLanguage(context).equals("ar"))
        {

            holder.imageView3.visibility=View.GONE
            holder.imageView4.visibility=View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return listString!!.size
    }

}
