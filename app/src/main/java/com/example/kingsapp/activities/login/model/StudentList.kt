package com.example.kingsapp.activities.login.model

import com.google.gson.annotations.SerializedName

class StudentList (
    @SerializedName("id") val id: Int,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("dob") val dob: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("class") val classs: String,
    @SerializedName("photo") val photo: String,
    @SerializedName("school_name") val school_name: String,
    @SerializedName("school_language_type") val school_language_type: String,
    var isclicked:Boolean
    )