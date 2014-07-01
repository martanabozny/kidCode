package com.martas.kidcode;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public abstract class FunctionStrip extends Object {
    protected String name= "";
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

    public abstract ImageButton getButton(Context context);
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
            Log.e("FunctionStrip.variableToInt", "Cannot convert " + variable + " to int");
            String var = map.get(variable);
            if (var == null) {
                throw new VariableLackException();
            }
            try {
                int v = Integer.valueOf(var);
                return v;
            } catch (Exception x) {
                throw new ConvertException();
            }
        }
    }
}
