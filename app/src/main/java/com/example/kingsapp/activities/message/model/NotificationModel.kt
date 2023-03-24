package com.example.kingsapp.activities.message.model

import com.example.kingsapp.activities.calender.model.CalendarList
import com.google.gson.annotations.SerializedName

class NotificationModel (
    @SerializedName("status") val status: Int,
    @SerializedName("notifications") val notifications: ArrayList<MessageListModel>
        )