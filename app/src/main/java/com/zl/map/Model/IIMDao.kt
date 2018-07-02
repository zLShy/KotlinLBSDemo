package com.zl.map.Model

import com.zl.map.Utils.CallBack

/**
 * Created by zhangli on 2018/7/2.
 */
interface IIMDao {
    fun getConnectIM(token:String,callBack: CallBack)
}