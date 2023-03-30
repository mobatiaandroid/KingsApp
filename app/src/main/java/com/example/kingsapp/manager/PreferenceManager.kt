package com.example.kingsapp.manager

import android.content.Context
import com.example.kingsapp.R
import com.example.kingsapp.activities.calender.model.CalendarList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class PreferenceManager {
    val PREFSNAME = "NAS"
    fun setAccessToken(context: Context, accesstoken: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("accesstoken", accesstoken)
        editor.apply()
    }

    fun getAccessToken(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("accesstoken", "")
    }
    fun setuser_id(context: Context, userid: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("userid", userid)
        editor.apply()
    }

    fun getuser_id(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("userid", "")
    }

    fun getIBackParent(context: Context): Boolean {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getBoolean("BackParent", true)
    }

    fun setBackParent(context: Context, result: Boolean) {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("BackParent", result)
        editor.commit()
    }


    fun setLanguage(context: Context, token: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("language", token)
        editor.apply()
    }

    fun getLanguage(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("language", "")

    }

    fun setLoginToken(context: Context, token: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("logintoken", token)
        editor.apply()
    }

    fun getLoginToken(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("logintoken", "")

    }

    fun setLoginID(context: Context, token: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("loginid", token)
        editor.apply()
    }

    fun getLoginID(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("loginid", "")

    }

    fun setLoginEmail(context: Context, loginemail: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("loginemail", loginemail)
        editor.apply()
    }

    fun getLoginEmail(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("loginemail", "")

    }


    /*************************USER CODE************************/
    fun setUserCode(context: Context, usercode: String?) {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("user_code", usercode)
        editor.apply()
    }
    fun getUserCode(context: Context): String? {
        val prefs = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        return prefs.getString("user_code", "")
    }
    fun setLoginPassword(context: Context, password: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("password", password)
        editor.apply()
    }

    fun getLoginPassword(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("password", "")

    }
    fun setStudent_ID(context: Context, id: String?) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("student_id", id)
        editor.apply()
    }

    /*GET STUDENT_ID*/
    fun getStudent_ID(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("student_id", "")
    }

    /*SET STUDENT_NAME*/
    fun setStudentName(context: Context, name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("student_name", name)
        editor.apply()
    }

    /*GET STUDENT_NAME*/
    fun getStudentName(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("student_name", "")
    }

    /*SET StudentPhoto*/
    fun setStudentPhoto(context: Context, name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("student_photo", name)
        editor.apply()
    }

    /*GET STUDENT_NAME*/
    fun getStudentPhoto(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("student_photo", "")
    }
    fun setAppversion(context: Context, name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("app_version", name)
        editor.apply()
    }

    /*GET STUDENT_NAME*/
    fun getAppVersion(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("app_version", "")
    }
    /*SET StudentClass*/
    fun setStudentClass(context: Context, name: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("student_class", name)
        editor.apply()
    }

    /*GET STUDENT_NAME*/
    fun getStudentClass(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("student_class", "")
    }

    fun setbuttononetabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_onetabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON ONE TAB ID
     */

    fun getbuttononetabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_onetabid", "1")
    }

    fun setbuttontwotabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_twotabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON TWO TAB ID
     */

    fun getbuttontwotabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_twotabid", "2")
    }

    fun setbuttonthreetabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_threetabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON THREE TAB ID
     */

    fun getbuttonthreetabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_threetabid", "9")
    }

    fun setbuttonfourtabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_fourtabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON FOUR TAB ID
     */

    fun getbuttonfourt_abid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_fourtabid", "4")
    }

    fun setbuttonfivetabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_fivetabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON FIVE TAB ID
     */

    fun getbuttonfivetabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_fivetabid", "5")
    }

    fun setbuttonsixtabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_sixtabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON SIX TAB ID
     */

    fun getbuttonsixtabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_sixtabid", "6")
    }

    fun setbuttonseventabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_seventabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON SEVEN TAB ID
     */

    fun getbuttonseventabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_seventabid", "7")
    }

    fun setbuttoneighttabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_eighttabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON EIGHT TAB ID
     */

    fun getbuttoneighttabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_eighttabid", "11")
    }

    fun setbuttonninetabid(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_ninetabid", id)
        editor.apply()
    }

    /**
     * GET BUTTON NINE TAB ID
     */

    fun getbuttonninetabid(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_ninetabid", "6")
    }


    /**
     * SET BUTTON ONE TEXT IMAGE
     */

    fun setbuttononetextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_onetextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON ONE TEXT IMAGE
     */

    fun getbuttononetextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_onetextimage", "1")
    }

    fun setbuttontwotextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_twotextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON TWO TEXT IMAGE
     */

    fun getbuttontwotextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_twotextimage", "2")
    }

    fun setbuttonthreetextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_threetextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON THREE TEXT IMAGE
     */

    fun getbuttonthreetextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_threetextimage", "9")
    }

    fun setbuttonfourtextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_fourtextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON FOUR TEXT IMAGE
     */

    fun getbuttonfourtextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_fourtextimage", "4")
    }

    fun setbuttonfivetextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_fivetextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON FIVE TEXT IMAGE
     */

    fun getbuttonfivetextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_fivetextimage", "5")
    }

    fun setbuttonsixtextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_sixtextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON SIX TEXT IMAGE
     */

    fun getbuttonsixtextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_sixtextimage", "6")
    }

    fun setbuttonseventextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_seventextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON SEVEN TEXT IMAGE
     */

    fun getbuttonseventextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_seventextimage", "7")
    }

    fun setbuttoneighttextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_eighttextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON EIGHT TEXT IMAGE
     */

    fun getbuttoneighttextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_eighttextimage", "11")
    }

    fun setbuttonninetextimage(context: Context, id: String?) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("button_ninetextimage", id)
        editor.apply()
    }

    /**
     * GET BUTTON NINE TEXT IMAGE
     */

    fun getbuttonninetextimage(context: Context): String? {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getString("button_ninetextimage", "6")
    }


    fun setButtonOneGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttononeguestbg", color)
        editor.apply()
    }

    fun getButtonOneGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttononeguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtontwoGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttontwoguestbg", color)
        editor.apply()
    }

    fun getButtontwoGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttontwoguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonthreeGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonthreeguestbg", color)
        editor.apply()
    }

    fun getButtonthreeGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonthreeguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonfourGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonfourguestbg", color)
        editor.apply()
    }

    fun getButtonfourGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonfourguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonfiveGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonfiveguestbg", color)
        editor.apply()
    }

    fun getButtonfiveGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonfiveguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonsixGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonsixguestbg", color)
        editor.apply()
    }

    fun getButtonsixGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonsixguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonsevenGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonsevenguestbg", color)
        editor.apply()
    }

    fun getButtonsevenGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonsevenguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtoneightGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttoneightguestbg", color)
        editor.apply()
    }

    fun getButtoneightGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttoneightguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setButtonnineGuestBg(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putInt("buttonnineguestbg", color)
        editor.apply()
    }

    fun getButtonnineGuestBg(context: Context): Int {
        val prefs = context.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        return prefs.getInt(
            "buttonnineguestbg", context.resources
                .getColor(R.color.transparent)
        )
    }

    fun setStudIdForCCA(context: Context, result: String?) {
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        val editor = prefs.edit()
        editor.putString("StudIdForCCA", result)
        editor.commit()
    }

    //getStudIdForCCA
    fun getStudIdForCCA(context: Context): String? {
        var StudIdForCCA = ""
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        StudIdForCCA = prefs.getString("StudIdForCCA", "").toString()
        return StudIdForCCA
    }

    fun setStudNameForCCA(context: Context, result: String?) {
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        val editor = prefs.edit()
        editor.putString("StudNameForCCA", result)
        editor.commit()
    }

    fun getStudNameForCCA(context: Context): String? {
        var StudNameForCCA = ""
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        StudNameForCCA = prefs.getString("StudNameForCCA", "").toString()
        return StudNameForCCA
    }

    fun setStudClassForCCA(context: Context, result: String?) {
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        val editor = prefs.edit()
        editor.putString("StudClassForCCA", result)
        editor.commit()
    }

    fun getStudClassForCCA(context: Context): String? {
        var StudClassForCCA = ""
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        StudClassForCCA = prefs.getString("StudClassForCCA", "").toString()
        return StudClassForCCA
    }

    fun setCCATitle(context: Context, result: String?) {
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        val editor = prefs.edit()
        editor.putString("CCATitle", result)
        editor.commit()
    }

    fun getCCATitle(context: Context): String? {
        var CCATitle = ""
        val prefs =
            context.getSharedPreferences(
                PREFSNAME,
                Context.MODE_PRIVATE
            )
        CCATitle = prefs.getString("CCATitle", "").toString()
        return CCATitle
    }

    fun setStudentname(context: Context, name: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("onclickname", name)
        editor.apply()
    }

    fun getStudentname(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("onclickname", "")

    }
    fun setStudentyeargroup(context: Context, group: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("group", group)
        editor.apply()
    }

    fun getStudentyeargroup(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("group", "")

    }
    fun setabsence_firstday(context: Context, id: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("firstday", id)
        editor.apply()
    }

    fun getabsence_firstday(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("firstday", "")

    }

    fun setabsence_returnday(context: Context, day: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("rday", day)
        editor.apply()
    }

    fun getabsence_returnday(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("rday", "")

    }

    fun setabsence_reason(context: Context, day: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("reason", day)
        editor.apply()
    }

    fun getabsence_reason(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("reason", "")

    }

    fun setemail_canteen(context: Context, email: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("email", email)
        editor.apply()
    }

    fun getemail_canteen(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE
        )
        return pref.getString("email", "")

    }
    fun setCCAItemId(context: Context, cca_itemid: String) {
        val pref = context.getSharedPreferences(PREFSNAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("cca_itemid", cca_itemid)
        editor.apply()
    }

    fun getCCAItemId(context: Context): String? {
        val pref = context.getSharedPreferences(
            PREFSNAME, Context.MODE_PRIVATE)
        return pref.getString("cca_itemid", "")
    }

    fun setPTABooked(context: Context?, b: Boolean) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putBoolean("pta_booked", b)
        editor.commit()
    }

    fun getPTABooked(context: Context?): Boolean {
        var b = false
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getBoolean("pta_booked", false)
        return b
    }
    fun setPTABookedID(context: Context?, id: String) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("pta_booked_id", id)
        editor.commit()
    }

    fun getPTABookedID(context: Context?): String {
        var b = ""
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getString("pta_booked_id", "").toString()
        return b
    }


    fun setFromYearView(context: Context?, value: String) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("value", value)
        editor.commit()
    }

    fun getFromYearView(context: Context?): String {
        var b = ""
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getString("value", "").toString()
        return b
    }
    fun setMonthView(context: Context?, value: String) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("month", value)
        editor.commit()
    }

    fun getMonthView(context: Context?): String {
        var b = ""
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getString("month", "").toString()
        return b
    }

    /*fun saveArrayList(context: Context, list: ArrayList<CalendarList>) {
        val prefs = context.getSharedPreferences(
            "NAS",
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString("list", json)
        editor.apply()
    }

    fun getArrayList(context: Context): ArrayList<CalendarList> {
        val prefs = context.getSharedPreferences(
            "NAS",
            Context.MODE_PRIVATE
        )
        val gson = Gson()
        val json = prefs.getString("list", null)
        val type: Type = object : TypeToken<ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }*/

    fun setvalue(context: Context?, value: String) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("value", value)
        editor.commit()
    }

    fun getvalue(context: Context?): String {
        var b = ""
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getString("value", "").toString()
        return b
    }
    fun setMonthSelected(context: Context?, month: String) {
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("month", month)
        editor.commit()
    }

    fun getMonthSelected(context: Context?): String {
        var b = ""
        val prefs = context!!.getSharedPreferences(
            PREFSNAME,
            Context.MODE_PRIVATE
        )
        b = prefs.getString("month", "").toString()
        return b
    }
}



