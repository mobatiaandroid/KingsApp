package com.kingseducation.app.activities.parentessentials

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.gson.JsonObject
import com.kingseducation.app.R
import com.kingseducation.app.activities.absence.imagicon
import com.kingseducation.app.activities.absence.studentImg
import com.kingseducation.app.activities.forms.model.FormList
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.login.SigninyourAccountActivity
import com.kingseducation.app.activities.parentessentials.adapter.ParentListAdapter
import com.kingseducation.app.activities.parentessentials.model.ParentModel
import com.kingseducation.app.activities.re_enrolment.ReEnrolmentListingActivity
import com.kingseducation.app.activities.re_enrolment.adapter.ReEnrolStudentListAdapter
import com.kingseducation.app.activities.re_enrolment.model.ReEnrolmentListResponseModel
import com.kingseducation.app.activities.teacher_contact.model.GeneralSubmitResponseModel
import com.kingseducation.app.constants.CommonClass
import com.kingseducation.app.constants.PdfReaderActivity
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.WebViewLoaderActivity
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response


class ParentEssentialsActivity : AppCompatActivity() {
    // lateinit var list_name:ArrayList<String>
    lateinit var list_name: ArrayList<FormList>
    lateinit var back: ImageView

    lateinit var parentList: RecyclerView
    lateinit var mcontext: Context
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var reEnrolList: ArrayList<ReEnrolmentListResponseModel.ReEnrollment>
    var check: Boolean = false

    // private lateinit var progressDialog: RelativeLayout
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var textView: TextView
    lateinit var reEnrolItem: ReEnrolmentListResponseModel.ReEnrollment
    var selectedStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_parentessentails)
        mcontext = this
        initFn()
        if (CommonClass.isInternetAvailable(mcontext)) {
            // progressDialog.visibility = View.VISIBLE
            parrentessentialApiCall()
            callReEnrolmentAPI()

        } else {
            Toast.makeText(
                mcontext,
                "Network error occurred. Please check your internet connection and try again later",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun callReEnrolmentAPI() {
        progressBarDialog.show()
        val call: Call<ReEnrolmentListResponseModel> = ApiClient.getApiService().getReEnrolments(
            "Bearer " +
                    PreferenceManager().getAccessToken(mcontext).toString()
        )
        call.enqueue(object : retrofit2.Callback<ReEnrolmentListResponseModel> {
            override fun onResponse(
                call: Call<ReEnrolmentListResponseModel>,
                response: Response<ReEnrolmentListResponseModel>
            ) {
                progressBarDialog.dismiss()
                if (response.body()!!.status == 100) {
                    if (response.body()!!.reEnrollments.isNotEmpty()) {
                        reEnrolList = response.body()!!.reEnrollments
                        if (reEnrolList.isEmpty()) {
                            Log.e("ReEnrolList", "Empty")
//                            Toast.makeText(mcontext, "Re-Enrolment not available!", Toast.LENGTH_SHORT).show()
                        } else {
                            for (i in 0 until reEnrolList.size) {
                                Log.e("getStudent_ID", PreferenceManager().getStudent_ID(mcontext).toString())
                                Log.e("api reenrol", reEnrolList[i].id.toString().toString())
                                if (PreferenceManager().getStudent_ID(mcontext) == reEnrolList[i].id.toString()){
                                    reEnrolItem = reEnrolList[i]
                                    Log.e("set reenrol", reEnrolItem.id.toString())
                                }
                            }
                        }
                    } else {

                    }

                } else {
                    Toast.makeText(
                        mcontext,
                        "Fail to get the data..",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<ReEnrolmentListResponseModel>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun parrentessentialApiCall() {
        progressBarDialog.show()
        Log.e("languagetype", PreferenceManager().getLanguagetype(mcontext).toString())
        val call: Call<ParentModel> = ApiClient.getApiService().parentessentials(
            "Bearer " +
                    PreferenceManager().getAccessToken(mcontext).toString(),
            PreferenceManager().getStudent_ID(mcontext).toString(),
            PreferenceManager().getLanguagetype(mcontext).toString()
        )
        call.enqueue(object : retrofit2.Callback<ParentModel> {
            override fun onResponse(
                call: Call<ParentModel>,
                response: Response<ParentModel>
            ) {
                progressBarDialog.hide()
                if (response.body() != null) {
                    Log.e("Response", response.body().toString())
                    if (response.body()!!.status.equals(100)) {
                        list_name.clear()
                        list_name.addAll(response.body()!!.parent_essentials)
                        list_name.add(FormList("9999999999","Re-Enrolment","pop_up","https://www.google.com"))
                        parentList.layoutManager = linearLayoutManager
                        val parentadapter = ParentListAdapter(mcontext, list_name)
                        parentList.adapter = parentadapter
                    } else {
                        CommonClass.checkApiStatusError(response.body()!!.status, mcontext)
                    }
                } else {
                    val intent = Intent(mcontext, SigninyourAccountActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ParentModel?>, t: Throwable) {
                progressBarDialog.hide()
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.e("succ", t.message.toString())
            }
        })
    }

    private fun initFn() {
        // list_name = mcontext.resources.getStringArray(R.array.parent_list)
        list_name = ArrayList()

        back = findViewById(R.id.back)
        textView = findViewById(R.id.textView)
        parentList = findViewById(R.id.parentessetialsrec)
        linearLayoutManager = LinearLayoutManager(mcontext)
        progressBarDialog = ProgressBarDialog(mcontext)
        /* val aniRotate: Animation =
             AnimationUtils.loadAnimation(mcontext, R.anim.linear_interpolator)
         progressDialog.startAnimation(aniRotate)*/
        if (PreferenceManager().getLanguage(mcontext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mcontext.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        parentList.layoutManager = linearLayoutManager

        parentList.addOnItemTouchListener(
            RecyclerItemListener(
                mcontext, parentList,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {

                        if (list_name.get(position).url.endsWith(".pdf")) {
                            val intent = Intent(mcontext, PdfReaderActivity::class.java)
                            intent.putExtra("pdf_url", list_name[position].url)
                            intent.putExtra("pdf_title", list_name[position].title)
                            startActivity(intent)
                        } else if (
                            list_name[position].title.contains("enrol", ignoreCase = true)
                        ) {
                            Log.e("reenrol id ", reEnrolItem.id.toString())
                            Log.e("reenrol isReEnrollment ", reEnrolItem.isReEnrollment.toString())
                            if (reEnrolItem.selectedOption == ""){
                                if (reEnrolItem.isReEnrollment == 1){
                                    showReEnrolPopUp()
                                }else{
                                    Toast.makeText(mcontext, "Re-Enrolment not available!", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(mcontext, "Re-Enrolment already submitted!", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            val intent = Intent(mcontext, WebViewLoaderActivity::class.java)
                            intent.putExtra("webview_url", list_name[position].url)
                            intent.putExtra("title", list_name[position].title)
                            startActivity(intent)
                        }

                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )

        back.setOnClickListener {
            val intent = Intent(mcontext, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showReEnrolPopUp() {
        val dialog = Dialog(mcontext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_re_enrolment)
        val questionTextView = dialog.findViewById<TextView>(R.id.questionTV)
        val closeButton = dialog.findViewById<ImageView>(R.id.close_btn)
        val headingTV = dialog.findViewById<TextView>(R.id.headingTV)
        val descriptionTV = dialog.findViewById<TextView>(R.id.descriptionTV)
        val stud_name = dialog.findViewById<TextView>(R.id.stud_name)
        val stud_class = dialog.findViewById<TextView>(R.id.stud_class)
        val stud_img = dialog.findViewById<ImageView>(R.id.stud_img)

        val selectedOptionTV = dialog.findViewById<TextView>(R.id.option_txt)
        val spinnerView = dialog.findViewById<Spinner>(R.id.spinnerlist)
        val personalInfoButton = dialog.findViewById<Button>(R.id.personal_info)
        val termsConditionsButton = dialog.findViewById<Button>(R.id.terms_conditions)
        val sub_btn = dialog.findViewById<Button>(R.id.sub_btn)
        val checkButton = dialog.findViewById<RadioButton>(R.id.check_btn)
        val dropdownList: java.util.ArrayList<String> = ArrayList()
        val optionsArray: ArrayList<String> = reEnrolItem.reEnrollmentData.options
        stud_name.text = reEnrolItem.fullName
        stud_class.text = reEnrolItem.className
        closeButton.setOnClickListener {
            check = false
            selectedStatus = ""
            dialog.dismiss()
        }
        personalInfoButton.setOnClickListener {
            val intent = Intent(mcontext, WebViewLoaderActivity::class.java)
            intent.putExtra("webview_url", reEnrolItem.reEnrollmentData.statementUrl)
            intent.putExtra("title", "Personal Info Collection Statement")
            startActivity(intent)
        }
        termsConditionsButton.setOnClickListener {
            val intent = Intent(mcontext, WebViewLoaderActivity::class.java)
            intent.putExtra("webview_url", reEnrolItem.reEnrollmentData.termsAndConditionsUrl)
            intent.putExtra("title", "Terms and Conditions")
            startActivity(intent)
        }
        checkButton.setOnCheckedChangeListener { buttonView, isChecked ->
            check = isChecked
        }
        sub_btn.setOnClickListener {
            if (selectedStatus.isEmpty()) {
                Toast.makeText(mcontext, "Please select the option", Toast.LENGTH_SHORT).show()
            } else {
                if (check) {
                    callReEnrolSubmitAPI(dialog, reEnrolItem.id)
                } else {
                    Toast.makeText(mcontext, "Please check the box", Toast.LENGTH_SHORT).show()
                }
            }

        }
        if (!PreferenceManager().getStudentPhoto(mcontext)!!.isEmpty()) {
            studentImg = PreferenceManager().getStudentPhoto(mcontext).toString()
            if (studentImg != null && !studentImg.isEmpty()) {
                val glideUrl = GlideUrl(
                    studentImg,
                    LazyHeaders.Builder()
                        .addHeader(
                            "Authorization",
                            "Bearer " + PreferenceManager().getAccessToken(mcontext).toString()
                        )
                        .build()
                )
                Glide.with(mcontext)
                    .load(glideUrl)
                    .transform(CircleCrop())
                    .placeholder(R.drawable.profile_photo)
                    .error(R.drawable.profile_photo)
                    .into(stud_img)
            } else {
                Toast.makeText(mcontext, "Image empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            imagicon.setImageResource(R.drawable.profile_photo)
        }
        headingTV.text = reEnrolItem.reEnrollmentData.title
        descriptionTV.text = reEnrolItem.reEnrollmentData.description
        questionTextView.text = reEnrolItem.reEnrollmentData.question
        dropdownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
            dropdownList.add(i, optionsArray[i - 1])
        }
        val sp_adapter = ArrayAdapter<String>(
            mcontext, R.layout.spinner_textview, dropdownList
        )
        spinnerView.adapter = sp_adapter
        sp_adapter.setDropDownViewResource(R.layout.spinner_textview)
        spinnerView.setSelection(0)

        spinnerView.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val selectedItem: String = parent.getItemAtPosition(position).toString()
                    val optionlistSize: Int = dropdownList.size - 1
                    for (i in 1..optionlistSize) {
                        if ((selectedItem == dropdownList[i])) {
                            selectedOptionTV.text = selectedItem
                            selectedStatus = dropdownList[i]
//                            check[0] = 1
                        } else if ((selectedItem == dropdownList[0])) {
                            selectedOptionTV.text = selectedItem
                            selectedStatus = ""
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
//        selectedOptionTV.text = reEnrollmentData.selectedOption
        dialog.show()
    }

    private fun callReEnrolSubmitAPI(d: Dialog, id: Int) {
        progressBarDialog.show()
        val paramObject = JsonObject().apply {
            addProperty("student_id", id)
            addProperty("selected_option", selectedStatus)

        }
        val call: Call<GeneralSubmitResponseModel> = ApiClient.getApiService().submitReEnrolment(
            "Bearer " +
                    PreferenceManager().getAccessToken(mcontext).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<GeneralSubmitResponseModel> {
            override fun onResponse(
                call: Call<GeneralSubmitResponseModel>,
                response: Response<GeneralSubmitResponseModel>
            ) {
                progressBarDialog.dismiss()
                if (response.body()!!.status == "100") {
                    Toast.makeText(
                        mcontext,
                        resources.getString(R.string.re_enrolment_submit_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    d.dismiss()

                } else {
                    Toast.makeText(
                        mcontext,
                        resources.getString(R.string.re_enrolment_submit_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                parrentessentialApiCall()

            }

            override fun onFailure(call: Call<GeneralSubmitResponseModel>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    mcontext,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}