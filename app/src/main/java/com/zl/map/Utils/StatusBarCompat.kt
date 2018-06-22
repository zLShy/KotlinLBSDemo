package com.zl.map.Utils

import android.graphics.Color
import android.graphics.Color.parseColor
import android.support.v4.view.ViewCompat.setAlpha
import android.view.ViewGroup
import android.os.Build
import android.app.Activity
import android.content.Context
import android.view.View


/**
 * Created by zhangli on 2018/6/20.
 */
class StatusBarCompat {

    private val INVALID_VAL = -1
    private val COLOR_DEFAULT = Color.parseColor("#ffF74C1E")

    fun compat(activity: Activity, statusColor: Int) {

        //当前手机版本为5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (statusColor != INVALID_VAL) {
                activity.window.statusBarColor = statusColor
            }
            return
        }

        //当前手机版本为4.4
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            var color = COLOR_DEFAULT
            val contentView = activity.findViewById<View>(android.R.id.content) as ViewGroup
            if (statusColor != INVALID_VAL) {
                color = statusColor
            }
            val statusBarView = View(activity)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity))
            statusBarView.setBackgroundColor(color)
            statusBarView.getBackground().setAlpha(100)
            contentView.addView(statusBarView, lp)
        }

    }

    fun compat(activity: Activity) {
        compat(activity, INVALID_VAL)
    }


    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId)
        }
        return result
    }
}