package com.example.kingsapp.fragment.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.manager.PreferenceManager


class SettingAdapter (
    mContext: Context,
    assignedDealerShowArrayList: Array<String>,
) : RecyclerView.Adapter<SettingAdapter.MyViewHolder>() {
    var context = mContext
    var listString: Array<String>? = assignedDealerShowArrayList

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView: TextView = view.findViewById(R.id.titleTextView)
        var imageView3: ImageView = view.findViewById(R.id.imageView3)
        var imageView4: ImageView = view.findViewById(R.id.imageView4)
        var txtUser: TextView = view.findViewById(R.id.titleTextView1)

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

            holder.imageView3.visibility= View.GONE
            holder.imageView4.visibility= View.VISIBLE
        }

        if (position==6)
        {

            holder.txtUser.visibility=View.VISIBLE
            holder.txtUser.text = "("+PreferenceManager().getUserCode(context)+")"
        }
        else{

            holder.txtUser.visibility=View.GONE
        }
    }

    override fun getItemCount(): Int {
        return listString!!.size
    }

}
