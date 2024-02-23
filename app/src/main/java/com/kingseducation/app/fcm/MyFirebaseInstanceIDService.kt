package com.kingseducation.app.fcm

import com.kingseducation.app.manager.PreferenceManager


class MyFirebaseInstanceIDService {
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