package com.kingseducation.app.fragment.absence

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingseducation.app.R


/**
 * Created by Arshad on 29,August,2022
 */
class AbsenceFragment :Fragment(){
    private var mContext: Context? = null
    lateinit var weburl: TextView
    lateinit var titleTextView: TextView
    var ccaOption: RelativeLayout? = null
    lateinit var epRelative:RelativeLayout
    lateinit var informationRelative:RelativeLayout
    lateinit var bannerImagePager: ImageView
    lateinit var mail_icon: ImageView
    lateinit var description: TextView
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var progress: ProgressBar
    var userId:String = ""
    var text_content: TextView? = null
    var text_dialog: TextView? = null

    //private val mListViewArray: ArrayList<SecondaryModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_absence_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFn()

    }

    private fun initFn() {
        mContext = requireContext()



    }





}