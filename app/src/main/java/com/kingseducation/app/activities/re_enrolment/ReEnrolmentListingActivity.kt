package com.kingseducation.app.activities.re_enrolment

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kingseducation.app.R
import com.kingseducation.app.activities.re_enrolment.model.ReEnrolmentListResponseModel
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import retrofit2.Call
import retrofit2.Response

class ReEnrolmentListingActivity : AppCompatActivity() {
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_enrolment_listing)
        initialiseUI()
        reEnrolmentDetailsAPICall()
    }

    private fun reEnrolmentDetailsAPICall() {

        val call: Call<ReEnrolmentListResponseModel> = ApiClient.getApiService().getReEnrolments(
            "Bearer " +
                    PreferenceManager().getAccessToken(context).toString()
        )
        call.enqueue(object : retrofit2.Callback<ReEnrolmentListResponseModel> {
            override fun onResponse(
                call: Call<ReEnrolmentListResponseModel>,
                response: Response<ReEnrolmentListResponseModel>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ReEnrolmentListResponseModel>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initialiseUI() {
        TODO("Not yet implemented")
    }
}