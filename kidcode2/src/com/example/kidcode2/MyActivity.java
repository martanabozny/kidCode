package com.example.kidcode2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
    LinearLayout layout;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ScrollView code = (ScrollView) findViewById(R.id.code);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        code.addView(layout);

    }



    public void AddMathStrip(View view) {
        Math math = new Math(this);
        if (layout.getChildCount() > 0)
            math.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(math);
        Toast.makeText(this, "MathStrip added", Toast.LENGTH_SHORT).show();
    }

    public void AddAccelerometerStrip(View view) {
        Accelerometer accelerometer = new Accelerometer(this);
        if (layout.getChildCount() > 0)
            accelerometer.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(accelerometer);
        Toast.makeText(this, "AccelerometerStrip added", Toast.LENGTH_SHORT).show();
    }

    public void runCode(View view) {
       int count = layout.getChildCount();
       for (int i = 0; i<count; i++) {
           FunctionStrip strip = (FunctionStrip) layout.getChildAt(i);
           strip.run();
       }
    }
}
