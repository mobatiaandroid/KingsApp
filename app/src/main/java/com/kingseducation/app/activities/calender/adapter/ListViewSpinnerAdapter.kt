package com.kingseducation.app.activities.calender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kingseducation.app.R
import com.kingseducation.app.activities.calender.model.ListViewSpinnerModel

class ListViewSpinnerAdapter : BaseAdapter {
    private var mContext: Context
    private var mAboutusLists: ArrayList<ListViewSpinnerModel>? = null
    private var view: View? = null
    private lateinit var mTitleTxt: TextView



    constructor(
        context: Context,
        arrList: ArrayList<ListViewSpinnerModel>?
    ) {
        mContext = context
        mAboutusLists = arrList
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getCount()
     */
    override fun getCount(): Int {
        // TODO Auto-generated method stub
        return mAboutusLists!!.size
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItem(int)
     */
    override fun getItem(position: Int): Any {
        // TODO Auto-generated method stub
        return mAboutusLists!![position]
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getItemId(int)
     */
    override fun getItemId(position: Int): Long {
        // TODO Auto-generated method stub
        return position.toLong()
    }

    /*
     * (non-Javadoc)
     *
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val inflate = LayoutInflater.from(mContext)
            view = inflate.inflate(R.layout.listview_items, null)
        } else {
            view = convertView
        }
        try {
            mTitleTxt = view!!.findViewById(R.id.listTxtTitle)
            mTitleTxt.text = mAboutusLists!![position].filename
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return view!!
    }
}