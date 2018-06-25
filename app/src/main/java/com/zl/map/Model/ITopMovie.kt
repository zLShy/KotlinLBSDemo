package com.zl.map.Model

interface ITopMovie {

    fun getTopMovie(count:Int,start:Int,mCallback:CallBack)

    interface CallBack{
        fun onSuccess(msg:String)
        fun onFail()
    }
}