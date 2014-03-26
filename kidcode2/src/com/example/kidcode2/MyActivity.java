package com.example.kidcode2;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MyActivity extends Activity { //implements View.OnTouchListener, View.OnDragListener {

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

        /*findViewById(R.id.Math_Button).setOnTouchListener(this);
        findViewById(R.id.code).setOnDragListener(this);
        findViewById(R.id.icons).setOnDragListener(this);
*/
    }

    /*public boolean onTouch(View v, MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(null, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent e) {
        if (e.getAction()==DragEvent.ACTION_DROP) {
            View view = (View) e.getLocalState();
            ViewGroup from = (ViewGroup) view.getParent();
            from.removeView(view);
            LinearLayout to = (LinearLayout) v;
            to.addView(view);
            view.setVisibility(View.VISIBLE);
        }
        return true;
    }*/
    public void AddStringStrip(View view) {
        Strings string = new Strings(this);
        if (layout.getChildCount() > 0)
            string.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(string);
    }

    public void AddMathStrip(View view) {
        Math math = new Math(this);
        if (layout.getChildCount() > 0)
            math.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(math);
    }

    public void AddFotoStrip(View view) {
        Foto foto = new Foto(this);
        if (layout.getChildCount() > 0)
            foto.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(foto);
    }

    public void AddAccelerometerStrip(View view) {
        Accelerometer accelerometer = new Accelerometer(this);
        if (layout.getChildCount() > 0)
            accelerometer.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
        layout.addView(accelerometer);
    }

    public void runCode(View view) {
        int count = layout.getChildCount();
        for (int i = 0; i<count; i++) {
           FunctionStrip strip = (FunctionStrip) layout.getChildAt(i);
            try {
                strip.run();
            } catch (UnknownVariableException e) {
                Toast.makeText(this, "Cannot find variable: " + e.toString(), Toast.LENGTH_SHORT).show();
                break;
            } catch (Exception e) {
                Toast.makeText(this, "Something has gone wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }
}
