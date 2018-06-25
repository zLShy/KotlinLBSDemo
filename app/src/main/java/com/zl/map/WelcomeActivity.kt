package com.zl.map

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message

class WelcomeActivity : BaseActivity() {
    override fun getLayoutResId(): Int {
        return R.layout.activity_welcome
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        setContentView(R.layout.activity_welcome)

        startIntent()

    }

    private fun startIntent() {
        var mMessage = Message()
        mMessage.what = 1
        mHandler.sendMessageDelayed(mMessage,3000)
    }


    var mHandler = object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg!!.what

            startActivity(Intent(this@WelcomeActivity, Main2Activity::class.java))
            finish()
        }
    }
}
