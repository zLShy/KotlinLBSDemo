package com.zl.map;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by zhangli on 2018/6/4.
 */

public class StepDetector implements SensorEventListener {

    private StepChangeListener mStepListener;

    public void OnStepDetector(StepChangeListener mStepListener) {
        this.mStepListener = mStepListener;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
