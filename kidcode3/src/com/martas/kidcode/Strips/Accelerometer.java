package com.martas.kidcode.Strips;

import android.content.Context;
import android.hardware.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Accelerometer extends FunctionStrip implements SensorEventListener {
    private String accel = "x";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    int x_,y_,z_;
    View view;
    boolean has_result = false;

    enum Mode {
        SETUP,
        PREVIEW,
        HIDDEN,
    }

    Mode mode = Mode.HIDDEN;

    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.accel);
        return button;
    }

    public View getPreview(Context context) {
        mode = Mode.PREVIEW;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.accelerometerpreview, null);

        TextView result = (TextView)view.findViewById(R.id.result);
        result.setText(name);

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        x_ = (int)(event.values[0]*5 + 50);
        y_ = (int)(event.values[1]*5 + 50);
        z_ = (int)(event.values[2]*5 + 50);

        has_result = true;

        if (mode == Mode.SETUP) {
            Spinner spinner = (Spinner)view.findViewById(R.id.accels);
            SeekBar x = (SeekBar) view.findViewById(R.id.x);
            SeekBar y = (SeekBar) view.findViewById(R.id.y);
            SeekBar z = (SeekBar) view.findViewById(R.id.z);

            x.setProgress(x_);
            y.setProgress(y_);
            z.setProgress(z_);
        } else if (mode == Mode.PREVIEW) {
            SeekBar value = (SeekBar)view.findViewById(R.id.value);
            if(accel.equals("x")) {
                value.setProgress(x_);
            } else if (accel.equals("y")) {
                value.setProgress(y_);
            } else if (accel.equals("z")) {
                value.setProgress(z_);
            }
        }
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        mode = Mode.SETUP;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.accelerometer, null);

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        addAutocomplete(context, result, previousVariables);

        final Spinner acceles = (Spinner)view.findViewById(R.id.accels);

        acceles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accel = acceles.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        accel = acceles.getSelectedItem().toString();

        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }


    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("accel", accel);
            object.put("type", "accelerometer");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            accel = object.get("accel").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        while (!has_result) {
            Log.e("run", "sleep");
            try {
                Thread.sleep(100, 0);
            } catch (Exception e) {

            }
        }

        HashMap<String, String> r = new HashMap<String, String>();
        if(accel.equals("x")) {
            r.put(name, "" + x_);
        } else if (accel.equals("y")) {
            r.put(name, "" + y_);
        } else if (accel.equals("z")) {
            r.put(name, "" + z_);
        }

        Log.e("Accel.run", r.get(name));

        return r;
    }
}
