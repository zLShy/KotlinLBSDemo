package com.zl.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import com.zl.map.R
import com.zl.map.present.IMoviePresenterCompl
import com.zl.map.view.IMovieView
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(),IMovieView {

    private var mIMoviePresenterCompl:IMoviePresenterCompl? = null
    override fun showSuccess(msg: String) {

        Log.e("TGA",msg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

//        touch_btn.setOnClickListener {
//            view -> Log.e("TGA","onclick")
//        }
        mIMoviePresenterCompl = IMoviePresenterCompl(this,this)
        touch_btn.setOnClickListener { view ->
            mIMoviePresenterCompl!!.getMovie(0,25)
        }

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        Log.e("TGA","dispatch")
        return false
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}
