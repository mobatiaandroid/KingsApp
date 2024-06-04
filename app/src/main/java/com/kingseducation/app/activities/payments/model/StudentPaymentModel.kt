package com.kingseducation.app.activities.payments.model

import com.google.gson.annotations.SerializedName


class StudentPaymentModel (
    @SerializedName("attributes") val attributes: AttributesModel,
    @SerializedName("Pupil_Code__c") val Pupil_Code__c: String,
)