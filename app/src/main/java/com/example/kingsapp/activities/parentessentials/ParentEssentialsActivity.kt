package com.example.kingsapp.activities.parentessentials

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.MainActivity
import com.example.kingsapp.R
import com.example.kingsapp.activities.AbsenceDeatilsActivity
import com.example.kingsapp.activities.adapter.AbsenceListAdapter
import com.example.kingsapp.activities.model.Studentlist_model
import com.example.kingsapp.activities.parentessentials.adapter.ParentListAdapter
import com.example.kingsapp.activities.parentessentials.model.ParentessentialModel
import com.example.kingsapp.constants.PdfReaderActivity
import com.example.kingsapp.constants.WebViewLoaderActivity
import com.example.kingsapp.manager.recyclerviewmanager.RecyclerItemListener


class ParentEssentialsActivity: AppCompatActivity() {
   // lateinit var list_name:ArrayList<String>
    lateinit var list_name:ArrayList<ParentessentialModel>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext:Context
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parentessentails)
        mcontext =this
        initFn()
    }

    private fun initFn() {
       // list_name = mcontext.resources.getStringArray(R.array.parent_list)
        list_name= ArrayList()
        var model=ParentessentialModel("Term Dates","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model)
        var model1=ParentessentialModel("Uniform","https://kings-edu.com/")
        list_name.add(model1)
        var model2=ParentessentialModel("Kings Lunch Box Menu","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model2)
        var model3=ParentessentialModel("Bus Service","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model3)
        var model4=ParentessentialModel("Information","https://kings-edu.com/")
        list_name.add(model4)
        back = findViewById(R.id.back)

        parentList =findViewById(R.id.parentessetialsrec)
        linearLayoutManager = LinearLayoutManager(mcontext)

        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        parentList.layoutManager = linearLayoutManager
        val parentadapter = ParentListAdapter(mcontext,list_name)
        parentList.setAdapter(parentadapter)
        parentList.addOnItemTouchListener(
            RecyclerItemListener(
                mcontext, parentList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if(list_name.get(position).url.endsWith(".pdf"))
                        {
                            val intent = Intent(mcontext, PdfReaderActivity::class.java)
                            intent.putExtra("pdf_url", list_name[position].url)
                            intent.putExtra("pdf_title", list_name[position].title)
                            startActivity(intent)
                        }
                        else
                        {
                            val intent = Intent(mcontext, WebViewLoaderActivity::class.java)
                            intent.putExtra("webview_url", list_name[position].url)
                            intent.putExtra("title", list_name[position].title)

                            startActivity(intent)
                        }

                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )

        back.setOnClickListener {
            val intent = Intent(mcontext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}