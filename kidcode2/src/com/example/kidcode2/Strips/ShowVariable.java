package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarImage;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 17.04.14.
 */
public class ShowVariable extends FunctionStrip {

    public ShowVariable(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.show_variable, this, true);

        final Button variable = (Button)findViewById(R.id.variable);
        TextView value_ = (TextView)findViewById(R.id.value);

        variable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, variable, false);
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

    public ShowVariable(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {

        try {
            final Button variable = (Button)findViewById(R.id.variable);
            EditText value_ = (EditText)findViewById(R.id.value);

            Variable var = getVariable(variable.getText().toString(), "");
            if (var.type.equals("VarInteger")) {
                value_.setText(String.valueOf(((VarInteger)var).value));
            }
            if (var.type.equals("VarString")) {
                value_.setText(String.valueOf(((VarString)var).value));
            }
            if (var.type.equals("VarImage")) {
                    value_.setText(String.valueOf(((VarImage)var).value));
            }


        } catch (UnknownVariableException e) {
            Toast.makeText(getContext(), "Unknown variable: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button variable = (Button)findViewById(R.id.variable);
            TextView value_ = (TextView)findViewById(R.id.value);

            object.put("variable", variable.toString());
            object.put("value_", value_.toString());

            object.put("type", "ShowVariable");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        try {
            Button variable = (Button)findViewById(R.id.variable);
            TextView value_ = (TextView)findViewById(R.id.value);

            variable.setText(object.getString("name"));
            value_.setText(object.getString("value"));


        } catch (JSONException e) {

        }
    }
}
