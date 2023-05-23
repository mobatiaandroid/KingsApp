package com.example.kingsapp.activities.social_media.model

import com.example.kingsapp.activities.reports.model.Reports
import com.google.gson.annotations.SerializedName

class SocialMediaResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("socialmedia") val socialmedia: ArrayList<SocialMediaDetailModel>
        )