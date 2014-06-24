package com.martas.kidcode.Strips;

import android.content.Context;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class Empty extends FunctionStrip {

    public View getButton(final Context context, final int position) {

       TextView text = new TextView(context);
        return text;
    }
    public View getPreview(Context context) {
        TextView view = new TextView(context);
        return view;
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
       TextView text = new TextView(context);
       return text;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "Empty");


        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {

    }
    public HashMap<String, String> run(HashMap<String, String> previousVariables) {
        return  null;
    }
}