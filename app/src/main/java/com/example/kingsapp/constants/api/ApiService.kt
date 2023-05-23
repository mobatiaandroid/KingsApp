package com.example.isgoman_kotlin.constants.api


import android.content.Context
import com.example.kingsapp.activities.absence.model.AbsenceLeaveApiModel
import com.example.kingsapp.activities.absence.model.AbsenceListModel
import com.example.kingsapp.activities.absence.model.RequestAbsenceApiModel
import com.example.kingsapp.activities.adapter.apps.model.AppsModel
import com.example.kingsapp.activities.calender.model.CalendarListModel
import com.example.kingsapp.activities.early_pickup.model.EarlyPickupListModel
import com.example.kingsapp.activities.early_pickup.model.RequestEarlyApiModel
import com.example.kingsapp.activities.forms.model.FormsModel
import com.example.kingsapp.activities.home.model.HomeGuestrResponseModel
import com.example.kingsapp.activities.home.model.HomeUserResponseModel

import com.example.kingsapp.activities.login.model.LoginResponseModel
import com.example.kingsapp.activities.login.model.StudentListResponseModel
import com.example.kingsapp.activities.message.model.NotificationModel
import com.example.kingsapp.activities.parentessentials.model.ParentModel
import com.example.kingsapp.activities.reports.model.ReportsResponseModel
import com.example.kingsapp.activities.social_media.model.SocialMediaResponseModel
import com.example.kingsapp.activities.student_info.model.StudentInfoResponseModel
import com.example.kingsapp.activities.timetable.model.TimeTableResponseModel
import com.example.kingsapp.common.CommonResponse
import com.example.kingsapp.fragment.contact.model.ContactusModel
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Arshad on 09,August,2022
 */
interface ApiService {
    val context: Context
    /*************access token****************/
    @POST("api/v1/parent-login")
    @FormUrlEncoded
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("devicetype") devicetype: String,
        @Field("deviceid") deviceid: String,
        @Field("device_identifier") device_identifier: String
    ): Call<LoginResponseModel>


    /*************Sign In****************/
    @POST("api/v1/parent-signup")
    @FormUrlEncoded
    fun signup(
        @Field("email") email: String,
    ): Call<CommonResponse>

    /*************Forget Password****************/
    @POST("api/v1/forgot-password")
    @FormUrlEncoded
    fun forgetpwsd(
        @Field("email") email: String,
    ): Call<CommonResponse>

    /*************Change Password****************/
    @POST("api/v1/changepassword")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun changepswd(
        @Header("Authorization") token:String,
        @Field("password") password: String,
        @Field("c_password") c_password: String,
        @Field("old_password") old_password: String
    ): Call<CommonResponse>

    /*************Student List****************/
    @GET("api/v1/student-list")
    @Headers("Accept: application/json")
    fun student_list(
        @Header("Authorization") token:String
    ): Call<StudentListResponseModel>


    /*************Logout****************/
    @GET("api/v1/parent-logout")
    @Headers("Accept: application/json")
    fun logout(
        @Header("Authorization") token:String
    ): Call<ResponseBody>

    /*************Student Info****************/
    @POST("api/v1/student-info")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun studentinfo(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String
    ): Call<StudentInfoResponseModel>


    /*************Guest user****************/
    @GET("api/v1/home-guest")
    fun homeguest(): Call<HomeGuestrResponseModel>


    /*************Delete My Account****************/
    @GET("api/v1/delete-account")
    @Headers("Accept: application/json")
    fun delete(
        @Header("Authorization") token:String
    ): Call<ResponseBody>
    /*************User****************/
    @GET("api/v1/home-user")
    @Headers("Accept: application/json")
    fun homeuser(
        @Header("Authorization") token:String
    ): Call<HomeUserResponseModel>

    /*************FeedBack****************/
    @POST("api/v1/sendemail")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun feedback(
        @Header("Authorization") token:String,
        @Field("subject") subject: String,
        @Field("message") message: String,
        @Field("email") email: String,
        @Field("name") name: String
        ): Call<ResponseBody>


    /*************Contact Us****************/
    @POST("api/v1/contactus")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun contactus(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String
    ): Call<ContactusModel>

    /*************Early Pickup****************/
    @POST("api/v1/request-early-pickup")
    @Headers("Accept: application/json")
    fun request_early_pickup(
        @Header("Authorization") token:String,
        @Body requestLeave: RequestEarlyApiModel
    ): Call<CommonResponse>
    /*************EarlyPickup List****************/
    @POST("api/v1/list-early-pickup")
    @Headers("Content-Type: application/json")
    fun earlyPickupList(
        @Header("Authorization") token:String,
        @Body  studentInfoModel: AbsenceLeaveApiModel

    ): Call<EarlyPickupListModel>
    /*************Forms****************/
    @POST("api/v1/forms")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun forms(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String

    ): Call<FormsModel>

    /*************ParentEssentials****************/
    @POST("api/v1/parent-essentials")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun parentessentials(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String

        ): Call<ParentModel>


    /*************Apps****************/
    @POST("api/v1/apps")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun apps(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String

    ): Call<AppsModel>

    /*************Request Leave****************/
    @POST("api/v1/request-leave")
    @Headers("Accept: application/json")
    fun requestleave(
        @Header("Authorization") token:String,
        @Body requestLeave: RequestAbsenceApiModel
    ): Call<CommonResponse>


    /*************Absence List****************/
    @POST("api/v1/leave-requests")
    @Headers("Content-Type: application/json")
    fun absenceList(
        @Header("Authorization") token:String,
        @Body  studentInfoModel: AbsenceLeaveApiModel

    ): Call<AbsenceListModel>


    /*************Calendar Month****************/
    @POST("api/v1/calendar")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun schoolcalendar(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String
    ): Call<CalendarListModel>
    /*************School Calendar MonthList****************/
    @POST("api/v1/school-calendar-month")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun schoolcalendarList(
        @Header("Authorization") token:String,
        @Field("student_id") student_id: String
    ): Call<CalendarListModel>


    /*************Absence List****************/
    @POST("api/v1/notifications")
    @Headers("Content-Type: application/json")
    fun notification(
        @Header("Authorization") token:String,
        @Body  studentInfoModel: AbsenceLeaveApiModel

    ): Call<NotificationModel>


    /*************Reports****************/
    @POST("api/v1/reports")
    @Headers("Content-Type: application/json")
    fun reportss(
        @Header("Authorization") token:String,
        @Body json: JsonObject

    ): Call<ReportsResponseModel>


    /*************Social Media****************/
    @POST("api/v1/socialmedia")
    @Headers("Content-Type: application/json")
    fun socialmedia(
        @Header("Authorization") token:String,
        @Body json: JsonObject

    ): Call<SocialMediaResponseModel>


    /*************Time Table****************/
    @POST("api/v1/timetable")
    @Headers("Content-Type: application/json")
    fun timetable(
        @Header("Authorization") token:String,
        @Body json: JsonObject

    ): Call<TimeTableResponseModel>
}
