package com.kingseducation.app.activities.social_media.model

import com.google.gson.annotations.SerializedName

class SocialMediaResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("socialmedia") val socialmedia: ArrayList<SocialMediaDetailModel>
        )