package com.kingseducation.app.activities.data_collection.model

import com.google.gson.annotations.SerializedName


data class DataCollectionResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("is_triggered") val isTriggered: Int,
    @SerializedName("collection_fields") val collectionFields: ArrayList<CollectionField>
){
    data class CollectionField(
        @SerializedName("field_name") val fieldName: String,
        @SerializedName("field_label") val fieldLabel: String,
        @SerializedName("field_type") val fieldType: String,
        @SerializedName("field_value") var fieldValue: String
    )
}

