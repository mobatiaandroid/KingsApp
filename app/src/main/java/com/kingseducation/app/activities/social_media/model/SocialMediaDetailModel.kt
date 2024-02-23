package com.kingseducation.app.activities.social_media.model

import com.google.gson.annotations.SerializedName

data class SocialMediaDetailModel (

    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("title_ar") val title_ar: String,
    @SerializedName("page_id") val page_id: String
)

//SocialMediaDetailModel