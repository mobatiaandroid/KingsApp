package com.kingseducation.app.manager.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class CustomVerdanaRegularItalic:androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"font/verdanai_regular_italic.ttf")
        this.typeface = type
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "font/verdanai_regular_italic.ttf")
        this.typeface = type
    }
}