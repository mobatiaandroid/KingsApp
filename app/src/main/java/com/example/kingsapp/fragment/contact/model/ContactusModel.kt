package com.example.kingsapp.fragment.contact.model

import com.example.kingsapp.activities.login.model.StudentList
import com.google.gson.annotations.SerializedName

 class ContactusModel (
    @SerializedName("status") val status: String,
    @SerializedName("contactus") val contactus: ArrayList<ContactusResponseArray>
)