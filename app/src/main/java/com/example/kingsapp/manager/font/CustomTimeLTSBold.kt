package com.example.kingsapp.manager.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet


class CustomTimeLTSBold : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        // ...
        val type = Typeface.createFromAsset(context.assets, "font/timesltstd_semibold.otf")
        this.typeface = type
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "font/timesltstd_semibold.otf")
        this.typeface = type
    }
}