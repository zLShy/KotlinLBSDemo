package com.zl.map.FragmentCom

import com.zl.map.FragmentCom.Functions.FunctionNoParamAndResult


/**
 * Created by zhangli on 2018/6/19.
 */
class Functions {
    var mFunctions: Functions? = null

    @Synchronized
    fun getInstance(): Functions {
        if (mFunctions == null) {
            synchronized(Functions::class.java) {
                if (mFunctions == null) {
                    mFunctions = Functions()
                }
            }
        }

        return mFunctions!!
    }

    /**
     * 无返回值参数集合
     */
    private var mFunctionNoParamAndResult: HashMap<String, FunctionNoParamAndResult>? = null

    abstract class Function(var mFunction: String)

    abstract class FunctionNoParamAndResult(mFunction: String) : Function(mFunction) {

        abstract fun function()
    }

    fun addFunction(function: FunctionNoParamAndResult) {
        if (mFunctionNoParamAndResult == null) {
            mFunctionNoParamAndResult = HashMap()
        }
        mFunctionNoParamAndResult!!.put(function.mFunction, function)
    }

    @Throws(FunctionException::class)
    fun invokeFunction(functionName: String) {

        var f: FunctionNoParamAndResult? = null
        if (mFunctionNoParamAndResult != null) {
            f = mFunctionNoParamAndResult!![functionName]
        }
        if (f != null) {
            f.function()
        }
        if (f == null) {
            throw FunctionException("has no Function" + functionName)
        }
    }


    /**
     * 有返回值无参数集合
     */
    private var mFunctionWithParamAndResult: HashMap<String, FunctionWithParamAndResult<*>>? = null

    abstract class FunctionWithParamAndResult<Result>(mFunction: String) : Function(mFunction) {

        abstract fun function(): Result
    }


    fun addFunction(function: FunctionWithParamAndResult<*>) {
        if (mFunctionWithParamAndResult == null) {
            mFunctionWithParamAndResult = HashMap()
        }
        mFunctionWithParamAndResult!!.put(function.mFunction, function)
    }

    @Throws(FunctionException::class)
    fun <Result> invokeFunction(functionName: String, c: Class<Result>?): Result? {

        var f: FunctionWithParamAndResult<*>? = null
        if (mFunctionWithParamAndResult != null) {
            f = mFunctionWithParamAndResult!![functionName]
        }
        if (f != null) {
            return if (c != null) {
                c.cast(f.function())
            } else f.function() as Result
        }
        if (f == null) {
            throw FunctionException("has no Function" + functionName)
        }
        return null
    }


}