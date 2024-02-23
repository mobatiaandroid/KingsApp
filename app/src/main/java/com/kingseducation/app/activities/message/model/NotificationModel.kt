package com.kingseducation.app.activities.message.model

import com.google.gson.annotations.SerializedName

class NotificationModel (
    @SerializedName("status") val status: Int,
    @SerializedName("notifications") val notifications: ArrayList<MessageListModel>
        )