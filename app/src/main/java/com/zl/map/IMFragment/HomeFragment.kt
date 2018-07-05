package com.zl.map.IMFragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.zl.map.R
import kotlinx.android.synthetic.main.im_fragment_home.*

/**
 * Created by zhangli on 2018/7/3.
 */
class HomeFragment:Fragment() {

    var mView:View? = null
    var mTv:TextView? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


//        fragment_tv.setText("home")
        mView = inflater!!.inflate(R.layout.im_fragment_home,container,false)
        mTv = mView!!.findViewById(R.id.fragment_tv)
        mTv!!.text = "home///"
        return mView
    }


}