package com.example.kidcode2.Strips;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.MyScrollView;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        final Button result = (Button) findViewById(R.id.result);
        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList <String> list = collectVariables("");
                selectVariable(list, result, true);
            }
        });

        final Button cancel = (Button) findViewById(R.id.cancel);
        final View realThis = this;

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((ViewManager)realThis.getParent()).removeView(realThis);
                    returnedValue = null;
                
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
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
    }

    public void run() throws UnknownVariableException {
        Spinner accels = (Spinner)findViewById(R.id.accels);
        String accel = accels.getSelectedItem().toString();
        Button result  = (Button) findViewById(R.id.result);

        returnedValue.name = result.toString();

        if (accel.contains("x")) {
            ((VarInteger)returnedValue).value = x_;
        } else if (accel.contains("y")) {
            ((VarInteger)returnedValue).value = y_;
        } else if (accel.contains("z")) {
            ((VarInteger)returnedValue).value = z_;
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Spinner accels = (Spinner)findViewById(R.id.accels);
            Button result  = (Button) findViewById(R.id.result);

            object.put("axis", accels.getSelectedItemPosition());
            object.put("result", result.getText().toString());
            object.put("type", "Accelerometer");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        Spinner accels = (Spinner)findViewById(R.id.accels);
        Button result  = (Button) findViewById(R.id.result);

        try {
            accels.setSelection(object.getInt("accels"));
            result.setText(object.getString("result"));

        } catch (JSONException e) {

        }
    }
}
