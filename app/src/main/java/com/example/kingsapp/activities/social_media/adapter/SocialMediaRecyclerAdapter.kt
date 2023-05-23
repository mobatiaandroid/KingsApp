package com.example.kingsapp.activities.social_media.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.social_media.model.SocialMediaDetailModel


internal class SocialMediaRecyclerAdapter (private var socialMediaArrayList: List<SocialMediaDetailModel>) :
    RecyclerView.Adapter<SocialMediaRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var socialMediaText: TextView = view.findViewById(R.id.socialMediaText)
        var cellLinear: LinearLayout = view.findViewById(R.id.cellLinear)
        var socialMediaIcon: ImageView = view.findViewById(R.id.socialMediaIcon)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_social_media_recycler_layout, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = socialMediaArrayList[position]
        holder.socialMediaText.text = movie.title
        if (socialMediaArrayList.get(position).title.equals("Youtube"))
        {
            holder.cellLinear.setBackgroundResource(R.drawable.curve_you)
            holder.socialMediaIcon.setImageResource(R.mipmap.you_new)

        }
        else if (socialMediaArrayList.get(position).title.equals("Instagram"))
        {
            holder.cellLinear.setBackgroundResource(R.drawable.curve_in)
            holder.socialMediaIcon.setImageResource(R.mipmap.in_new)
        }
        else if (socialMediaArrayList.get(position).title.equals("Facebook"))
        {
            holder.cellLinear.setBackgroundResource(R.drawable.curve_fb)
            holder.socialMediaIcon.setImageResource(R.mipmap.fa_new)
        }
        else if (socialMediaArrayList.get(position).title.equals("Twitter"))
        {
            holder.cellLinear.setBackgroundResource(R.drawable.curve_tw)
            holder.socialMediaIcon.setImageResource(R.mipmap.tw_new)
        }
        else if (socialMediaArrayList.get(position).title.equals("Linkedin"))
        {
           // holder.cellLinear.setBackgroundResource(R.drawable.curve_linked)
           // holder.socialMediaIcon.setImageResource(R.mipmap.linkedin)
        }
        else{
            holder.cellLinear.setBackgroundResource(R.drawable.curve_blo)
            holder.socialMediaIcon.setImageResource(R.mipmap.blo_new)
        }
    }
    override fun getItemCount(): Int {
        return socialMediaArrayList.size
    }
}