package com.example.kidcode2;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.Strips.*;
import com.example.kidcode2.Strips.Math;


public class MyActivity extends Activity {

    LinearLayout layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ScrollView code = (ScrollView) findViewById(R.id.code);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        code.addView(layout);
        code.setOnDragListener(new MyDragListener());

        findViewById(R.id.Math_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Accelerometer_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.While_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.If_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Foto_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.FotoOp_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Show_Variable_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Strings_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.New_Variable_Button).setOnTouchListener(new MyTouchListener());

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

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                //view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            if(event.getAction() == DragEvent.ACTION_DROP) {
                FunctionStrip strip = null;

                switch(((View)event.getLocalState()).getId()){
                    case R.id.Math_Button:
                        strip = new Math(getApplicationContext());
                        break;

                    case R.id.Accelerometer_Button:
                        strip = new Accelerometer(getApplicationContext());
                        break;

                    case R.id.While_Button:
                        strip = new While_Strip(getApplicationContext());
                        break;

                    case R.id.If_Button:
                        strip = new If_Strip(getApplicationContext());
                        break;

                    case R.id.Foto_Button:
                        strip = new Foto(getApplicationContext());
                        break;

                    case R.id.Strings_Button:
                        strip = new Strings(getApplicationContext());
                        break;

                    case R.id.New_Variable_Button:
                        strip = new NewVariable(getApplicationContext());
                        break;

                    default:
                        break;
                }
                if (strip == null) {
                    return true;
                }

                int index = layout.indexOfChild(v);
                if (index == -1) {
                    layout.addView(strip);
                } else {
                    layout.addView(strip, index+1);
                }

                strip.setOnDragListener(new MyDragListener());
            }
            return  true;
        }
    }
}
