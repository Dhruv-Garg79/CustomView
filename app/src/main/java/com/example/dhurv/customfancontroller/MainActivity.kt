package com.example.dhurv.customfancontroller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "kuch bhi"
        supportActionBar?.hide()

        container.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity){
            override fun onRightSwipe() { textView.text = "Left to right" }
            override fun onLeftSwipe() { textView.text = "Right to Left" }
            override fun onTopSwipe() {
                textView.text = "Down to Top"
                val fragment = supportFragmentManager.findFragmentById(R.id.container_top)
                if (fragment != null)
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }
            override fun onDownSwipe() {
                textView.text = "Top to Down"
                supportFragmentManager.beginTransaction().replace(R.id.container_top, TopFragment()).commit()
            }
        })
    }
}