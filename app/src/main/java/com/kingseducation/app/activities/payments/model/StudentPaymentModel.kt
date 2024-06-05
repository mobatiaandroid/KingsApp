package com.kingseducation.app.activities.payments.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class StudentPaymentModel(
    @SerializedName("attributes") var attributes: AttributesModel,
    @SerializedName("Pupil_Code__c") var Pupil_Code__c: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(AttributesModel::class.java.classLoader)!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(attributes, flags)
        parcel.writeString(Pupil_Code__c)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentPaymentModel> {
        override fun createFromParcel(parcel: Parcel): StudentPaymentModel {
            return StudentPaymentModel(parcel)
        }

        override fun newArray(size: Int): Array<StudentPaymentModel?> {
            return arrayOfNulls(size)
        }
    }
}