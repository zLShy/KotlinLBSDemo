package com.zl.map.FragmentCom

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zl.map.R
import android.widget.Toast



class FunctionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function)
    }

    fun setFunctionFragment(functionTag:String) {

        var fm = supportFragmentManager
        var bf = fm.findFragmentByTag(functionTag) as BaseFragment

        bf.setFunction(Functions().getInstance())
        Log.e("TGA",functionTag)
        if (BlankFragment1().TAG.equals(functionTag)) {
            var bf1 = fm.findFragmentByTag(functionTag) as BlankFragment1
//            bf1.getFunctions().addFunction(object : Functions.FunctionNoParamAndResult(BlankFragment2.TAG) {
//                override fun function() {
//                    Toast.makeText(this@FunctionActivity, "123", Toast.LENGTH_SHORT).show()
//                }
//            })
            bf1.getFunctions().addFunction(object :Functions.FunctionNoParamAndResult(bf1.TAG)
            {
                override fun function() {
                    Toast.makeText(this@FunctionActivity, "123", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

}
