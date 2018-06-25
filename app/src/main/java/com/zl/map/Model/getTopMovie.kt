package com.zl.map.Model

import android.content.Context
import android.util.Log
import com.zl.map.RetrofitRequest.ApiMethods
import com.zl.map.RetrofitRequest.ProgressObserver
import com.zl.map.RetrofitRequest.onSuccessListener
import com.zl.map.StepService
import okhttp3.ResponseBody
import retrofit2.Response

class getTopMovie(context: Context) : ITopMovie {

    private var mCallback:ITopMovie.CallBack? = null
    private var mContext:Context? = null

    init {
        this.mContext = context
    }
    override fun getTopMovie(count: Int, start: Int, mCallback: ITopMovie.CallBack) {

        this.mCallback = mCallback


       var listener = object : onSuccessListener<Response<ResponseBody>>{
           override fun onNext(t: Response<ResponseBody>) {
                Log.e("TGA",String(t.body()!!.bytes()))
                mCallback.onSuccess(String(t.body()!!.bytes()))
           }

       }
        ApiMethods().getTopMovie(ProgressObserver<Response<ResponseBody>>(listener, mContext!!),0,25)

    }

}