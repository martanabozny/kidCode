package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.Strips.FunctionStrip;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class If_Strip extends FunctionStrip {

    public If_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.if_strip, this, true);

        returnedValue = null;

        Spinner condition = (Spinner) this.findViewById(R.id.condition);
        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                condition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public If_Strip(Context context) {
        this(context, null);
    }

    public void condition() {
        try {
            Spinner condition = (Spinner)findViewById(R.id.condition);
            String operation = condition.getSelectedItem().toString();
            HorizontalScrollView scroll = (HorizontalScrollView)findViewById(R.id.scroll);

            scroll.removeAllViews();

            if (operation.equals("check")){
               Check check = new check();
                scroll.addView(check);
            }
            else if (operation.equals("compare")){
                Compare compare = new compare();
                scroll.addView(compare);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("type", "If_Strip");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){

    }
}
