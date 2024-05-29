package com.kingseducation.app.constants.api


import android.content.Context
import com.google.gson.JsonObject
import com.kingseducation.app.activities.absence.model.AbsenceLeaveApiModel
import com.kingseducation.app.activities.absence.model.AbsenceListModel
import com.kingseducation.app.activities.apps.model.AppsModel
import com.kingseducation.app.activities.calender.model.CalendarListModel
import com.kingseducation.app.activities.calender.model.CalendarResponseModel
import com.kingseducation.app.activities.early_pickup.model.EarlyPickupListModel
import com.kingseducation.app.activities.early_pickup.model.RequestEarlyApiModel
import com.kingseducation.app.activities.forms.model.FormsModel
import com.kingseducation.app.activities.home.model.HomeGuestrResponseModel
import com.kingseducation.app.activities.home.model.HomeUserResponseModel
import com.kingseducation.app.activities.login.model.LoginResponseModel
import com.kingseducation.app.activities.login.model.StudentListResponseModel
import com.kingseducation.app.activities.message.model.NotificationModel
import com.kingseducation.app.activities.newsletter.model.NewsLetterResponseModel
import com.kingseducation.app.activities.parentessentials.model.ParentModel
import com.kingseducation.app.activities.payments.model.PendingInvoiceResponseModel
import com.kingseducation.app.activities.re_enrolment.model.ReEnrolmentListResponseModel
import com.kingseducation.app.activities.reports.model.ReportsNewResponseModel
import com.kingseducation.app.activities.social_media.model.SocialMediaResponseModel
import com.kingseducation.app.activities.student_info.model.StudentInfoResponseModel
import com.kingseducation.app.activities.teacher_contact.model.ContactTeacherResponseModel
import com.kingseducation.app.activities.teacher_contact.model.SubjectTeachersResponseModel
import com.kingseducation.app.activities.timetable.model.TimeTableResponseModel
import com.kingseducation.app.common.CommonResponse
import com.kingseducation.app.fragment.contact.model.ContactusModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


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
    /*************deviceRegistration****************/
    @POST("api/v1/update-token")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun devicereg(
        @Header("Authorization") token:String,
        @Field("devicetype") devicetype: String,
        @Field("deviceid") deviceid: String,
        @Field("device_identifier") device_identifier: String
    ): Call<ResponseBody>
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
//    @POST("api/v1/request-leave")
//    @Headers("Accept: application/json")
//    fun requestleave(
//        @Header("Authorization") token:String,
//        @Body requestLeave: RequestAbsenceApiModel
//    ): Call<CommonResponse>
    @Multipart

    @POST("api/v1/request-leave")
    fun requestleave(
        @Header("Authorization") token: String?,
        @Part("student_id") studentID: RequestBody?,
        @Part("from_date") fromDate: RequestBody?,
        @Part("to_date") toDate: RequestBody?,
        @Part("reason") reason: RequestBody?,
        @Part("device_type") device_type: RequestBody?,
        @Part("device_name") device_name: RequestBody?,
        @Part("app_version") app_version: RequestBody?,
        @Part file: MultipartBody.Part?,
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
        @Header("Authorization") token: String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String
    ): Call<CalendarListModel>

    @POST("api/v1/calendar")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun schoolcalendarNew(
        @Header("Authorization") token: String,
        @Field("student_id") student_id: String,
        @Field("language_type") language_type: String
    ): Call<CalendarResponseModel>

    /*************School Calendar MonthList****************/
    @POST("api/v1/school-calendar-month")
    @Headers("Accept: application/json")
    @FormUrlEncoded
    fun schoolcalendarList(
        @Header("Authorization") token: String,
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
    @POST("api/v1/student-reports")
    @Headers("Content-Type: application/json")
    fun reportss(
        @Header("Authorization") token: String,
        @Body json: JsonObject

    ): Call<ReportsNewResponseModel>

    /*************newsletter****************/
    @POST("api/v1/newsletter")
    @Headers("Content-Type: application/json")
    fun newsletter(
        @Header("Authorization") token: String,
        @Body json: JsonObject

    ): Call<NewsLetterResponseModel>


    /*************Social Media****************/
    @POST("api/v1/socialmedia")
    @Headers("Content-Type: application/json")
    fun socialmedia(
        @Header("Authorization") token: String,
        @Body json: JsonObject

    ): Call<SocialMediaResponseModel>


    /*************Time Table****************/
    @POST("api/v1/timetable-new")
    @Headers("Content-Type: application/json")
    fun timetable(
        @Header("Authorization") token: String,
        @Body json: JsonObject

    ): Call<TimeTableResponseModel>

    /***************Subject Teachers****************/
    @POST("api/v1/subject-teachers")
    @Headers("Content-Type: application/json")
    fun getSubjectTeachers(
        @Header("Authorization") token: String,
        @Body json: JsonObject

    ): Call<SubjectTeachersResponseModel>

    /******************Contact Teacher*************/

    @POST("api/v1/contact-teacher")
    @Headers("Content-Type: application/json")
    fun postContactTeacher(
        @Header("Authorization") token: String,
        @Body json: JsonObject
    ): Call<ContactTeacherResponseModel>

    @POST("api/v1/get-reenrollments")
    @Headers("Content-Type: application/json")
    fun getReEnrolments(
        @Header("Authorization") token: String
    ): Call<ReEnrolmentListResponseModel>

    @POST("api/v1/calendar-outlook")
    @Headers("Content-Type: application/json")
    fun getOutlookCalendar(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    /******************Pending Invoices*************/

    @POST("api/v1/pending-invoices")
    @Headers("Content-Type: application/json")
    fun pendingInvoices(
        @Header("Authorization") token: String,
        @Body json: JsonObject
    ): Call<PendingInvoiceResponseModel>
}
