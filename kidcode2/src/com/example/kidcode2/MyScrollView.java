package com.example.kidcode2;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.example.kidcode2.R;
import com.example.kidcode2.Strips.*;
import com.example.kidcode2.Strips.Math;
import com.example.kidcode2.UnknownVariableException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Attr;

import java.util.ArrayList;

/**
 * Created by marta on 09.04.14.
 */
public class MyScrollView extends ScrollView {
    LinearLayout layout;
    public FunctionStrip previous = null;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout);

        Empty_strip empty_strip = new Empty_strip(getContext());
        empty_strip.setOnDragListener(new MyDragListener());
        layout.addView(empty_strip);

        setOnDragListener(new MyDragListener());

    }

    public void runCode() {
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            FunctionStrip strip = (FunctionStrip) layout.getChildAt(i);
            try {
                strip.run();
            } catch (UnknownVariableException e) {
                Toast.makeText(getContext(), "Cannot find variable: " + e.toString(), Toast.LENGTH_SHORT).show();
                break;
            } catch (Exception e) {
                Toast.makeText(getContext(), "Something has gone wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    public void fromJson(String strips) {
        layout.removeAllViews();

        try {
            JSONArray list = new JSONArray(strips);
            for (int i = 0; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);

                FunctionStrip fstrip = null;

                if (object.getString("type").equals("Math"))
                    fstrip = new Math(getContext());
                if (object.getString("type").equals("Strings"))
                    fstrip = new Strings(getContext());
                if (object.getString("type").equals("Accelerometer"))
                    fstrip = new Accelerometer(getContext());
                if (object.getString("type").equals("Condition_Strip"))
                    fstrip = new Condition_Strip(getContext());
                if (object.getString("type").equals("NewVariable"))
                    fstrip = new NewVariable(getContext());
                if (object.getString("type").equals("ShowVariable"))
                    fstrip = new ShowVariable(getContext());
                if (object.getString("type").equals("Empty_strip"))
                    fstrip = new Empty_strip(getContext());

                fstrip.fromJson(object);
                if (layout.getChildCount() != 0) {
                    fstrip.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount() - 1);
                }
                else {
                    fstrip.previous = previous;
                }
                layout.addView(fstrip);
                fstrip.setOnDragListener(new MyDragListener());

            }
        } catch (JSONException e) {

        } catch (NullPointerException e) {
            Toast.makeText(getContext(), "Unsuported strip", Toast.LENGTH_LONG).show();
        }
    }

    public String toJson() {
        try {
            JSONArray array = new JSONArray();

            int count = layout.getChildCount();
            for (int i = 0; i < count; i++) {
                FunctionStrip strip = (FunctionStrip) layout.getChildAt(i);
                JSONObject object = strip.toJson();
                array.put(object);
            }

            return array.toString();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Cannot json: " + e.toString(), Toast.LENGTH_LONG).show();
            return "";
        }
    }


    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            if (event.getAction() == DragEvent.ACTION_DROP) {
                FunctionStrip strip = null;

                switch(((View)event.getLocalState()).getId()){
                    case R.id.Math_Button:
                        strip = new Math(getContext());
                        break;

                    case R.id.Accelerometer_Button:
                        strip = new Accelerometer(getContext());
                        break;

                    case R.id.While_Button:
                        strip = new Condition_Strip(getContext());
                        break;

                    case R.id.If_Button:
                        strip = new Condition_Strip(getContext());
                        break;

                    case R.id.Foto_Button:
                        strip = new Foto(getContext());
                        break;

                    case R.id.Strings_Button:
                        strip = new Strings(getContext());
                        break;

                    case R.id.New_Variable_Button:
                        strip = new NewVariable(getContext());
                        break;

                    case R.id.Show_Variable_Button:
                        strip = new ShowVariable(getContext());
                        break;

                    default:
                        break;
                }
                if (strip == null) {
                    return true;
                }


                int index = layout.indexOfChild(v);
                if (index == -1) {
                    if (layout.getChildCount() != 0) {

                        strip.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
                    } else {
                        strip.previous = previous;
                    }
                    layout.addView(strip);
                } else {
                    if (layout.getChildCount() > index) {
                        FunctionStrip next = (FunctionStrip) layout.getChildAt(index+1);
                        next.previous = strip;
                    }

                    layout.addView(strip, index+1);
                    strip.previous = (FunctionStrip)v;
                    strip.previous.makeNormal();
                }

                strip.setOnDragListener(new MyDragListener());
            } else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
                try {
                    FunctionStrip strip = (FunctionStrip) v;
                    strip.makeShadow();
                } catch (Exception e) {

                }
            } else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED) {
                try {
                    FunctionStrip strip = (FunctionStrip) v;
                    strip.makeNormal();
                } catch (Exception e) {

                }
            }
            return  true;
        }
    }
}
