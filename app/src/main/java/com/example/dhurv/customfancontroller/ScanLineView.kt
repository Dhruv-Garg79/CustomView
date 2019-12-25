package com.example.dhurv.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ScanLineView(context: Context?) : View(context) {
    private val mPaint = Paint()
    private var yPos = 0f
    private var runAnim = true
    private var showLine = true
    private var isGoingDown = false
    private var mHeightStart = 0f
    private var mWidth = 0f

    init {
        mPaint.color = Color.WHITE
        mPaint.strokeWidth = 8f
    }

    fun stopAnimation(){
        runAnim = false
        showLine = false
        yPos = 0f
        isGoingDown = false
        this.invalidate()
    }

    fun startAnimation(){
        runAnim = true
        showLine = true
        this.invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        if (showLine){
            canvas?.drawLine(mWidth / 3 , yPos, mWidth * 4/3 , yPos, mPaint)
        }
        if (runAnim){
            refreshView()
        }
    }

    private fun refreshView() = runBlocking{
        launch{
            delay(10)
            if (isGoingDown){
                yPos += 5
                if (yPos >= mWidth + mHeightStart){
                    yPos = mWidth + mHeightStart
                    isGoingDown = false
                }
            }else{
                yPos -= 5
                if (yPos < mHeightStart){
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