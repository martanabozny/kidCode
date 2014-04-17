package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
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
