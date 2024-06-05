package com.kingseducation.app.activities.payments.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class AttributesModel(
    @SerializedName("type") var type: String,
    @SerializedName("url") var url: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttributesModel> {
        override fun createFromParcel(parcel: Parcel): AttributesModel {
            return AttributesModel(parcel)
        }

        override fun newArray(size: Int): Array<AttributesModel?> {
            return arrayOfNulls(size)
        }
    }
}