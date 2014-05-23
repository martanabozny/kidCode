package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.*;
import com.example.kidcode2.*;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class WhileStrip extends ConditionStrip {

    public WhileStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView text = (TextView)findViewById(R.id.conditionName);
        text.setText("While");

        findViewById(R.id.MyView).setBackgroundResource(R.drawable.while_background);
    }

    public WhileStrip(Context context) {
        this(context, null);
    }

    public JSONObject toJson() {
        JSONObject object = super.toJson();

        try {
            object.put("type", "WhileStrip");

        } catch (Exception e) {
            Toast.makeText(getContext(), "blad: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        return object;
    }

    public void run() throws UnknownVariableException, StopException, VariableConvertException {

        Button variable = (Button)findViewById(R.id.variable);
        Variable var = getVariable(variable.getText().toString(), "");
        Spinner function = (Spinner)findViewById(R.id.function);
        String functionString = function.getSelectedItem().toString();
        Spinner condition = (Spinner)findViewById(R.id.condition);
        String conditionString = condition.getSelectedItem().toString();
        Button compareWith = (Button)findViewById(R.id.compareWith);
        compareWith.setBackgroundResource(android.R.drawable.btn_default);
        Variable varWith;
        
        try {
            varWith = getVariable(compareWith.getText().toString(), "");
        } catch (UnknownVariableException e) {
            try {
                varWith = var.fromString(compareWith.getText().toString());
            } catch (VariableConvertException e2) {
                compareWith.setBackgroundColor(Color.RED);
                throw e2;

            }
        }

        MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

        boolean result = true;

        if (conditionString.contains("compare")){
            result = var.compare(varWith, functionString);
        } else if (conditionString.contains("check")) {
            result = var.check(functionString);
        }

        int i = 0;
        while (result && i < 10) {
            myView.runCode();

            if (conditionString.contains("compare")){
                result = var.compare(varWith, functionString);
            } else if (conditionString.contains("check")) {
                result = var.check(functionString);
            }
            i++;
        }
    }
}