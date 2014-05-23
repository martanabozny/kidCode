package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.*;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 17.04.14.
 */
public abstract class ConditionStrip extends FunctionStrip {

    public ConditionStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        returnedValue = new VarInteger();
        returnedValue.name = "";

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.condition_strip, this, true);

        MyScrollView scrool = (MyScrollView)findViewById(R.id.MyView);
        scrool.setPrevious(this);

        final Button variable = (Button)findViewById(R.id.variable);
        final Spinner condition = (Spinner)findViewById(R.id.condition);
        final Button compareWith = (Button)findViewById(R.id.compareWith);

        variable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");

                if (list.size() == 0) {
                    Toast.makeText(getContext(), "Declare some variable first", Toast.LENGTH_LONG).show();
                } else {
                    selectVariable(list, variable, false);
                }
            }
        });

        compareWith.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Variable var = getVariable(variable.getText().toString(), "");
                    ArrayList list = collectVariables(var.type);
                    selectVariable(list, compareWith, false);
                } catch (UnknownVariableException e) {
                    Toast.makeText(getContext(), "Unknown variable: " + e.toString(), Toast.LENGTH_LONG).show();
                }
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
                    Toast.makeText(getContext(), "Cancel on click listener: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showCondition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public ConditionStrip(Context context) {
        this(context, null);
    }

    public void showCondition() {
        try {
            Button variable = (Button)findViewById(R.id.variable);
            Variable var = getVariable(variable.getText().toString(), "");

            Spinner condition = (Spinner)findViewById(R.id.condition);
            Spinner function = (Spinner)findViewById(R.id.function);
            Button compareWith = (Button)findViewById(R.id.compareWith);

            String[] functions = {};
            if (condition.getSelectedItemPosition() == 1) {
                functions = var.showCheckMethods();
                function.setVisibility(VISIBLE);
                compareWith.setVisibility(INVISIBLE);
            } else if (condition.getSelectedItemPosition() == 2) {
                functions = var.showCompareMethods();
                function.setVisibility(VISIBLE);
                compareWith.setVisibility(VISIBLE);
            } else {
                function.setVisibility(INVISIBLE);
                compareWith.setVisibility(INVISIBLE);
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, functions);
            function.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Show condition: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void run() throws UnknownVariableException, VariableConvertException, StopException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button variable = (Button)findViewById(R.id.variable);
            Spinner function = (Spinner)findViewById(R.id.function);
            Spinner condition = (Spinner)findViewById(R.id.condition);
            Button compareWith = (Button)findViewById(R.id.compareWith);
            MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

            object.put("variable", variable.getText().toString());
            object.put("function", function.getSelectedItemPosition());
            object.put("condition", condition.getSelectedItemPosition());
            object.put("compareWith", compareWith.getText().toString());
            object.put("code", myView.toJson());

        } catch (Exception e) {
            Toast.makeText(getContext(), "toJson: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        return object;
    }

    public void fromJson(JSONObject object) {

        Button variable = (Button)findViewById(R.id.variable);
        Spinner function = (Spinner)findViewById(R.id.function);
        Spinner condition = (Spinner)findViewById(R.id.condition);
        Button compareWith = (Button)findViewById(R.id.compareWith);
        MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

        try {
            variable.setText(object.getString("variable"));
            compareWith.setText(object.getString("compareWith"));
            myView.fromJson(object.getString("code"));
            condition.setSelection(object.getInt("condition"));
            showCondition();
            Toast.makeText(getContext(), "index: " + function.getSelectedItemPosition(), Toast.LENGTH_LONG).show();

            function.getAdapter().notify();
            function.setSelection(object.getInt("function"));
            Toast.makeText(getContext(), "index: " + function.getSelectedItemPosition(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "fromJson: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
