package com.example.kingsapp.fcm

import android.content.Context
import android.util.Log
import com.example.kingsapp.manager.PreferenceManager


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
public class MyFirebaseInstanceIDService  {
    //var mContext: Context
    var sharedprefs: PreferenceManager = PreferenceManager()


   /* override fun onTokenRefresh() {

        //val refreshedToken = FirebaseInstanceId.getInstance().token

        val refreshedToken = FirebaseInstanceId.getInstance().token.toString()

        Log.e("FIREBASETOKEN", refreshedToken)
        sendRegistrationToServer(refreshedToken)
        super.onTokenRefresh()
    }*/

    private fun sendRegistrationToServer(refreshedToken: String) {
       /* if (refreshedToken != null) {
            sharedprefs.setFcmID(mContext, refreshedToken)
        }*/

    }
}