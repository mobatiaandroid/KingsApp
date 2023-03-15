package com.example.kingsapp.activities.login.model

import com.google.gson.annotations.SerializedName

class StudentListResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("student_list") val student_list: ArrayList<StudentList>
)