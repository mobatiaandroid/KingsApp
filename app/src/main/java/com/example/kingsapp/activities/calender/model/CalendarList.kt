package com.example.kingsapp.activities.calender.model

import com.google.gson.annotations.SerializedName

class CalendarList (
        var cLASS: String?,
        var cLASSArray: List<Any?>?,
        var dESCRIPTION: String?,
        var dESCRIPTIONArray: List<Any?>?,
        var dTENDArray: List<Any?>?,
        var dTENDTz: String?,
        var dTSTAMP: String?,
        var dTSTAMPArray: List<Any?>?,
        @SerializedName("DTSTART") val dTSTART: String,
        @SerializedName("DTEND") val dTEND: String,
        @SerializedName("SUMMARY") val sUMMARY: String,
        var dTSTARTArray: List<Any?>?,
        var dTSTARTTz: String?,
        var pRIORITY: String?,
        var pRIORITYArray: List<Any?>?,
        var sTATUS: String?,
        var sTATUSArray: List<Any?>?,
        var sUMMARYArray: List<Any?>?,
        var tRANSP: String?,
        var tRANSPArray: List<Any?>?,
        var uID: String?,
        var uIDArray: List<Any?>?,
        var xMICROSOFTCDOALLDAYEVENT: String?,
        var xMICROSOFTCDOALLDAYEVENTArray: List<Any?>?,
        var xMICROSOFTCDOBUSYSTATUS: String?,
        var xMICROSOFTCDOBUSYSTATUSArray: List<Any?>?,
        var xMICROSOFTCDOIMPORTANCE: String?,
        var xMICROSOFTCDOIMPORTANCEArray: List<Any?>?,
        var xMICROSOFTCDOINTENDEDSTATUS: String?,
        var xMICROSOFTCDOINTENDEDSTATUSArray: List<Any?>?,
        var xMICROSOFTDISALLOWCOUNTER: String?,
        var xMICROSOFTDISALLOWCOUNTERArray: List<Any?>?,
        var xMICROSOFTDONOTFORWARDMEETING: String?,
        var xMICROSOFTDONOTFORWARDMEETINGArray: List<Any?>?

        )