package com.example.kingsapp.activities.student_info.model

import com.example.kingsapp.activities.login.model.StudentList
import com.google.gson.annotations.SerializedName

class StudentInfoResponseModel (
    @SerializedName("status") val status: String,
    @SerializedName("student_info") val student_info: StudentInfo
        )