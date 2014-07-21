package com.martas.kidcode.Strips;

import android.content.Context;
import android.graphics.Color;
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
public class Strings extends FunctionStrip {

    private String newText = "";
    private String functionText = "";

    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.string);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("strings");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.strings_background);
        view.setText("" + name + " = " + newText + "." + functionText);
        view.setTextColor(Color.BLACK);
        view.setTextSize(25);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.strings2, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        AutoCompleteTextView text = (AutoCompleteTextView)view.findViewById(R.id.text);

        result.setText(name);
        text.setText(newText);

        addAutocomplete(context, result, previousVariables);
        addAutocomplete(context, text, previousVariables);

        TextView reverse = (TextView)view.findViewById(R.id.reverse);


        final Spinner function = (Spinner)view.findViewById(R.id.function);
        functionText = function.getSelectedItem().toString();

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

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                newText = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        function.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                functionText = function.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("text", newText);
            object.put("functionText", functionText);
            object.put("type", "Strings");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            newText = object.get("text").toString();
            functionText = object.get("functionText").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        String result = "";
        String var = previousVariables.get(newText);
        if (var != null) {
            if (functionText.contains("UPPER")){
                result = var.toUpperCase();
            }else if(functionText.contains("lower")){
                result = var.toLowerCase();
            }else if(functionText.contains("reverse")){
                result = new StringBuilder(var).reverse().toString();
            }
        } else {
            if (functionText.contains("UPPER")){
                result = newText.toUpperCase();
            }else if(functionText.contains("lower")){
                result = newText.toLowerCase();
            }else if(functionText.contains("reverse")){
                result = new StringBuilder(newText).reverse().toString();
            }
        }
        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, "" + result);
        return r;
    }

    public void accelerometerVariable(int x,int y, int z) {

    }
}
