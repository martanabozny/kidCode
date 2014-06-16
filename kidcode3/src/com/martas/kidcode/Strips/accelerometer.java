package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class accelerometer extends FunctionStrip implements SensorEventListener {
    private String accel = "";
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    int x_,y_,z_;
    View view;



    public View getButton(final Context context, final int position) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.accel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Setup.class);
                intent.putExtra("strip", toJson().toString());
                intent.putExtra("position", String.valueOf(position));
                context.startActivity(intent);
            }
        });
        return button;
    }

    public View getPreview(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.accelerometerpreview, null);

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event){

        SeekBar value = (SeekBar)view.findViewById(R.id.value);

        if(accel.equals("x")){
            x_ = (int)(event.values[0]*5 + 50);
            value.setProgress(x_);
        } else if (accel.equals("y")) {
            y_ = (int)(event.values[1]*5 + 50);
            value.setProgress(y_);
        } else if (accel.equals("z")) {
            z_ = (int)(event.values[2]*5 + 50);
            value.setProgress(z_);
        }
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.accelerometer, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        Spinner acceles = (Spinner)view.findViewById(R.id.accels);
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
            accel= object.get("function").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public Map<String, String> run(Map<String, String> previousVariables) {
        return  null;
    }
}
