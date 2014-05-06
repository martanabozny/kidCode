package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.*;
import com.example.kidcode2.*;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class IfStrip extends ConditionStrip {

    public IfStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView text = (TextView)findViewById(R.id.conditionName);
        text.setText("If");

        findViewById(R.id.MyView).setBackgroundResource(R.drawable.if_background);
    }

    public IfStrip(Context context) {
        this(context, null);
    }

    public JSONObject toJson() {
        JSONObject object = super.toJson();

        try {
            object.put("type", "IfStrip");

        } catch (Exception e) {
            Toast.makeText(getContext(), "blad: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        return object;
    }

    public void run() throws UnknownVariableException, VariableConvertException, StopException {
        Button variable = (Button)findViewById(R.id.variable);
        Variable var = getVariable(variable.getText().toString(), "");


        Spinner function = (Spinner)findViewById(R.id.function);
        String functionString = function.getSelectedItem().toString();
        Spinner condition = (Spinner)findViewById(R.id.condition);
        String conditionString = condition.getSelectedItem().toString();
        Button compareWith = (Button)findViewById(R.id.compareWith);
        Variable varWith;
        try {
            varWith = getVariable(compareWith.getText().toString(), "");
        } catch (UnknownVariableException e) {
            varWith = var.fromString(compareWith.getText().toString());
        }

        MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

        boolean result = false;
        if (conditionString.contains("compare")){
            result = var.compare(varWith, functionString);
        } else if (conditionString.contains("check")) {
            result = var.check(functionString);
        }

        if (result) {
            myView.runCode();
        }
    }
}
