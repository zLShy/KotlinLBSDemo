package com.zl.map.Model

import android.app.Application
import android.util.Log
import com.zl.map.MyApplication
import com.zl.map.Utils.CallBack
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient

/**
 * Created by zhangli on 2018/7/2.
 */
class IMDao:IIMDao {
    override fun getConnectIM(token: String, callBack: CallBack) {
        RongIM.connect(token,object : RongIMClient.ConnectCallback() {
            override fun onSuccess(p0: String?) {
                Log.e("TGA","Success")

               callBack.onSuccess(p0!!)
            }


            override fun onError(p0: RongIMClient.ErrorCode?) {

                callBack.onFail(p0!!)
            }

            override fun onTokenIncorrect() {
                Log.e("TGA","onTokenIncorrect")

            }

        })
    }


}