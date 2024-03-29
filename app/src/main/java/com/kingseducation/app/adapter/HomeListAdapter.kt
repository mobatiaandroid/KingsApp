package com.example.nas_dubai_kotlin.activities.home.adapter

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.kingseducation.app.R
import com.kingseducation.app.manager.PreferenceManager


/**
 * Created by Arshad on 05,August,2022
 */
class HomeListAdapter(
    context: Context,
    listItemArray: Array<String>,
    listImgArray: TypedArray,
    customLayout: Int,
    displayListImage: Boolean
) : BaseAdapter() {

    private val mContext: Context = context
    private var mListItemArray: Array<String> = listItemArray
    private val mListImgArray: TypedArray = listImgArray
    private val customLayout = customLayout
    private val mDisplayListImage = displayListImage
    override fun getCount(): Int {
        return mListItemArray.size
    }

    override fun getItem(position: Int): Any {
        return mListItemArray[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val retView: View
        if (convertView == null) {
            val mInflater =
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            retView = mInflater.inflate(customLayout, parent, false)
        } else
            retView = convertView
        val txtTitle = retView.findViewById<View>(R.id.listTxtView) as TextView
        val imgView = retView.findViewById<View>(R.id.listImg) as ImageView
        if (PreferenceManager().getLanguage(mContext).equals("ar")) {
            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/times_new_roman.ttf")
            txtTitle.typeface = face
        } else {
            val face: Typeface =
                Typeface.createFromAsset(mContext.assets, "font/montserrat_medium.ttf")
            txtTitle.typeface = face
        }
        txtTitle.text = mListItemArray[position]

        if (mDisplayListImage) {
            imgView.visibility = View.VISIBLE
            imgView.setImageDrawable(mListImgArray.getDrawable(position))
        } else {
            imgView.visibility = View.GONE
        }
        return retView
    }
}