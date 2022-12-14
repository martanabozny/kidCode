package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.math, this, true);

        returnedValue = new VarInteger();

        final Button result = (Button) this.findViewById(R.id.result);
        final Button number1 = (Button) this.findViewById(R.id.number1);
        final Button number2 = (Button) this.findViewById(R.id.number2);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarInteger");
                selectVariable(list, result, true);
            }
        });
        number1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarInteger");
                selectVariable(list, number1, false);
            }
        });
        number2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarInteger");
                selectVariable(list, number2, false);
            }
        });

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

    public Math(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {
        Spinner function = (Spinner) this.findViewById(R.id.function);
        String operation = function.getSelectedItem().toString();

        Button result = (Button) this.findViewById(R.id.result);
        Button a = (Button) this.findViewById(R.id.number1);
        Button b = (Button) this.findViewById(R.id.number2);
        a.setBackgroundResource(android.R.drawable.btn_default);
        b.setBackgroundResource(android.R.drawable.btn_default);

        double numbera, numberb;

        try {
            numbera = Double.valueOf(a.getText().toString());
        } catch (Exception e) {
            try{
                VarInteger i = (VarInteger) getVariable(a.getText().toString(), "VarInteger");
                numbera = i.value;
            } catch (UnknownVariableException e2) {
                a.setBackgroundColor(Color.RED);
                throw e2;
            }

        }

        try {
            numberb = Double.valueOf(b.getText().toString());
        } catch (Exception e) {
            try{
                VarInteger i = (VarInteger) getVariable(b.getText().toString(), "VarInteger");
                numberb = i.value;
            } catch (UnknownVariableException e2) {
                b.setBackgroundColor(Color.RED);
                throw e2;
            }
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

        try {
            VarInteger oldVar = (VarInteger)getVariable(result.getText().toString(), "VarInteger");
            oldVar.value = numberc;
            returnedValue.name = "";
        } catch (Exception e) {
            ((VarInteger) returnedValue).value = numberc;
            returnedValue.name = result.getText().toString();
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Spinner function = (Spinner)findViewById(R.id.function);
            Button result  = (Button) findViewById(R.id.result);
            Button a = (Button) findViewById(R.id.number1);
            Button b = (Button) findViewById(R.id.number2);

            object.put("function", function.getSelectedItemPosition());
            object.put("result", result.getText().toString());
            object.put("a", a.getText().toString());
            object.put("b", b.getText().toString());
            object.put("type", "Math");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        Spinner function = (Spinner)findViewById(R.id.function);
        Button result  = (Button) findViewById(R.id.result);
        Button a = (Button) findViewById(R.id.number1);
        Button b = (Button) findViewById(R.id.number2);

        try {
            function.setSelection(object.getInt("function"));
            result.setText(object.getString("result"));
            a.setText(object.getString("a"));
            b.setText(object.getString("b"));
            returnedValue.name = object.getString("result");

        } catch (JSONException e) {

        }
    }


}
