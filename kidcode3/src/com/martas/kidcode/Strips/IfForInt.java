package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.CodeListAdapter;
import com.martas.kidcode.JsonToStrip;
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
    CodeListAdapter codeAdapter;
    PreviewAdapter previewAdapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;

    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.condition);
        return button;
    }

    public View getPreview(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.condition_background);

        TextView condition = new TextView(context);
        condition.setTextSize(20);
        condition.setTextColor(Color.BLACK);
        //view2.setBackgroundResource(R.drawable.condition_background);
        if(conditionText.contains("check")) {
            condition.setText("" + "if (" + name + "." + functionText + ")");
        } else if (conditionText.contains("compare")){
            condition.setText("" + "if (" + name + functionText + value2 + ")");
        }
        layout.addView(condition);

        ListView stripList = new ListView(context);
        layout.addView(stripList);
        previewAdapter = new PreviewAdapter(context, list);
        stripList.setAdapter(previewAdapter);
        previewAdapter.notifyDataSetChanged();

        Log.e("kidcode", "getPreview: " + list.size());

        return layout;
    }

    public class PreviewAdapter extends ArrayAdapter<String> {
        public PreviewAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("kidcode", "Adding: " + getItem(position) + " at position " + position);

            JSONObject obj;
            try {
                obj = new JSONObject(getItem(position));
                FunctionStrip strip = JsonToStrip.fromJson(obj);
                View prev = strip.getPreview(getContext());
                Log.e("kidcode", "return prev;");
                return prev;
            } catch (Exception e) {
                Log.e("kidcode", "return null;");
                return  null;
            }
        }
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        Log.e("kidcode", "getSetup: " + list.size());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.ifxml, null);

        final String[] functions_check = {"is odd", "is even"};
        final String[] functions_compare = {"==", "!=", "<", ">"};


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

                    if (functions_check != null){
                        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, functions_check);
                        function.setAdapter(arrayAdapter);
                        conditionText = "check";
                        arrayAdapter.notifyDataSetChanged();
                        function.setVisibility(View.VISIBLE);
                    }
                } else if (i == 2){
                    if (functions_compare != null){
                        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, functions_compare);
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

        codeAdapter = new CodeListAdapter(view.getContext(), list);
        ListView lv = (ListView) view.findViewById(R.id.list);
        lv.setAdapter(codeAdapter);

        return view;
    }

    public void add() {
        addClicked = !addClicked;
        if (addClicked) {
            codeAdapter.setMode(CodeListAdapter.Mode.MODE_ADD);
        } else {
            codeAdapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
        }
    }

    public void delete() {
        deleteClicked = !deleteClicked;
        if (deleteClicked) {
            codeAdapter.setMode(CodeListAdapter.Mode.MODE_DELETE);
        } else {
            codeAdapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
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
                Log.e("kidcode", "toJson: " + list.get(i));
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


            list.clear();
            Empty empty = new Empty();
            list.add(empty.toJson().toString());
            JSONArray strips = new JSONArray(object.getString("strips"));
            for (int i = 0; i < strips.length(); i++) {
                list.add(strips.getString(i));
                Log.e("kidcode", "fromJson: " + strips.getString(i));
            }
            if (codeAdapter != null) {
                codeAdapter.notifyDataSetChanged();
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
            if (codeAdapter != null) {
                codeAdapter.notifyDataSetChanged();
            }
        }
    }

    public int accelerometerVariable(int x,int y, int z) {
        return 0;
    }
}
