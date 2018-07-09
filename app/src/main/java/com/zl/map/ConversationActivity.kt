package com.zl.map

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import com.zl.map.present.IMoviePresenterCompl
import com.zl.map.view.IMovieView
import kotlinx.android.synthetic.main.activity_conversation.*

class ConversationActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_conversation
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_conversation )

        tv_announce_msg.text = intent.data.getQueryParameter("title")



    }

//    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
//        Log.e("TGA", "dispatch")
//        return false
//    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return super.onTouchEvent(event)
//    }
}