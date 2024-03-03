package com.kingseducation.app.activities.login.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kingseducation.app.R
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.manager.PreferenceManager

class ChildSelectionAdapter(private val mcontext: Context,
                            private  val student_name:ArrayList<StudentList>):
    RecyclerView.Adapter<ChildSelectionAdapter.MyViewHolder>()  {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var studentName: TextView = view.findViewById(R.id.student_name)
        var stud_profile : ImageView = view.findViewById(R.id.stud_profile)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item_adapter, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val namelist = student_name[position].fullname
        /*val startAnimation: Animation =
            AnimationUtils.loadAnimation(mcontext, R.anim.blinking_animation)
        holder.stud_profile.startAnimation(startAnimation)*/
        if (student_name[position].photo != null && !student_name[position].photo.isEmpty()) {
            val glideUrl = GlideUrl(
                student_name[position].photo,
                LazyHeaders.Builder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + PreferenceManager().getAccessToken(mcontext)
                            .toString()
                    )
                    .build()
            )
            Glide.with(mcontext)
                .load(glideUrl)
                .transform(CircleCrop()) // Apply circular transformation
                .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                .error(R.drawable.profile_photo) // Image to display in case of error
                .into(holder.stud_profile)
        } else {
            Toast.makeText(mcontext, "Image empty", Toast.LENGTH_SHORT).show()
            // Handle the case when studentImg is null or empty
        }
        holder.studentName.text = namelist

    }

    override fun getItemCount(): Int {
        return student_name.size
    }
}