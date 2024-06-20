package com.kingseducation.app.activities.staff_directory.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList


data class StaffDirectoryResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("staff_list") val staffList: ArrayList<Staff>
){
    data class Staff(
        @SerializedName("id") val id: Int,
        @SerializedName("full_name") val fullName: String,
        @SerializedName("email") val email: String,
        @SerializedName("mobile") val mobile: String?,
        @SerializedName("subject_type") val subject_type: String?,
        @SerializedName("subject") val subject: String?,
        @SerializedName("staff_photo") val staff_photo: String?
    )
}

