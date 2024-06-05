package com.kingseducation.app.activities.payments.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class PaymentInitiateModel (
    @SerializedName("student_id") var student_id: String,
    @SerializedName("device_type") var device_type: String,
    @SerializedName("device_name") var device_name: String,
    @SerializedName("app_version") var app_version: String,
    @SerializedName("attributes") var attributes: AttributesModel,
    @SerializedName("Id") var Id: String,
    @SerializedName("Invoice_Number__c") var Invoice_Number__c: String,
    @SerializedName("Name") var name: String,
    @SerializedName("Outstanding__c") var Outstanding__c: Double,
    @SerializedName("Component__c") var Component__c: String,
    @SerializedName("Academic_Year__c") var Academic_Year__c: String,
    @SerializedName("Description__c") var Description__c: String,
    @SerializedName("Grade__c") var Grade__c: String,
    @SerializedName("Invoice_Date__c") var Invoice_Date__c: String,
    @SerializedName("Invoice_Due_Date__c") var Invoice_Due_Date__c: String,
    @SerializedName("Invoice_Unique_Number__c") var Invoice_Unique_Number__c: String,
    @SerializedName("Student__r") var Student__r: StudentPaymentModel,
    @SerializedName("Total_Amount__c") var Total_Amount__c: Double,
    @SerializedName("Unique_Invoice__c") var Unique_Invoice__c: String,
    @SerializedName("Total_Amount_Before_Tax__c") var Total_Amount_Before_Tax__c: Double
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(AttributesModel::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(StudentPaymentModel::class.java.classLoader)!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(student_id)
        parcel.writeString(device_type)
        parcel.writeString(device_name)
        parcel.writeString(app_version)
        parcel.writeParcelable(attributes, flags)
        parcel.writeString(Id)
        parcel.writeString(Invoice_Number__c)
        parcel.writeString(name)
        parcel.writeDouble(Outstanding__c)
        parcel.writeString(Component__c)
        parcel.writeString(Academic_Year__c)
        parcel.writeString(Description__c)
        parcel.writeString(Grade__c)
        parcel.writeString(Invoice_Date__c)
        parcel.writeString(Invoice_Due_Date__c)
        parcel.writeString(Invoice_Unique_Number__c)
        parcel.writeParcelable(Student__r, flags)
        parcel.writeDouble(Total_Amount__c)
        parcel.writeString(Unique_Invoice__c)
        parcel.writeDouble(Total_Amount_Before_Tax__c)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaymentInitiateModel> {
        override fun createFromParcel(parcel: Parcel): PaymentInitiateModel {
            return PaymentInitiateModel(parcel)
        }

        override fun newArray(size: Int): Array<PaymentInitiateModel?> {
            return arrayOfNulls(size)
        }
    }
}