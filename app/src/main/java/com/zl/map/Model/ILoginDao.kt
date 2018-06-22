package com.zl.map.Model

/**
 * Created by zhangli on 2018/6/21.
 */
interface ILoginDao {
    fun CheckUserRight(name: String,pass: String,mCallBack: CallBackUser)

    interface CallBackUser{
        fun onSuccess()
        fun onFail()
    }
}