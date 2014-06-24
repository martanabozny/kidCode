package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.martas.kidcode.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class Stop extends FunctionStrip {


    public View getButton(final Context context, final int position) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.stop);
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
        view.setText("Show as program result:" + name );
        return view;
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.stop, null);

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

            }});
        return view;
    }


    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {

            object.put("type", "Stop");
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
    public HashMap<String, String> run(HashMap<String, String> previousVariables) throws StopException,VariableLackException {
        String value = previousVariables.get(name);
        if (value == null) {
            throw new VariableLackException();
        }
        throw new StopException(name, value);
    }
}