package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.*;
import com.example.kidcode2.MyScrollView;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
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

    public void run() throws UnknownVariableException {

        Button variable = (Button)findViewById(R.id.variable);
        Variable var = getVariable(variable.getText().toString(), "");
        Spinner function = (Spinner)findViewById(R.id.function);
        String functionString = function.getSelectedItem().toString();
        Spinner condition = (Spinner)findViewById(R.id.condition);
        String conditionString = condition.getSelectedItem().toString();
        Button compareWith = (Button)findViewById(R.id.compareWith);
        Variable varWith = getVariable(compareWith.getText().toString(), "");
        MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

        boolean result = true;

        if (conditionString.contains("compare")){
            result = var.compare(varWith, functionString);
            Toast.makeText(getContext(), "Wynik compare " + ((VarInteger)var).value + " i " + ((VarInteger)varWith).value + ": " + result, Toast.LENGTH_LONG).show();
        } else if (conditionString.contains("check")) {
            result = var.check(functionString);
            Toast.makeText(getContext(), "Wynik check: " + result, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Nie znam operacji", Toast.LENGTH_LONG).show();
        }

        int i = 0;
        while (result && i < 10) {
            myView.runCode();

            if (conditionString.contains("compare")){
                result = var.compare(varWith, functionString);
                Toast.makeText(getContext(), "Wynik compare: " + result, Toast.LENGTH_LONG).show();
            } else if (conditionString.contains("check")) {
                result = var.check(functionString);
                Toast.makeText(getContext(), "Wynik check: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Nie znam operacji", Toast.LENGTH_LONG).show();
            }
            i++;
        }
    }
}