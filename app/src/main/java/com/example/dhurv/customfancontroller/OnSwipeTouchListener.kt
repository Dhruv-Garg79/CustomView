package com.example.dhurv.customfancontroller

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

open class OnSwipeTouchListener(context: Context) : View.OnTouchListener{

    private val gestureDetector = GestureDetector(context, GestureListener())

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            val diffX = e2?.x - e1?.x
            val diffY = e2?.y - e1?.y
            if (abs(diffX) > abs(diffY) && abs(diffX) > SWIPE_DISTANCE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                if (diffX > 0) onRightSwipe()
                else onLeftSwipe()
                return true
            }
            else if (abs(diffY) > SWIPE_DISTANCE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD){
                if (diffY < 0) onTopSwipe()
                else onDownSwipe()
                return true
            }
            return false
        }
    }

    open fun onRightSwipe() {}
    open fun onLeftSwipe() {}
    open fun onTopSwipe() {}
    open fun onDownSwipe() {}

    private companion object{
        const val SWIPE_DISTANCE_THRESHOLD = 100
        const val SWIPE_VELOCITY_THRESHOLD = 100
    }
}

private operator fun Float?.minus(x: Float?): Float {
    if (this == null || x == null) return 0f
    else return this - x
}
