package com.zl.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat





open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
            WRITE_READ_EXTERNAL_CODE -> doSDcardPermission()
            CAMERA_CODE -> takePhoto()
            CALL_PHONE_CODE -> callPhone()
            SEM_MESSAGE_CODE -> sendSEM()
            PHONE_LOCATION_CODE -> getLocation()
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
}
