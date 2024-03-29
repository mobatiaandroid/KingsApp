package com.kingseducation.app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.kingseducation.app.R
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.model.StudentList
import com.kingseducation.app.fragment.mContext
import com.kingseducation.app.manager.PreferenceManager


class StudentListAdapter(
    private val context: Context,
    private val parentassoictionlist: ArrayList<StudentList>,
    private val recyclerView: RecyclerView,
    private val lang_switch: Switch
) :
    RecyclerView.Adapter<StudentListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var stud_profile: ImageView = view.findViewById(R.id.stud_profile)
        var textview: TextView = view.findViewById(R.id.textview)
        var relstudenttext: RelativeLayout = view.findViewById(R.id.relstudenttext)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list, parent, false)
        return MyViewHolder(itemView)
    }

    fun convertFormat(input: String): String {
        // Use regular expressions to replace the extra space between digits and letters
        return input.replace("(\\d)\\s(\\d)([A-Z])".toRegex(), "$1 $2 $3")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var flag: Boolean = true
        Log.e("list", parentassoictionlist.toString())
        val list = parentassoictionlist[position].fullname

        if (!parentassoictionlist[position].photo.equals("")) {
            val glideUrl = GlideUrl(
                parentassoictionlist[position].photo,
                LazyHeaders.Builder()
                    .addHeader(
                        "Authorization",
                        "Bearer " + PreferenceManager().getAccessToken(mContext)
                            .toString()
                    )
                    .build()
            )

            Glide.with(mContext)
                .load(glideUrl)
                .transform(CircleCrop()) // Apply circular transformation
                .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                .error(R.drawable.profile_photo) // Image to display in case of error
                .into(holder.stud_profile)

        } else {
            holder.stud_profile.setImageResource(R.drawable.profile_photo)
        }

        holder.stud_profile.setOnClickListener {
            PreferenceManager().setStudentName(
                context,
                parentassoictionlist[position].fullname.toString()
            )
            PreferenceManager().setStudent_ID(context, parentassoictionlist[position].id.toString())
            PreferenceManager().setStudentClass(
                context,
                convertFormat(parentassoictionlist[position].classs.toString())
            )
            PreferenceManager().setStudentPhoto(
                context,
                parentassoictionlist[position].photo.toString()
            )
            PreferenceManager().setLanguageschool(
                context,
                parentassoictionlist[position].school_language_type
            )
            PreferenceManager().setSchoolName(context, parentassoictionlist[position].school_name)
            PreferenceManager().setSchoolIdentifier(
                context,
                parentassoictionlist[position].schoolIdentifier
            )
            Log.e(
                "shool",
                PreferenceManager().getLanguageschool(context)
                    .toString()
            )

            if (PreferenceManager().getLanguageschool(context).equals("2")) {
                lang_switch.visibility = View.VISIBLE
            } else {
                lang_switch.visibility = View.GONE
            }

            recyclerView.visibility = View.GONE
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)

        }
        Log.e("studentlistimages", list.toString())
        //slideUp(holder.stud_profile,namelist,holder.textview)
        fun onSlideViewButtonClick(view: View) {}
        var animation = AnimationUtils.loadAnimation(context, R.anim.slide_top)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                holder.relstudenttext.visibility = View.VISIBLE
                holder.textview.visibility = View.VISIBLE
                holder.textview.text = list
                /* var animationlist=AnimationUtils.loadAnimation(context,R.anim.slide_down)
                 holder.textview.startAnimation(animationlist)*/
                slideUp(holder.relstudenttext)
            }

            override fun onAnimationEnd(p0: Animation?) {
                Log.e("NameList", list)


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

    fun slideUp(view: RelativeLayout) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,  // fromXDelta
            0F,  // toXDelta
            -80F,  // fromYDelta
            0F
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        animate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            @SuppressLint("ResourceAsColor")
            override fun onAnimationEnd(p0: Animation?) {
                //view.setTextColor(ContextCompat.getColor(context, R.color.white))
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