package com.example.dhurv.customfancontroller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TopFragment : Fragment() {

    private var bool = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)
//        val scanLineView : ScanLineView = view.scanLine
//        view.setOnClickListener {
//            if (bool)
//                scanLineView.startAnimation()
//            else
//                scanLineView.stopAnimation()
//        }
        return view
    }
}
