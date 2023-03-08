package com.example.kingsapp.activities.message.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.adapter.AbsenceListAdapter
import com.example.kingsapp.activities.message.model.MessageListModel

class MessageListAdapter(private val context: Context, private  val name:ArrayList<MessageListModel>):
    RecyclerView.Adapter<MessageListAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date: TextView = view.findViewById(R.id.date)
        var time: TextView = view.findViewById(R.id.time)
        var desc: TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item_list, parent, false)
        return MessageListAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val date = name[position].date
        val time = name[position].time
        val desc = name[position].desc
        holder.date.setText(date)
        holder.time.setText(time)
        holder.desc.setText(desc)
        addReadMore(desc,holder.desc)
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
                ds.setUnderlineText(false)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ds.setColor(R.color.black)
                } else {
                    ds.setColor(R.color.black)
                }
            }
        }
        ss.setSpan(clickableSpan, ss.length - 2, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.setText(ss)
        textView.setMovementMethod(LinkMovementMethod.getInstance())
    }
    override fun getItemCount(): Int {
return name.size
    }
}