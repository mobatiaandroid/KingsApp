package com.kingseducation.app.activities.calender.model

data class CalendarResponseModel(
    var calendar: List<Calendar?>?,
    var status: Int?
) {
    data class Calendar(
        var color: String?,
        var details: List<Detail?>?,
        var title: String?
    ) {
        data class Detail(
            var cLASS: String?,
            var cLASSArray: List<Any?>?,
            var dESCRIPTION: String?,
            var dESCRIPTIONArray: List<Any?>?,
            var dTEND: String?,
            var dTENDArray: List<Any?>?,
            var dTENDTz: String?,
            var dTSTAMP: String?,
            var dTSTAMPArray: List<Any?>?,
            var dTSTART: String?,
            var dTSTARTArray: List<Any?>?,
            var dTSTARTTz: String?,
            var pRIORITY: String?,
            var pRIORITYArray: List<Any?>?,
            var sTATUS: String?,
            var sTATUSArray: List<Any?>?,
            var sUMMARY: String?,
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
    }
}