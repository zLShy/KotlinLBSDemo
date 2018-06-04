package com.zl.map;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by zhangli on 2018/6/4.
 */

public class StepService extends Service{

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private CallBack mCallBack;

    public class StepBinder extends Binder{
        public StepService getService(){
            return StepService.this;
        }
    }

    private final IBinder mBinder = new StepBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        StepDetector stepDetector = new StepDetector();
        stepDetector.OnStepDetector(new StepChangeListener() {
            @Override
            public void stepChanged(int value) {
                mCallBack.StepChanged(value);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

    }



    public interface CallBack{
        void StepChanged(int value);
    }


    public void registCallback(CallBack callBack) {
        this.mCallBack = callBack;
    }
}
