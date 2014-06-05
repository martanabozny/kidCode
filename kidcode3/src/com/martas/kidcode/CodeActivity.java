package com.martas.kidcode;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by marta on 01.06.14.
 */
public class CodeActivity extends Activity {
    String[] tab = {"{\"type\": \"Math\", \"a\": \"1\", \"b\": \"2\", \"name\": \"x\"}", "{\"type\": \"Math\", \"a\": \"1\", \"b\": \"2\", \"name\": \"x\"}"};
    MySimpleArrayAdapter adapter;
    Boolean addClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeactivity);

        ListView lv = (ListView) findViewById(R.id.Code);

        adapter = new MySimpleArrayAdapter(this, tab);
        lv.setAdapter(adapter);
    }

    public void add(View view) {
        addClicked = !addClicked;
        adapter.notifyDataSetChanged();

    }


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;

        public MySimpleArrayAdapter(Context context, String[] values) {
            super(context, R.layout.codeactivity, values);
            this.context = context;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            JSONObject obj;
            String name;
            try {
                obj = new JSONObject(getItem(position));
                name = obj.getString("type");
            } catch (Exception e) {
                return  null;
            }

            FunctionStrip strip = null;
            if (name.equals("Math")){
                strip = new Math();
            } else {
                return null;
            }

            strip.fromJson(obj);

            if (addClicked) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                Button plus = new Button(context);
                plus.setText("+");

                layout.addView(strip.getPreview(context));
                layout.addView(plus);
                return layout;
            } else {
                return strip.getPreview(context);
            }
        }
    }

}
