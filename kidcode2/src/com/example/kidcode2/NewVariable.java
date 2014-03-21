package com.example.kidcode2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by marta on 20.03.14.
 */
public class NewVariable extends FunctionStrip {

    public NewVariable(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.new_variable, this, true);
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
        } else if (type.contains("int")){
            returnedValue = new VarInteger();
            returnedValue.name = _name;
            ((VarInteger)returnedValue).value = Double.valueOf(_value);
        }

    }
}
