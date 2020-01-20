package com.example.dhurv.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScanLineView(context: Context?) : View(context) {
    private val mPaint = Paint()
    private val mPaintSide = Paint()
    private var yPos = 0f
    private var runAnim = true
    private var switch = true
    private var isGoingDown = false
    private var mHeightStart = 0f
    private var mWidth = 0f

    init {
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = 3f

        context?.let{
            mPaintSide.apply {
                color = ContextCompat.getColor(it, R.color.transParent)
                strokeWidth = 5f
            }
        }

        setOnClickListener {
            if (switch) startAnimation() else stopAnimation()
        }
    }

    fun stopAnimation() {
        runAnim = false
        switch = true
        this.invalidate()
    }

    fun startAnimation() {
        runAnim = true
        switch = false
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(mWidth / 3, yPos, mWidth * 4 / 3, yPos, mPaint)
        canvas?.drawLine(mWidth / 3, yPos - 5, mWidth * 4 / 3, yPos - 5, mPaintSide)
        canvas?.drawLine(mWidth / 3, yPos + 5, mWidth * 4 / 3, yPos + 5, mPaintSide)
        if (runAnim) {
            refreshView()
        }
    }

    private fun refreshView() = runBlocking {
        launch {
            delay(10)
            if (isGoingDown) {
                yPos += 5
                if (yPos >= mWidth + mHeightStart) {
                    yPos = mWidth + mHeightStart
                    isGoingDown = false
                }
            } else {
                yPos -= 5
                if (yPos < mHeightStart) {
                    yPos = mHeightStart
                    isGoingDown = true
                }
            }
            this@ScanLineView.invalidate()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mWidth = (w * 0.6).toFloat()
        mHeightStart = (h - mWidth) / 2
        yPos = mHeightStart
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs)
}