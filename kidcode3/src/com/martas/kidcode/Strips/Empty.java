package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Buttons;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONException;
import org.json.JSONObject;

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
    public Map<String, String> run(Map<String, String> previousVariables) {
        return  null;
    }
}
