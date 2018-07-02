package com.zl.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat
import com.zl.map.*
import com.zl.map.Utils.Constants


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(getLayoutResId())

//        val contentFrameLayout = findViewById<View>(Window.ID_ANDROID_CONTENT) as ViewGroup
//        val parentView = contentFrameLayout.getChildAt(0)
//        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
//            parentView.setFitsSystemWindows(true)
//        }
//        StatusBarCompat().compat(this, 0xff000000.toInt())
    }


    /**
     * 判断是否有权限
     *
     * @param permissions
     * @return
     */
    fun hasPermission(vararg permissions: String): Boolean {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    /**
     * 申请权限方法
     *
     * @param code        返回码
     * @param permissions 具体权限
     */
    fun requestPermission(code: Int, vararg permissions: String) {
        ActivityCompat.requestPermissions(this, permissions, code)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Constants.WRITE_READ_EXTERNAL_CODE -> doSDcardPermission()
            Constants.CAMERA_CODE -> takePhoto()
            Constants.CALL_PHONE_CODE -> callPhone()
            Constants.SEM_MESSAGE_CODE -> sendSEM()
            Constants.PHONE_LOCATION_CODE -> getLocation()
            else -> {
            }
        }
    }

     open fun getLocation() {

    }

     fun sendSEM() {

    }

     fun callPhone() {

    }

     fun takePhoto() {

    }

     fun doSDcardPermission() {

    }

    /**
     * 返回当前Activity布局文件的id
     *
     * @return
     */
    protected abstract fun getLayoutResId(): Int

}
