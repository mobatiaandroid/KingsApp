package com.example.kingsapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceListAdapter
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.RecyclerItemListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog


class AbsenceActivity: AppCompatActivity() {
    lateinit var absencelist:RecyclerView
    lateinit var ncontext:Context
    lateinit var studentName:TextView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var registerabsence:RelativeLayout
    lateinit var backarrow_absense : ImageView
    var mListAdapter: AbsenceListAdapter? = null
    lateinit var name:ArrayList<String>
    lateinit var student_name:ArrayList<Studentlist_model>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.fragment_absence_main)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext=this
        initFn()
    }

    private fun initFn() {
        name= ArrayList()
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 ")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 ")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        name.add("25 Nov 2022 - 26 Nov 2020")
        student_name = ArrayList()
        var model=Studentlist_model("Jane Mathewes",false)
        student_name.add(model)
        var xmodel=Studentlist_model("Esther Mathews",false)
        student_name.add(xmodel)
        var nmodel=Studentlist_model("Gay Mathewes",false)
        student_name.add(nmodel)
        var emodel=Studentlist_model("Jane Mathewes",false)
        student_name.add(emodel)
        studentName = findViewById(R.id.studentName)
        absencelist=findViewById(R.id.absencelist)
        linearLayoutManager = LinearLayoutManager(ncontext)
        registerabsence = findViewById(R.id.featureLinear)
        backarrow_absense = findViewById(R.id.backarrow_absense)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        absencelist.layoutManager = linearLayoutManager
        val abseneadapter = AbsenceListAdapter(ncontext,name)
        absencelist.setAdapter(abseneadapter)


        backarrow_absense.setOnClickListener {
            val intent = Intent(ncontext, HomeActivity::class.java)
            startActivity(intent)
        }
        studentName.setOnClickListener {
            studentlist_popup(student_name)
           /* val intent = Intent(mContext, StudentListActivity::class.java)
            startActivity(intent)*/

        }
        //mAbsenceListView.setLayoutManager(recyclerViewLayoutManager);
        absencelist.addOnItemTouchListener(
            RecyclerItemListener(
                ncontext, absencelist,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        val intent = Intent(ncontext, AbsenceDeatilsActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        registerabsence.setOnClickListener {
             val intent = Intent(ncontext, RegisterAbsenceActivity::class.java)
            startActivity(intent)
        }
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
        /*val studentlist_adapter =
            AbsenceStudentListAdapter(ncontext, student_name)
        recycler_view!!.adapter = studentlist_adapter*/

        crossicon.setOnClickListener {
            dialog.dismiss()
        }
        recycler_view.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                var name: String = student_name.get(position).name
                studentName.setText(name)
                dialog.dismiss()
            }

        })
        dialog.show()
    }
}