package com.martas.kidcode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 20.06.14.
 */
public class CodeListAdapter extends ArrayAdapter<String> {

    private final Context context;
    ArrayList<String> list;

    public enum Mode {
        MODE_NORMAL,
        MODE_ADD,
        MODE_DELETE
    }
    private Mode mode = Mode.MODE_NORMAL;

    public CodeListAdapter(Context context, ArrayList<String> list) {
        super(context, R.layout.codeactivity, list);
        this.list = list;
        this.context = context;
    }

    public void setMode(Mode new_mode) {
        mode = new_mode;
        notifyDataSetChanged();
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

        View preview = strip.getPreview(context);

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("kidcode", "click");
                Intent intent = new Intent(getContext(), Setup.class);
                JSONArray variableList = new JSONArray();
                for (int i = 0; i <= position; i++) {
                    try {
                        JSONObject strip = new JSONObject(getItem(i).toString());
                        variableList.put(0, strip.getString("name"));
                    } catch (Exception e) {
                        Log.e("MySimpleAdapter.getView", e.toString());
                    }
                }
                intent.putExtra("position", String.valueOf(position));
                intent.putExtra("mode", "edit");
                intent.putExtra("strip", getItem(position));
                intent.putExtra("variables", variableList.toString());
                Log.e("kidcode", getItem(position));
                ((Activity)getContext()).startActivityForResult(intent, 1);
            }
        });

        if (mode == Mode.MODE_ADD) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            Button plus = new Button(context);
            plus.setHeight(70);
            plus.setWidth(90);
            plus.setBackgroundResource(R.drawable.plusbutton_background);
            plus.setText("+");
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Buttons.class);
                    JSONArray variableList = new JSONArray();
                    for (int i = 0; i <= position; i++) {
                        try {
                            JSONObject strip = new JSONObject(getItem(i).toString());
                            Log.e("kidcode", strip.toString());
                            variableList.put(0, strip.getString("name"));
                        } catch (Exception e) {
                            Log.e("MySimpleAdapter.getView", e.toString());
                        }
                    }
                    intent.putExtra("position", String.valueOf(position));
                    intent.putExtra("mode", "add");
                    intent.putExtra("variables", variableList.toString());
                    ((Activity)getContext()).startActivityForResult(intent, 1);
                }
            });

            layout.addView(preview);
            layout.addView(plus);
            return layout;

        } else if (mode == Mode.MODE_DELETE && position != 0) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            Button remove = new Button(context);
            remove.setHeight(90);
            remove.setWidth(90);


            remove.setPadding(0,0,0,10);

            remove.setBackgroundResource(R.drawable.minusbutton_background);
            remove.setText("-");
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 2;
            lp.setMargins(0,5,0,5);
            preview.setLayoutParams(lp);

            layout.addView(preview);
            layout.addView(remove);
            layout.setWeightSum(2);
            return layout;
        } else {
            return preview;
        }
    }
}