package com.kingseducation.app.activities.re_enrolment.model

import com.google.gson.annotations.SerializedName


data class ReEnrolmentListResponseModel(
    @SerializedName("status") val status: Int,
    @SerializedName("re_enrollments") val reEnrollments: ArrayList<ReEnrollment>
) {
    data class ReEnrollment(
        @SerializedName("id") val id: Int,
        @SerializedName("fullname") val fullName: String,
        @SerializedName("dob") val dob: String,
        @SerializedName("gender") val gender: String,
        @SerializedName("class") val className: String,
        @SerializedName("school_name") val schoolName: String,
        @SerializedName("school_identifier") val schoolIdentifier: String,
        @SerializedName("school_language_type") val schoolLanguageType: Int,
        @SerializedName("photo") val photoUrl: String,
        @SerializedName("is_re_enrollment") val isReEnrollment: Int,
        @SerializedName("selected_option") val selectedOption: String,
        @SerializedName("re_enrollment_data") val reEnrollmentData: ReEnrollmentData
    )

    data class ReEnrollmentData(
        @SerializedName("id") val id: Int,
        @SerializedName("title") val title: String,
        @SerializedName("start_date") val startDate: String,
        @SerializedName("end_date") val endDate: String,
        @SerializedName("description") val description: String,
        @SerializedName("t_and_c") val termsAndConditionsUrl: String,
        @SerializedName("statement_url") val statementUrl: String,
        @SerializedName("question") val question: String,
        @SerializedName("options") val options: ArrayList<String>
    )
}
