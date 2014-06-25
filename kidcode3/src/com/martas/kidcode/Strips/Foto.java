package com.martas.kidcode.Strips;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Foto extends FunctionStrip {
    public View getButton(final Context context, final int position, final JSONArray variables) {
        ImageButton button = getMyButton(context, position, variables);
        button.setBackgroundResource(R.drawable.foto);
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = ");
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.foto, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);

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
        return view;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "Foto");
            object.put("name", name);
        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object) {
        try {
            name = object.get("name").toString();
        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        return  null;
    }
}
