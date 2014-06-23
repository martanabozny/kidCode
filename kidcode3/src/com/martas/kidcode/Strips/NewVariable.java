package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class NewVariable extends FunctionStrip {

    private String value = "";
    private String functionText = "";


    public View getButton(final Context context, final int position) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.new_variable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Setup.class);
                intent.putExtra("strip", toJson().toString());
                intent.putExtra("position", String.valueOf(position));
                context.startActivity(intent);
            }
        });
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + functionText + " " + name + " = " + value);
        return view;
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.new_variable, null);

        EditText result = (EditText)view.findViewById(R.id.result);
        EditText value = (EditText)view.findViewById(R.id.value);
        Spinner function = (Spinner)view.findViewById(R.id.kind);
        functionText = function.getSelectedItem().toString();

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
            object.put("value", value);
            object.put("functionText", functionText);
            object.put("type", "NewVariable");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            value = object.get("value").toString();
            functionText = object.get("functionText").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(HashMap<String, String> previousVariables) {
        return  null;
    }
}
