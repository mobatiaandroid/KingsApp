package com.kingseducation.app.activities.teacher_contact.model

import com.google.gson.annotations.SerializedName

class TeacherModel(
    @SerializedName("id") val id: String,
    @SerializedName("staff_name") val staff_name: String,
    @SerializedName("staff_email") val staff_email: String,
    @SerializedName("subject") val subject: String,
)

