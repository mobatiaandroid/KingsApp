package com.example.kingsapp.activities.forms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.parentessentials.adapter.ParentListAdapter
import com.example.kingsapp.activities.parentessentials.model.ParentessentialModel
import com.example.kingsapp.constants.PdfReaderActivity
import com.example.kingsapp.constants.WebViewLoaderActivity
import com.example.kingsapp.manager.recyclerviewmanager.RecyclerItemListener

class FormsActivity : AppCompatActivity() {
    lateinit var list_name: ArrayList<ParentessentialModel>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext: Context
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forms_layout)
        mcontext = this
        initFn()
    }

    private fun initFn() {
        list_name= ArrayList()
        var model=ParentessentialModel("Test","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model)
        var model1=ParentessentialModel("Test1","https://kings-edu.com/")
        list_name.add(model1)
        var model2=ParentessentialModel("Test2","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model2)
        var model3=ParentessentialModel("Test3","http:\\/\\/naisakcore.mobatia.in:8081\\/storage\\/payment_services\\/2021\\/08\\/03\\/payment_services_dummy_1627970518.pdf")
        list_name.add(model3)
        var model4=ParentessentialModel("Test4","https://kings-edu.com/")
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
            val intent = Intent(mcontext, HomeActivity::class.java)
            startActivity(intent)
        }
    }
    }
