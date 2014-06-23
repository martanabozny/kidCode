package com.martas.kidcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 20.06.14.
 */
public class MySimpleArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    ArrayList<String> list;
    enum Mode {
        MODE_NORMAL,
        MODE_ADD,
        MODE_DELETE
    }
    private Mode mode = Mode.MODE_NORMAL;

    public MySimpleArrayAdapter(Context context, ArrayList<String> list) {
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

        if (mode == Mode.MODE_ADD) {

            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);

            Button plus = new Button(context);
            plus.setHeight(90);
            plus.setWidth(90);
            plus.setBackgroundColor(Color.GREEN);
            plus.setText("+");
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), Buttons.class);
                    intent.putExtra("position", String.valueOf(position));
                    getContext().startActivity(intent);

                }
            });

            layout.addView(strip.getPreview(context));
            layout.addView(plus);
            return layout;

        } else if (mode == Mode.MODE_DELETE && position != 0) {
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.HORIZONTAL);

            Button remove = new Button(context);
            remove.setHeight(90);
            remove.setWidth(90);
            remove.setBackgroundColor(Color.BLUE);
            remove.setText("-");
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
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