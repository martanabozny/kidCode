package com.martas.kidcode;

import android.content.Context;
import android.view.View;
import org.json.JSONObject;


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
    public abstract Map<String, String> run(Map<String, String> previousVariables);

}
