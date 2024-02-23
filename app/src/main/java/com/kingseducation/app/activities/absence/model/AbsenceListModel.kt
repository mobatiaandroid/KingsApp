package com.kingseducation.app.activities.absence.model

import com.google.gson.annotations.SerializedName

class AbsenceListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("leave_requests") val leave_requests: ArrayList<AbsenceList>
        )