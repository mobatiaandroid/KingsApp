package com.example.kingsapp.activities.timetable

import android.content.Context
import android.content.Intent
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
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.activities.timetable.adapter.TimeTableAllWeekSelectionAdapterNew
import com.example.kingsapp.activities.timetable.adapter.TimeTableSingleWeekSelectionAdapter
import com.example.kingsapp.activities.timetable.adapter.TimeTableWeekListAdapter
import com.example.kingsapp.activities.timetable.model.DayModel
import com.example.kingsapp.activities.timetable.model.FieldModel
import com.example.kingsapp.activities.timetable.model.TimeTableApiListModel
import com.example.kingsapp.activities.timetable.model.WeekModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.constants.ProgressBarDialog
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response
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
    lateinit var progressBar: ProgressBarDialog

    lateinit var mFieldModel: ArrayList<FieldModel>
    lateinit var timeTableListS: ArrayList<DayModel>

    //    lateinit var student1timetable: ArrayList<DayModel>
//    lateinit var student2timetable: ArrayList<DayModel>
    lateinit var mSundayArrayList: ArrayList<TimeTableApiListModel>
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
        progressBar = ProgressBarDialog(ncontext)
        studentNameTextView = findViewById(R.id.studentName)
        linearlayoutstudentlist = findViewById(R.id.studentSpinner)
        studentclass = findViewById(R.id.studentclass)
        backarrow = findViewById(R.id.back)
        imagicon = findViewById(R.id.imagicon)
       // studentListApiCall()


        /* var modell=Studentlist_model("Jane Mathewes",false)
         student_name.add(modell)
         var xmodel=Studentlist_model("Esther Mathews",false)
         student_name.add(xmodel)
         var nmodel=Studentlist_model("Gay Mathewes",false)
         student_name.add(nmodel)
         var emodel=Studentlist_model("Jane Mathewes",false)
         student_name.add(emodel)*/

        var model = FieldModel("TUT", "10;30", "11;30")
        mFieldModel.add(model)
        var model1 = FieldModel("P1", "10;30", "11;30")
        mFieldModel.add(model1)
        var model2 = FieldModel("P2", "10;30", "11;30")
        mFieldModel.add(model2)
        var model11 = FieldModel("Break", "10;30", "11;30")
        mFieldModel.add(model11)
        var model3 = FieldModel("P3", "10;30", "11;30")
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
        var sub6 = DayModel(6, "History")
        timeTableListS.add(sub6)
        var sub7= DayModel(7,"Mathematics")
        timeTableListS.add(sub7)
        var sub8 = DayModel(8, "Social Science")
        timeTableListS.add(sub8)
        var sub9 = DayModel(9, "Extras")
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
        Log.e("mSundayArrayList", mSundayArrayList.toString())
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
        if (weekPosition != 0) {
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
        timeTableAllRecycler.visibility = View.VISIBLE

            if (weekPosition == 1) {

                var mRecyclerViewMainAdapter =
                    TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
            } else if (weekPosition == 2) {

                var mRecyclerViewMainAdapter =
                    TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter

            } else if (weekPosition == 3) {
            Log.e("Success","Successs")
                var mRecyclerViewMainAdapter =
                    TimeTableSingleWeekSelectionAdapter(ncontext, mSundayArrayList)
                timeTableSingleRecycler.adapter = mRecyclerViewMainAdapter
            } else if (weekPosition == 4) {
                Log.e("Failed","Successs")
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
            timeTableListS.shuffle()
            var mRecyclerAllAdapter =
                TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel, timeTableListS)
            timeTableAllRecycler.adapter = mRecyclerAllAdapter

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
                    var mRecyclerAllAdapter =
                        TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel, timeTableListS)
                    timeTableAllRecycler.adapter = mRecyclerAllAdapter

                }
            }


        })
    }

    private fun studentListApiCall() {
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
    }

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
                showTimeTable()
                dialog.dismiss()
            }

        })
        dialog.show()
    }

    private fun showTimeTable() {
        timeTableListS.shuffle()
        var mRecyclerAllAdapter =
            TimeTableAllWeekSelectionAdapterNew(ncontext, mFieldModel, timeTableListS)
        timeTableAllRecycler.adapter = mRecyclerAllAdapter

//        timeTableListS.shuffle()

    }

    fun recyclerinitializer() {
        timeTableAllRecycler = findViewById(R.id.timeTableAllRecycler) as RecyclerView
        linearLayoutManagerVertical.orientation = LinearLayoutManager.VERTICAL
        timeTableAllRecycler.layoutManager = linearLayoutManagerVertical
        timeTableAllRecycler.itemAnimator = DefaultItemAnimator()
    }
}