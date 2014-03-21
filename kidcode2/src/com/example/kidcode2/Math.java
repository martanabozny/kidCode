package com.example.kidcode2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by marta on 10.03.14.
 */
public class Math extends FunctionStrip {
    public Math(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.math, this, true);

        returnedValue = new VarInteger();
    }

    public Math(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {
        Spinner function = (Spinner) findViewById(R.id.function);
        String operation = function.getSelectedItem().toString();
        EditText a  = (EditText) findViewById(R.id.a);
        EditText b  = (EditText) findViewById(R.id.b);
        EditText result  = (EditText) findViewById(R.id.result);

        double numbera, numberb;

        try {
            numbera = Double.valueOf(a.getText().toString());
        } catch (Exception e) {
            VarInteger i = (VarInteger) getVariable(a.getText().toString(), "VarInteger");
            numbera = i.value;
        }

        try {
            numberb = Double.valueOf(b.getText().toString());
        } catch (Exception e) {
            VarInteger i = (VarInteger) getVariable(b.getText().toString(), "VarInteger");
            numberb = i.value;
        }

        double numberc = 0;

        if(operation.contains("+")){
             numberc = numbera + numberb;
        }else if(operation.contains("-")){
            numberc = numbera - numberb;
        }else if(operation.contains("x")){
            numberc = numbera * numberb;
        }else if(operation.contains("/")){
            numberc = numbera / numberb;
        }

        ((VarInteger) returnedValue).value = numberc;
        returnedValue.name = result.getText().toString();

        Toast.makeText(getContext(), "Value: " + numberc, Toast.LENGTH_SHORT).show();
    }
}
