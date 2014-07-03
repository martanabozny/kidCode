package com.martas.kidcode.Strips;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Buttons;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
* Created by marta on 01.06.14.
*/
public class Draw extends FunctionStrip {

    private String figureText = "";
    private String colorText = "";

    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.math);
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = " + colorText + " " + figureText);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.draw, null);
        Button button = null;

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        addAutocomplete(context, result, previousVariables);

        ArrayList<View> list = new ArrayList<View>();
        GridView colors = (GridView)view.findViewById(R.id.colors);
        ColorsAdapter adapter = new ColorsAdapter(context, list);
        colors.setAdapter(adapter);
        colors.setNumColumns(view.getResources().getDisplayMetrics().widthPixels / 240);

       

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        adapter.notifyDataSetChanged();

        final Spinner figure = (Spinner)view.findViewById(R.id.figure);
        figureText = figure.getSelectedItem().toString();

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

        figure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                figureText = figure.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public class ColorsAdapter extends ArrayAdapter<View> {
        public ColorsAdapter(Context context, ArrayList<View> list) {
            super(context, R.layout.codeactivity, list);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            return getItem(position);
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("figure", figureText);
            object.put("color", colorText);
            object.put("type", "Draw");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            figureText = object.get("figure").toString();
            colorText = object.get("color").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
//        String result = "";
//        String var = previousVariables.get(newText);
//        if (var != null) {
//            if (functionText.contains("UPPER")){
//                result = var.toUpperCase();
//            }else if(functionText.contains("lower")){
//                result = var.toLowerCase();
//            }else if(functionText.contains("reverse")){
//                result = new StringBuilder(var).reverse().toString();
//            }
//        } else {
//            if (functionText.contains("UPPER")){
//                result = newText.toUpperCase();
//            }else if(functionText.contains("lower")){
//                result = newText.toLowerCase();
//            }else if(functionText.contains("reverse")){
//                result = new StringBuilder(newText).reverse().toString();
//            }
//        }
//        HashMap<String, String> r = new HashMap<String, String>();
//        r.put(name, "" + result);
        return null;
    }
}
