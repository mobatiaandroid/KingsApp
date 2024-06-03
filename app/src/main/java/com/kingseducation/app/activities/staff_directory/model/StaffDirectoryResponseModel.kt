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
        @SerializedName("mobile") val mobile: String?
    )
}

