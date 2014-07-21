package com.martas.kidcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public abstract class FunctionStrip extends Object {

    protected String name = "";

    protected void addAutocomplete(Context context, AutoCompleteTextView field, JSONArray previousVariables) {
        ArrayList<String> variables = new ArrayList<String>();
        for (int i = 0; i < previousVariables.length(); i++) {
            try {
                variables.add(previousVariables.getString(i));
            } catch (Exception e) {

            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_2, variables);
        field.setAdapter(adapter);
        field.performCompletion();
    }

    public void onActivityResult(Intent i) {

    }

    public abstract LinearLayout getButton(Context context);
    public abstract View getPreview(Context context);
    public abstract View getSetup(Context context, JSONArray previousVariables);
    public abstract JSONObject toJson();
    public abstract void fromJson(JSONObject object);
    public abstract HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) throws StopException,VariableLackException,ConvertException;

    public int variableToInt(String variable, HashMap<String,String> map) throws  VariableLackException,ConvertException {
        try {
            int v = Integer.valueOf(variable);
            return v;
        } catch(Exception e) {
            Log.e("kidcode", "Cannot convert " + variable + " to int. Trying to find variable...");
            String var = map.get(variable);
            if (var == null) {
                Log.e("kidcode", "Cannot find variable named " + variable);
                throw new VariableLackException();
            }
            try {
                int v = Integer.valueOf(var);
                return v;
            } catch (Exception x) {
                Log.e("kidcode", "Found variable, but cannot be converted to int");
                throw new ConvertException();
            }
        }
    }

    public Bitmap variableToBitmap(String variable, HashMap<String, String> map) throws ConvertException {
        File file = new File(variable);

        if (!file.exists()) {
            String value = map.get(variable);
            if (value == null) {
                throw new ConvertException();
            } else {
                file = new File(value);
                if (!file.exists()) {
                    throw new ConvertException();
                }
            }
        }

        try {
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bmp == null) {
                throw new ConvertException();
            }
            return bmp;
        } catch (Exception e) {
            throw new ConvertException();
        }
    }

    public void accelerometerVariable(int x, int y, int z) {

    }
}
