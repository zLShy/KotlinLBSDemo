package com.zl.map.view

/**
 * Created by zhangli on 2018/6/21.
 */
interface ILoginView {
    /**
     *
     */
    fun onClearText()

    fun getUserName(): String
    fun getUserPass(): String

    fun showSuccessMsg()
    fun showErrorMsg()

    fun showLoading()
    fun hideLoading()

}