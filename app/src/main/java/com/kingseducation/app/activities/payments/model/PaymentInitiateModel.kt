package com.kingseducation.app.activities.payments.model

import com.google.gson.annotations.SerializedName

class PaymentInitiateModel (
    @SerializedName("student_id") val student_id: String,
    @SerializedName("device_type") val device_type: String,
    @SerializedName("device_name") val device_name: String,
    @SerializedName("app_version") val app_version: String,
    @SerializedName("attributes") val attributes: AttributesModel,
    @SerializedName("Id") val Id: String,
    @SerializedName("Invoice_Number__c") val Invoice_Number__c: String,
    @SerializedName("Name") val Name: String,
    @SerializedName("Outstanding__c") val Outstanding__c: Int,
    @SerializedName("Component__c") val Component__c: String,
    @SerializedName("Academic_Year__c") val Academic_Year__c: String,
    @SerializedName("Description__c") val Description__c: String,
    @SerializedName("Grade__c") val Grade__c: String,
    @SerializedName("Invoice_Date__c") val Invoice_Date__c: String,
    @SerializedName("Invoice_Due_Date__c") val Invoice_Due_Date__c: String,
    @SerializedName("Invoice_Unique_Number__c") val Invoice_Unique_Number__c: String,
    @SerializedName("Student__r") val Student__r: StudentPaymentModel,
    @SerializedName("Total_Amount__c") val Total_Amount__c: Int,
    @SerializedName("Unique_Invoice__c") val Unique_Invoice__c: String,
    @SerializedName("Total_Amount_Before_Tax__c") val Total_Amount_Before_Tax__c: Int
    )