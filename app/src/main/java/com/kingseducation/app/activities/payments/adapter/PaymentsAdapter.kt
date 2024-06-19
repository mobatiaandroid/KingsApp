package com.kingseducation.app.activities.payments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kingseducation.app.R
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel


class PaymentsAdapter(
    private val context: Context,
    private val studentList: ArrayList<PendingInvoiceResponseModel.Invoice>
) :
    RecyclerView.Adapter<PaymentsAdapter.MyViewHolder>() {
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var invoiceTitleTV: TextView = view.findViewById(R.id.invoiceTitleTV)
        var invoiceTitle2TV: TextView = view.findViewById(R.id.invoiceTitle2TV)
        var paymentStatusTV: TextView = view.findViewById(R.id.paymentStatusTV)
        var amountTV: TextView = view.findViewById(R.id.amountTV)
        var dueDateTV: TextView = view.findViewById(R.id.dueDateTV)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pending_invoice, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.invoiceTitleTV.text = studentList[position].component
        holder.invoiceTitle2TV.text = studentList[position].description
        if (studentList[position].outstanding > 0) {
            holder.amountTV.text = studentList[position].outstanding.toString() + " AED"
        } else {
            holder.amountTV.text = ""
        }
        holder.dueDateTV.text = context.resources.getString(R.string.due_date) + " " + studentList[position].invoiceDueDate
        if (studentList[position].outstanding > 0) {
            if (studentList[position].Online_Payment__c){
                holder.paymentStatusTV.setTextColor(context.resources.getColor(R.color.payment_amber))
                holder.paymentStatusTV.text = "In Progress"
            }else{
                holder.paymentStatusTV.text = "Pay"
                holder.paymentStatusTV.setTextColor(context.resources.getColor(R.color.payment_red))

            }
        } else {
            holder.paymentStatusTV.setTextColor(context.resources.getColor(R.color.payment_green))
            holder.paymentStatusTV.text = "Paid"
        }


    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}