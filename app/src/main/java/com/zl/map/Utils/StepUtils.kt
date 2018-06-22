package com.zl.map.Utils

/**
 * Created by zhangli on 2018/6/21.
 */
class StepUtils {

    fun getStepLength(type: Int): Double{
        when(type) {

            1 -> return 0.60
            2 -> return 0.55
            else -> return 0.57
        }
    }
}