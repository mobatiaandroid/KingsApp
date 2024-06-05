package com.kingseducation.app.activities.payments.model

import com.google.gson.annotations.SerializedName
import com.kingseducation.app.activities.newsletter.model.NewsLetterResponseModel

class PaymentInitiateResponseModel (
    @SerializedName("status")
    val status: Int,
    @SerializedName("payment_url")
    val paymentUrl: String
)
