package com.kingseducation.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R

class StudentListAdapterArab (private val context: Context, private val parentassoictionlist: ArrayList<Int>, private  val name:Array<String>):
    RecyclerView.Adapter<StudentListAdapter.MyViewHolder>(){
    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var stud_profile: ImageView = view.findViewById(R.id.stud_profile)
        var textview: TextView = view.findViewById(R.id.textview)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_arab, parent, false)
        return StudentListAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentListAdapter.MyViewHolder, position: Int) {
        Log.e("list",parentassoictionlist.toString())
        val list = parentassoictionlist[position]
        val namelist = name[position]
        holder.stud_profile .setImageResource(list)
        Log.e("studentlistimages", list.toString())
        //slideUp(holder.stud_profile,namelist,holder.textview)

        var animation= AnimationUtils.loadAnimation(context,R.anim.slide_top)
        animation.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {
                holder.textview.visibility = View.VISIBLE
                holder.textview.text = namelist
                /* var animationlist=AnimationUtils.loadAnimation(context,R.anim.slide_down)
                 holder.textview.startAnimation(animationlist)*/
                slideUp(holder.textview)
            }

            @SuppressLint("ResourceAsColor")
            override fun onAnimationEnd(p0: Animation?) {
                Log.e("NameList",namelist)



            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        holder.stud_profile.startAnimation(animation)

        // holder.textview.setText(namelist)

        /* Glide.with(context) //1
             .load(list)
             .placeholder(R.drawable.boy)
             .error(R.drawable.boy)
             .skipMemoryCache(true) //2
             .diskCacheStrategy(DiskCacheStrategy.NONE) //3
             .transform(CircleCrop()) //4
             .into(holder.stud_profile)*/
    }
    fun slideUp(view: TextView) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            -80F,  // fromYDelta
            0F) // toYDelta
        animate.duration = 3000
        animate.fillAfter = true
        animate.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onAnimationEnd(p0: Animation?) {
                view.setTextColor(ContextCompat.getColor(context, R.color.white))
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        view.startAnimation(animate)
    }

    override fun getItemCount(): Int {
        return parentassoictionlist.size
    }
}