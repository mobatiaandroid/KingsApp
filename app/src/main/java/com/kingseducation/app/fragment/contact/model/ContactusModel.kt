package com.kingseducation.app.fragment.contact.model

import com.google.gson.annotations.SerializedName

 class ContactusModel (
    @SerializedName("status") val status: Int,
    @SerializedName("contactus") val contactus: SingleContactusResponseArray,
     @SerializedName("all_contacts") val all_contacts: ArrayList<ContactusResponseArray>
)