package com.example.kingsapp.manager.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class CustomVerdanaRegularEditext:androidx.appcompat.widget.AppCompatEditText  {
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"font/verdana_regular.ttf")
        this.typeface = type
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "font/verdana_regular.ttf")
        this.typeface = type
    }
}