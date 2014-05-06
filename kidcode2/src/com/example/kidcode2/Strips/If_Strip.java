package com.example.kidcode2.Strips;

import android.content.Context;
import android.content.DialogInterface;
import android.opengl.Visibility;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.MyScrollView;
import com.example.kidcode2.R;
import com.example.kidcode2.SelectVariableDialog;
import com.example.kidcode2.Strips.FunctionStrip;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 04.04.14.
 */
public class If_Strip extends Condition_Strip {

    public If_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        TextView text = (TextView)findViewById(R.id.conditionName);
        text.setText("If");

        this.setBackgroundResource(R.drawable.if_background);
    }

    public If_Strip(Context context) {
        this(context, null);
    }

    public JSONObject toJson() {
        JSONObject object = super.toJson();

        try {
            object.put("type", "If_Strip");

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
        Variable varWith = getVariable(variable.getText().toString(), "");
        MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

        boolean result = false;
        if (conditionString.equals("compare")){
            result = var.compare(varWith, functionString);
        } else if (conditionString.equals("check")) {
            result = var.check(functionString);
        }

        if (result) {
            myView.runCode();
        }
    }
}
