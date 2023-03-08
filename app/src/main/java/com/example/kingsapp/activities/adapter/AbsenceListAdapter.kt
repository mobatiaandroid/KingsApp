package com.example.kingsapp.activities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.AbsenceDeatilsActivity
import com.example.kingsapp.activities.RegisterAbsenceActivity

class AbsenceListAdapter(private val context: Context, private  val name:ArrayList<String>):
    RecyclerView.Adapter<AbsenceListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textview: TextView = view.findViewById(R.id.listDate)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.absence_list_items, parent, false)
        return AbsenceListAdapter.MyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val namelist = name[position]
       // Log.e("nameList",namelist)
        var str = namelist.split("-")
        val str1 = str[0]
        val Str2 = str[0].split(" ")

        val str3 = Str2[0]
        //Log.e("Split", str.toString())
       // Log.e("str1", str1)
       // Log.e("Str2", Str2.toString())
       // Log.e("str3", str3)


        val str11 = str[1]
        val Str22 = str[1].split(" ")

        val str33 = Str22[1]
       // Log.e("Split2", str.toString())
        //Log.e("str22", str11)
       // Log.e("Str222", Str22.toString())
       // Log.e("str2222", str33)


      var drop1=  str1.drop(2)
        Log.e("drop", str1.drop(2))

       var drop2= str11.drop(3)
        Log.e("drop", str11.drop(2))

        val styledText = "<font color='black'>str3</font><font color='red'>namelist</font>"
        holder.textview.setText(Html.fromHtml(
            "<b>"+"<font color=black>"+str3 + "</font>"+"</b>"+ "<font color=grey>"+ drop1+ "</font>" +
                    "<font color=grey>"+ "-" +" "+"</font>"+"<b>"+"<font color=black>"+str33+"</font>"+"</b>"+"<font color=grey>"+drop2+"</font>"))

         Log.e("str1", str1)
        Log.e("str11", str11)

        // Log.e("Str222", Str22.toString())
    }

    override fun getItemCount(): Int {
        return  name.size
    }
}