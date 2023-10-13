package com.example.kingsapp.activities.teacher_contact.model

import com.google.gson.annotations.SerializedName

class TeacherModel(
    @SerializedName("name") val name: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("role") val designation: String,
    @SerializedName("email") val email: String
)

