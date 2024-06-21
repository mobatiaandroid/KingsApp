package com.kingseducation.app.constants

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.kingseducation.app.R

class CommonClass {
    companion object{

        fun isInternetAvailable(context: Context): Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }
        fun checkApiStatusError(statusCode : Int,context: Context)
        {
            if (statusCode==101)
            {
                showErrorAlert(context,"Some error occured","Alert")
            }
            else if (statusCode==102)

            {
                showErrorAlert(context,"Internal server error","Alert")
            }
            else if (statusCode==103)
            {

                showErrorAlert(context,"Invalid username/password","Alert")
            }
            else if (statusCode==104)
            {
                showErrorAlert(context,"Verification code not match","Alert")
            }
            else if (statusCode==105)
            {
                showErrorAlert(context,"User not found in our database","Alert")
            }
            else if (statusCode==106)
            {
                showErrorAlert(context,"Token expired","Alert")
            }
            else if (statusCode==107)
            {
                showErrorAlert(context,"Invalid file access","Alert")
            }
            else if(statusCode==108)
            {
                showErrorAlert(context,"Route Not Found","Alert")
            }
            else if (statusCode==109)
            {
                showErrorAlert(context,"Student not found in our database","Alert")
            }
            else if (statusCode==110)
            {
                showErrorAlert(context,"DecryptException Error","Alert")
            }
            if (statusCode==111)
            {
                showErrorAlert(context,"No records found","Alert")

            }
            else if (statusCode==112)
            {
                showErrorAlert(context,"Already registered","Alert")

            }
            else if (statusCode==113)
            {
                showErrorAlert(context,"Account blocked","Alert")

            }
            else if (statusCode==114)
            {
                showErrorAlert(context, "Account deleted / not found in database","Alert")

            } else if (statusCode==115)
            {
                showErrorAlert(context, "no student linking with this parent","Alert")

            } else if (statusCode==116)
            {
                showErrorAlert(context, "Already exists in the given range","Alert")

            }else if (statusCode==117)
            {
                showErrorAlert(context, "Required Fields Not Submitted!","Alert")

            }else if (statusCode==118)
            {
                showErrorAlert(context, "Invalid Gateway!","Alert")

            }else if (statusCode==119)
            {
                showErrorAlert(context, "Already Paid","Alert")

            }else if (statusCode==120)
            {
                showErrorAlert(context, "Early pickup can only be scheduled at least 2 hours in advance.","Alert")

            }
            else if (statusCode==300)
            {
                showErrorAlert(context, "Validation Error","Alert")

            }
        }
        fun showErrorAlert(context: Context,message : String,msgHead : String)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_dialogue_ok_layout)
           // var alertHead = dialog.findViewById(R.id.alertHead) as TextView
            var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
            var iconImageView=dialog.findViewById(R.id.iconImageView) as ImageView
           // iconImageView.setImageResource(R.drawable.ic_baseline_clear_24)
            text_dialog.text = message
           // alertHead.text = msgHead
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }


    }
}