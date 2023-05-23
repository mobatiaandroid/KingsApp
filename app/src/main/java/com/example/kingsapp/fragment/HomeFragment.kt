package com.example.kingsapp.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.kingsapp.BuildConfig
import com.example.kingsapp.R
import com.example.kingsapp.activities.absence.AbsenceActivity
import com.example.kingsapp.activities.adapter.apps.AppsActivity
import com.example.kingsapp.activities.calender.SchoolCalendarActivity
import com.example.kingsapp.activities.early_pickup.EarlyPickupListActivity
import com.example.kingsapp.activities.forms.FormsActivity
import com.example.kingsapp.activities.home.HomeActivity
import com.example.kingsapp.activities.home.model.HomeUserResponseModel
import com.example.kingsapp.activities.message.MessageFragment
import com.example.kingsapp.activities.parentessentials.ParentEssentialsActivity
import com.example.kingsapp.activities.reports.ReportsActivity
import com.example.kingsapp.activities.social_media.SocialMediaActivity
import com.example.kingsapp.activities.student_info.StudentInfoActivity
import com.example.kingsapp.activities.student_planner.StudentPlannerActivity
import com.example.kingsapp.activities.timetable.TimeTableActivity
import com.example.kingsapp.manager.AppController
import com.example.kingsapp.manager.NaisClassNameConstants
import com.example.kingsapp.manager.NaisTabConstants
import com.example.kingsapp.manager.PreferenceManager
import com.mobatia.nasmanila.api.ApiClient
import retrofit2.Call
import retrofit2.Response
import java.util.*

private var mTxtOne: TextView? = null
private var mTxtTwo: TextView? = null
private var mTxtThree: TextView? = null
private var mTxtFour: TextView? = null
private var mTxtFive: TextView? = null
private var mTxtSix: TextView? = null
private var mTxtSeven: TextView? = null


private var mImgOne: ImageView? = null
private var mImgTwo: ImageView? = null
private var mImgThree: ImageView? = null
private var mImgFour: ImageView? = null
private var mImgFive: ImageView? = null
private var mImgSix: ImageView? = null
private var mImgSeven: ImageView? = null

private var home_logo_image_arab : ImageView?= null
private var home_logo_image : ImageView?= null

private var mRelOne: RelativeLayout? = null
private var mRelTwo: RelativeLayout? = null
private var mRelThree: RelativeLayout? = null
private var mRelFour: RelativeLayout? = null
private var mRelFive: RelativeLayout? = null
private var mRelSix: RelativeLayout? = null
private var mRelSeven: RelativeLayout? = null

var isDraggable: Boolean = false
var key:String=" "
lateinit var homeActivity: HomeActivity
lateinit var mSectionText: Array<String?>
lateinit var mContext:Context
lateinit var classNameConstants: NaisClassNameConstants
lateinit var naisTabConstants: NaisTabConstants
lateinit var mListImgArrays: TypedArray
lateinit var mListItemArray:Array<String>
lateinit var TouchedView: View
private var TAB_ID: String = ""

lateinit var appController: AppController
var versionfromapi: String = ""
var currentversion: String = ""
@Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "CAST_NEVER_SUCCEEDS",
    "ControlFlowWithEmptyBody"
)
class HomeFragment  : Fragment(),View.OnClickListener{
    lateinit var rootView: View
    private var INTENT_TAB_ID: String? = null
    private var CLICKED: String = ""
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView= inflater.inflate(R.layout.home_screen_fragments, container, false)
        mContext=requireContext()
        homeActivity = activity as HomeActivity
        appController = AppController()
        classNameConstants = NaisClassNameConstants()
        naisTabConstants = NaisTabConstants()
        mListItemArray = resources.getStringArray(R.array.home_list_items)
        mListImgArrays = mContext.resources.obtainTypedArray(R.array.home_list_reg_icons)
        mContext=requireContext()
        currentversion = BuildConfig.VERSION_NAME
        initFn()

        setListeners()
        setdraglisteners()
        getButtonBgAndTextImages()


        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }




    private fun setListeners() {
        mRelOne!!.setOnClickListener(this)
        mRelTwo!!.setOnClickListener(this)
        mRelThree!!.setOnClickListener(this)
        mRelFour!!.setOnClickListener(this)
        mRelFive!!.setOnClickListener(this)
        mRelSix!!.setOnClickListener(this)
        mRelSeven!!.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v == mRelOne) {

            INTENT_TAB_ID = PreferenceManager().getbuttononetabid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelTwo) {

            INTENT_TAB_ID = PreferenceManager().getbuttontwotabid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
           CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelThree) {

            INTENT_TAB_ID = PreferenceManager().getbuttonthreetabid(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelFour) {

            INTENT_TAB_ID = PreferenceManager().getbuttonfourt_abid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelFive) {

            INTENT_TAB_ID = PreferenceManager().getbuttonfivetabid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelSix) {

            INTENT_TAB_ID = PreferenceManager().getbuttonsixtabid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }
        if (v == mRelSeven) {

            INTENT_TAB_ID = PreferenceManager().getbuttonseventabid(mContext).toString()
            Log.e("ErrorTAbild", PreferenceManager().getbuttonfourt_abid(mContext).toString())
            CHECKINTENTVALUE(INTENT_TAB_ID!!)
        }

    }

    private fun CHECKINTENTVALUE(intentTabId: String) {
        TAB_ID = intentTabId
        var mFragment: Fragment? = null
       // Log.e("Student Id",PreferenceManager().getStudentID(mContext).toString())
        Log.e("intentTabId",TAB_ID)

        if(PreferenceManager().getAccessToken(mContext).equals("")) {
            when (intentTabId) {
                naisTabConstants.TAB_STUDENT_INFORMATION -> {
                    // Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                }


                naisTabConstants.TAB_CALENDAR -> {
                    //Toast.makeText(mContext, "frg2", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                    /*Log.e("EmailHelp","EmailHelp")
                   */
                }

            naisTabConstants.TAB_CALENDAR -> {
                //Toast.makeText(mContext, "frg2", Toast.LENGTH_SHORT).show()
                PreferenceManager().setFromYearView(mContext,"0")
                val intent = Intent(mContext, SchoolCalendarActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                /*Log.e("EmailHelp","EmailHelp")
               */
            }


                naisTabConstants.TAB_MESSAGES -> {
                    // Toast.makeText(mContext, "frg3", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }
                naisTabConstants.TAB_COMMUNICATION -> {
                    // Toast.makeText(mContext, "frg4", Toast.LENGTH_SHORT).show()

                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                    /* mFragment = CommunicationFragment()
                     fragmentIntent(mFragment)*/
                }
                naisTabConstants.TAB_REPORT_ABSENCE -> {
                    // Toast.makeText(mContext, "frg5", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }

                /*naisTabConstants.TAB_SOCIAL_MEDIA -> {
                    mFragment = SocialMediaFragment()
                    fragmentIntent(mFragment)
                }*/
                naisTabConstants.TAB_TIME_TABLE -> {
                    // Toast.makeText(mContext, "frg7", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()

                    /* showSuccessAlert(
                         mContext,
                         "This feature is only available for registered users.",
                         "Alert"
                     )*/
                }
                naisTabConstants.TAB_REPORTS -> {
                    // Toast.makeText(mContext, "frg8", Toast.LENGTH_SHORT).show()
                    // Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }

                naisTabConstants.TAB_SOCIAL_MEDIA -> {
                    // Toast.makeText(mContext, "frg8", Toast.LENGTH_SHORT).show()
                    // Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()
                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }
//                naisTabConstants.TAB_ATTENDANCE -> {
//                    showSuccessAlert(
//                        mContext,
//                        "This feature is only available for registered users.",
//                        "Alert"
//                    )
//                }
                naisTabConstants.TAB_CONTACT_US -> {
                    //  Toast.makeText(mContext, "frg9", Toast.LENGTH_SHORT).show()

                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }


                naisTabConstants.TAB_EARLYPICKUP -> {
                    //  Toast.makeText(mContext, "frg9", Toast.LENGTH_SHORT).show()

                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }

                naisTabConstants.TAB_APPS -> {
                    //  Toast.makeText(mContext, "frg12", Toast.LENGTH_SHORT).show()

                    Toast.makeText(mContext,"This feature is only available for registered users.",
                        Toast.LENGTH_SHORT).show()

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }naisTabConstants.TAB_FORMS -> {
                // Toast.makeText(mContext, "frg13", Toast.LENGTH_SHORT).show()
                Toast.makeText(mContext,"This feature is only available for registered users.",
                    Toast.LENGTH_SHORT).show()

                /* showSuccessAlert(
                     mContext,
                     "This feature is only available for registered users.",
                     "Alert"
                 )*/
            }
            }
        }

        else
        {
            when (intentTabId) {
                naisTabConstants.TAB_STUDENT_INFORMATION -> {
                    // Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                    val intent = Intent(mContext, StudentInfoActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }

                naisTabConstants.TAB_CALENDAR -> {
                    //Toast.makeText(mContext, "frg2", Toast.LENGTH_SHORT).show()
                    PreferenceManager().setFromYearView(mContext,"0")
                    val intent = Intent(mContext, SchoolCalendarActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    /*Log.e("EmailHelp","EmailHelp")
                   */
                }

                naisTabConstants.TAB_MESSAGES -> {
                    // Toast.makeText(mContext, "frg3", Toast.LENGTH_SHORT).show()

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }
                naisTabConstants.TAB_COMMUNICATION -> {
                    // Toast.makeText(mContext, "frg4", Toast.LENGTH_SHORT).show()

                    val intent = Intent(mContext, ParentEssentialsActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    /* mFragment = CommunicationFragment()
                     fragmentIntent(mFragment)*/
                }
                naisTabConstants.TAB_REPORT_ABSENCE -> {
                    // Toast.makeText(mContext, "frg5", Toast.LENGTH_SHORT).show()
                    val intent = Intent(mContext, AbsenceActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }

                naisTabConstants.TAB_EARLYPICKUP -> {
                    val intent = Intent(mContext, EarlyPickupListActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }

                naisTabConstants.TAB_SOCIAL_MEDIA -> {
                    val intent = Intent(mContext, SocialMediaActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                naisTabConstants.TAB_TIME_TABLE -> {
                    // Toast.makeText(mContext, "frg7", Toast.LENGTH_SHORT).show()
                    val intent = Intent(mContext, TimeTableActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    /* showSuccessAlert(
                         mContext,
                         "This feature is only available for registered users.",
                         "Alert"
                     )*/
                }
                naisTabConstants.TAB_REPORTS -> {
                    // Toast.makeText(mContext, "frg8", Toast.LENGTH_SHORT).show()
                    // Toast.makeText(mContext, "Coming Soon", Toast.LENGTH_SHORT).show()
                    val intent = Intent(mContext, ReportsActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }
//                naisTabConstants.TAB_ATTENDANCE -> {
//                    showSuccessAlert(
//                        mContext,
//                        "This feature is only available for registered users.",
//                        "Alert"
//                    )
//                }
                naisTabConstants.TAB_CONTACT_US -> {
                    //  Toast.makeText(mContext, "frg9", Toast.LENGTH_SHORT).show()



                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }




                naisTabConstants.TAB_APPS -> {
                    //  Toast.makeText(mContext, "frg12", Toast.LENGTH_SHORT).show()

                    val intent = Intent(mContext, AppsActivity::class.java)
                    startActivity(intent)
                    requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                    /*showSuccessAlert(
                        mContext,
                        "This feature is only available for registered users.",
                        "Alert"
                    )*/
                }naisTabConstants.TAB_FORMS -> {
                // Toast.makeText(mContext, "frg13", Toast.LENGTH_SHORT).show()
                val intent = Intent(mContext, FormsActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);

                /* showSuccessAlert(
                     mContext,
                     "This feature is only available for registered users.",
                     "Alert"
                 )*/
            }
            }
        }

        //}

    }
    private fun getButtonBgAndTextImages() {
        if (PreferenceManager()
                .getbuttononetextimage(mContext)!!.toInt() != 0
        ) {
            mImgOne!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttononetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                if (mListItemArray[PreferenceManager()
                        .getbuttononetextimage(mContext)!!.toInt()].equals(
                        classNameConstants.CCAS,
                        ignoreCase = true
                    )
                ) {
                    classNameConstants.CCAS
                } else if (mListItemArray[PreferenceManager()
                        .getbuttononetextimage(mContext)!!.toInt()].equals(
                        classNameConstants.STUDENT_INFORMATION,
                        ignoreCase = true
                    )
                ) {
                    classNameConstants.STUDENT_INFORMATION
                } else {
                    mListItemArray[PreferenceManager()
                        .getbuttononetextimage(mContext)!!.toInt()]
                }
            mTxtOne!!.text = relTwoStr
            mTxtOne!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelOne!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonOneGuestBg(mContext)
            )
        }
        if (PreferenceManager().getbuttontwotextimage(mContext)!!.toInt() != 0
        ) {
            mImgTwo!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttontwotextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr =
                if (mListItemArray[PreferenceManager()
                        .getbuttontwotextimage(mContext)!!.toInt()].equals(
                        classNameConstants.CCAS,
                        ignoreCase = true
                    )
                ) {
                    classNameConstants.CCAS
                } else if (mListItemArray[PreferenceManager()
                        .getbuttontwotextimage(mContext)!!.toInt()].equals(
                        classNameConstants.STUDENT_INFORMATION,
                        ignoreCase = true
                    )
                ) {
                    classNameConstants.STUDENT_INFORMATION
                } else {
                    mListItemArray[PreferenceManager()
                        .getbuttontwotextimage(mContext)!!.toInt()]
                }
            mTxtTwo!!.text = relTwoStr
            mTxtTwo!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelTwo!!.setBackgroundColor(
                PreferenceManager()
                    .getButtontwoGuestBg(mContext)
            )
        }
        if (PreferenceManager()
                .getbuttonthreetextimage(mContext)!!.toInt() != 0
        ) {
            mImgThree!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttonthreetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = if (mListItemArray[PreferenceManager()
                    .getbuttonthreetextimage(mContext)!!.toInt()].equals(
                    classNameConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                classNameConstants.CCAS
            } else if (mListItemArray[PreferenceManager()
                    .getbuttonthreetextimage(mContext)!!.toInt()].equals(
                    classNameConstants.STUDENT_INFORMATION,
                    ignoreCase = true
                )
            ) {
                classNameConstants.STUDENT_INFORMATION
            } else {
                mListItemArray[PreferenceManager()
                    .getbuttonthreetextimage(mContext)!!.toInt()]
            }
            mTxtThree!!.text = relTwoStr
            mTxtThree!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelThree!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonthreeGuestBg(mContext)
            )
        }


        if (PreferenceManager()
                .getbuttonfourtextimage(mContext)!!.toInt() != 0
        ) {
            mImgFour!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttonfourtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = if (mListItemArray[PreferenceManager()
                    .getbuttonfourtextimage(mContext)!!.toInt()].equals(
                    classNameConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                classNameConstants.CCAS
            } else if (mListItemArray[PreferenceManager()
                    .getbuttonfourtextimage(mContext)!!.toInt()].equals(
                    classNameConstants.STUDENT_INFORMATION,
                    ignoreCase = true
                )
            ) {
                classNameConstants.STUDENT_INFORMATION
            } else {
                mListItemArray[PreferenceManager()
                    .getbuttonfourtextimage(mContext)!!.toInt()]
            }
            mTxtFour!!.text = relTwoStr
            mTxtFour!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelFour!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonfourGuestBg(mContext)
            )
        }


        if (PreferenceManager()
                .getbuttonfivetextimage(mContext)!!.toInt() != 0
        ) {
            mImgFive!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttonfivetextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = if (mListItemArray[PreferenceManager()
                    .getbuttonfivetextimage(mContext)!!.toInt()].equals(
                    classNameConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                classNameConstants.CCAS
            } else if (mListItemArray[PreferenceManager()
                    .getbuttonfivetextimage(mContext)!!.toInt()].equals(
                    classNameConstants.STUDENT_INFORMATION,
                    ignoreCase = true
                )
            ) {
                classNameConstants.STUDENT_INFORMATION
            } else {
                mListItemArray[PreferenceManager()
                    .getbuttonfivetextimage(mContext)!!.toInt()]
            }
            mTxtFive!!.text = relTwoStr
            mTxtFive!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelFive!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonfiveGuestBg(mContext)
            )
        }
        if (PreferenceManager().getbuttonsixtextimage(mContext)!!.toInt() != 0) {
            mImgSix!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttonsixtextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = if (mListItemArray[PreferenceManager()
                    .getbuttonsixtextimage(mContext)!!.toInt()].equals(
                    classNameConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                classNameConstants.CCAS
            } else if (mListItemArray[PreferenceManager()
                    .getbuttonsixtextimage(mContext)!!.toInt()].equals(
                    classNameConstants.STUDENT_INFORMATION,
                    ignoreCase = true
                )
            ) {
                classNameConstants.STUDENT_INFORMATION
            } else {
                mListItemArray[PreferenceManager()
                    .getbuttonsixtextimage(mContext)!!.toInt()]
            }
            mTxtSix!!.text = relTwoStr
            mTxtSix!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelSix!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonsixGuestBg(mContext)
            )
        }
        if (PreferenceManager()
                .getbuttonseventextimage(mContext)!!.toInt() != 0
        ) {
            mImgSeven!!.setImageDrawable(
                mListImgArrays.getDrawable(
                    PreferenceManager()
                        .getbuttonseventextimage(mContext)!!.toInt()
                )
            )
            var relTwoStr: String? = ""
            relTwoStr = if (mListItemArray[PreferenceManager()
                    .getbuttonseventextimage(mContext)!!.toInt()].equals(
                    classNameConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                classNameConstants.CCAS
            } else {
                mListItemArray[PreferenceManager()
                    .getbuttonseventextimage(mContext)!!.toInt()]
            }
            mTxtSeven!!.text = relTwoStr
            mTxtSeven!!.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            mRelSeven!!.setBackgroundColor(
                PreferenceManager()
                    .getButtonsevenGuestBg(mContext)
            )
        }


        /* mImgOne!!.background = mContext!!.resources.getDrawable(R.drawable.calendar)
         mTxtOne!!.text = "CALENDER"
         mImgFive!!.background = mContext!!.resources.getDrawable(R.drawable.earlyyears)
         mTxtFive!!.text= "EARLY YEARS"
         mImgSix!!.background = mContext!!.resources.getDrawable(R.drawable.primary)
         mTxtSix!!.text= "PRIMARY"
         mImgTwo!!.background = mContext!!.resources.getDrawable(R.drawable.secondary)
         mTxtTwo!!.text= "SECONDARY"
         mImgSeven!!.background = mContext!!.resources.getDrawable(R.drawable.communication)
         mTxtSeven!!.text= "COMMUNICATION"
         mImgEight!!.background = mContext!!.resources.getDrawable(R.drawable.aboutus)
         mTxtEight!!.text= "ABOUT US"
         mImgThree!!.background = mContext!!.resources.getDrawable(R.drawable.gallery)
         mTxtThree!!.text= "GALLERY"
         mImgFour!!.background = mContext!!.resources.getDrawable(R.drawable.arts)
         mTxtFour!!.text= "PERFORMING"
         mImgNine!!.background = mContext!!.resources.getDrawable(R.drawable.sports)
         mTxtNine!!.text= "SPORTS"*/

    }

    private fun initFn() {

        CLICKED = homeActivity.sPosition.toString()
        home_logo_image_arab =rootView. findViewById<View>(R.id.home_logo_image_arab) as ImageView
        home_logo_image =rootView. findViewById<View>(R.id.home_logo_image) as ImageView

        mRelOne = rootView.findViewById<View>(R.id.relOne) as? RelativeLayout?
        mRelTwo = rootView.findViewById<View>(R.id.relTwo) as? RelativeLayout
        mRelThree = rootView.findViewById<View>(R.id.relThree) as? RelativeLayout
        mRelFour = rootView.findViewById<View>(R.id.relFour) as? RelativeLayout
        mRelFive = rootView.findViewById<View>(R.id.relFive) as? RelativeLayout
        mRelSix = rootView.findViewById<View>(R.id.relEight) as? RelativeLayout
        mRelSeven = rootView.findViewById<View>(R.id.relSeven) as? RelativeLayout
        mTxtOne = rootView.findViewById<View>(R.id.relTxtOne) as? TextView?
        mImgOne = rootView.findViewById<View>(R.id.relImgOne) as? ImageView?
        mTxtTwo = rootView.findViewById<View>(R.id.relTxtTwo) as? TextView
        mImgTwo = rootView.findViewById<View>(R.id.relImgTwo) as? ImageView
        mTxtThree = rootView.findViewById<View>(R.id.relTxtThree) as? TextView
        mImgThree = rootView.findViewById<View>(R.id.relImgThree) as? ImageView
        mTxtFour = rootView.findViewById<View>(R.id.relTxtFour) as? TextView
        mImgFour = rootView.findViewById<View>(R.id.relImgFour) as? ImageView
        mTxtFive = rootView.findViewById<View>(R.id.relTxtFive) as? TextView
        mImgFive = rootView.findViewById<View>(R.id.relImgFive) as? ImageView
        mTxtSix = rootView.findViewById<View>(R.id.relTxtEight) as? TextView
        mImgSix = rootView.findViewById<View>(R.id.relImgEight) as? ImageView
        mTxtSeven = rootView.findViewById<View>(R.id.relTxtSeven) as? TextView
        mImgSeven = rootView.findViewById<View>(R.id.relImgSeven) as? ImageView
        mSectionText = arrayOfNulls(7)
        if(PreferenceManager().getLanguage(mContext).equals("ar")) {
            home_logo_image_arab!!.visibility = View.VISIBLE
            home_logo_image!!.visibility = View.GONE
                val face: Typeface =
                    Typeface.createFromAsset(mContext.getAssets(), "font/times_new_roman.ttf")
            mTxtOne!!.setTypeface(face);
            mTxtTwo!!.setTypeface(face);
            mTxtThree!!.setTypeface(face);
            mTxtFour!!.setTypeface(face);
            mTxtFive!!.setTypeface(face);
            mTxtSix!!.setTypeface(face);
            mTxtSeven!!.setTypeface(face);

        }else
        {
            home_logo_image_arab!!.visibility=View.GONE
            home_logo_image!!.visibility=View.VISIBLE
        }

    }
    private fun setdraglisteners() {
Log.e("Sucesss","Sucbnbfhjdevcess")
        mRelOne!!.setOnDragListener(DropListener())
        mRelTwo!!.setOnDragListener(DropListener())
        mRelThree!!.setOnDragListener(DropListener())
        mRelFour!!.setOnDragListener(DropListener())
        mRelFive!!.setOnDragListener(DropListener())
        mRelSix!!.setOnDragListener(DropListener())
        mRelSeven!!.setOnDragListener(DropListener())

    }
    @Suppress("EqualsBetweenInconvertibleTypes")
    class DropListener : View.OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            Log.e("inside drag class", event.toString())
            Log.e("v", v.toString())

            when (event?.action) {

                DragEvent.ACTION_DRAG_STARTED -> Log.d("DRAG", "START")
                DragEvent.ACTION_DRAG_ENTERED -> Log.d("DRAG", "ENTERED")
                DragEvent.ACTION_DRAG_EXITED -> Log.d("DRAG", "EXITED")
                DragEvent.ACTION_DROP -> {
                    Log.e("Sucess","Success")
                    val intArray = IntArray(2)
                    v?.getLocationInWindow(intArray)
                    val x = intArray[0]
                    val y = intArray[1]
                    val sPosition = homeActivity.sPosition
                    getButtonViewTouched(x.toFloat().toInt(), y.toFloat().toInt())
                   /* mSectionText[0] = mTxtOne!!.setText(R.string.SchoolCalender).toString()
                    mSectionText[1] = mTxtTwo!!.setText(R.string.StudentInfo).toString()
                    mSectionText[2] = mTxtThree!!.setText(R.string.Canteen).toString()
                    mSectionText[3] = mTxtFour!!.setText(R.string.Payments).toString()
                    mSectionText[4] = mTxtFive!!.setText(R.string.Timetable).toString()
                    mSectionText[5] = mTxtSix!!.setText(R.string.Absencereporting).toString()
                    mSectionText[6] = mTxtSeven!!.setText(R.string.form).toString()*/

                    mSectionText[0] = mTxtOne!!.text.toString()
                    mSectionText[1] = mTxtTwo!!.text.toString()
                    mSectionText[2] = mTxtThree!!.text.toString()
                    mSectionText[3] = mTxtFour!!.text.toString()
                    mSectionText[4] = mTxtFive!!.text.toString()
                    mSectionText[5] = mTxtSix!!.text.toString()
                    mSectionText[6] = mTxtSeven!!.text.toString()


                    for (i in mSectionText.indices) {
                        isDraggable = true
                        if (mSectionText[i].equals(
                                mListItemArray[homeActivity.sPosition],
                                ignoreCase = true
                            )
                        ) {
                            Log.e("position",mListItemArray[homeActivity.sPosition])
                            isDraggable = false
                            key="0"
                            break
                        }
                     else if(mListItemArray[homeActivity.sPosition].equals("Parent Comms"))
                        {
                            isDraggable = false
                            key="1"
                            break
                        }

                        else if(mListItemArray[homeActivity.sPosition].equals("Contact Us"))
                        {
                            isDraggable = false
                            key="1"
                            break
                        }
                    }
                    if (isDraggable) {
                        getButtonDrawablesAndText(TouchedView, homeActivity.sPosition)

                    } else {

                        if (key.equals("1"))
                        {
                            Toast.makeText(mContext, "This Feature cannot be added to the Home Screen !!!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else
                        {
                            Toast.makeText(mContext, "Item Already Exists !!!", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }

                }
                DragEvent.ACTION_DRAG_ENDED -> Log.d("DRAG", "END")


            }


            return true

        }

        private fun getButtonDrawablesAndText(touchedView: View, sPosition: Int) {
            if (sPosition != 0) {
                Log.e("TabSuccess","mRelOne")
                if (touchedView == mRelOne) {
                    Log.e("TabSuccess","mRelOne")
                    mImgOne!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtOne!!.text = relstring
                    Log.e("TabSuccess","des")

                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttononetabid(mContext, TAB_ID)
                    Log.e("dragtabid",TAB_ID)
                    //setBackgroundColorForView(appController.listitemArrays[sPosition], relone)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelOne!!)
                    PreferenceManager().setbuttononetextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelTwo) {
                    mImgTwo!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtTwo!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttontwotabid(mContext, TAB_ID)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelTwo!!)
                    PreferenceManager().setbuttontwotextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelThree) {
                    mImgThree!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtThree!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    Log.e("beforesetdragtabid3",TAB_ID)
                    PreferenceManager().setbuttonthreetabid(mContext, TAB_ID)
                    Log.e("dragtabid3",TAB_ID)

                    setBackgroundColorForView(mListItemArray[sPosition], mRelThree!!)
                    PreferenceManager().setbuttonthreetextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelFour) {
                    mImgFour!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtFour!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttonfourtabid(mContext, TAB_ID)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelFour!!)
                    PreferenceManager().setbuttonfourtextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelFive) {
                    mImgFive!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtFive!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttonfivetabid(mContext, TAB_ID)
                    Log.e("setIntentId",TAB_ID)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelFive!!)
                    PreferenceManager().setbuttonfivetextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelSix) {
                    mImgSix!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtSix!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttonsixtabid(mContext, TAB_ID)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelSix!!)
                    PreferenceManager().setbuttonsixtextimage(mContext, sPosition.toString())
                } else if (touchedView == mRelSeven) {
                    mImgSeven!!.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (mListItemArray[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else if (mListItemArray[sPosition] == "Student Information") {
                        relstring = "Student Information"
                    } else {
                        relstring = mListItemArray[sPosition]
                    }
                    mTxtSeven!!.text = relstring
                    getTabId(mListItemArray[sPosition])
                    PreferenceManager().setbuttonseventabid(mContext, TAB_ID)
                    setBackgroundColorForView(mListItemArray[sPosition], mRelSeven!!)
                    PreferenceManager().setbuttonseventextimage(mContext, sPosition.toString())
                }

            }
        }

        private fun setBackgroundColorForView(s: String, v: View) {
            if (v == mRelOne) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelTwo) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelThree) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelFour) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelFive) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelSix) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == mRelSeven) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            }
        }

        private fun saveButtonBgColor(v: View, color: Int) {
            if (v == mRelOne) {
                PreferenceManager().setButtonOneGuestBg(mContext, color)
            } else if (v == mRelTwo) {
                PreferenceManager().setButtontwoGuestBg(mContext, color)
            } else if (v == mRelThree) {
                PreferenceManager().setButtonthreeGuestBg(mContext, color)
            } else if (v == mRelFour) {
                PreferenceManager().setButtonfourGuestBg(mContext, color)
            } else if (v == mRelFive) {
                PreferenceManager().setButtonfiveGuestBg(mContext, color)
            } else if (v == mRelSix) {
                PreferenceManager().setButtonsixGuestBg(mContext, color)
            } else if (v == mRelSeven) {
                PreferenceManager().setButtonsevenGuestBg(mContext, color)
            }

        }

        private fun getTabId(textdata: String) {
            Log.e("TabSuccess",textdata)
            when {

                textdata.equals(classNameConstants.STUDENT_INFORMATION) -> {
                    TAB_ID = naisTabConstants.TAB_STUDENT_INFORMATION
                    Log.e("TabId", TAB_ID)

                }
                textdata.equals("Student Information") -> {
                    TAB_ID = naisTabConstants.TAB_STUDENT_INFORMATION

                }


                textdata.equals(classNameConstants.ABSENCE_REPORTING, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_REPORT_ABSENCE
                }
                textdata.equals(classNameConstants.SCHOOL_CALENDER, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_CALENDAR
                }
                textdata.equals(classNameConstants.EARLYPICKUP, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_EARLYPICKUP
                }
                textdata.equals(classNameConstants.SOCIAL_MEDIA, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_SOCIAL_MEDIA
                }
                textdata.equals(classNameConstants.TIME_TABL, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_TIME_TABLE
                }
                textdata.equals(classNameConstants.PARENT_ESSENTIAL, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_COMMUNICATION
                }


                textdata.equals(classNameConstants.FORMS, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_FORMS

                }
                textdata.equals(classNameConstants.APPS, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_APPS

                }
                textdata.equals(classNameConstants.REPORTS, ignoreCase = true) -> {
                    TAB_ID = naisTabConstants.TAB_REPORTS

                }
//                textdata.equals(classNameConstants.ATTENDANCE, ignoreCase = true) -> {
//                    TAB_ID = naisTabConstants.TAB_ATTENDANCE
//
//                }


            }

        }

        private fun getButtonViewTouched(centerX: Int, centerY: Int) {
            val array1 = IntArray(2)
            mRelOne!!.getLocationInWindow(array1)
            val x1: Int = array1[0]
            val x2 = x1 + mRelOne!!.width
            val y1: Int = array1[1]
            val y2 = y1 + mRelOne!!.height

            val array2 = IntArray(2)
            mRelTwo!!.getLocationInWindow(array2)
            val x3: Int = array2[0]
            val x4 = x3 + mRelTwo!!.height
            val y3: Int = array2[1]
            val y4 = y3 + mRelTwo!!.height

            val array3 = IntArray(2)
            mRelThree!!.getLocationInWindow(array3)
            val x5: Int = array3[0]
            val x6 = x5 + mRelThree!!.width
            val y5: Int = array3[1]
            val y6 = y5 + mRelThree!!.height

            val array4 = IntArray(2)
            mRelFour!!.getLocationInWindow(array4)
            val x7: Int = array4[0]
            val x8 = x7 + mRelFour!!.width
            val y7: Int = array4[1]
            val y8 = y7 + mRelFour!!.height

            val array5 = IntArray(2)
            mRelFive!!.getLocationInWindow(array5)
            val x9: Int = array5[0]
            val x10 = x9 + mRelFive!!.width
            val y9: Int = array5[1]
            val y10 = y9 + mRelFive!!.height

            val array6 = IntArray(2)
            mRelSix!!.getLocationInWindow(array6)
            val x11: Int = array6[0]
            val x12 = x11 + mRelSix!!.width
            val y11: Int = array6[1]
            val y12 = y11 + mRelSix!!.height

            val array7 = IntArray(2)
            mRelSeven!!.getLocationInWindow(array7)
            val x13: Int = array7[0]
            val x14 = x13 + mRelSeven!!.width
            val y13: Int = array7[1]
            val y14 = y13 + mRelSeven!!.height





            if (centerX in x1..x2 && y1 <= centerY && centerY <= y2) {
                TouchedView = mRelOne!!
            } else if (centerX in x3..x4 && y3 <= centerY && centerY <= y4) {
                TouchedView = mRelTwo!!
            } else if (centerX in x5..x6 && y5 <= centerY && centerY <= y6) {
                TouchedView = mRelThree!!
            } else if (centerX in x7..x8 && y7 <= centerY && centerY <= y8) {
                TouchedView = mRelFour!!
            } else if (centerX in x9..x10 && y9 <= centerY && centerY <= y10) {
                TouchedView = mRelFive!!
            } else if (centerX in x11..x12 && y11 <= centerY && centerY <= y12) {
                TouchedView = mRelSix!!
            } else if (centerX in x13..x14 && y13 <= centerY && centerY <= y14) {
                TouchedView = mRelSeven!!
            }

        }

    }
    fun fragmentIntent(mFragment: Fragment?) {
        if (mFragment != null) {
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.frame_container, mFragment, appController.mTitles)
                .addToBackStack(appController.mTitles).commitAllowingStateLoss() //commit
            //.commit()
        }
    }
    fun showSuccessAlert(context: Context, message: String, msgHead: String) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_dialogue_ok_layout)
      //  var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as TextView
        text_dialog.text = message
        alertHead.text = msgHead
       // iconImageView.setImageResource(R.color.white)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()


        }
        dialog.show()
    }
    fun showEmailHelpAlert(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.alert_send_email_dialog)
        //  var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        val dialogCancelButton = dialog.findViewById<View>(R.id.cancelButton) as TextView
        val submitButton = dialog.findViewById<View>(R.id.submitButton) as TextView
       val text_dialog = dialog.findViewById<View>(R.id.text_dialog) as EditText
       val text_content = dialog.findViewById<View>(R.id.text_content) as EditText

        //  text_dialog.text = message
       // alertHead.text = msgHead
        // iconImageView.setImageResource(R.color.white)
        dialogCancelButton.setOnClickListener { //   AppUtils.hideKeyBoard(mContext);
            val imm =
                mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(text_dialog.windowToken, 0)
            imm.hideSoftInputFromWindow(text_content.windowToken, 0)
            dialog.dismiss()
        }
        dialog.show()
    }
}
