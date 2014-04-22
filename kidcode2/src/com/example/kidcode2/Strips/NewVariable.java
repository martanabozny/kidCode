package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 20.03.14.
 */
public class NewVariable extends FunctionStrip {

    public NewVariable(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.new_variable, this, true);

        final Button cancel = (Button) findViewById(R.id.cancel);
        final View realThis = this;

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((ViewManager)realThis.getParent()).removeView(realThis);
                    returnedValue = null;

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public NewVariable(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {

        EditText name  = (EditText) findViewById(R.id.name);
        EditText value  = (EditText) findViewById(R.id.value);
        Spinner variables_types = (Spinner) findViewById(R.id.variables_types);

        String type = variables_types.getSelectedItem().toString();
        String _value = value.getText().toString();
        String _name = name.getText().toString();

        if (type.contains("String")) {
            returnedValue = new VarString();
            returnedValue.name = _name;
            ((VarString)returnedValue).value = _value;
        } else if (type.contains("Integer")){
            returnedValue = new VarInteger();
            returnedValue.name = _name;
            ((VarInteger)returnedValue).value = Double.valueOf(_value);
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            EditText name  = (EditText) findViewById(R.id.name);
            EditText value  = (EditText) findViewById(R.id.value);
            Spinner variables_types = (Spinner) findViewById(R.id.variables_types);

            object.put("variables_types", variables_types.getSelectedItemPosition());
            object.put("name", name.toString());
            object.put("value", value.toString());
            object.put("type", "NewVariable");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        try {
            EditText name  = (EditText) findViewById(R.id.name);
            EditText value  = (EditText) findViewById(R.id.value);
            Spinner variables_types = (Spinner) findViewById(R.id.variables_types);

            name.setText(object.getString("name"));
            value.setText(object.getString("value"));
            variables_types.setSelection(object.getInt("variables_types"));

        } catch (JSONException e) {

        }
    }
}
