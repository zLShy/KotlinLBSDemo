package com.zl.map.Model

import android.os.Handler
import android.os.Message
import android.util.Log

/**
 * Created by zhangli on 2018/6/21.
 */
class LoginDao:ILoginDao {
    private var mCallBack: ILoginDao.CallBackUser? = null
    companion object {
        var sCount = 0
    }
//    var sCount = 0
     override fun CheckUserRight(name: String, pass: String, mCallBack: ILoginDao.CallBackUser) {

        this.mCallBack = mCallBack

        object : Thread() {
            override fun run() {
                super.run()
                Log.e("TGA","==count=="+sCount)
                if (sCount++ % 2 == 0) {
                    mHander.sendEmptyMessageDelayed(1,3000)
                }else{
                    mHander.sendEmptyMessageDelayed(2,3000)
                }
            }
        }.start()

    }
    var mHander = object :Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            if (msg!!.what == 1) {
                mCallBack!!.onSuccess()
            }else {
                mCallBack!!.onFail()
            }
        }
    }
}