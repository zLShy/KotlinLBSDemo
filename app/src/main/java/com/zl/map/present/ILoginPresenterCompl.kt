package com.zl.map.present

import com.zl.map.Model.ILoginDao
import com.zl.map.Model.LoginDao
import com.zl.map.view.ILoginView

/**
 * Created by zhangli on 2018/6/21.
 */
class ILoginPresenterCompl(loginView: ILoginView):ILoginPresenter {

    private var mILoginDao: ILoginDao? = null
    private var mILoginView: ILoginView? = null

    init {
        this.mILoginView = loginView
        this.mILoginDao = LoginDao()
    }
    override fun doLogin() {
        mILoginView!!.showLoading()
        mILoginDao!!.CheckUserRight(mILoginView!!.getUserName(),mILoginView!!.getUserPass(),object : ILoginDao.CallBackUser {
            override fun onSuccess() {
                mILoginView!!.hideLoading()
                mILoginView!!.showSuccessMsg()
            }

            override fun onFail() {
                mILoginView!!.hideLoading()
                mILoginView!!.showErrorMsg()
            }

        })
    }


}