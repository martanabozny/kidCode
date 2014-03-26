package com.example.kidcode2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

        Button result = (Button) findViewById(R.id.result);
        Button a = (Button) findViewById(R.id.number1);
        Button b = (Button) findViewById(R.id.number2);

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

    public void OnResultClick(View view){
        Button result = (Button) findViewById(R.id.result);
        String variable = selectVariable("VarInteger");

        result.setText(variable);
        returnedValue.name = variable;
    }

    public void OnNumber2Click(View view){
        Button number2 = (Button) findViewById(R.id.number2);
        String variable = selectVariable("VarInteger");

        number2.setText(variable);
    }

    public void OnNumber1Click(View view){
        Button number1 = (Button) findViewById(R.id.number1);
        String variable = selectVariable("VarInteger");

        number1.setText(variable);
    }
}
