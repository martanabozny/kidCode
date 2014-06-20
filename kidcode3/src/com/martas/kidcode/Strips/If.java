package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class If extends FunctionStrip {

    private String value2 = "";
    private String functionText = "";

    public View getButton(final Context context, final int position) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.condition);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Setup.class);
                intent.putExtra("strip", toJson().toString());
                intent.putExtra("position", String.valueOf(position));
                context.startActivity(intent);
            }
        });
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = " + text + "." + functionText);
        return view;
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.condition, null);

        TextView conditionName = (TextView)view.findViewById(R.id.conditionName);
        conditionName.setText("if");
        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        AutoCompleteTextView compareWith = (AutoCompleteTextView)view.findViewById(R.id.compareWith);
        Spinner condition = (Spinner)view.findViewById(R.id.condition);

        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        compareWith.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                value2 = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

        return view;
    }

    public void showCondition() {
        try {
            AutoCompleteTextView result = (AutoCompleteTextView)findViewById(R.id.result);
            result.getText()
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

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("text", text);
            object.put("functionText", functionText);
            object.put("type", "strings");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            text = object.get("text").toString();
            functionText = object.get("functionText").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public Map<String, String> run(Map<String, String> previousVariables) {
        return  null;
    }
}
