package com.zl.map.IMFragment

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.zl.map.R
import io.rong.imkit.RongIM
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import android.annotation.TargetApi
import android.net.Uri
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.util.Log
import io.rong.imlib.model.UserInfo
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.model.LatLng


/**
 * Created by zhangli on 2018/7/3.
 */
class NoticeFragment : Fragment(),RongIM.UserInfoProvider {

    override fun getUserInfo(p0: String?): UserInfo {

        return UserInfo("15982056336","hehe", Uri.parse("http://images.runmate2015.com/0000D759-549B-43DF-8678-3E73DDC39D3B"))
    }

    var mView: View? = null
    var mBtn: Button? = null
    private val PERMISSIONS = arrayOf<String>(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private val PERMISSION_CODES = 1001
    private var permissionGranted = true
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {


//        fragment_tv.setText("home")
        mView = inflater!!.inflate(R.layout.im_fragment_notice,container,false)
        mBtn = mView!!.findViewById(R.id.fragment_btn)
//        requestPermission()

        RongIM.setUserInfoProvider(this@NoticeFragment,true)
//        val distance = AMapUtils.calculateLineDistance(latLng1, latLng2)

        var point1 = LatLng(30.588653,104.070605)
        var point2 = LatLng(30.58179,104.070857)
        var dis = AMapUtils.calculateArea(point1,point2)
        Log.e("TGA","dis==="+dis)

        mBtn!!.setOnClickListener {
            view -> if (RongIM.getInstance() != null) {
            RongIM.getInstance().startPrivateChat(activity,"17308004973","our")
//            RongIM.getInstance().startChatRoomChat(activity,"10088",true)
        }
        }
        return mView
    }

//    @TargetApi(Build.VERSION_CODES.M)
//    private fun requestPermission() {
//        val p:ArrayList<String> = ArrayList()
//        for (permission in PERMISSIONS) {
//            if (ContextCompat.checkSelfPermission(activity, permission) !== PackageManager.PERMISSION_GRANTED) {
//                p.add(permission)
//            }
//        }
//        if (p.size > 0) {
//            requestPermissions(p.toTypedArray(), PERMISSION_CODES)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
//        when (requestCode) {
//            PERMISSION_CODES -> permissionGranted = !(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
//        }
//
//    }


}