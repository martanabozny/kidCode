package com.martas.kidcode;

import android.content.Context;
import android.view.View;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public abstract class FunctionStrip extends Object {
    protected String name= "";


    public abstract View getButton(Context context, int position);
    public abstract View getPreview(Context context);
    public abstract View getSetup(Context context, Map<String, String> previousVariables);
    public abstract JSONObject toJson();
    public abstract void fromJson(JSONObject object);
    public abstract HashMap<String, String> run(HashMap<String, String> previousVariables) throws StopException,VariableLackException,ConvertException;

    public int variableToInt(String variable, HashMap<String,String> map) throws  VariableLackException,ConvertException {
        try {
            int v = Integer.valueOf(variable);
            return v;
        } catch(Exception e) {
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
