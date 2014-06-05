package com.martas.kidcode.Strips;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class Math extends FunctionStrip {
    private String a = "";
    private String b = "";


    public View getButton(Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.math);
        return button;
    }
    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = " + a + " + " + b);
        return view;
    }
    public View getSetup(Context context, Map<String, String> previousVariables) {
        TextView view = new TextView(context);
        view.setText("" + name + " = " + a + " + " + b);
        return view;
    }
    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("a", a);
            object.put("b", b);
            object.put("type", "Math");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            a = object.get("a").toString();
            b = object.get("b").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public Map<String, String> run(Map<String, String> previousVariables) {
        return  null;
    }
}
