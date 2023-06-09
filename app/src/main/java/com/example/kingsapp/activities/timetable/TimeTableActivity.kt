package com.example.kingsapp.activities.timetable

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.SigninyourAccountActivity
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.timetable.adapter.TimeTableAllWeekSelectionAdapterNew
import com.example.kingsapp.activities.timetable.adapter.TimeTableSingleWeekSelectionAdapter
import com.example.kingsapp.activities.timetable.adapter.TimeTableWeekListAdapter
import com.example.kingsapp.activities.timetable.model.*
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.example.kingsapp.constants.api.ApiClient
import com.ryanharter.android.tooltips.ToolTipLayout
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    lateinit var progressBarDialog: ProgressBarDialog

    lateinit var mFieldModel: ArrayList<FieldModel>
    lateinit var timeTableListS: ArrayList<DayModel>

    //    lateinit var student1timetable: ArrayList<DayModel>
//    lateinit var student2timetable: ArrayList<DayModel>
   // lateinit var mSundayArrayList: ArrayList<TimeTableApiListModel>
    lateinit var mMondayArrayList: ArrayList<MondayList>
    lateinit var mTuesdayArrayList: ArrayList<MondayList>
    lateinit var mwednesdayArrayList: ArrayList<MondayList>
    lateinit var mThurdayArrayList: ArrayList<MondayList>
    lateinit var mFridayArrayList: ArrayList<MondayList>
    lateinit var tipContainer: ToolTipLayout


    var feildAPIArrayList = ArrayList<FieldModel>()
    var mTimetableApiArrayList = ArrayList<MondayList>()
    var mPeriodModel = ArrayList<PeriodModel>()

    lateinit var studentNameTextView: TextView
    lateinit var studentName: String
    lateinit var student_name: ArrayList<StudentList>
    lateinit var backarrow: ImageView


    lateinit var studentId: String
    lateinit var studentImg: String
    lateinit var student_class: String
    lateinit var imagicon: ImageView
    lateinit var studentclass: TextView
    lateinit var reasonAPI: String
    var weekPosition = 0
    lateinit var dayOfTheWeek: String
    lateinit var textView:TextView
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

    private fun callTimeTableApi() {
       // mSundayArrayList = ArrayList()
        progressBarDialog.show()
        mMondayArrayList= ArrayList()
        mTuesdayArrayList= ArrayList()
        mwednesdayArrayList= ArrayList()
        mThurdayArrayList= ArrayList()
        mFridayArrayList= ArrayList()
        mFridayArrayList = ArrayList()
        weekRecyclerList.visibility=View.GONE

        Log.e("type", PreferenceManager().getLanguagetype(ncontext).toString())
        val paramObject = JsonObject().apply {
            addProperty("student_id", PreferenceManager().getStudent_ID(ncontext).toString())


        }
        val call: Call<TimeTableResponseModel> = ApiClient.getApiService().timetable("Bearer "+
                PreferenceManager().getAccessToken(ncontext).toString(), paramObject)
        call.enqueue(object : retrofit2.Callback<TimeTableResponseModel> {
            override fun onResponse(
                call: Call<TimeTableResponseModel>,
                response: Response<TimeTableResponseModel>
            ) {
                progressBarDialog.hide()

                Log.e("Response",response.body().toString())
                if (response.body() != null) {
                    if (response.body()!!.status.equals(100))
                    {

                        weekRecyclerList.visibility=View.VISIBLE

                        feildAPIArrayList.addAll(response.body()!!.timetable.field1List)
                        for (i in 0..feildAPIArrayList.size - 1) {
                            var model = FieldModel(
                                feildAPIArrayList.get(i).sortname,
                                feildAPIArrayList.get(i).starttime,
                                feildAPIArrayList.get(i).endtime
                            )
                            mFieldModel.add(model)
                        }

                        Log.e("monday", response.body()!!.timetable.range.Monday.toString())

if (response.body()!!.timetable.range.Monday.isEmpty()&&response.body()!!.timetable.range.Tuesday.isEmpty()&&
    response.body()!!.timetable.range.Wednesday.isEmpty()&&response.body()!!.timetable.range.Thursday.isEmpty()&&
    response.body()!!.timetable.range.Friday.isEmpty())
{

    timeTableSingleRecycler.visibility = View.GONE
    timeTableAllRecycler.visibility = View.GONE
    card_viewAll.visibility = View.GONE
    weekRecyclerList.visibility = View.GONE
    Toast.makeText(ncontext, "No Data", Toast.LENGTH_SHORT).show()
}
      else
{
    mMondayArrayList.addAll(response.body()!!.timetable.range.Monday)
    Log.e("monday", mMondayArrayList.toString())

    mTuesdayArrayList.addAll(response.body()!!.timetable.range.Tuesday)
    Log.e("tuesday", mTuesdayArrayList.toString())

    mwednesdayArrayList.addAll(response.body()!!.timetable.range.Wednesday)
    Log.e("wednesday", mwednesdayArrayList.toString())

    mThurdayArrayList.addAll(response.body()!!.timetable.range.Thursday)
    Log.e("thursday", mThurdayArrayList.toString())

    mFridayArrayList.addAll(response.body()!!.timetable.range.Friday)
    Log.e("friday", mFridayArrayList.toString())
}


                        timeTableSingleRecycler.visibility = View.GONE
                        timeTableAllRecycler.visibility = View.VISIBLE
                        card_viewAll.visibility = View.VISIBLE
                        mTimetableApiArrayList = ArrayList()


                        mTimetableApiArrayList.addAll(response.body()!!.timetable.timeTableList)
                        /*for(i in mTimetableApiArrayList.indices)
                        {
                            PreferenceManager().savesortname(ncontext,mTimetableApiArrayList)
                            Log.e("set", PreferenceManager().getsortname(ncontext).toString())
                        }*/

                       /* Log.e("arrayList",mTimetableApiArrayList.get(0).period_name)
                        Log.e("arrayList2",mTimetableApiArrayList.get(1).period_name)*/

                        mPeriodModel = ArrayList()
                        var mDataModelArrayList = ArrayList<DayModel>()
                        var s = 0
                        var m = 0
                        var tu = 0
                        var w = 0
                        var th = 0
                        for (f in 0..feildAPIArrayList.size - 1) {
                            var mDayModel: DayModel = DayModel()
                            var mPeriod: PeriodModel = PeriodModel()
                            var timeTableListS = ArrayList<DayModel>()
                            var timeTableListM = ArrayList<DayModel>()
                            var timeTableListT = ArrayList<DayModel>()
                            var timeTableListW = ArrayList<DayModel>()
                            var timeTableListTh = ArrayList<DayModel>()
                            for (t in 0..mTimetableApiArrayList.size - 1) {
                                if (feildAPIArrayList.get(f).sortname.equals(
                                        mTimetableApiArrayList.get(t).period_name
                                    )
                                ) {
                                    Log.e(
                                        "Sortname",
                                        mTimetableApiArrayList.get(t).period_name
                                    )
                                    mDayModel.id = mTimetableApiArrayList.get(t).id
                                    mDayModel.period_id =
                                        mTimetableApiArrayList.get(t).period_order
                                    mDayModel.day = mTimetableApiArrayList.get(t).day_name
                                    mDayModel.sortname =
                                        mTimetableApiArrayList.get(t).period_name
                                    mDayModel.starttime =
                                        mTimetableApiArrayList.get(t).start_time
                                    mDayModel.endtime =
                                        mTimetableApiArrayList.get(t).end_time
                                    mDayModel.subject_name =
                                        mTimetableApiArrayList.get(t).subject_name


                                    //    mDayModel.staff=mTimetableApiArrayList.get(t).staff

                                    if (mTimetableApiArrayList.get(t).day_name.equals("Monday")) {
                                        s = s + 1
                                        var dayModel = DayModel()
                                        dayModel.id = mTimetableApiArrayList.get(t).id
                                        dayModel.period_id =
                                            mTimetableApiArrayList.get(t).period_order
                                        dayModel.day = mTimetableApiArrayList.get(t).day_name
                                        dayModel.sortname =
                                            mTimetableApiArrayList.get(t).period_name
                                        dayModel.starttime =
                                            mTimetableApiArrayList.get(t).start_time
                                        dayModel.endtime =
                                            mTimetableApiArrayList.get(t).end_time
                                        dayModel.subject_name =
                                            mTimetableApiArrayList.get(t).subject_name

                                        dayModel.staff = mTimetableApiArrayList.get(t).staff
                                        timeTableListS.add(dayModel)
                                        mPeriod.sunday =
                                            mTimetableApiArrayList.get(t).subject_name
                                    } else if (mTimetableApiArrayList.get(t).day_name.equals("Tuesday")) {
                                        m = m + 1
                                        var dayModel = DayModel()
                                        dayModel.id = mTimetableApiArrayList.get(t).id
                                        dayModel.period_id =
                                            mTimetableApiArrayList.get(t).period_order
                                        dayModel.subject_name =
                                            mTimetableApiArrayList.get(t).subject_name
                                        dayModel.staff = mTimetableApiArrayList.get(t).staff

                                        dayModel.day = mTimetableApiArrayList.get(t).day_name
                                        dayModel.sortname =
                                            mTimetableApiArrayList.get(t).period_name
                                        dayModel.starttime =
                                            mTimetableApiArrayList.get(t).start_time
                                        dayModel.endtime =
                                            mTimetableApiArrayList.get(t).end_time
                                        timeTableListM.add(dayModel)
                                        mPeriod.monday =
                                            mTimetableApiArrayList.get(t).subject_name
                                    } else if (mTimetableApiArrayList.get(t).day_name.equals("Wednesday")) {
                                        tu = tu + 1
                                        var dayModel = DayModel()
                                        dayModel.id = mTimetableApiArrayList.get(t).id
                                        dayModel.period_id =
                                            mTimetableApiArrayList.get(t).period_order
                                        dayModel.day = mTimetableApiArrayList.get(t).day_name
                                        dayModel.sortname =
                                            mTimetableApiArrayList.get(t).period_name
                                        dayModel.starttime =
                                            mTimetableApiArrayList.get(t).start_time
                                        dayModel.endtime =
                                            mTimetableApiArrayList.get(t).end_time
                                        dayModel.subject_name =
                                            mTimetableApiArrayList.get(t).subject_name

                                        dayModel.staff = mTimetableApiArrayList.get(t).staff
                                        timeTableListT.add(dayModel)
                                        mPeriod.tuesday =
                                            mTimetableApiArrayList.get(t).subject_name
                                    } else if (mTimetableApiArrayList.get(t).day_name.equals("Thursday")) {
                                        w = w + 1
                                        var dayModel = DayModel()
                                        dayModel.id = mTimetableApiArrayList.get(t).id
                                        dayModel.period_id =
                                            mTimetableApiArrayList.get(t).period_order
                                        dayModel.day = mTimetableApiArrayList.get(t).day_name
                                        dayModel.sortname =
                                            mTimetableApiArrayList.get(t).period_name
                                        dayModel.starttime =
                                            mTimetableApiArrayList.get(t).start_time
                                        dayModel.endtime =
                                            mTimetableApiArrayList.get(t).end_time
                                        dayModel.subject_name =
                                            mTimetableApiArrayList.get(t).subject_name

                                        dayModel.staff = mTimetableApiArrayList.get(t).staff
                                        timeTableListW.add(dayModel)
                                        mPeriod.wednesday =
                                            mTimetableApiArrayList.get(t).subject_name
                                    } else if (mTimetableApiArrayList.get(t).day_name.equals("Friday")) {
                                        th = th + 1
                                        var dayModel = DayModel()
                                        dayModel.id = mTimetableApiArrayList.get(t).id
                                        dayModel.period_id =
                                            mTimetableApiArrayList.get(t).period_order
                                        dayModel.day = mTimetableApiArrayList.get(t).day_name
                                        dayModel.sortname =
                                            mTimetableApiArrayList.get(t).period_name
                                        dayModel.starttime =
                                            mTimetableApiArrayList.get(t).start_time
                                        dayModel.endtime =
                                            mTimetableApiArrayList.get(t).end_time
                                        dayModel.subject_name =
                                            mTimetableApiArrayList.get(t).subject_name
                                        dayModel.staff = mTimetableApiArrayList.get(t).staff
                                        timeTableListTh.add(dayModel)
                                        mPeriod.thursday =
                                            mTimetableApiArrayList.get(t).subject_name
                                    } else {
                                        mPeriod.sunday = ""
                                        mPeriod.monday = ""
                                        mPeriod.tuesday = ""
                                        mPeriod.wednesday = ""
                                        mPeriod.thursday = ""
                                    }
                                    mPeriod.countS = s
                                    mPeriod.countM = m
                                    mPeriod.countT = tu
                                    mPeriod.countW = w
                                    mPeriod.countTh = th
                                    mPeriod.timeTableListS = timeTableListS
                                    mPeriod.timeTableListM = timeTableListM
                                    mPeriod.timeTableListTu = timeTableListT
                                    mPeriod.timeTableListW = timeTableListW
                                    mPeriod.timeTableListTh = timeTableListTh

                                }
                            }
                            mDataModelArrayList.add(mDayModel)
                            mPeriod.timeTableDayModel = mDataModelArrayList
                            mPeriodModel.add(mPeriod)
                        }

                        if (weekPosition != 0) {
                            timeTableAllRecycler.visibility = View.GONE
                            card_viewAll.visibility = View.GONE
                            //  timeTableAllRecycler.visibility=View.GONE
                             tipContainer.visibility = View.GONE
                            timeTableSingleRecycler.visibility = View.VISIBLE
//                    if (mRangeModel.size>0)
//                    {
                            //card_viewAll.visibility = View.GONE
                            // timeTableAllRecycler.visibility = View.GONE
                            timeTableSingleRecycler.visibility = View.VISIBLE
                            // timeTableAllRecycler.visibility = View.VISIBLE
                            Log.e("weekposition", weekPosition.toString())
                            if (weekPosition == 1) {

                                var mRecyclerViewMainAdapter =
                                    TimeTableSingleWeekSelectionAdapter(ncontext, mMondayArrayList)
                                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                            } else if (weekPosition == 2) {
                                Log.e("week","Successs")
                                var mRecyclerViewMainAdapter =
                                    TimeTableSingleWeekSelectionAdapter(ncontext, mTuesdayArrayList)
                                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter

                            } else if (weekPosition == 3) {
                                Log.e("Success","Successs")
                                var mRecyclerViewMainAdapter =
                                    TimeTableSingleWeekSelectionAdapter(ncontext, mwednesdayArrayList)
                                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                            } else if (weekPosition == 4) {
                                Log.e("Failed","Successs")
                                var mRecyclerViewMainAdapter =
                                    TimeTableSingleWeekSelectionAdapter(ncontext, mThurdayArrayList)
                                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                            } else if (weekPosition == 5) {

                                var mRecyclerViewMainAdapter =
                                    TimeTableSingleWeekSelectionAdapter(ncontext, mFridayArrayList)
                                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                            }
                        }
                        else {
                            timeTableSingleRecycler.visibility = View.GONE
                            // timeTableAllRecycler.visibility = View.VISIBLE
                             tipContainer.visibility = View.VISIBLE

                            card_viewAll.visibility = View.VISIBLE

                            recyclerinitializer()
                            timeTableAllRecycler.visibility = View.VISIBLE
                            // timeTableListS.shuffle()
                            var mRecyclerAllAdapter =
                                TimeTableAllWeekSelectionAdapterNew(ncontext, mPeriodModel, feildAPIArrayList,tipContainer,mTimetableApiArrayList)
                            timeTableAllRecycler.adapter = mRecyclerAllAdapter

                        }

                    }
                    else
                    {
                        CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                    }
                }
                else{
                    val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<TimeTableResponseModel?>, t: Throwable) {
                progressBarDialog.hide()

                Toast.makeText(
                    ncontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun initFn() {

        mFieldModel = ArrayList()
        timeTableListS = ArrayList()
        weekListArrayString = ArrayList()




        student_name = ArrayList()
        weekListArrayString.add("ALL")
        weekListArrayString.add("MONDAY")
        weekListArrayString.add("TUESDAY")
        weekListArrayString.add("WEDNESDAY")
        weekListArrayString.add("THURSDAY")
        weekListArrayString.add("FRIDAY")
        progressBarDialog = ProgressBarDialog(ncontext)
        studentNameTextView = findViewById(R.id.studentName)
        linearlayoutstudentlist = findViewById(R.id.studentSpinner)
        studentclass = findViewById(R.id.studentclass)
        backarrow = findViewById(R.id.back)
        imagicon = findViewById(R.id.imagicon)
        textView=findViewById(R.id.textView)

        tipContainer = findViewById(R.id.tooltip_container) as ToolTipLayout


        if (PreferenceManager().getLanguage(ncontext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(ncontext.getAssets(), "font/times_new_roman.ttf")
            textView.setTypeface(face);
        }
       // studentListApiCall()



        studentNameTextView.text=PreferenceManager().getStudentName(ncontext)
        studentclass.text=PreferenceManager().getStudentClass(ncontext)
        if(!PreferenceManager().getStudentPhoto(ncontext).equals(""))
        {
            Glide.with(ncontext) //1
                .load(studentImg)
                .placeholder(R.drawable.profile_photo)
                .error(R.drawable.profile_photo)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(imagicon)
        }
        else{
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        backarrow.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)
        }
       // Log.e("mSundayArrayList", mSundayArrayList.toString())
        /*linearlayoutstudentlist.setOnClickListener {
            studentlist_popup(student_name)
            *//* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*//*

        }*/
        linearLayoutManagerVertical = LinearLayoutManager(ncontext)
        linearLayoutManagerVertical1 = LinearLayoutManager(ncontext)

//        student1timetable = timeTableListS.shuffle() as ArrayList<DayModel>
//        student2timetable = timeTableListS.shuffle() as ArrayList<DayModel>
        timeTableSingleRecycler = findViewById<RecyclerView>(R.id.timeTableSingleRecycler)
        linearLayoutManagerVertical1.orientation = LinearLayoutManager.VERTICAL
        timeTableSingleRecycler.layoutManager = linearLayoutManagerVertical1
        timeTableSingleRecycler.itemAnimator = DefaultItemAnimator()
        weekRecyclerList = findViewById<RecyclerView>(R.id.weekRecyclerList)
        recyclerinitializer()
        card_viewAll = findViewById(R.id.card_viewAll) as CardView

        weekListArray = ArrayList()
        Log.e("weekListArrayString", weekListArrayString.toString())
        for (i in weekListArrayString.indices) {
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
      //  weekRecyclerList.visibility=View.GONE

        if(CommonClass.isInternetAvailable(ncontext)) {
            callTimeTableApi()

            //    callAPI()
        }
        else{
            Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }

      //
        val llm = LinearLayoutManager(ncontext)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        weekRecyclerList.layoutManager = llm

        val timeTableWeekListAdapter =
            TimeTableWeekListAdapter(ncontext, weekListArray, weekPosition)
        weekRecyclerList.setAdapter(timeTableWeekListAdapter)

        timeTableSingleRecycler.visibility = View.GONE
        // timeTableAllRecycler.visibility = View.VISIBLE
        // tipContainer.visibility = View.VISIBLE

        card_viewAll.visibility = View.GONE

      //  recyclerinitializer()



        var sdf = SimpleDateFormat("EEEE")
        var d = Date()
        Log.e("size", d.toString())
        dayOfTheWeek = sdf.format(d)
        Log.e("daofweek",dayOfTheWeek)
        when (dayOfTheWeek) {
            "Monday" -> {
                weekPosition = 1
            }
            "Tuesday" -> {
                weekPosition = 2
            }
            "Wednesday" -> {
                weekPosition = 3
            }
            "Thursday" -> {
                weekPosition = 4
            }
            "Friday" -> {
                weekPosition = 5
            }

            else -> {
                weekPosition = 0
            }
        }
       // weekPosition = dayOfTheWeek.length
        Log.e("weekposition", weekPosition.toString())
        if (weekPosition < 3) {
            weekRecyclerList.scrollToPosition(0)
        } else {
            weekRecyclerList.scrollToPosition(5)
        }
        for (i in 0 until weekListArray.size) {
            // Log.e("i", i.toString())

            if (i == weekPosition) {

                weekListArray.get(i).positionSelected = i

            } else {
                weekListArray.get(i).positionSelected = -1
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
                    tipContainer.visibility = View.GONE
                    timeTableSingleRecycler.visibility = View.VISIBLE
//                    if (mRangeModel.size>0)
//                    {
                    //card_viewAll.visibility = View.GONE
                    // timeTableAllRecycler.visibility = View.GONE
                    timeTableSingleRecycler.visibility = View.VISIBLE
                    if (weekPosition == 1) {

                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mMondayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 2) {

                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mTuesdayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter

                    } else if (weekPosition == 3) {

                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mwednesdayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 4) {

                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mThurdayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    } else if (weekPosition == 5) {

                        var mRecyclerViewMainAdapter =
                            TimeTableSingleWeekSelectionAdapter(ncontext, mFridayArrayList)
                        timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
                    }
                }
                else {
                    timeTableSingleRecycler.visibility = View.GONE
                    // timeTableAllRecycler.visibility = View.VISIBLE
                     tipContainer.visibility = View.VISIBLE

                    card_viewAll.visibility = View.VISIBLE

                    recyclerinitializer()
                    timeTableAllRecycler.visibility = View.VISIBLE
                    var mRecyclerAllAdapter =
                        TimeTableAllWeekSelectionAdapterNew(
                            ncontext,
                            mPeriodModel,
                            feildAPIArrayList,
                            tipContainer,
                            mTimetableApiArrayList
                        )
                    timeTableAllRecycler.adapter = mRecyclerAllAdapter

                }
            }


        })
    }

    /*private fun studentListApiCall() {
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list(
            "Bearer " +
                    PreferenceManager().getAccessToken(ncontext).toString()
        )
        progressBar.show()
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                progressBar.hide()
                Log.e("Response", response.body().toString())
                if (response.body() != null) {
                if (response.body()!!.status.equals(100)) {
                    student_name.addAll(response.body()!!.student_list)
                    Log.e("StudentNameid", PreferenceManager().getStudent_ID(ncontext).toString())
                    if (PreferenceManager().getStudent_ID(ncontext).equals("")) {
                        studentName =
                            student_name[0].fullname
                        student_class = student_name[0].classs
                        Log.e("StudentName", studentNameTextView.toString())
                        Log.e("student_class", student_class)
                        studentImg = student_name[0].photo
                        studentId = student_name[0].id.toString()
                        PreferenceManager().setStudent_ID(ncontext, studentId)
                        PreferenceManager().setStudentName(
                            ncontext,
                            studentName
                        )
                        PreferenceManager().setStudentPhoto(ncontext, studentImg)
                        PreferenceManager().setStudentClass(ncontext, student_class)
                        studentNameTextView.text = studentName
                        studentclass.text = student_class
                        if (!studentImg.equals("")) {
                            Glide.with(ncontext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_icon_grey)
                                .error(R.drawable.profile_icon_grey)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        } else {
                            imagicon.setImageResource(R.drawable.profile_icon_grey)
                        }

                    } else {

                        studentName = PreferenceManager().getStudentName(
                            ncontext
                        )!!
                        Log.e("StudentName", studentName)
                        student_class = PreferenceManager().getStudentClass(ncontext)!!
                        studentImg = PreferenceManager().getStudentPhoto(ncontext)!!
                        studentId = PreferenceManager().getStudent_ID(ncontext)!!
                        studentNameTextView.text = studentName
                        studentclass.text = student_class
                        if (!studentImg.equals("")) {
                            Glide.with(ncontext) //1
                                .load(studentImg)
                                .placeholder(R.drawable.profile_photo)
                                .error(R.drawable.profile_photo)
                                .skipMemoryCache(true) //2
                                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                                .transform(CircleCrop()) //4
                                .into(imagicon)
                        } else {
                            imagicon.setImageResource(R.drawable.profile_photo)
                        }
                    }

                } else {
                    CommonClass.checkApiStatusError(response.body()!!.status, ncontext)
                }
                }
                else{
                    val intent = Intent(ncontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
                progressBar.hide()
                Toast.makeText(
                    ncontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }*/

    private fun studentlist_popup(student_name: ArrayList<StudentList>) {
        // progress.visibility = View.VISIBLE
        val dialog = BottomSheetDialog(ncontext, R.style.AppBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.student_list_popup)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        var rel = dialog.findViewById<RecyclerView>(R.id.rel2)!! as RelativeLayout
        var crossicon = dialog.findViewById<ImageView>(R.id.crossicon)!!

        var recycler_view = dialog.findViewById<RecyclerView>(R.id.studentlistrecycler)
        recycler_view!!.layoutManager = LinearLayoutManager(ncontext)
        val studentlist_adapter =
            AbsenceStudentListAdapter(
                ncontext,
                student_name)
        recycler_view!!.adapter = studentlist_adapter
        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                //var id: String = student_name.get(position).id.toString()
                var name: String = student_name.get(position).fullname
                var classs:String=student_name.get(position).classs
                // PreferenceManager().setStudentname(nContext, name).toString()
                // Log.e("recycler id", id.toString())
                //leavelist(id)
                studentclass.setText(classs)
                studentNameTextView.setText(name)
               // showTimeTable()
                dialog.dismiss()
            }

        })
        dialog.show()
    }

   /* private fun showTimeTable() {
        timeTableListS.shuffle()
        var mRecyclerAllAdapter =
            TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel, timeTableListS)
        timeTableAllRecycler.adapter = mRecyclerAllAdapter

//        timeTableListS.shuffle()

    }*/

    fun recyclerinitializer() {
        timeTableAllRecycler = findViewById(R.id.timeTableAllRecycler) as RecyclerView
        linearLayoutManagerVertical.orientation = LinearLayoutManager.VERTICAL
        timeTableAllRecycler.layoutManager = linearLayoutManagerVertical
        timeTableAllRecycler.itemAnimator = DefaultItemAnimator()
    }
}