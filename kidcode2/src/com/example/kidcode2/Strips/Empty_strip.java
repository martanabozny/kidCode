package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 25.04.14.
 */
public class Empty_strip extends FunctionStrip {

    public Empty_strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.empty_strip, this, true);
    }

    public Empty_strip(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {

            object.put("type", "Empty_strip");

        } catch (JSONException e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return object;
    }

    public void fromJson(JSONObject object){}
}
