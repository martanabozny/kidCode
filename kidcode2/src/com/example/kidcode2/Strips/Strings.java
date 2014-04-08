package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 17.03.14.
 */
public class Strings extends FunctionStrip {

    public Strings(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.strings, this, true);

        returnedValue = new VarString();
    }

    public Strings(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {

        Spinner operationSpinner = (Spinner) findViewById(R.id.functions);
        EditText result  = (EditText) findViewById(R.id.result);
        String text  = ((EditText) findViewById(R.id.editable_string)).toString();
        CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

        String operation = operationSpinner.getSelectedItem().toString();
        ((VarString) returnedValue).name = result.toString();

        if (isVariable.isChecked()) {
            VarString variable_name = (VarString) getVariable(text, "VarString");
            text = variable_name.value;
        }


        if(operation.contains("UPPER")){
            ((VarString)returnedValue).value = text.toUpperCase();
        }else if(operation.contains("lower")){
            ((VarString)returnedValue).value = text.toLowerCase();
        }else if(operation.contains("invert")){
            ((VarString)returnedValue).value = new StringBuilder(text).reverse().toString();
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Spinner operationSpinner = (Spinner) findViewById(R.id.functions);
            EditText result  = (EditText) findViewById(R.id.result);
            String text  = ((EditText) findViewById(R.id.editable_string)).toString();
            CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

            object.put("functions", operationSpinner.getSelectedItemPosition());
            object.put("result", result.toString());
            object.put("text", text.toString());
            object.put("isVariable", isVariable.toString());
            object.put("type", "Strings");

        } catch (JSONException e) {

        }
        return object;
    }
}
