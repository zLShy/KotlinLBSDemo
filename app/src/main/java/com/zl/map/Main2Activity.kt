package com.zl.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import com.zl.map.R

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        touch_btn.setOnClickListener {
//            view -> Log.e("TGA","onclick")
//        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        Log.e("TGA","dispatch")
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}
