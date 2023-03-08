package com.example.kingsapp.activities.reports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceStudentListAdapter
import com.example.kingsapp.activities.message.model.MessageListModel
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.activities.reports.adapter.ReportsAdapterList
import com.example.kingsapp.activities.reports.model.ReportModel
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReportsActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var linearlayoutstudentlist:LinearLayout
    lateinit var reportrec:RecyclerView
    lateinit var student_name:ArrayList<Studentlist_model>
    lateinit var report_array:ArrayList<ReportModel>
    lateinit var studentName : TextView
    lateinit var backarrow : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_report)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
    }

    private fun initFn() {
        linearlayoutstudentlist=findViewById(R.id.linearlayoutstudentlist)
        student_name = ArrayList()
        reportrec = findViewById(R.id.reportrec)
        studentName = findViewById(R.id.studentName)
        backarrow = findViewById(R.id.back)
        var model=Studentlist_model("Jane Mathewes",false)
        student_name.add(model)
        var xmodel=Studentlist_model("Esther Mathews",false)
        student_name.add(xmodel)
        var nmodel=Studentlist_model("Gay Mathewes",false)
        student_name.add(nmodel)
        var emodel=Studentlist_model("Jane Mathewes",false)
        student_name.add(emodel)
        report_array = ArrayList()
        var reportmodel=ReportModel("2022-2023","Test report cycle 2022-2023","https:\\/\\/file.io\\/386klufU8FEr")
        report_array.add(reportmodel)
        var reportmodel1=ReportModel("2019-2020","Test report cycle 2019-2020","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        report_array.add(reportmodel1)
        backarrow.setOnClickListener {
            val intent = Intent(ncontext, MainActivity::class.java)
            startActivity(intent)
        }

        reportrec!!.layoutManager = LinearLayoutManager(ncontext)
        val report_rec_adapter =
            ReportsAdapterList(ncontext, report_array)
        reportrec!!.adapter = report_rec_adapter

        linearlayoutstudentlist.setOnClickListener {
            studentlist_popup(student_name)
            /* val intent = Intent(mContext, StudentListActivity::class.java)
             startActivity(intent)*/

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
                 reportrec!!.layoutManager = LinearLayoutManager(ncontext)
                 val report_rec_adapter =
                     ReportsAdapterList(ncontext, report_array)
                 reportrec!!.adapter = report_rec_adapter

                 dialog.dismiss()
             }

         })
        dialog.show()
    }
}