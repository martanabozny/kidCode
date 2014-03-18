package com.example.kidcode2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;

/**
 * Created by marta on 18.03.14.
 */
public class Foto extends FunctionStrip {

    public Foto(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.foto, this, true);

        returnedValue = new VarImage();
    }

    public Foto(Context context) {
        this(context, null);
    }

    public void run(){

    }
}
