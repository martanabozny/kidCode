package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 20.03.14.
 */
public class NewVariable extends FunctionStrip {

    public NewVariable(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.new_variable, this, true);

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

        final Button name = (Button)findViewById(R.id.name);
        name.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList list = collectVariables("");
                selectVariable(list, name, true);
                createVariable();
            }
        });

        final Button value = (Button)findViewById(R.id.value);
        value.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, value, false);
                createVariable();
            }
        });

        Spinner variable_types = (Spinner) findViewById(R.id.variables_types);
        variable_types.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                createVariable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public NewVariable(Context context) {
        this(context, null);
    }

    private void createVariable() {
        Button name  = (Button) findViewById(R.id.name);
        Button value  = (Button) findViewById(R.id.value);
        Spinner variables_types = (Spinner) findViewById(R.id.variables_types);

        String type = variables_types.getSelectedItem().toString();
        String _value = value.getText().toString();
        String _name = name.getText().toString();

        if (type.contains("String")) {
            returnedValue = new VarString();
            returnedValue.name = _name;
            ((VarString)returnedValue).value = _value;
        } else if (type.contains("Integer")){
            returnedValue = new VarInteger();
            returnedValue.name = _name;
            if (!_value.equals("")) {
                try {
                    ((VarInteger)returnedValue).value = Double.valueOf(_value);
                    value.setBackgroundResource(android.R.drawable.btn_default);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Cannot convert " + _value + " to integer!", Toast.LENGTH_LONG).show();
                    value.setBackgroundColor(Color.RED);
                }
            } else {
                ((VarInteger)returnedValue).value = 0;
            }
        }
    }

    public void run() throws UnknownVariableException {
        createVariable();
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button name  = (Button) this.findViewById(R.id.name);
            Button value  = (Button) this.findViewById(R.id.value);
            Spinner variables_types = (Spinner) this.findViewById(R.id.variables_types);

            object.put("variables_types", variables_types.getSelectedItemPosition());
            object.put("name", name.getText().toString());
            object.put("value", value.getText().toString());
            object.put("type", "NewVariable");

            name.clearFocus();
            value.clearFocus();

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object) {
        try {
            Button name  = (Button) this.findViewById(R.id.name);
            Button value  = (Button) this.findViewById(R.id.value);
            Spinner variables_types = (Spinner) this.findViewById(R.id.variables_types);

            name.clearFocus();
            value.clearFocus();

            name.setText(object.getString("name"));
            value.setText(object.getString("value"));
            variables_types.setSelection(object.getInt("variables_types"));

            createVariable();
        } catch (JSONException e) {

        }
    }
}
