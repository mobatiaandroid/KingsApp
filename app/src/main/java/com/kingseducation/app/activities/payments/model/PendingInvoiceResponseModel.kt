package com.kingseducation.app.activities.payments.model

import com.google.gson.annotations.SerializedName


data class PendingInvoiceResponseModel(
    val status: Int,
    val invoices: ArrayList<Invoice>
) {
    data class Invoice(
        val attributes: Attribute,
        val id: String,
        @SerializedName("Invoice_Number__c") val invoiceNumber: String,
        val name: String,
        @SerializedName("Outstanding__c") val outstanding: Double,
        @SerializedName("Component__c") val component: String,
        @SerializedName("Academic_Year__c") val academicYear: String,
        @SerializedName("Description__c") val description: String?,
        @SerializedName("Grade__c") val grade: String,
        @SerializedName("Invoice_Date__c") val invoiceDate: String,
        @SerializedName("Invoice_Due_Date__c") val invoiceDueDate: String,
        @SerializedName("Invoice_Unique_Number__c") val invoiceUniqueNumber: String,
        @SerializedName("Student__r") val student: Student,
        @SerializedName("Total_Amount__c") val totalAmount: Double,
        @SerializedName("Unique_Invoice__c") val uniqueInvoice: String?,
        @SerializedName("Total_Amount_Before_Tax__c") val totalAmountBeforeTax: Double
    ) {

        data class Attribute(
            val type: String,
            val url: String
        )

        data class Student(
            val attributes: StudentAttribute,
            @SerializedName("Pupil_Code__c") val pupilCode: String?
        )
    }
}


data class StudentAttribute(
    val type: String,
    val url: String
)