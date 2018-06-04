package com.zl.map

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.LocationSource
import com.amap.api.maps.MapView
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.offlinemap.OfflineMapManager
import com.amap.api.maps.offlinemap.OfflineMapStatus
import kotlinx.android.synthetic.main.activity_main.*
import android.location.LocationManager
import android.content.Context.LOCATION_SERVICE
import android.content.ServiceConnection
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.IBinder
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.PolylineOptions
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class MainActivity : BaseActivity(),OfflineMapManager.OfflineLoadedListener,OfflineMapManager.OfflineMapDownloadListener,AMapLocationListener,View.OnClickListener
    ,LocationSource,StepService.CallBack{
    override fun StepChanged(value: Int) {

    }

    private var aMap: AMap? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var offLineManager: OfflineMapManager? = null
    private var mPointList: ArrayList<LatLng>? = null;
    /**
     * 停止定位
     */
    override fun deactivate() {

        mListener = null
        if (mLocationClient != null) {
            mLocationClient!!.stopLocation();
            mLocationClient!!.onDestroy();
        }
        mLocationClient = null

    }

    /**
     * 激活定位
     */
    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0;

        mLocationClient = AMapLocationClient(application);
        mLocationClient!!.setLocationListener(this)
        mLocationOption = AMapLocationClientOption();
    //        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Sport)
        mLocationOption!!.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setOnceLocation(true)
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption!!.setInterval(3000);
//设置定位参数
        mLocationClient!!
                .setLocationOption(mLocationOption);
        if (mLocationClient != null) {
            mLocationClient!!.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient!!.stopLocation();
            mLocationClient!!.startLocation();
        }

    }

    override fun onClick(p0: View?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(p0?.id) {
            R.id.btn -> Toast.makeText(this,"123",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLocationChanged(p0: AMapLocation?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (p0 != null) {
            var city = p0.city;
            Toast.makeText(this,city+"123456===="+p0.cityCode,Toast.LENGTH_SHORT).show()
            offLineManager = OfflineMapManager(this,this)
            mListener!!.onLocationChanged(p0)
            var offLineCityLists = offLineManager!!.downloadOfflineMapCityList;
            var hasMap = false

            var point = LatLng(p0.latitude,p0.longitude)
            if (!mPointList!!.contains(point)) {
                mPointList!!.add(point)
                PaintLine(mPointList!!)
            }
            Log.e("TGA", "size==="+mPointList!!.size)
            //lambda 标签名和跳出foreach 循环
            offLineCityLists.forEach block@{
                if (p0.city.equals(it)) {
                    hasMap = true
                    return@block
                }
            }
            if (!hasMap) {
                offLineManager!!.downloadByCityName(p0.cityCode)
            }
        }
    }

    private fun PaintLine(mPointList: ArrayList<LatLng>) {
        aMap!!.addPolyline(PolylineOptions().
        addAll(mPointList).width(10F).color(Color.argb(255, 1, 1, 1)));
    }


    override fun onDownload(p0: Int, p1: Int, p2: String?) {
        when (p0) {
            OfflineMapStatus.SUCCESS -> return
            OfflineMapStatus.ERROR -> return
            OfflineMapStatus.EXCEPTION_NETWORK_LOADING -> offLineManager!!.pause()
        }
    }

    override fun onCheckUpdate(p0: Boolean, p1: String?) {
    }

    override fun onRemove(p0: Boolean, p1: String?, p2: String?) {
    }

    override fun onVerifyComplete() {
    }


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.e("TGA","GPS is open:  "+isOPen(this))
        Log.e("TGA","SHA1:  "+getAppSH.sHA1(this))
//        Toast.makeText(this,"GPS is open:  "+isOPen(this),Toast.LENGTH_SHORT).show()
        map_view.onCreate(savedInstanceState)
        mPointList = ArrayList<LatLng>()


        if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getLocation()
        }else{
            requestPermission(PHONE_LOCATION_CODE,Manifest.permission.ACCESS_COARSE_LOCATION)
        }

//        setMapPoint()
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        var mStepService =
        val btn = findViewById<Button>(R.id.btn) as Button
        btn.setOnClickListener(this)
    }


    private var mService: StepService? = null
    private var mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = (service as StepService.StepBinder).service
            mService!!.registCallback(this@MainActivity)
        }

        override fun onServiceDisconnected(className: ComponentName) {
            mService = null
        }
    }

//    private var mCallBack: StepService.CallBack? = Objects: StepService.CallBack {
//
//    }

//    private val mCallback = object : StepService.CallBack {
//
//        override fun StepChanged(value: Int) {
////            Toast.makeText("")
//        }
//
//    }
    private fun initAMap() {

        if (aMap == null) {
            aMap = map_view.getMap()
            setMapPoint()
        }
    }

    private fun setMapPoint() {
        // 自定义系统定位小蓝点
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.abc_ic_star_black_16dp))// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.WHITE)// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0))// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0.0f)// 设置圆形的边框粗细
        aMap!!.setMyLocationStyle(myLocationStyle)
        aMap!!.setLocationSource(this)// 设置定位监听
        aMap!!.getUiSettings().isMyLocationButtonEnabled = false// 设置默认定位按钮是否显示
        aMap!!.getUiSettings().isZoomControlsEnabled = false
        aMap!!.setMyLocationEnabled(true)// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(15f))
        aMap!!.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW)
//        aMap!!.setMapType(AMap.MAP_TYPE_NORMAL)
        aMap!!.mapType = AMap.MAP_TYPE_NORMAL
    }

    override fun onDestroy() {
        super.onDestroy()
        deactivate()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        map_view.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()

        map_view.onResume()
    }


    fun isOPen(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return if (gps || network) {
            true
        } else false

    }


     override fun getLocation() {
        initAMap()
    }

}
