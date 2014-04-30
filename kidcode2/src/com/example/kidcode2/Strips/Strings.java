package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 17.03.14.
 */
public class Strings extends FunctionStrip {

    public Strings(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.strings, this, true);

        returnedValue = new VarString();

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

        final Button result = (Button)findViewById(R.id.result);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarString");
                selectVariable(list, result, true);
            }
        });

        final Button textButton = (Button)findViewById(R.id.text);

        textButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarString");
                selectVariable(list, textButton, false);
            }
        });
    }

    public Strings(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {

        Spinner functions = (Spinner) findViewById(R.id.functions);
        Button result  = (Button) findViewById(R.id.result);
        Button textButton  = (Button) findViewById(R.id.text);
        CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

        String operation = functions.getSelectedItem().toString();
        ((VarString) returnedValue).name = result.getText().toString();

        String text = textButton.getText().toString();

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
            Spinner functions = (Spinner) findViewById(R.id.functions);
            Button result  = (Button) findViewById(R.id.result);
            Button text  = (Button) findViewById(R.id.text);
            CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

            object.put("functions", functions.getSelectedItemPosition());
            object.put("result", result.getText().toString());
            object.put("text", text.getText().toString());
            object.put("isVariable", isVariable.isChecked());
            object.put("type", "Strings");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        try {
            Spinner functions = (Spinner) findViewById(R.id.functions);
            Button result  = (Button) findViewById(R.id.result);
            Button text  = (Button) findViewById(R.id.text);
            CheckBox isVariable = (CheckBox) findViewById(R.id.is_variable);

            result.setText(object.getString("result"));
            text.setText(object.getString("text"));
            functions.setSelection(object.getInt("functions"));
            isVariable.setChecked(object.getBoolean("isVariable"));
        } catch (JSONException e) {

        }
    }
}
