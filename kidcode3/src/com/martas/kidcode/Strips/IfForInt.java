package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.CodeListAdapter;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
* Created by marta on 01.06.14.
*/
public class IfForInt extends FunctionStrip {

    private String value2 = "";
    private String conditionText = "";
    private String functionText = "";
    ArrayList<String> list = new ArrayList<String>();
    CodeListAdapter adapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;

    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.condition);
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        if(conditionText.contains("check")) {
        view.setText("" + "if (" + name + "." + functionText + ")");
        } else if (conditionText.contains("compare")){
            view.setText("" + "if (" + name + functionText + value2 + ")");
        }

        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        Log.e("kidcode", "getSetup: " + list.size());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ifxml, null);

        final String[] functions = {"is odd", "is even"};
        final String[] functions2 = {"==", "!=", "<", ">"};


        Button addButton = (Button)view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        Button deleteButton = (Button)view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        final AutoCompleteTextView variable = (AutoCompleteTextView)view.findViewById(R.id.variable);
        final AutoCompleteTextView compareWith = (AutoCompleteTextView)view.findViewById(R.id.compareWith);
        Spinner condition = (Spinner)view.findViewById(R.id.condition);
        final Spinner function = (Spinner)view.findViewById(R.id.function);

        variable.addTextChangedListener(new TextWatcher() {
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
                if(i == 1){

                    if (functions != null){
                        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, functions);
                        function.setAdapter(arrayAdapter);
                        conditionText = "check";
                        arrayAdapter.notifyDataSetChanged();
                        function.setVisibility(View.VISIBLE);
                    }
                } else if (i == 2){
                    if (functions2 != null){
                        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, functions2);
                        function.setAdapter(arrayAdapter);
                        conditionText = "compare";
                        arrayAdapter.notifyDataSetChanged();
                        function.setVisibility(View.VISIBLE);
                        compareWith.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        function.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                functionText = function.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter = new CodeListAdapter(view.getContext(), list);
        ListView lv = (ListView) view.findViewById(R.id.list);
        lv.setAdapter(adapter);

        return view;
    }

    public void add() {
        Log.e("kidcode", "" + list.size());
        addClicked = !addClicked;
        if (addClicked) {
            adapter.setMode(CodeListAdapter.Mode.MODE_ADD);
        } else {
            adapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
        }
    }

    public void delete() {
        deleteClicked = !deleteClicked;
        if (deleteClicked) {
            adapter.setMode(CodeListAdapter.Mode.MODE_DELETE);
        } else {
            adapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("compareWith", value2);
            object.put("functionText", functionText);
            object.put("conditionText", conditionText);
            object.put("type", "IfForInt");
            object.put("name", name);

            JSONArray strips = new JSONArray();
            for (int i = 1; i < list.size(); i++) {
                strips.put(list.get(i));
            }
            object.put("strips", strips.toString());
        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            value2 = object.get("compareWith").toString();
            functionText = object.get("functionText").toString();
            conditionText = object.get("conditionText").toString();
            name = object.get("name").toString();


            Empty empty = new Empty();
            list.add(empty.toJson().toString());
            JSONArray strips = new JSONArray(object.getString("strips"));
            for (int i = 0; i < strips.length(); i++) {
                list.add(strips.getString(i));
            }
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        if(functionText.contains("is odd")) {

        }

        return  null;
    }

    public void onActivityResult(Intent intent) {
        if (intent != null) {
            int position = Integer.parseInt(intent.getStringExtra("position"));
            list.add(position+1, intent.getStringExtra("strip"));
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }
}