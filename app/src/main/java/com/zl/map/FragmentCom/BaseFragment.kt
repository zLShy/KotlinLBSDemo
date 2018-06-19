package com.zl.map.FragmentCom


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.zl.map.R


/**
 * A simple [Fragment] subclass.
 */
open class BaseFragment : Fragment() {
    var  mFunctions: Functions? = null
    var mFunctionActivity: FunctionActivity? = null


    fun setFunction(functions: Functions): Unit {
        mFunctions = functions
    }

    fun getFunctions(): Functions {
        return mFunctions!!
    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FunctionActivity) {
            mFunctionActivity = context
            mFunctionActivity!!.setFunctionFragment(tag)
        }
    }
}
