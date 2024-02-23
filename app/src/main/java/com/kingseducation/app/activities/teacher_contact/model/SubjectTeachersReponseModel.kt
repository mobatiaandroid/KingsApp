package com.kingseducation.app.activities.teacher_contact.model

import com.google.gson.annotations.SerializedName

class SubjectTeachersResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("subject_teachers") val teachersList: ArrayList<TeacherModel>
)

