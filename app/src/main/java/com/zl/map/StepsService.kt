package com.zl.map

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Binder
import android.os.IBinder

/**
 * Created by zhangli on 2018/6/8.
 */
class StepsService:Service() {

    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    private var stepDetector: StepDetector? = null
    private var mCallBack: CallBack? = null

    private final var mBinder = StepsBinder()
    override fun onBind(p0: Intent?): IBinder? {

        return mBinder
    }

  open inner class StepsBinder:Binder() {
       public fun getService(): StepsService{
           return this@StepsService
       }
    }

//    inner class StepBinder : Binder() {
//        val service: StepService
//            get() = this@StepService
//    }

    override fun onCreate() {
        super.onCreate()
        stepDetector = StepDetector()
        registerDetector()
//        stepDetector!!.OnStepDetector(StepChangeListener { value ->
//            if (mCallBack != null) {
//                mCallBack!!.StepChanged(value)
//            }
//        })
        stepDetector!!.OnStepDetector(StepChangeListener {
            value ->
            if (mCallBack != null) {
                mCallBack!!.StepChanged(value)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        unRegisterDector()
    }

    private fun registerDetector() {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorManager!!.registerListener(stepDetector, mSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun unRegisterDector() {
        if (stepDetector != null && mSensorManager != null) {
            //            stepDetector.setStepListener(null);
            mSensorManager!!.unregisterListener(stepDetector)
        }
    }

    interface CallBack {
        fun StepChanged(value: Int)
    }

    fun registCallback(callBack: CallBack) {
        this.mCallBack = callBack
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }
}