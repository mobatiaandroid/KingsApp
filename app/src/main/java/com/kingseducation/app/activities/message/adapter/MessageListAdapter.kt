package com.kingseducation.app.activities.message.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.message.model.MessageListModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MessageListAdapter(
    private val context: Context,
    private val name: ArrayList<MessageListModel>
) :
    RecyclerView.Adapter<MessageListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date: TextView = view.findViewById(R.id.date)
        var time: TextView = view.findViewById(R.id.time)
        var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item_list, parent, false)
        return MyViewHolder(itemView)
    }

    fun formatDateWithSuffix(dateString: String): String {
        val dateParts = dateString.split(" ")
        if (dateParts.size == 3) {
            val day = dateParts[0].toInt()
            val month = dateParts[1]
            val year = dateParts[2]

            val dayWithSuffix = when (day) {
                1, 21, 31 -> "${day}st"
                2, 22 -> "${day}nd"
                3, 23 -> "${day}rd"
                else -> "${day}th"
            }

            return "$dayWithSuffix $month $year"
        }
        return dateString
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val desc = name[position].title
        /* holder.date.setText(date)
         holder.time.setText(time)*/
        holder.desc.text = desc
        val datestring = name[position].created_at
        // addReadMore(desc,holder.desc)

        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat: DateFormat = SimpleDateFormat("hh:mm a")
        val outputFormatdate: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val inputDateStr = datestring
        val date: Date = inputFormat.parse(inputDateStr)
        val datetring: String = date.toString()
        Log.e("date", date.toString())
        val outputDateStr: String = outputFormat.format(date)
        val outputDateStr1: String = outputFormatdate.format(date)
        val substr: String = datetring.substring(8, 10)
        val substr1: String = datetring.substring(4, 7)
        val substr2: String = datetring.substring(30, 34)
        Log.e("datestring", substr)
        Log.e("substr1", substr1)
        Log.e("substr2", substr2)

        val finaldate: String = substr + "-" + substr1 + "-" + substr2
        holder.date.text = formatDateWithSuffix(outputDateStr1)
        holder.time.text = outputDateStr

    }

    private fun addReadMore(text: String, textView: TextView) {
        val ss = SpannableString(text.substring(0, 78) + "..")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                //addReadLess(text, textView)
            }

            @SuppressLint("ResourceAsColor")
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
                ds.color = R.color.black
            }
        }
        ss.setSpan(clickableSpan, ss.length - 2, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = ss
        textView.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun getItemCount(): Int {
        return name.size
    }
}