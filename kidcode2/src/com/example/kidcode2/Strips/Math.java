package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;

/**
 * Created by marta on 10.03.14.
 */
public class Math extends FunctionStrip {
//    LinearLayout layout;
//    Button result;
//    TextView equalsTo;
//    Button number1;
//    Button number2;
//    Spinner function;

    public Math(Context context, AttributeSet attrs) {
        super(context, attrs);

        drawStrip();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.math, this, true);

        returnedValue = new VarInteger();

        final Button result = (Button) this.findViewById(R.id.result);
        final Button number1 = (Button) this.findViewById(R.id.number1);
        final Button number2 = (Button) this.findViewById(R.id.number2);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVariable("VarInteger", result);
            }
        });
        number1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVariable("VarInteger", number1);
            }
        });
        number2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVariable("VarInteger", number2);
            }
        });
    }

    public Math(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {
        Spinner function = (Spinner) this.findViewById(R.id.function);
        String operation = function.getSelectedItem().toString();

        Button result = (Button) this.findViewById(R.id.result);
        Button a = (Button) this.findViewById(R.id.number1);
        Button b = (Button) this.findViewById(R.id.number2);

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

        if  (operation.contains("+")){
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

    private void drawStrip(){
//        LayoutParams paramsResult = new LayoutParams(0, LayoutParams.MATCH_PARENT);
//        LayoutParams params1 = new LayoutParams(0, LayoutParams.MATCH_PARENT);
//        LayoutParams params2 = new LayoutParams(0, LayoutParams.MATCH_PARENT);
//
//        layout = new LinearLayout(getContext());
//        result = new Button(getContext());
//        equalsTo = new TextView(getContext());
//        number1 = new Button(getContext());
//        number2 = new Button(getContext());
//        function = new Spinner(getContext());
//
//        layout.setWeightSum(4);
//        equalsTo.setText("=");
//        equalsTo.setWidth(20);
//
//
//        function.setMinimumWidth(40);
//
//
//        params1.weight = 1.5f;
//        params2.weight = 1.5f;
//        paramsResult.weight = 1.0f;
//
//        result.setLayoutParams(paramsResult);
//        number1.setLayoutParams(params1);
//        number1.setLayoutParams(params2);
//
//
//        layout.setOrientation(HORIZONTAL);
//        layout.addView(result);
//        layout.addView(function);
//        layout.addView(number1);
//        layout.addView(number2);
//
//        addView(layout);
    }
}
