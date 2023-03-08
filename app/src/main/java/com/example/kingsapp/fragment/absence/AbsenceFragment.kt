package com.example.kingsapp.fragment.absence

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kingsapp.R


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