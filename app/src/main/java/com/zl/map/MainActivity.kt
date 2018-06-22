package com.zl.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.graphics.Color
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
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.maps.offlinemap.OfflineMapManager
import com.amap.api.maps.offlinemap.OfflineMapStatus
import kotlinx.android.synthetic.main.activity_main.*
import android.location.LocationManager
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.PolylineOptions
import com.zl.map.RetrofitRequest.ApiMethods
import com.zl.map.RetrofitRequest.CreatRunClass
import com.zl.map.RetrofitRequest.ProgressObserver
import com.zl.map.RetrofitRequest.onSuccessListener
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity(),OfflineMapManager.OfflineLoadedListener,OfflineMapManager.OfflineMapDownloadListener,AMapLocationListener,View.OnClickListener
    ,LocationSource, StepsService.CallBack {
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun StepChanged(value: Int) {
        Toast.makeText(this@MainActivity,"step:  "+value,Toast.LENGTH_SHORT).show()
    }

    private var aMap: AMap? = null
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private var offLineManager: OfflineMapManager? = null
    private var mPointList: ArrayList<LatLng>? = null;
    private var mIntent: Intent? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mMediaMap: ArrayList<MediaPlayer>? = null
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

        if (mMediaPlayer!!.isPlaying) {
            mMediaPlayer!!.stop()
        }
        mMediaPlayer!!.release()
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
//            Toast.makeText(this,city+"123456===="+p0.cityCode,Toast.LENGTH_SHORT).show()
            offLineManager = OfflineMapManager(this,this)
            mListener!!.onLocationChanged(p0)
            var offLineCityLists = offLineManager!!.downloadOfflineMapCityList;
            var hasMap = false

            var point = LatLng(p0.latitude,p0.longitude)
            if (!mPointList!!.contains(point)) {
                mPointList!!.add(point)
                PaintLine(mPointList!!)
            }
            //lambda 标签名和跳出foreach 循环
            offLineCityLists.forEach block@{
                if (p0.city.equals(it)) {
                    hasMap = true
                    return@block
                }
            }
            if (!hasMap) {
                offLineManager!!.downloadByCityCode(p0.cityCode)
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
//        setContentView(R.layout.activity_main)
//        Log.e("TGA","GPS is open:  "+isOPen(this))
        Log.e("TGA","SHA1:  "+ getAppSH.sHA1(this))
//        Toast.makeText(this,"GPS is open:  "+isOPen(this),Toast.LENGTH_SHORT).show()
        map_view.onCreate(savedInstanceState)
        mPointList = ArrayList<LatLng>()

//        startActivity(Intent(this.applicationContext,
//                com.amap.api.maps.offlinemap.OfflineMapActivity::class.java))

        if (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getLocation()
        }else{
            requestPermission(PHONE_LOCATION_CODE,Manifest.permission.ACCESS_COARSE_LOCATION)
        }

//        setMapPoint()
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        var mStepService =
        val btn = findViewById<Button>(R.id.btn) as Button
        btn.setOnClickListener(this@MainActivity)

        mIntent = Intent(this, StepsService::class.java)
        bindStepService()
        map_view.setOnClickListener {
            view ->
            Toast.makeText(this,"heh",Toast.LENGTH_SHORT).show()
        }

        getData()

        playMusic()
    }

    /**
     *
     */
    private fun bindStepService() {

        bindService(mIntent,mConnection, Context.BIND_AUTO_CREATE+ Context.BIND_DEBUG_UNBIND)
        startService(mIntent)
    }


    private var mService: StepsService? = null
    private var mConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = (service as StepsService.StepsBinder).getService()
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
        unBindStepService()
    }

    private fun unBindStepService() {
        unbindService(mConnection)
        stopService(mIntent)
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

    fun getData() {
      val  listener = object: onSuccessListener<Response<ResponseBody>>{
          override fun onNext(t: Response<ResponseBody>) {
              Log.e("TGA","code===="+t.code())
              try {
                  Log.e("TGA", t.body().toString())
                  Log.e("TGA", String(t.body()!!.bytes()))
              } catch (e: IOException) {
                  e.printStackTrace()
              }

          }

      }
        var mRunInfo =  CreatRunClass()
        mRunInfo.setGuid("58504d802e7111e89a8700163e0221a7123");
        mRunInfo.setName("123");
        mRunInfo.setMinKm("4");
        mRunInfo.setMoney("4");
        mRunInfo.setRemark("123");
        mRunInfo.setStartTime(1524499200);
        mRunInfo.setNum("300");
        mRunInfo.setImgUrl("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%9B%BE%E7%89%87&hs=0&pn=1&spn=0&di=113313207810&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=594559231%2C2167829292&os=2394225117%2C7942915&simid=3436308227%2C304878115&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F120727%2F201995-120HG1030762.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bpw5rtv_z%26e3Bv54AzdH3Fejvp56AzdH3Fda8da0AzdH3Fdanll9_z%26e3Bip4s&gsm=0&islist=&querylist=");
        mRunInfo.setWeekCount(3);
        mRunInfo.setEndTime(1525104000);
        ApiMethods().getAdd(ProgressObserver<Response<ResponseBody>>(listener,this@MainActivity),mRunInfo)
    }


    fun playMusic() {
//        var mSoundPool = SoundPool(25, AudioManager.STREAM_MUSIC,0)
//
//
//        var mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//        val streamVolumeCurrent = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
//
//        val streamVolumeMax = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
//
//        val volume = streamVolumeCurrent / streamVolumeMax
//
//        mSoundPool.play(mSoundPool.load(this@MainActivity,R.raw.welcome_to_yp,1),volume,volume,1,0,1.0f)

//        mAudioManager.a
            if (mMediaMap == null) {
                mMediaMap = ArrayList<MediaPlayer>()
            }
            if (judgementTime()) {
                mMediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.good_night)
                mMediaMap!!.add(mMediaPlayer!!)
            }else{
                mMediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.good_morning)
                mMediaMap!!.add(mMediaPlayer!!)
            }

        var mPalyer = MediaPlayer.create(this@MainActivity, R.raw.welcome_to_yp)
         mMediaMap!!.add(mPalyer)


        mHandler.sendEmptyMessageDelayed(1,3000)
    }


    fun judgementTime() :Boolean{

        var time = System.currentTimeMillis()
        var mCalendar = Calendar.getInstance()
        mCalendar.timeInMillis = time
        var hour = mCalendar.get(Calendar.HOUR)

        return hour <= 12

    }

    var mHandler = @SuppressLint("HandlerLeak")
    object :Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
           for (no in mMediaMap!!) {
               no.start()
               Thread.sleep(1000)
           }

        }
    }


}

