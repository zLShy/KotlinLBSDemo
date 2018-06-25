package com.zl.map.present

import android.content.Context
import com.zl.map.Model.ITopMovie
import com.zl.map.Model.getTopMovie
import com.zl.map.view.IMovieView

class IMoviePresenterCompl(context: Context,mIMovieView: IMovieView):IMoviePresenter {


    private var mContext:Context? = null
    private var mITopMovie:ITopMovie? = null
    private var mIMovieView:IMovieView? = null

    init {
        mContext = context
        this.mIMovieView = mIMovieView
        mITopMovie = getTopMovie(mContext!!)
    }

    override fun getMovie(start: Int, count: Int) {

        mITopMovie!!.getTopMovie(start,count,object : ITopMovie.CallBack{
            override fun onSuccess(msg: String) {
                mIMovieView!!.showSuccess(msg)
            }

            override fun onFail() {

            }

        })
    }
}