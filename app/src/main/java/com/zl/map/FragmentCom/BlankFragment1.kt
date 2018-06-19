package com.zl.map.FragmentCom


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.zl.map.R
import kotlinx.android.synthetic.main.fragment_blank_fragment1.*


/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment1 : BaseFragment() {

    val TAG = "BlankFragment1"
    var mView :View? = null
    var mBtn: Button? = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       mView =  inflater!!.inflate(R.layout.fragment_blank_fragment1, container, false)

        mBtn = mView!!.findViewById(R.id.fragment1_btn)
        mBtn!!.setOnClickListener {
            view ->
            try {
                getFunctions().invokeFunction(TAG)
            }catch (e:FunctionException) {

            }
        }
        return mView!!
    }




}
