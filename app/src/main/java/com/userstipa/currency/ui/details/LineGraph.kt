package com.userstipa.currency.ui.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.userstipa.currency.R
import com.userstipa.currency.domain.model.CurrencyPriceDetails

class LineGraph @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var currency: CurrencyPriceDetails? = null
        set(value) {
            field = value
            calculateBorderRect()
            calculateLinePath()
            requestLayout()
            invalidate()
        }

    private var colorOfLine = Color.WHITE
    private var colorOfButtons = Color.WHITE
    private var colorOfText = Color.WHITE

    private var linePath = Path()
    private var linePaint = Paint()
    private val lineWidth = 3f

    private var borderRect = RectF()
    private var borderPaint = Paint()
    private var borderWidth = 2f

    init {
        initAttributes(attrs, defStyleAttr, defStyleRes)
        initPaint()
        calculateBorderRect()
        calculateLinePath()
    }

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

    private fun initPaint() {
        linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            pathEffect = CornerPathEffect(35f)
            strokeWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                lineWidth,
                resources.displayMetrics
            )
            color = colorOfLine
        }
        borderPaint = Paint().apply {
            style = Paint.Style.STROKE
            color = Color.RED
            strokeWidth = borderWidth
        }
    }

    private fun calculateBorderRect() {
        borderRect = RectF(0f, 0f, width.toFloat(), height.toFloat())
    }

    private fun calculateLinePath() {
        currency?.let { currency ->
            linePath.reset()
            val list = currency.history
            val maxPrice = list.maxBy { it.priceUsd }.priceUsd
            val minPrice = list.minBy { it.priceUsd }.priceUsd

            val xScale = (borderRect.width()) / (list.size - 1)
            val yScale = (borderRect.height()) / (maxPrice - minPrice).toFloat()

            for (i in list.indices) {
                val x = ((i * xScale) + borderRect.left)
                val y = ((maxPrice - list[i].priceUsd) * yScale + borderRect.top).toFloat()

                with(linePath) {
                    if (i == 0) moveTo(x, y)
                    else lineTo(x, y)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (currency == null) return
        drawLine(canvas)
        drawRect(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawPath(linePath, linePaint)
    }

    private fun drawRect(canvas: Canvas) {
        canvas.drawRect(borderRect, borderPaint)
    }
}