package com.zl.map.RetrofitRequest

import android.content.Context
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.app.Activity







/**
 * Created by zhangli on 2018/6/14.
 */
class ProgressObserver<Any>:Observer<Any> {
    private var listener: onSuccessListener<Any>? = null
    private var context: Context? = null
    private var waitingDialog: WaitingDialog? = null
//    fun ProgressObserver(listener: onSuccessListener<Any>, context: Context){
//        this.listener = listener
//        this.context = context
//    }

    constructor(listener: onSuccessListener<Any>,context: Context) {
        this.listener = listener
        this.context = context
    }

    override fun onComplete() {
        dismisWaitingDialog()
    }

    override fun onNext(t: Any) {
        listener!!.onNext(t)
    }

    override fun onSubscribe(d: Disposable?) {
        showWaitingDialog("123",true);
    }

    override fun onError(e: Throwable?) {
        dismisWaitingDialog();
    }

    fun showWaitingDialog(msg: String, iscancel: Boolean) {
        if (context is Activity && !(context as Activity).isFinishing) {
            val builder = WaitingDialog.Builder(context)
            builder.setMsg(msg)
            builder.setCancelable(iscancel)
            waitingDialog = builder.create()
            waitingDialog!!.show()
        }
    }

    fun dismisWaitingDialog() {
        if (waitingDialog != null) {
            waitingDialog!!.dismiss()
            waitingDialog!!.cancel()
            waitingDialog = null
        }
    }
}