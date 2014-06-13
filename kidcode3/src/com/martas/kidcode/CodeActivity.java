package com.martas.kidcode;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marta on 01.06.14.
 */
public class CodeActivity extends Activity {
    private SharedPreferences mPrefs;
    ArrayList<String> list = new ArrayList<String>();
    MySimpleArrayAdapter adapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeactivity);

        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);
        if (mPrefs != null) {
            list.clear();

            Empty empty = new Empty();
            list.add(empty.toJson().toString());

            String strips = mPrefs.getString("strips", "[]");
            try {
                JSONArray jarray = new JSONArray(strips);
                for (int i = 0; i < jarray.length(); i++) {
                    list.add(jarray.getString(i));
                }
            } catch (Exception e) {}
        } else {
            Empty empty = new Empty();
            list.add(empty.toJson().toString());
        }

        ListView lv = (ListView) findViewById(R.id.Code);

        adapter = new MySimpleArrayAdapter(this, list);
        lv.setAdapter(adapter);
    }

    public void add(View view) {
        addClicked = !addClicked;
        deleteClicked = false;
        adapter.notifyDataSetChanged();
    }

    public void delete(View view) {
        deleteClicked = !deleteClicked;
        addClicked = false;
        adapter.notifyDataSetChanged();
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();


        JSONArray jarray = new JSONArray();
        for (int i = 1; i < list.size(); i++) {
            jarray.put(list.get(i));
        }

        ed.putString("strips", jarray.toString());
        ed.commit();
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;

        public MySimpleArrayAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
            this.context = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            JSONObject obj;
            try {
                obj = new JSONObject(getItem(position));
            } catch (Exception e) {
                return  null;
            }

            FunctionStrip strip = JsonToStrip.fromJson(obj);

            strip.fromJson(obj);

            if (addClicked) {

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                Button plus = new Button(context);
                plus.setText("+");
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CodeActivity.this, Buttons.class);
                        intent.putExtra("position", String.valueOf(position));
                        startActivity(intent);

                    }
                });

                layout.addView(strip.getPreview(context));
                layout.addView(plus);
                return layout;

            } else if (deleteClicked && position != 0) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                Button remove = new Button(context);
                remove.setText("-");
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                layout.addView(strip.getPreview(context));
                layout.addView(remove);
                return layout;
            } else {
                return strip.getPreview(context);
            }
        }
    }
}
