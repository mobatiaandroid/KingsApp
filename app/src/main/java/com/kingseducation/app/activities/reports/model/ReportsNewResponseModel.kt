package com.kingseducation.app.activities.reports.model

import com.google.gson.annotations.SerializedName

data class ReportsNewResponseModel(
    @SerializedName("status")
    val status: Int,
    @SerializedName("pdf_viewer_url")
    val pdfViewerUrl: String,
    @SerializedName("reports")
    val reports: ArrayList<Report>
) {
    data class Report(
        @SerializedName("reportFileName")
        val reportFileName: String,
        @SerializedName("reportFormat")
        val reportFormat: String,
        @SerializedName("academicYear")
        val academicYear: Int,
        @SerializedName("academicLabel")
        val academicLabel: String,
        @SerializedName("termName")
        val termName: String,
        @SerializedName("className")
        val className: String,
        @SerializedName("publishedOn")
        val publishedOn: Long,
        @SerializedName("termId")
        val termId: Int,
        @SerializedName("studentId")
        val studentId: Int,
        @SerializedName("classId")
        val classId: Int,
        @SerializedName("sectionId")
        val sectionId: Int,
        @SerializedName("gradeId")
        val gradeId: Int,
        @SerializedName("reportUrl")
        val reportUrl: String,
        @SerializedName("staticReport")
        val staticReport: Int,
        @SerializedName("sectionName")
        val sectionName: String,
        @SerializedName("gradeName")
        val gradeName: String,
        @SerializedName("publishStatus")
        val publishStatus: String
    )

}

