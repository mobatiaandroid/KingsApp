package com.example.kingsapp.activities.timetable

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.activities.reports.adapter.ReportsAdapterList
import com.example.kingsapp.activities.timetable.adapter.TimeTableAllWeekSelectionAdapterNew
import com.example.kingsapp.activities.timetable.adapter.TimeTableSingleWeekSelectionAdapter
import com.example.kingsapp.activities.timetable.adapter.TimeTableWeekListAdapter
import com.example.kingsapp.activities.timetable.model.DayModel
import com.example.kingsapp.activities.timetable.model.FieldModel
import com.example.kingsapp.activities.timetable.model.TimeTableApiListModel
import com.example.kingsapp.activities.timetable.model.WeekModel
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class TimeTableActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var weekListArrayString: ArrayList<String>
    lateinit var weekListArray: ArrayList<WeekModel>
    lateinit var weekRecyclerList: RecyclerView
    lateinit var timeTableAllRecycler: RecyclerView
    lateinit var card_viewAll: CardView
    lateinit var timeTableSingleRecycler: RecyclerView
    lateinit var linearLayoutManagerVertical: LinearLayoutManager
    lateinit var linearLayoutManagerVertical1: LinearLayoutManager
    lateinit var linearlayoutstudentlist: LinearLayout


    lateinit var mFieldModel : ArrayList<FieldModel>
    lateinit var timeTableListS : ArrayList<DayModel>
    lateinit var mSundayArrayList : ArrayList<TimeTableApiListModel>
    lateinit var studentName : TextView
    lateinit var student_name:ArrayList<Studentlist_model>
    lateinit var backarrow : ImageView



    var weekPosition = 0
    lateinit var dayOfTheWeek: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_timetable)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
    }

    private fun initFn() {

        mFieldModel = ArrayList()
        timeTableListS = ArrayList()
        weekListArrayString = ArrayList()
        mSundayArrayList = ArrayList()
        student_name = ArrayList()
        weekListArrayString.add("ALL")
        weekListArrayString.add("MONDAY")
        weekListArrayString.add("TUESDAY")
        weekListArrayString.add("WEDNESDAY")
        weekListArrayString.add("THURSDAY")
        weekListArrayString.add("FRIDAY")
        studentName = findViewById(R.id.studentName)
        linearlayoutstudentlist=findViewById(R.id.studentSpinner)
        backarrow = findViewById(R.id.back)

        var modell=Studentlist_model("Jane Mathewes",false)
        student_name.add(modell)
        var xmodel=Studentlist_model("Esther Mathews",false)
        student_name.add(xmodel)
        var nmodel=Studentlist_model("Gay Mathewes",false)
        student_name.add(nmodel)
        var emodel=Studentlist_model("Jane Mathewes",false)
        student_name.add(emodel)

        var model= FieldModel("TUT","10;30","11;30")
        mFieldModel.add(model)
        var model1= FieldModel("P1","10;30","11;30")
        mFieldModel.add(model1)
        var model2= FieldModel("P2","10;30","11;30")
        mFieldModel.add(model2)
        var model11= FieldModel("Break","10;30","11;30")
        mFieldModel.add(model11)
        var model3= FieldModel("P3","10;30","11;30")
        mFieldModel.add(model3)
        var model44= FieldModel("L1","10;30","11;30")
        mFieldModel.add(model44)
        var model444= FieldModel("L2","10;30","11;30")
        mFieldModel.add(model444)
        var model4= FieldModel("P4","10;30","11;30")
        mFieldModel.add(model4)
        var model5= FieldModel("P5","10;30","11;30")
        mFieldModel.add(model5)
        var model6= FieldModel("P6","10;30","11;30")
        mFieldModel.add(model6)

        var sub= DayModel(0,"Tutor")
        timeTableListS.add(sub)
        var sub1= DayModel(1,"Mathematics")
        timeTableListS.add(sub1)
        var sub2= DayModel(2,"PhysicalEducations")
        timeTableListS.add(sub2)
        var sub3= DayModel(3,"Mathematics")
        timeTableListS.add(sub3)
        var sub4= DayModel(4,"Economics")
        timeTableListS.add(sub4)
        var sub5= DayModel(5,"History")
        timeTableListS.add(sub5)
        var sub6= DayModel(6,"Mathematics")
        timeTableListS.add(sub6)
        var sub7= DayModel(7,"Mathematics")
        timeTableListS.add(sub7)
        var sub8= DayModel(8,"Mathematics")
        timeTableListS.add(sub8)
        var sub9= DayModel(9,"Mathematics")
        timeTableListS.add(sub9)

        var weekk= TimeTableApiListModel(0,"Tutor","Mark Tidball","TUT","07:45 AM")
        mSundayArrayList.add(weekk)
        var weekk1= TimeTableApiListModel(1,"Mathematics","Vicky Wright","P1","08:30 AM")
        mSundayArrayList.add(weekk1)
        var weekk7= TimeTableApiListModel(2,"Mathematics","Ben davey","Break","10:20 AM")
        mSundayArrayList.add(weekk7)
        var weekk2= TimeTableApiListModel(3,"PhysicalEducations","Wasim Nawab","P2","10:40 AM")
        mSundayArrayList.add(weekk2)
        var weekk3= TimeTableApiListModel(4,"Mathematics","Charlotte Giles","P3","11:35 AM")
        mSundayArrayList.add(weekk3)
        var weekk4= TimeTableApiListModel(5,"Null","Arun","L1","12:30 PM")
        mSundayArrayList.add(weekk4)
        var weekk5= TimeTableApiListModel(6,"History","Arun","P4","12:55 PM")
        mSundayArrayList.add(weekk5)
        var weekk6= TimeTableApiListModel(7,"Economics","Arun","P6","02:15 PM")
        mSundayArrayList.add(weekk6)
        backarrow.setOnClickListener {
            val intent = Intent(ncontext, MainActivity::class.java)
            startActivity(intent)
        }
    Log.e("mSundayArrayList", mSundayArrayList.toString())
        linearlayoutstudentlist.setOnClickListener {
            studentlist_popup(student_name)
            /* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*/

        }
        linearLayoutManagerVertical = LinearLayoutManager(ncontext)
        linearLayoutManagerVertical1 = LinearLayoutManager(ncontext)

        timeTableSingleRecycler = findViewById<RecyclerView>(R.id.timeTableSingleRecycler)
        linearLayoutManagerVertical1.orientation = LinearLayoutManager.VERTICAL
        timeTableSingleRecycler.layoutManager = linearLayoutManagerVertical1
        timeTableSingleRecycler.itemAnimator = DefaultItemAnimator()
        weekRecyclerList = findViewById<RecyclerView>(R.id.weekRecyclerList)
        recyclerinitializer()
        card_viewAll = findViewById(R.id.card_viewAll) as CardView

        weekListArray = ArrayList()
        Log.e("weekListArrayString", weekListArrayString.toString())
        for (i in 0..weekListArrayString.size - 1) {
            var weekModel =
                WeekModel(
                    weekListArrayString.get(i).toString(),
                    -1
                )
            weekListArray.add(weekModel)

        }
        //mWeekModel.setmTitle(weekListArrayString.get(i));
        /* if (!PreferenceManager.getUserId(mContext).equalsIgnoreCase("")) {
                mRecyclerViewArray.add(mWeekModel);
            } else {
                if (i != 3) {
                    mRecyclerViewArray.add(mWeekModel);

                }
            }*/
        val llm = LinearLayoutManager(ncontext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        weekRecyclerList.layoutManager = llm





        val timeTableWeekListAdapter =
            TimeTableWeekListAdapter(ncontext, weekListArray, weekPosition)
        weekRecyclerList.setAdapter(timeTableWeekListAdapter)

        timeTableSingleRecycler.visibility = View.GONE
        // timeTableAllRecycler.visibility = View.VISIBLE
        // tipContainer.visibility = View.VISIBLE

        card_viewAll.visibility = View.VISIBLE

        recyclerinitializer()
        timeTableAllRecycler.visibility = View.VISIBLE
        var mRecyclerAllAdapter = TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel,timeTableListS)
        timeTableAllRecycler.adapter = mRecyclerAllAdapter

        var sdf = SimpleDateFormat("EEEE")
        var d = Date()
        dayOfTheWeek = sdf.format(d)
        when (dayOfTheWeek) {
            "Sunday" -> {
                weekPosition = 1
            }
            "Monday" -> {
                weekPosition = 2
            }
            "Tuesday" -> {
                weekPosition = 3
            }
            "Wednesday" -> {
                weekPosition = 4
            }
            "Thursday" -> {
                weekPosition = 5
            }

            else -> {
                weekPosition = 0
            }
        }
        weekRecyclerList.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

//                val buttonAnimator =
//                    ObjectAnimator.ofFloat(tipContainer, "translationX", 0f, 400f)
//                buttonAnimator.duration = 3000
//                buttonAnimator.interpolator = BounceInterpolator()
//                buttonAnimator.start()
                // Your logic
                weekPosition = position
                if (weekPosition < 3) {
                    weekRecyclerList.scrollToPosition(0)
                } else {
                    weekRecyclerList.scrollToPosition(5)
                }
                for (i in 0 until weekListArray.size) {
                    if (i == position) {
                        weekListArray.get(i).positionSelected = i

                    } else {
                        weekListArray.get(i).positionSelected = -1
                    }
                }
                timeTableWeekListAdapter.notifyDataSetChanged()

                if (position != 0) {
                    timeTableAllRecycler.visibility = View.GONE
                    card_viewAll.visibility = View.GONE
                    //  timeTableAllRecycler.visibility=View.GONE
                   // tipContainer.visibility = View.GONE
                    timeTableSingleRecycler.visibility = View.VISIBLE
//                    if (mRangeModel.size>0)
//                    {
                    //card_viewAll.visibility = View.GONE
                    // timeTableAllRecycler.visibility = View.GONE
                    timeTableSingleRecycler.visibility = View.VISIBLE
                    if (weekPosition == 1) {
                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 2) {
                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter

                    } else if (weekPosition == 3) {
                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 4) {
                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 5) {
                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    }
                }
                else {
                    timeTableSingleRecycler.visibility = View.GONE
                    // timeTableAllRecycler.visibility = View.VISIBLE
                   // tipContainer.visibility = View.VISIBLE

                    card_viewAll.visibility = View.VISIBLE

                        recyclerinitializer()
                        timeTableAllRecycler.visibility = View.VISIBLE
                        var mRecyclerAllAdapter = TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel,timeTableListS)
                        timeTableAllRecycler.adapter = mRecyclerAllAdapter

                }
            }


        })
    }
    private fun studentlist_popup(student_name: ArrayList<Studentlist_model>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(ncontext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!! as ImageView

        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(ncontext)
        val studentlist_adapter =
            AbsenceStudentListAdapter(ncontext, student_name)
        recycler_view!!.adapter = studentlist_adapter

        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                //var id: String = student_name.get(position).id.toString()
                var name: String = student_name.get(position).name
                // PreferenceManager().setStudentname(nContext, name).toString()
                // Log.e("recycler id", id.toString())
                //leavelist(id)
                studentName.setText(name)
                dialog.dismiss()
            }

        })
        dialog.show()
    }
    fun recyclerinitializer() {
        timeTableAllRecycler = findViewById(R.id.timeTableAllRecycler) as RecyclerView
        linearLayoutManagerVertical.orientation = LinearLayoutManager.VERTICAL
        timeTableAllRecycler.layoutManager = linearLayoutManagerVertical
        timeTableAllRecycler.itemAnimator = DefaultItemAnimator()
    }
}