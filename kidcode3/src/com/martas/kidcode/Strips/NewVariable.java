package com.martas.kidcode.Strips;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class NewVariable extends FunctionStrip {

    private String valueText = "";
    private String kind = "";


    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.new_variable);
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.new_variable_background);
        view.setText("" + kind + " " + name + " = " + valueText);
        view.setTextSize(20);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_variable, null);

        EditText result = (EditText)view.findViewById(R.id.result);
        EditText value = (EditText)view.findViewById(R.id.value);
        Spinner function = (Spinner)view.findViewById(R.id.kind);
        kind = function.getSelectedItem().toString();

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

        value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                valueText = charSequence.toString();
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
            object.put("value", valueText);
            object.put("kind", kind);
            object.put("type", "NewVariable");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            valueText = object.get("value").toString();
            kind = object.get("kind").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {

        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, "" + valueText);
        return r;
    }

    public void accelerometerVariable(int x,int y, int z) {

    }
}
