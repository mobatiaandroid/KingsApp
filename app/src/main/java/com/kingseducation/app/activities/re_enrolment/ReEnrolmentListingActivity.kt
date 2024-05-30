package com.kingseducation.app.activities.re_enrolment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.kingseducation.app.activities.home.HomeActivity
import com.kingseducation.app.activities.re_enrolment.adapter.ReEnrolStudentListAdapter
import com.kingseducation.app.activities.re_enrolment.model.ReEnrolmentListResponseModel
import com.kingseducation.app.activities.teacher_contact.model.ContactTeacherResponseModel
import com.kingseducation.app.constants.ProgressBarDialog
import com.kingseducation.app.constants.api.ApiClient
import com.kingseducation.app.manager.PreferenceManager
import com.kingseducation.app.manager.recyclerviewmanager.RecyclerItemListener
import retrofit2.Call
import retrofit2.Response

class ReEnrolmentListingActivity : AppCompatActivity() {
    lateinit var context: Context
    lateinit var studentRecycler: RecyclerView
    lateinit var back: ImageView
    lateinit var noDataTV: TextView
    lateinit var textView: TextView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var progressBarDialog: ProgressBarDialog
    lateinit var reEnrolList: ArrayList<ReEnrolmentListResponseModel.ReEnrollment>
    var selectedStatus: String = ""
    var check: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_enrolment_listing)
        context = this
        initialiseUI()
        reEnrolmentDetailsAPICall()
    }

    private fun reEnrolmentDetailsAPICall() {
        progressBarDialog.show()
        val call: Call<ReEnrolmentListResponseModel> = ApiClient.getApiService().getReEnrolments(
            "Bearer " +
                    PreferenceManager().getAccessToken(context).toString()
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
                            studentRecycler.layoutManager = linearLayoutManager
                            val studentListAdapter = ReEnrolStudentListAdapter(context, ArrayList())
                            studentRecycler.adapter = studentListAdapter
                            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show()
                            noDataTV.visibility = View.VISIBLE
                        } else {
                            studentRecycler.layoutManager = linearLayoutManager
                            val studentListAdapter = ReEnrolStudentListAdapter(context, reEnrolList)
                            studentRecycler.adapter = studentListAdapter
                            noDataTV.visibility = View.GONE
                        }
                    } else {
                        noDataTV.visibility = View.VISIBLE
                        studentRecycler.visibility = View.GONE
                    }

                } else {
                    Toast.makeText(
                        context,
                        "Fail to get the data..",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<ReEnrolmentListResponseModel>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun initialiseUI() {
        back = findViewById(R.id.back)
        noDataTV = findViewById(R.id.noDataTV)
        textView = findViewById(R.id.titleTV)
        studentRecycler = findViewById(R.id.studentRecycler)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        progressBarDialog = ProgressBarDialog(context)
        if (PreferenceManager().getLanguage(context).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(context.assets, "font/times_new_roman.ttf")
            textView.typeface = face
        }

        studentRecycler.addOnItemTouchListener(
            RecyclerItemListener(
                context, studentRecycler,
                object : RecyclerItemListener.RecyclerTouchListener {
                    override fun onClickItem(v: View?, position: Int) {
                        if (reEnrolList[position].isReEnrollment == 0) {
                            Toast.makeText(
                                context,
                                resources.getString(R.string.re_enrolment_not_available),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (reEnrolList[position].selectedOption == "") {
                                showReEnrolPopUp(reEnrolList[position])
                            } else {
                                Toast.makeText(
                                    context,
                                    resources.getString(R.string.re_enrolment_submitted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onLongClickItem(v: View?, position: Int) {}
                })
        )
        back.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showReEnrolPopUp(reEnrollmentData: ReEnrolmentListResponseModel.ReEnrollment) {
        val dialog = Dialog(context)
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
        val optionsArray: ArrayList<String> = reEnrollmentData.reEnrollmentData.options
        stud_name.text = reEnrollmentData.fullName
        stud_class.text = reEnrollmentData.className
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        checkButton.setOnCheckedChangeListener { buttonView, isChecked ->
            check = isChecked
        }
        sub_btn.setOnClickListener {
            if (selectedStatus.isEmpty()) {
                Toast.makeText(context, "Please select the option", Toast.LENGTH_SHORT).show()
            } else {
                if (check) {
                    callReEnrolSubmitAPI(dialog, reEnrollmentData.id)
                } else {
                    Toast.makeText(context, "Please check the box", Toast.LENGTH_SHORT).show()
                }
            }

        }
        if (!PreferenceManager().getStudentPhoto(context)!!.isEmpty()) {
            studentImg = PreferenceManager().getStudentPhoto(context).toString()
            if (studentImg != null && !studentImg.isEmpty()) {
                val glideUrl = GlideUrl(
                    studentImg,
                    LazyHeaders.Builder()
                        .addHeader(
                            "Authorization",
                            "Bearer " + PreferenceManager().getAccessToken(context).toString()
                        )
                        .build()
                )
                Glide.with(context)
                    .load(glideUrl)
                    .transform(CircleCrop()) // Apply circular transformation
                    .placeholder(R.drawable.profile_photo) // Placeholder image while loading
                    .error(R.drawable.profile_photo) // Image to display in case of error
                    .into(stud_img)
            } else {
                Toast.makeText(context, "Image empty", Toast.LENGTH_SHORT).show()
                // Handle the case when studentImg is null or empty
            }
        } else {
            imagicon.setImageResource(R.drawable.profile_photo)
            // Set default circular image resource
        }
        headingTV.text = reEnrollmentData.reEnrollmentData.title
        descriptionTV.text = reEnrollmentData.reEnrollmentData.description
//        val calendar = Calendar.getInstance()
//        val sdf = SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault())
//        val currentDate = sdf.format(calendar.time)
//        dateTV.text = currentDate
        questionTextView.text = reEnrollmentData.reEnrollmentData.question
        dropdownList.add(0, "Please Select")
        for (i in 1..optionsArray.size) {
            dropdownList.add(i, optionsArray[i - 1])
        }
        val sp_adapter = ArrayAdapter<String>(
            context, R.layout.spinner_textview, dropdownList
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
        val call: Call<ContactTeacherResponseModel> = ApiClient.getApiService().submitReEnrolment(
            "Bearer " +
                    PreferenceManager().getAccessToken(context).toString(), paramObject
        )
        call.enqueue(object : retrofit2.Callback<ContactTeacherResponseModel> {
            override fun onResponse(
                call: Call<ContactTeacherResponseModel>,
                response: Response<ContactTeacherResponseModel>
            ) {
                progressBarDialog.dismiss()
                if (response.body()!!.status == "100") {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.re_enrolment_submit_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    d.dismiss()

                } else {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.re_enrolment_submit_fail),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                reEnrolmentDetailsAPICall()
            }

            override fun onFailure(call: Call<ContactTeacherResponseModel>, t: Throwable) {
                progressBarDialog.dismiss()
                Toast.makeText(
                    context,
                    "Fail to get the data..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}