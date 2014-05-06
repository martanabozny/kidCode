package com.example.kidcode2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.example.kidcode2.Strips.*;
import com.example.kidcode2.Strips.Math;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 09.04.14.
 */
public class MyScrollView extends ScrollView {
    LinearLayout layout;
    public FunctionStrip previous = null;
    private EmptyStrip empty_strip;

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        addView(layout);

        empty_strip = new EmptyStrip(getContext());
        empty_strip.setOnDragListener(new MyDragListener());
        layout.addView(empty_strip);

        setOnDragListener(new MyDragListener());
    }

    public void setPrevious(FunctionStrip strip) {
        empty_strip.previous = strip;
        previous = strip;
    }

    public void runCode() throws UnknownVariableException, StopException, VariableConvertException{
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            FunctionStrip strip = (FunctionStrip) layout.getChildAt(i);
            strip.run();
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
                if (object.getString("type").equals("IfStrip"))
                    fstrip = new IfStrip(getContext());
                if (object.getString("type").equals("WhileStrip"))
                    fstrip = new WhileStrip(getContext());
                if (object.getString("type").equals("NewVariable"))
                    fstrip = new NewVariable(getContext());
                if (object.getString("type").equals("ShowVariable"))
                    fstrip = new ShowVariable(getContext());
                if (object.getString("type").equals("EmptyStrip"))
                    fstrip = new EmptyStrip(getContext());
                if (object.getString("type").equals("StopStrip"))
                    fstrip = new StopStrip(getContext());

                if (layout.getChildCount() != 0) {
                    fstrip.previous = (FunctionStrip) layout.getChildAt(layout.getChildCount() - 1);
                }
                else {
                    fstrip.previous = previous;
                }
                fstrip.fromJson(object);
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
                        strip = new WhileStrip(getContext());
                        break;

                    case R.id.If_Button:
                        strip = new IfStrip(getContext());
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
                    case R.id.Stop_Button:
                        strip = new StopStrip(getContext());
                        break;

                    default:
                        break;
                }
                if (strip == null) {
                    return true;
                }

                int position = layout.indexOfChild(v);
                if (position == -1) {
                    strip.previous = (FunctionStrip)layout.getChildAt(layout.getChildCount()-1);
                    layout.addView(strip);
                } else {
                    strip.previous = (FunctionStrip)v;
                    if (layout.getChildCount() >= position+2) {
                        FunctionStrip stripNext = (FunctionStrip)layout.getChildAt(position + 1);
                        stripNext.previous = strip;
                    }
                    layout.addView(strip, position + 1);
                    ((FunctionStrip)v).makeNormal();
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
