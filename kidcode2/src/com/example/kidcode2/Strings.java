package com.example.kidcode2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

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

    public void run() {
        Spinner operationSpinner = (Spinner) findViewById(R.id.functions);
        String operation = operationSpinner.getSelectedItem().toString();
        EditText result  = (EditText) findViewById(R.id.result);
        String text  = ((EditText) findViewById(R.id.editable_string)).toString();
        CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

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
}
