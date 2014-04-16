package com.example.kidcode2;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
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

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout);
        setOnDragListener(new MyDragListener());
    }

    public void runCode() {
        int count = layout.getChildCount();
        for (int i = 0; i<count; i++) {
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

    public void fromJson(String strips){
        try {
            JSONArray list = new JSONArray(strips);
            for (int i = 0; i < list.length(); i++) {
                JSONObject object = list.getJSONObject(i);

                FunctionStrip fstrip = null;

                if (object.getString("type").equals("Math"))
                    fstrip = new com.example.kidcode2.Strips.Math(getContext());
                if (object.getString("type").equals("Strings"))
                    fstrip = new Strings(getContext());
                if (object.getString("type").equals("Accelerometer"))
                    fstrip = new Accelerometer(getContext());
                if (object.getString("type").equals("If_Strip"))
                    fstrip = new If_Strip(getContext());

                fstrip.fromJson(object);
                layout.addView(fstrip);
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
            if(event.getAction() == DragEvent.ACTION_DROP) {
                FunctionStrip strip = null;

                switch(((View)event.getLocalState()).getId()){
                    case R.id.Math_Button:
                        strip = new Math(getContext());

                        break;

                    case R.id.Accelerometer_Button:
                        strip = new Accelerometer(getContext());
                        break;

                    case R.id.While_Button:
                        strip = new While_Strip(getContext());
                        break;

                    case R.id.If_Button:
                        strip = new If_Strip(getContext());
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

                    default:
                        break;
                }
                if (strip == null) {
                    return true;
                }


                int index = layout.indexOfChild(v);
                if (index == -1) {
                    if (layout.getChildCount() != 0){
                        strip.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount()-1);
                    }
                    layout.addView(strip);
                } else {
                    layout.addView(strip, index+1);
                    strip.previous = (FunctionStrip)v;
                }

                strip.setOnDragListener(new MyDragListener());
            }
            return  true;
        }
    }
}
