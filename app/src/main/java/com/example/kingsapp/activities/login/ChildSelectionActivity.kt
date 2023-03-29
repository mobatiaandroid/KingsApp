package com.example.kingsapp.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.login.adapter.ChildSelectionAdapter
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.manager.PreferenceManager
import com.example.kingsapp.manager.recyclerviewmanager.OnItemClickListener
import com.example.kingsapp.manager.recyclerviewmanager.addOnItemClickListener
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response

class ChildSelectionActivity:AppCompatActivity() {
    lateinit var ncontext: Context
    lateinit var circleImageView:RecyclerView
    lateinit var student_name: ArrayList<StudentList>
    lateinit var imageView18:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.kings_child_selection)
        Intent.FLAG_ACTIVITY_CLEAR_TASK
        ncontext = this
        initFn()
        if(CommonClass.isInternetAvailable(ncontext)) {
            studentListApiCall()
        }
        else{
            Toast.makeText(ncontext,"Network error occurred. Please check your internet connection and try again later",Toast.LENGTH_SHORT).show()

        }

    }

    private fun studentListApiCall() {
        val call: Call<StudentListResponseModel> = ApiClient.getApiService().student_list("Bearer "+
                PreferenceManager().getAccessToken(ncontext).toString())
        call.enqueue(object : retrofit2.Callback<StudentListResponseModel> {
            override fun onResponse(
                call: Call<StudentListResponseModel>,
                response: Response<StudentListResponseModel>
            ) {
                Log.e("Response",response.body().toString())
                if (response.body() != null) {
                if (response.body()!!.status.equals(100))
                {
                    student_name.addAll(response.body()!!.student_list)

                    val llm = LinearLayoutManager(ncontext)
                    llm.orientation = LinearLayoutManager.HORIZONTAL
                    circleImageView!!.layoutManager = llm
                    val studentlist_adapter =
                        ChildSelectionAdapter(ncontext, student_name)
                    circleImageView!!.adapter = studentlist_adapter
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

            override fun onFailure(call: Call<StudentListResponseModel?>, t: Throwable) {
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
        student_name= ArrayList()
        circleImageView=findViewById(R.id.circleImageView)

        imageView18=findViewById(R.id.imageView18)
        imageView18.setOnClickListener {
            startActivity(Intent(this, SigninyourAccountActivity::class.java))
        }
        circleImageView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var id: Int = student_name.get(position).id
                PreferenceManager().setStudent_ID(ncontext,id.toString())
                Log.e("Childselid", PreferenceManager().getStudent_ID(ncontext).toString())
                startActivity(Intent(ncontext, HomeActivity::class.java))
            }

        })

    }
}