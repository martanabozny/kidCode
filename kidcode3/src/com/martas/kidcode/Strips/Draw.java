package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.martas.kidcode.Setup;
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

        ArrayList<String> list = new ArrayList<String>();
        GridView colors = (GridView)view.findViewById(R.id.colors);
        ColorsAdapter adapter = new ColorsAdapter(context, list);
        colors.setAdapter(adapter);
        colors.setNumColumns(view.getResources().getDisplayMetrics().widthPixels / 240);

        list.add("#FFFFFF");
        list.add("#FFFF00");
        list.add( "#FF0000");
        list.add("#808080");
        list.add("#800080");
        list.add( "#008000");
        list.add( "#0000FF");

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

    public class ColorsAdapter extends ArrayAdapter<String> {
        public ColorsAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageButton button = new ImageButton(getContext());
            button.setBackgroundColor(Color.parseColor(getItem(position)));
            button.setMinimumHeight(80);
            button.setPadding(20, 20, 20, 20);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorText = getItem(position);
                    ImageView colorPreview = (ImageView)view.findViewById(R.id.colorPreview);
                }
            });

//            button.setAdjustViewBounds(false);
//            button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            button.setPadding(5, 5, 5, 5);

            return button;
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
