package com.example.kingsapp.activities.calender.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.context
import com.example.kingsapp.fragment.mContext
import com.example.kingsapp.manager.PreferenceManager
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarYearAdapter(
    context: Context,
    data: ArrayList<String>
):
    RecyclerView.Adapter<CalendarYearAdapter.MyViewHolder>() {
    var mData: ArrayList<String> = data
    var mContext: Context =context
    private val mInflater: LayoutInflater? = null

    lateinit var  monthNames : Array<String>
    private val dayNames =
        arrayOf("SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY")
  //  private val mHolidaysArray: ArrayList<String>? = null
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //var textview: TextView = view.findViewById(R.id.listDate)
        var titleLabel: TextView? = null
        var dateTextView = arrayOfNulls<TextView>(100)


        init {
            titleLabel = itemView.findViewById<TextView>(R.id.titleLabel)
            for (i in 0..49) {
                val resID: Int =
                    mContext.resources.getIdentifier("label_$i", "id", mContext.packageName)
                Log.e("res",resID.toString())
                dateTextView[i] = itemView.findViewById(resID)

                //dateTextView[i]!!.setBackgroundColor(Color.WHITE)
                //dateTextView[i]!!.setTextColor(Color.BLACK)
            }
            /* for (i in 1..49) {
                 val resID =
                     context.resources.getIdentifier("label_$i", "id", context.packageName)
                 dateTextView[i] = itemView.findViewById<View>(resID) as TextView
             }*/
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false)
        return CalendarYearAdapter.MyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        monthNames= mContext.getResources().getStringArray(R.array.Months)
        val monthNumber = mData[position].toInt()
        val monthName = monthNames[monthNumber]
        Log.e("mnth",monthName)

        val cal = Calendar.getInstance()
        val year_date = SimpleDateFormat("yyyy", Locale.ENGLISH)
        val yearName = year_date.format(cal.time)
        val actualYearNumber = yearName.toInt()
        var yearNumber = yearName.toInt()

        val month_date = SimpleDateFormat("MM", Locale.ENGLISH)
        val currentMonthName = month_date.format(cal.time)
        val currentMonthNumber = currentMonthName.toInt() // Device current month



        yearNumber = if (currentMonthNumber < 7) { // JAN - JUL
            if (monthNumber >= 7) {
                actualYearNumber - 1
            } else {
                actualYearNumber
            }
        } else {                     // AUG - DEC
            if (monthNumber >= 7) {
                actualYearNumber
            } else {
                actualYearNumber + 1
            }
        }

        val input_date = "01/" + (monthNumber + 1) + "/" + yearNumber // Since monthNumber=0 => Jan


        //Log.d("startDay","startDay===="+input_date);


        //Log.d("startDay","startDay===="+input_date);
        val format1 = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        var dt1: Date? = null
        try {
            dt1 = format1.parse(input_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val format2 = SimpleDateFormat("EEEE", Locale.ENGLISH)
        val startDay = format2.format(dt1).uppercase(Locale.getDefault())

        var dayNumber = 1
        for (i in dayNames.indices) {
            if (dayNames[i] == startDay) {
                dayNumber = i + 1
                break
            }
        }
        //Log.d("dayNumber","dayNumber===="+dayNumber);
        //Log.d("dayNumber","dayNumber===="+dayNumber);
        holder.titleLabel!!.setText(monthName)


        for (i in 1..49) {

            holder.dateTextView[i]!!.setText("")
        }

        var lastDayOfThisMonth = 30
        val c = Calendar.getInstance()
        c[yearNumber, monthNumber] = 1
        c[Calendar.DAY_OF_MONTH] = c.getActualMaximum(Calendar.DAY_OF_MONTH)

        val sdf = SimpleDateFormat("dd", Locale.ENGLISH)
        lastDayOfThisMonth = sdf.format(c.time).toInt()


        var days = 0
        for (i in dayNumber..49) {
            if (days >= lastDayOfThisMonth) {
                break
            }
            days = days + 1
            if (PreferenceManager().getLanguage(com.example.kingsapp.fragment.mContext).equals("ar")) {
                //calendararabictextview(value)
                if(days.equals(1))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }

                else if(days.equals(2))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }
                else if(days.equals(3))
                {
                    val str:String=mContext.getResources().getString(R.string.three)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }else if(days.equals(4))
                {
                    val str:String=mContext.getResources().getString(R.string.four)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }else if(days.equals(5))
                {
                    val str:String=mContext.getResources().getString(R.string.five)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }else if(days.equals(6))
                {
                    val str:String=mContext.getResources().getString(R.string.six)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }else if(days.equals(7))
                {
                    val str:String=mContext.getResources().getString(R.string.seven)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }else if(days.equals(8))
                {
                    val str:String=mContext.getResources().getString(R.string.eight)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }
                else if(days.equals(9))
                {
                    val str:String=mContext.getResources().getString(R.string.nine)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }
                else if(days.equals(10))
                {
                    val str:String=mContext.getResources().getString(R.string.ten)
                    holder.dateTextView.get(i)!!.text = str.toString()

                }
                else if(days.equals(11))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.one)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }

                else if(days.equals(12))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.tw0)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(13))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.three)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(14))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.four)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(15))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.five)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(16))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.six)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(17))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.seven)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(18))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.eight)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(19))
                {
                    val str:String=mContext.getResources().getString(R.string.one)
                    val str1:String=mContext.getResources().getString(R.string.nine)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(20))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.zero)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(21))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.one)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(22))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.tw0)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(23))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.three)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(24))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.four)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(25))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.five)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(26))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.six)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(27))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.seven)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(28))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.eight)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(29))
                {
                    val str:String=mContext.getResources().getString(R.string.tw0)
                    val str1:String=mContext.getResources().getString(R.string.nine)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(30))
                {
                    val str:String=mContext.getResources().getString(R.string.three)
                    val str1:String=mContext.getResources().getString(R.string.zero)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }else if(days.equals(31))
                {
                    val str:String=mContext.getResources().getString(R.string.three)
                    val str1:String=mContext.getResources().getString(R.string.one)

                    holder.dateTextView.get(i)!!.text = str.toString()+str1

                }
            }
            else
            {
                holder.dateTextView.get(i)!!.setText("" + days)

            }
            val formatter = DecimalFormat("00")
            val monthIn2Digits = formatter.format((monthNumber + 1).toLong())
            val dayIn2Digits = formatter.format(days.toLong())
            val currentDayString = "$yearNumber-$monthIn2Digits-$dayIn2Digits"
            Log.e("currentDayString",currentDayString)
            val holidaysArray: ArrayList<String>
            holidaysArray = PreferenceManager().getArrayList(mContext)
            Log.e("mHolidaysArray", holidaysArray.toString())

            if (holidaysArray!!.contains(currentDayString)) {
                 println("if case working holiday")
                 holder.dateTextView.get(i)!!.setBackgroundResource(R.drawable.circle_calendar)
                 holder.dateTextView.get(i)!!
                     .setTextColor(mContext.resources.getColor(R.color.white))
             } else {
                 println("else case working holiday")
                 holder.dateTextView.get(i)!!
                     .setTextColor(mContext.resources.getColor(R.color.black))
             }
        }
    }

    override fun getItemCount(): Int {
        Log.e("adpsize",mData.size.toString())
        return  mData.size
    }
}