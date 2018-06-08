package com.zl.map;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by zhangli on 2018/6/4.
 */

public class StepService extends Service{

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private CallBack mCallBack;
    private StepDetector stepDetector;
    public static int step = 0;

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

        stepDetector = new StepDetector();
        registerDetector();
        stepDetector.OnStepDetector(new StepChangeListener() {
            @Override
            public void stepChanged(int value) {
                if (mCallBack != null) {
                    mCallBack.StepChanged(value);
                }
            }
        });

        
    }

    private void registerDetector() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(stepDetector, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        step = 0;
        return super.onStartCommand(intent, flags, startId);
    }

    public void unRegisterDector(){
        if(stepDetector != null && mSensorManager != null){
//            stepDetector.setStepListener(null);
            mSensorManager.unregisterListener(stepDetector);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        unRegisterDector();
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
