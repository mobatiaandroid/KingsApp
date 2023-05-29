package com.example.kingsapp.activities.timetable.adapter

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.timetable.model.FieldModel
import com.example.kingsapp.activities.timetable.model.MondayList
import com.example.kingsapp.activities.timetable.model.PeriodModel
import com.example.kingsapp.manager.PreferenceManager
import com.ryanharter.android.tooltips.ToolTip
import com.ryanharter.android.tooltips.ToolTipLayout


var isClick: Boolean = false

@Suppress("DEPRECATION")
class TimeTableAllWeekSelectionAdapterNew(
    private var mContext: Context,
    private var mPeriodModel: List<PeriodModel>,
    private var mFeildList: ArrayList<FieldModel>,
    var tipContainer: ToolTipLayout,
    private var mTimetableApiArrayList: ArrayList<MondayList>


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
         // var mStaffModelArrayList: ArrayList<MondayList>?
      //  mStaffModelArrayList= PreferenceManager().getsortname(mContext)
        //Log.e("sortname:", mStaffModelArrayList!![position].period_name.toString())
        if(mFeildList.get(position).sortname.equals("Break"))
        {
            holder.relSub.visibility=View.GONE
            holder.timeLinear.visibility=View.VISIBLE
            holder.txtBreak.setText(mFeildList.get(position).sortname)
            holder.timeLinear.setBackgroundResource(R.color.kings_blue)

        }
        else if (mFeildList.get(position).sortname.equals("Lunch"))
        {
            holder.relSub.visibility=View.GONE
            holder.timeLinear.visibility=View.VISIBLE
            holder.txtBreak.setText(mFeildList.get(position).sortname)
            holder.timeLinear.setBackgroundResource(R.color.kings_blue)
        }
        else
        {
        holder.periodTxt.text = mFeildList[position].sortname
        Log.e("PERIODTEXT:",mFeildList[position].sortname)

        holder.timeTxt.visibility = View.GONE
        if (mPeriodModel[position].timeTableDayModel.size > 0) {
            holder.timeLinear.visibility = View.GONE
            holder.tutorLinear.visibility = View.VISIBLE
            holder.llread.visibility = View.VISIBLE
            holder.starLinearR.visibility = View.VISIBLE
            holder.relSub.visibility = View.VISIBLE


            if (mPeriodModel[position].timeTableListS.size > 1) {
                holder.tutor1.text = mPeriodModel.get(position).timeTableListS.get(0).subject_name
            } else {
                holder.tutor1.text = mPeriodModel.get(position).sunday
            }
            if (mPeriodModel.get(position).timeTableListM.size > 1) {
                holder.tutor2.text = mPeriodModel.get(position).timeTableListM.get(0).subject_name
            } else {
                holder.tutor2.text = mPeriodModel.get(position).monday
            }
            if (mPeriodModel.get(position).timeTableListTu.size > 1) {
                holder.tutor3.text = mPeriodModel.get(position).timeTableListTu.get(0).subject_name
            } else {
                holder.tutor3.text = mPeriodModel.get(position).tuesday
            }
            if (mPeriodModel.get(position).timeTableListW.size > 1) {
                holder.tutor4.text = mPeriodModel.get(position).timeTableListW.get(0).subject_name
            } else {
                holder.tutor4.text = mPeriodModel.get(position).wednesday
            }
            if (mPeriodModel.get(position).timeTableListTh.size > 1) {
                holder.tutor5.text = mPeriodModel.get(position).timeTableListTh.get(0).subject_name
            } else {
                holder.tutor5.text = mPeriodModel.get(position).thursday
            }

            if (mPeriodModel.get(position).countS > 1) {
//                holder.countMRel.visibility=View.VISIBLE
//////                holder.countMTextView.visibility=View.VISIBLE
                holder.countMRel.visibility = View.GONE
                holder.countMTextView.visibility = View.GONE
                var count: Int = mPeriodModel.get(position).countS - 1
                holder.countMTextView.text = "+" + count
            } else {
                holder.countMRel.visibility = View.GONE
                holder.countMTextView.visibility = View.GONE
            }
            if (mPeriodModel.get(position).countM > 1) {
                holder.countTRel.visibility = View.GONE
                holder.countTTextView.visibility = View.GONE
//                holder.countTRel.visibility=View.VISIBLE
//                holder.countTTextView.visibility=View.VISIBLE
                var count: Int = mPeriodModel.get(position).countM - 1
                holder.countTTextView.text = "+" + count
            } else {
                holder.countTRel.visibility = View.GONE
                holder.countTTextView.visibility = View.GONE
            }
            if (mPeriodModel.get(position).countT > 1) {
                holder.countWRel.visibility = View.GONE
                holder.countWTextView.visibility = View.GONE
//                holder.countWRel.visibility=View.VISIBLE
//                holder.countWTextView.visibility=View.VISIBLE
                var count: Int = mPeriodModel.get(position).countT - 1
                holder.countWTextView.text = "+" + count
            } else {
                holder.countWRel.visibility = View.GONE
                holder.countWTextView.visibility = View.GONE
            }
            if (mPeriodModel.get(position).countW > 1) {
                holder.countThRel.visibility = View.GONE
                holder.countThTextView.visibility = View.GONE
//                holder.countThRel.visibility=View.VISIBLE
//                holder.countThTextView.visibility=View.VISIBLE
                var count: Int = mPeriodModel.get(position).countW - 1
                holder.countThTextView.text = "+" + count
            } else {
                holder.countThRel.visibility = View.GONE
                holder.countThTextView.visibility = View.GONE
            }
            if (mPeriodModel.get(position).countTh > 1) {
                holder.countFRel.visibility = View.GONE
                holder.countFTextView.visibility = View.GONE
//                holder.countFRel.visibility=View.VISIBLE
//                holder.countFTextView.visibility=View.VISIBLE
                var count: Int = mPeriodModel.get(position).countTh - 1
                holder.countFTextView.text = "+" + count
            } else {
                holder.countFRel.visibility = View.GONE
                holder.countFTextView.visibility = View.GONE
            }

        }
        else {
            holder.tutor1.text = ""
            holder.tutor2.text = ""
            holder.tutor3.text = ""
            holder.tutor4.text = ""
            holder.tutor5.text = ""
            holder.countMRel.visibility = View.GONE
            holder.countMTextView.visibility = View.GONE
            holder.countTRel.visibility = View.GONE
            holder.countTTextView.visibility = View.GONE
            holder.countWRel.visibility = View.GONE
            holder.countWTextView.visibility = View.GONE
            holder.countThRel.visibility = View.GONE
            holder.countThTextView.visibility = View.GONE
            holder.countFRel.visibility = View.GONE
            holder.countFTextView.visibility = View.GONE
        }
        }
        holder.tutor1.setOnClickListener {
            //Quick and Easy intent selector in tooltip styles
            if (holder.tutor1.text.toString().equals("", ignoreCase = true)) {
            } else {
                isClick = true
//                System.out.println(
//                    "mon:::" + mPeriodModel.get(position).getTimeTableListM().size()
//                )
                val itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable: RecyclerView =
                    itemView.findViewById(R.id.recycler_view_timetable)
                val linearlayout: LinearLayout =
                    itemView.findViewById(R.id.Linear)
                val close_popup: ImageView = itemView.findViewById(R.id.popup_close)

                close_popup.setOnClickListener {
                    linearlayout.visibility = View.GONE
                }

                recycler_view_timetable.setHasFixedSize(true)
                //mainRecycleRel.setVisibility(View.GONE);
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter =
                    TimeTablePopUpRecyclerAdapter(
                        mContext,
                        mPeriodModel.get(position).timeTableListS
                    )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                //  chromeHelpPopup.show(holder.tutor1);
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor1) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        ) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor1) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        ) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        }

        holder.tutor2.setOnClickListener(View.OnClickListener {
            if (holder.tutor2.text.equals("")) {

            } else {
                isClick = true
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable: RecyclerView =
                    itemView.findViewById(R.id.recycler_view_timetable)
                val linearlayout: LinearLayout =
                    itemView.findViewById(R.id.Linear)
                val close_popup: ImageView = itemView.findViewById(R.id.popup_close)

                close_popup.setOnClickListener {
                    linearlayout.visibility = View.GONE
                }
                recycler_view_timetable.setHasFixedSize(true)
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter =
                    TimeTablePopUpRecyclerAdapter(
                        mContext,
                        mPeriodModel.get(position).timeTableListM
                    )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor2) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor2) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        )
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        })
        holder.tutor3.setOnClickListener {

            if (holder.tutor3.text == "") {

            } else {

                isClick = true


                val itemView = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)


                val recycler_view_timetable: RecyclerView = itemView.findViewById(R.id.recycler_view_timetable)
                val linearlayout: LinearLayout =
                    itemView.findViewById(R.id.Linear)
                val close_popup: ImageView = itemView.findViewById(R.id.popup_close)

                close_popup.setOnClickListener {
                    linearlayout.visibility = View.GONE
                }

                recycler_view_timetable.setHasFixedSize(true)
                val llmAtime = LinearLayoutManager(mContext)

                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter = TimeTablePopUpRecyclerAdapter(mContext,
                    mPeriodModel[position].timeTableListTu)
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                var t: ToolTip? = null
                if (position == 0) {
                    t =  ToolTip.Builder(mContext.applicationContext)
                        .anchor(holder.tutor3) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    t = ToolTip.Builder(mContext)
                        .anchor(holder.tutor3) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        )
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()

                }
                tipContainer.addTooltip(t)
            }

        }
        holder.tutor4.setOnClickListener(View.OnClickListener {
            if (holder.tutor4.text.equals("")) {

            } else {
                isClick = true
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable: RecyclerView =
                    itemView.findViewById(R.id.recycler_view_timetable)
                val linearlayout: LinearLayout =
                    itemView.findViewById(R.id.Linear)
                val close_popup: ImageView = itemView.findViewById(R.id.popup_close)

                close_popup.setOnClickListener {
                    linearlayout.visibility = View.GONE
                }
                recycler_view_timetable.setHasFixedSize(true)
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter =
                    TimeTablePopUpRecyclerAdapter(
                        mContext,
                        mPeriodModel.get(position).timeTableListW
                    )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor4) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor4) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        )
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        })
        holder.tutor5.setOnClickListener(View.OnClickListener {
            if (holder.tutor5.text.equals("")) {

            } else {
                isClick = true
                val itemView: View = LayoutInflater.from(mContext)
                    .inflate(R.layout.popup_timetable_activity, null, false)
                val recycler_view_timetable: RecyclerView =
                    itemView.findViewById(R.id.recycler_view_timetable)
                val linearlayout: LinearLayout =
                    itemView.findViewById(R.id.Linear)
                val close_popup: ImageView = itemView.findViewById(R.id.popup_close)

                close_popup.setOnClickListener {
                    linearlayout.visibility = View.GONE
                }
                recycler_view_timetable.setHasFixedSize(true)
                val llmAtime = LinearLayoutManager(mContext)
                llmAtime.orientation = LinearLayoutManager.VERTICAL
                recycler_view_timetable.layoutManager = llmAtime
                val mTimeTablePopUpRecyclerAdapter =
                    TimeTablePopUpRecyclerAdapter(
                        mContext,
                        mPeriodModel.get(position).timeTableListTh
                    )
                mTimeTablePopUpRecyclerAdapter.notifyDataSetChanged()
                recycler_view_timetable.adapter = mTimeTablePopUpRecyclerAdapter
                var t: ToolTip? = null
                t = if (position == 0) {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor5) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.BOTTOM) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(mContext.resources.getColor(R.color.ttpop)) // The color of the pointer arrow
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                } else {
                    ToolTip.Builder(mContext)
                        .anchor(holder.tutor5) // The view to which the ToolTip should be anchored
                        .gravity(Gravity.TOP) // The location of the view in relation to the anchor (LEFT, RIGHT, TOP, BOTTOM)
                        .color(
                            mContext.resources.getColor(R.color.ttpop)
                        )
                        .pointerSize(10) // The size of the pointer
                        .contentView(itemView) // The actual contents of the ToolTip
                        .build()
                }
                tipContainer.addTooltip(t)
            }
        })

    }

    override fun getItemCount(): Int {

        return mFeildList.size

    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }
}