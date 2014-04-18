package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.kidcode2.MyScrollView;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 17.04.14.
 */
public class Condition_Strip extends FunctionStrip {

    public Condition_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.condition_strip, this, true);

        MyScrollView scrool = (MyScrollView)findViewById(R.id.MyView);
        scrool.previous = this;

        final Button variable = (Button)findViewById(R.id.variable);
        final Spinner condition = (Spinner)findViewById(R.id.condition);
        final Button compareWith = (Button)findViewById(R.id.compareWith);

        variable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, variable, false);
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

    public Condition_Strip(Context context) {
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
            Toast.makeText(getContext(), e.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button variable = (Button)findViewById(R.id.variable);
            Spinner function = (Spinner)findViewById(R.id.function);
            Spinner condition = (Spinner)findViewById(R.id.condition);
            Button compareWith = (Button)findViewById(R.id.compareWith);
            MyScrollView myView = (MyScrollView)findViewById(R.id.MyView);

            try {
                object.put("type", "If_Strip");
            } catch (Exception e) {
                Toast.makeText(getContext(), "put: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            object.put("variable", variable.getText().toString());
            object.put("function", function.getSelectedItemPosition());
            object.put("condition", condition.getSelectedItemPosition());
            object.put("compareWith", compareWith.getText().toString());
            object.put("code", myView.toJson());
        } catch (Exception e) {
            Toast.makeText(getContext(), "nll: " + e.toString(), Toast.LENGTH_LONG).show();
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
            function.setSelection(object.getInt("function"));
            condition.setSelection(object.getInt("condition"));
            compareWith.setText(object.getString("compareWith"));
            myView.fromJson(object.getString("code"));
        } catch (JSONException e) {

        }
    }
}
