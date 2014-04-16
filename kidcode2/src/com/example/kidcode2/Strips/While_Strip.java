package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class While_Strip extends FunctionStrip {

    public While_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.if_strip, this, true);

        returnedValue = null;

    }

    public While_Strip(Context context) {
        this(context, null);
    }


    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Spinner condition = (Spinner)findViewById(R.id.condition);
            HorizontalScrollView scroll = (HorizontalScrollView)findViewById(R.id.scroll);
            View view = (View)findViewById(R.id.view);

            object.put("condition", condition.getSelectedItemPosition());
            object.put("type", "If_Strip");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        Spinner condition = (Spinner)findViewById(R.id.condition);
        HorizontalScrollView scroll = (HorizontalScrollView)findViewById(R.id.scroll);
        View view = (View)findViewById(R.id.view);

        try {
            condition.setSelection(object.getInt("condition"));

        } catch (JSONException e) {

        }
    }
}