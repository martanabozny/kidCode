package com.martas.kidcode.Strips;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.martas.kidcode.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Stop extends FunctionStrip {
    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.stop);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("stop programm");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.stop_background);
        view.setText("Show variable:" + " " + name);
        view.setTextSize(20);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.stop, null);

        AutoCompleteTextView result = (AutoCompleteTextView) view.findViewById(R.id.result);
        result.setText(name);
        addAutocomplete(context, result, previousVariables);

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

    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) throws StopException, VariableLackException {
        String value = previousVariables.get(name);
        if (value == null) {
            throw new VariableLackException();
        }
        throw new StopException(name, value);
    }

    public void accelerometerVariable(int x, int y, int z) {

    }
}
