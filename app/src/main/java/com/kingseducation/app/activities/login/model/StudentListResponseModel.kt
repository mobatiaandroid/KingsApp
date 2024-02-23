package com.kingseducation.app.activities.login.model

import com.google.gson.annotations.SerializedName

class StudentListResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("student_list") val student_list: ArrayList<StudentList>
)