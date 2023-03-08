package com.example.kingsapp.activities.timetable.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.timetable.model.DayModel
import com.example.kingsapp.activities.timetable.model.FieldModel


var isClick: Boolean = false

@Suppress("DEPRECATION")
class TimeTableAllWeekSelectionAdapterNew(
    private var mContext: Context,
    private var mFeildList: ArrayList<FieldModel>,
    private var timeTableListS: ArrayList<DayModel>
) :
    RecyclerView.Adapter<TimeTableAllWeekSelectionAdapterNew.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var periodTxt: TextView = view.findViewById(R.id.periodTxt)
        var timeTxt: TextView = view.findViewById(R.id.timeTxt)
        var tutor1: TextView = view.findViewById(R.id.tutor1)
        var tutor2: TextView = view.findViewById(R.id.tutor2)
        var tutor3: TextView = view.findViewById(R.id.tutor3)
        var tutor4: TextView = view.findViewById(R.id.tutor4)
        var tutor5: TextView = view.findViewById(R.id.tutor5)
        var timeBreak: TextView = view.findViewById(R.id.timeBreak)
        var txtBreak: TextView = view.findViewById(R.id.txtBreak)
        var countMTextView: TextView = view.findViewById(R.id.countMTextView)
        var countTTextView: TextView = view.findViewById(R.id.countTTextView)
        var countWTextView: TextView = view.findViewById(R.id.countWTextView)
        var countThTextView: TextView = view.findViewById(R.id.countThTextView)
        var countFTextView: TextView = view.findViewById(R.id.countFTextView)
        var timeLinear: LinearLayout = view.findViewById(R.id.timeLinear)
        var tutorLinear: LinearLayout = view.findViewById(R.id.tutorLinear)
        var starLinearR: LinearLayout = view.findViewById(R.id.starLinearR)
        var relSub: LinearLayout = view.findViewById(R.id.relSub)
        var llread: RelativeLayout = view.findViewById(R.id.llread)
        var countMRel: RelativeLayout = view.findViewById(R.id.countMRel)
        var countTRel: RelativeLayout = view.findViewById(R.id.countTRel)
        var countWRel: RelativeLayout = view.findViewById(R.id.countWRel)
        var countThRel: RelativeLayout = view.findViewById(R.id.countThRel)
        var countFRel: RelativeLayout = view.findViewById(R.id.countFRel)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_selection_timetable_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(mFeildList.get(position).sortname.equals("Break"))
            {
                holder.relSub.visibility=View.GONE
                holder.timeLinear.visibility=View.VISIBLE
                holder.txtBreak.setText(mFeildList.get(position).sortname)
                holder.timeLinear.setBackgroundResource(R.color.kings_blue)

            }
        else if (mFeildList.get(position).sortname.equals("L1"))
        {
            holder.relSub.visibility=View.GONE
            holder.timeLinear.visibility=View.VISIBLE
            holder.txtBreak.setText(mFeildList.get(position).sortname)
            holder.timeLinear.setBackgroundResource(R.color.kings_blue)
        }
        else if (mFeildList.get(position).sortname.equals("L2"))
        {
            holder.relSub.visibility=View.GONE
            holder.timeLinear.visibility=View.VISIBLE
            holder.txtBreak.setText(mFeildList.get(position).sortname)
            holder.timeLinear.setBackgroundResource(R.color.kings_blue)
        }
        else{
            holder.relSub.visibility=View.VISIBLE
            holder.timeLinear.visibility=View.GONE
            holder.periodTxt.setText(mFeildList.get(position).sortname)
            holder.tutor1.text = timeTableListS.get(position).subject_name
            holder.tutor2.text = timeTableListS.get(position).subject_name
            holder.tutor3.text = timeTableListS.get(position).subject_name
            holder.tutor4.text = timeTableListS.get(position).subject_name
            holder.tutor5.text = timeTableListS.get(position).subject_name
        }

    }

    override fun getItemCount(): Int {

        return mFeildList.size

    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }
}