package com.martas.kidcode.Strips;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Math extends FunctionStrip {
    private String a = "0";
    private String b ="0";
    private String function = "+";


    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.math);
        return button;
    }
    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.math_background);
        view.setText("" + name + " = " + a + " " + function + " " + b);
        view.setTextSize(30);
        view.setTextColor(Color.BLACK);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.math, null);
        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        AutoCompleteTextView a_text = (AutoCompleteTextView)view.findViewById(R.id.a);
        AutoCompleteTextView b_text = (AutoCompleteTextView)view.findViewById(R.id.b);

        result.setText(name);
        a_text.setText(a);
        b_text.setText(b);

        addAutocomplete(context, result, previousVariables);
        addAutocomplete(context, a_text, previousVariables);
        addAutocomplete(context, b_text, previousVariables);

        final Spinner spinner = (Spinner)view.findViewById(R.id.function);
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

        a_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                a = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        b_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                b = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                function = spinner.getSelectedItem().toString();
                 Log.d("Math.itemListener", "Selected function: " + function);
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
            object.put("type", "Math");
            object.put("a", a);
            object.put("b", b);
            object.put("function", function);
            object.put("name", name);

        } catch (JSONException e) {
            Log.e("kidcode.math", e.toString());
        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            a = object.getString("a");
            b = object.getString("b");
            function = object.getString("function");
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) throws ConvertException,VariableLackException{
        int aint = variableToInt(a, previousVariables);
        int bint = variableToInt(b, previousVariables);
        Log.e("Math.run", "a: " + aint);
        Log.e("Math.run", "b: " + bint);
        int result = 0;
        if (function.contains("+")) {
            result = aint + bint;
        } else if (function.contains("-")) {
            result = aint - bint;
        } else if (function.contains("/")) {
            result = aint / bint;
        } else if (function.contains("x")) {
            result = aint * bint;
        }
        Log.e("Math.run", "" + result);
        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, "" + result);
        return r;
    }

    public int accelerometerVariable(int x,int y, int z) {
        return 0;
    }
}
