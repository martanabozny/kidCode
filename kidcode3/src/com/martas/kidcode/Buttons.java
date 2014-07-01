package com.martas.kidcode;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        ArrayList<FunctionStrip> list = new ArrayList<FunctionStrip>();
        GridView layout = new GridView(getApplicationContext());
        ButtonAdapter adapter = new ButtonAdapter(getApplicationContext(), list);
        layout.setAdapter(adapter);
        layout.setNumColumns(getResources().getDisplayMetrics().widthPixels / 240);

        list.add(new Math());
        list.add(new Accelerometer());
        list.add(new Foto());
        list.add(new Fotoop());
        list.add(new NewVariable());
        list.add(new Stop());
        list.add(new Strings());

        adapter.notifyDataSetChanged();

        setContentView(layout);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            setResult(1, data);
            finish();
        }
    }

    public class ButtonAdapter extends ArrayAdapter<FunctionStrip> {
        public ButtonAdapter(Context context, ArrayList<FunctionStrip> list) {
            super(context, R.layout.codeactivity, list);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageButton button = getItem(position).getButton(getContext());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Setup.class);
                    intent.putExtra("variables", getIntent().getStringExtra("variables"));
                    intent.putExtra("position", getIntent().getStringExtra("position"));
                    intent.putExtra("strip", getItem(position).toJson().toString());
                    startActivityForResult(intent, 1);
                }
            });

            button.setAdjustViewBounds(false);
            button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            button.setPadding(5, 5, 5, 5);

            return button;
        }
    }
}
