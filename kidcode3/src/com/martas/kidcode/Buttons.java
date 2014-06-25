package com.martas.kidcode;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by marta on 05.06.14.
 */
public class Buttons extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = Integer.valueOf(getIntent().getStringExtra("position"));

        JSONArray variables;
        try {
            variables = new JSONArray(getIntent().getStringExtra("variables"));
        } catch (Exception e) {
            return;
        }

        ArrayList<View> list = new ArrayList<View>();
        GridView layout = new GridView(getApplicationContext());
        ButtonAdapter adapter = new ButtonAdapter(getApplicationContext(), list);
        layout.setAdapter(adapter);
        layout.setNumColumns(getResources().getDisplayMetrics().widthPixels / 240);

        list.add(new Math().getButton(this, position, variables));
        list.add(new Accelerometer().getButton(this, position, variables));
        list.add(new Foto().getButton(this, position, variables));
        list.add(new Fotoop().getButton(this, position, variables));
        list.add(new NewVariable().getButton(this, position, variables));
        list.add(new ShowVariable().getButton(this, position, variables));
        list.add(new Stop().getButton(this, position, variables));
        list.add(new Strings().getButton(this, position, variables));

        adapter.notifyDataSetChanged();

        setContentView(layout);
    }

    public class ButtonAdapter extends ArrayAdapter<View> {
        public ButtonAdapter(Context context, ArrayList<View> list) {
            super(context, R.layout.codeactivity, list);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            return getItem(position);
        }
    }
}
