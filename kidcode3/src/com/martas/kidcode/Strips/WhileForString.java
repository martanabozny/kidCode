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
import com.martas.kidcode.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class WhileForString extends FunctionStrip {

    private String value1 = "";
    private String value2 = "";
    private String functionText = "";
    ArrayList<String> list = new ArrayList<String>();
    CodeListAdapter codeAdapter;
    PreviewAdapter previewAdapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;
    int x_, y_, z_;

    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.loopstring);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("while for strings");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundResource(R.drawable.condition_background);

        TextView condition = new TextView(context);
        condition.setTextSize(20);
        condition.setTextColor(Color.BLACK);
        condition.setText(" " + "while (" + value1 + " " + functionText + " " + value2 + ")");

        layout.addView(condition);

        ListView stripList = new ListView(context);
        previewAdapter = new PreviewAdapter(context, list);
        stripList.setAdapter(previewAdapter);
        stripList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.FILL_PARENT, 1));
        layout.addView(stripList);
        previewAdapter.notifyDataSetChanged();

        layout.setWeightSum(1);
        layout.setMinimumHeight(300);
        return layout;
    }

    public class PreviewAdapter extends ArrayAdapter<String> {
        public PreviewAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JSONObject obj;
            try {
                obj = new JSONObject(getItem(position));
                FunctionStrip strip = JsonToStrip.fromJson(obj);
                strip.fromJson(obj);
                View prev = strip.getPreview(getContext());
                return prev;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public View getSetup(Context context, JSONArray previousVariables) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.whilexmlstring, null);

        final String[] functions = {"is lower", "is upper", "equals", "contains", "longer"};

        Button addButton = (Button) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        Button deleteButton = (Button) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        final AutoCompleteTextView variable = (AutoCompleteTextView) view.findViewById(R.id.variable);
        final AutoCompleteTextView compareWith = (AutoCompleteTextView) view.findViewById(R.id.compareWith);

        variable.setText(value1);
        compareWith.setText(value2);

        addAutocomplete(context, variable, previousVariables);
        addAutocomplete(context, compareWith, previousVariables);

        final Spinner function = (Spinner) view.findViewById(R.id.function);

        if (functions != null) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_spinner_item, functions);
            function.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }

        variable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                value1 = charSequence.toString();
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

        function.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                functionText = function.getSelectedItem().toString();
                if (i == 2 || i == 3 || i == 4) {
                    compareWith.setVisibility(View.VISIBLE);
                } else {
                    compareWith.setVisibility(View.INVISIBLE);
                    value2 = "";
                }
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
            object.put("value1", value1);
            object.put("value2", value2);
            object.put("functionText", functionText);
            object.put("type", "WhileForString");

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

    public void accelerometerVariable(int x, int y, int z) {
        x_ = x;
        y_ = y;
        z_ = z;
    }

    public void fromJson(JSONObject object) {
        try {
            value1 = object.get("value1").toString();
            value2 = object.get("value2").toString();
            functionText = object.get("functionText").toString();

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

    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) throws StopException, ConvertException, VariableLackException {
        boolean result = false;

        HashMap<String, String> results = new HashMap<String, String>();
        results.putAll(previousVariables);

        int g = 0;
        while (g < 100) {
            if (functionText.contains("is upper")) {
                result = value1.equals(value1.toLowerCase());
            } else if (functionText.contains("is upper")) {
                result = value1.equals(value1.toLowerCase());
            } else if (functionText.contains("equals")) {
                result = value1.equals(value2);
            } else if (functionText.contains("contains")) {
                result = value1.contains(value2);
            } else if (functionText.contains("longer")) {
                result = value1.length() == value2.length();
            }

            if (result == true) {
                break;
            }

            for (int i = 0; i < list.size(); i++) {
                try {
                    JSONObject strip = new JSONObject(list.get(i));
                    FunctionStrip fstrip = JsonToStrip.fromJson(strip);
                    fstrip.fromJson(strip);
                    fstrip.accelerometerVariable(x_, y_, z_);
                    HashMap<String, String> strip_result = fstrip.run(context, results);
                    if (strip_result != null) {
                        results.putAll(strip_result);
                    }
                } catch (JSONException e) {

                }
            }
            g++;
        }

        return results;
    }

    public void onActivityResult(Intent data) {
        if (data != null) {
            int position = Integer.parseInt(data.getStringExtra("position"));
            if (data.getStringExtra("mode").equals("edit")) {
                list.remove(position);
                list.add(position, data.getStringExtra("strip"));
            } else if (data.getStringExtra("mode").equals("add")) {
                list.add(position + 1, data.getStringExtra("strip"));
            }
            codeAdapter.notifyDataSetChanged();
        }
    }
}
