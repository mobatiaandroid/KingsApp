package com.kingseducation.app.activities.student_info.model

import com.google.gson.annotations.SerializedName

class StudentInfoResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("student_info") val student_info: StudentInfo
        )