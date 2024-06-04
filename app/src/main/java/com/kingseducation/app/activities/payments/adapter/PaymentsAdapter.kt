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
            holder.paymentStatusTV.text = "Pending"
        } else {
            holder.paymentStatusTV.text = "Paid"
        }


    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}