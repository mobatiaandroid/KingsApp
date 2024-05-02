package com.kingseducation.app.activities.newsletter.model

import com.google.gson.annotations.SerializedName


data class NewsLetterResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("newsletter")
    val newsletter: ArrayList<Newsletter>
) {
    data class Newsletter(
        @SerializedName("id")
        val id: Int,
        @SerializedName("title")
        val title: String,
        @SerializedName("type")
        val type: Int,
        @SerializedName("url")
        val url: String
    )
}

