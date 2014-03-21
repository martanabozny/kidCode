package com.example.kidcode2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by marta on 16.03.14.
 */
public class Accelerometer extends FunctionStrip implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    int x_,y_,z_;

    public Accelerometer(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.accelerometer, this, true);

        returnedValue = new VarInteger();

        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public Accelerometer(Context context) {
        this(context, null);
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event){
        SeekBar x = (SeekBar) findViewById(R.id.x);
        SeekBar y = (SeekBar) findViewById(R.id.y);
        SeekBar z = (SeekBar) findViewById(R.id.z);

        x_ = (int)(event.values[0]*5 + 50);
        y_ = (int)(event.values[1]*5 + 50);
        z_ = (int)(event.values[2]*5 + 50);

        x.setProgress(x_);
        y.setProgress(y_);
        z.setProgress(z_);
        EditText result  = (EditText) findViewById(R.id.result);

        result.setText(String.valueOf(x_));
    }

    public void run() throws UnknownVariableException {
        Spinner accels = (Spinner)findViewById(R.id.accels);
        String accel = accels.getSelectedItem().toString();
        EditText result  = (EditText) findViewById(R.id.result);

        returnedValue.name = result.toString();

        if (accel.contains("x")) {
            ((VarInteger)returnedValue).value = x_;
        } else if (accel.contains("y")) {
            ((VarInteger)returnedValue).value = y_;
        } else if (accel.contains("z")) {
            ((VarInteger)returnedValue).value = z_;
        }

        Toast.makeText(getContext(), "Value: " + x_, Toast.LENGTH_SHORT).show();

    }
}
