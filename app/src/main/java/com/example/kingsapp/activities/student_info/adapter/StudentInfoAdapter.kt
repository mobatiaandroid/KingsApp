package com.example.kingsapp.activities.student_info.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.login.model.StudentList
import com.example.kingsapp.activities.student_info.model.StudentInfoResponseModel
import com.example.kingsapp.constants.CommonClass
import com.example.kingsapp.manager.PreferenceManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response


internal class StudentInfoAdapter(
    private val context: Context,
    private val student_name: ArrayList<StudentList>,
    private val studentName_Text: TextView,
    private val text: TextView,
    private val name: TextInputEditText,
    private val address: TextInputEditText,
    private val classs: TextInputEditText,
    private val dialog: BottomSheetDialog
) :
    RecyclerView.Adapter<StudentInfoAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var studentSpinner: LinearLayout = view.findViewById(R.id.studentSpinner)
        var studentName: TextView = view.findViewById(R.id.studentName)
        var studentclass: TextView = view.findViewById(R.id.studentclass)
        var check : ImageView = view.findViewById(R.id.check)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_list_item_popup, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val namelist = student_name[position].fullname
        val nameclass = student_name[position].classs
        holder.studentName.setText(namelist)
        holder.studentclass.setText(nameclass)
        holder.studentSpinner.setOnClickListener {


            var foundPosition = -1
            var isFound: Boolean = false

            for (i in 0..student_name.size - 1) {
                if (student_name.get(i).isclicked) {
                    foundPosition = i
                    isFound = true
                }
            }

            if (isFound) {
                if (position == foundPosition) {
                    // make it as false
                    student_name.get(foundPosition).isclicked = false
                    notifyDataSetChanged()
                }
                else
                {
                    student_name.get(foundPosition).isclicked=false
                    student_name.get(position).isclicked=true
                    notifyDataSetChanged()
                }
            }
            else
            {
                student_name.get(position).isclicked=true
                notifyDataSetChanged()
            }

        }
        if (student_name.get(position).isclicked)
        {
            Log.e("true", student_name.get(position).isclicked.toString())
            holder.check.setBackgroundResource(R.drawable.rectangle1)

            var name: String = student_name.get(position).fullname
            var classs: String = student_name.get(position).classs
            var id: Int = student_name.get(position).id
            studentName_Text.setText(name)
            text.setText(classs)
            PreferenceManager().setStudent_ID(context,id.toString())

            studentInfoApiCall()

        }
        else
        {
            holder.check.setBackgroundResource(R.drawable.rectangle)
        }

    }
    override fun getItemCount(): Int {
        return student_name.size
    }

    private fun studentInfoApiCall() {
        val call: Call<StudentInfoResponseModel> = ApiClient.getApiService().studentinfo("Bearer "+
                PreferenceManager().getAccessToken(context).toString(),
            PreferenceManager().getStudent_ID(context).toString()
        )
        call.enqueue(object : retrofit2.Callback<StudentInfoResponseModel> {
            override fun onResponse(
                call: Call<StudentInfoResponseModel>,
                response: Response<StudentInfoResponseModel>
            ) {
                if (response.body()!!.status.equals(100))
                {
                    name.setText(response.body()!!.student_info.fullname)
                    address.setText(response.body()!!.student_info.address)
                    classs.setText(response.body()!!.student_info.classs)

                }
                else
                {
                    CommonClass.checkApiStatusError(response.body()!!.status, context)
                }
            }

            override fun onFailure(call: Call<StudentInfoResponseModel?>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }
}
