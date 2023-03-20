package com.example.kingsapp.activities.student_info.model

import com.google.gson.annotations.SerializedName

class StudentInfo (
        @SerializedName("id") val id: Int,
        @SerializedName("fullname") val fullname: String,
        @SerializedName("dob") val dob: String,
        @SerializedName("sourceId") val sourceId: String,
        @SerializedName("userId") val userId: String,
        @SerializedName("gender") val gender: String,
        @SerializedName("class") val classs: String,
        @SerializedName("photo") val photo: String,
        @SerializedName("contact_number") val contact_number: String,
        @SerializedName("academicYear") val academicYear: String,
        @SerializedName("joinDate") val joinDate: String,
        @SerializedName("address") val address: String
)