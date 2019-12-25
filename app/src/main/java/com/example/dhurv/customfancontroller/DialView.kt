package com.example.dhurv.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DialView(context: Context?) : View(context) {

    private var mWidth: Float = 0f
    private var mHeight: Float = 0f
    private var mRadius: Float = 0f
    private val mTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDialPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mActiveSelection: Int = 0
    private val mTempLabel = StringBuffer(8)
    private val mTempResult = FloatArray(2)

    init {
        mTextPaint.apply {
            color = Color.BLACK
            style = Paint.Style.FILL_AND_STROKE
            textAlign = Paint.Align.CENTER
            textSize = 40f
        }
        mDialPaint.color = Color.GRAY
        setOnClickListener {
            mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT
            // Set dial background color to green if selection is >= 1.
            if (mActiveSelection >= 1) {
                mDialPaint.color = Color.GREEN
            } else {
                mDialPaint.color = Color.GRAY
            }
            // Redraw the view.
            invalidate()
        }

    }

    private fun computeXYForPosition(pos : Int, radius : Float) : FloatArray {
        val result = mTempResult
        val startAngle = Math.PI * (9 / 8.0)
        val angle = startAngle + pos * (Math.PI / 4)
        result[0] = (radius * Math.cos(angle)).toFloat() + mWidth / 2
        result[1] = (radius * Math.sin(angle)).toFloat() + mHeight / 2
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint)
        // Draw the text labels.
        val labelRadius = mRadius + 20
        val label = mTempLabel
        for (i in 0 until SELECTION_COUNT) {
            val xyData = computeXYForPosition(i, labelRadius)
            val x = xyData[0]
            val y = xyData[1]
            label.setLength(0)
            label.append(i)
            canvas?.drawText(label, 0, label.length, x, y, mTextPaint)
        }
        // Draw the indicator mark.
        val markerRadius = mRadius - 35
        val xyData = computeXYForPosition(
            mActiveSelection,
            markerRadius
        )
        val x = xyData[0]
        val y = xyData[1]
        canvas?.drawCircle(x, y, 20f, mTextPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mRadius = (Math.min(mWidth, mHeight) / 2 * 0.8).toFloat()
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)

    private companion object {
        const val SELECTION_COUNT = 4
    }
}