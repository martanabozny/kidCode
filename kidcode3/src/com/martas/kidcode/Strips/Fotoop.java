package com.martas.kidcode.Strips;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Fotoop extends FunctionStrip {

    private String functionText = "";
    private String variableText = "";
    Bitmap newFoto;



    public View getButton(final Context context, final int position, final JSONArray variables) {
        ImageButton button = getMyButton(context, position, variables);
        button.setBackgroundResource(R.drawable.fotoop);
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = " + variableText + "." + functionText);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fotoop, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        AutoCompleteTextView variable = (AutoCompleteTextView)view.findViewById(R.id.variable);
        Spinner function = (Spinner)view.findViewById(R.id.function);
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

        variable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                variableText = charSequence.toString();
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
            object.put("variable", variableText);
            object.put("function", functionText);
            object.put("type", "Fotoop");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            functionText = object.get("function").toString();
            variableText = object.get("variable").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(HashMap<String, String> previousVariables) {

        String var = previousVariables.get(variableText);
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode/fotos";
        String eol = System.getProperty("line.separator");
        BufferedWriter writer = null;
        try {
            //writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(path + name, Context.MODE_PRIVATE)));
            //writer.write(newFoto);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, "" + newFoto);
        return r;
    }
}
