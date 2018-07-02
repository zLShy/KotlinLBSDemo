package com.zl.map.present

import android.util.Log
import com.zl.map.Model.IIMDao
import com.zl.map.Model.IMDao
import com.zl.map.Utils.CallBack
import com.zl.map.Utils.Constants
import com.zl.map.view.IIMView

/**
 * Created by zhangli on 2018/7/2.
 */
class IMPresenterCompl(iimView: IIMView):IIMPresenter {

    var mIMView :IIMView? = null
    var mIMDao :IIMDao? = null
    init {
        mIMView = iimView
        mIMDao = IMDao()
    }
    override fun doConnect() {
        mIMDao!!.getConnectIM(Constants.RONG_TOKEN,object : CallBack {
            override fun onSuccess(t: Any) {
                mIMView!!.showConnectInfo(t)
            }

            override fun onFail(t: Any) {
                mIMView!!.showConnectInfo(t)
            }

        })
    }
}