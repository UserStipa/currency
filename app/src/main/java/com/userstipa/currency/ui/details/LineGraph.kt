package com.userstipa.currency.ui.details

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.userstipa.currency.R
import com.userstipa.currency.domain.model.CurrencyPrice

class LineGraph @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    init {
        initAttributes(attrs, defStyleAttr, defStyleRes)
    }

    var currency: CurrencyPrice? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    private var colorOfLine = Color.WHITE
    private var colorOfButtons = Color.WHITE
    private var colorOfText = Color.WHITE

    private fun initAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.LineGraph,
            defStyleAttr,
            defStyleRes
        )
        try {
            colorOfLine = array.getColor(R.styleable.LineGraph_colorOfLine, Color.WHITE)
            colorOfButtons = array.getColor(R.styleable.LineGraph_colorOfButtons, Color.WHITE)
            colorOfText = array.getColor(R.styleable.LineGraph_colorOfText, Color.WHITE)
        } finally {
            array.recycle()
        }
    }
}