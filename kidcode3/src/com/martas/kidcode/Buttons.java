package com.martas.kidcode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;

/**
 * Created by marta on 05.06.14.
 */
public class Buttons extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int position = Integer.valueOf(getIntent().getStringExtra("position"));

        /*GridLayout layout = new GridLayout(getApplicationContext());
        layout.setRowCount(3);
        layout.setColumnCount(3);*/

        LinearLayout layout = new LinearLayout(getApplicationContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout layout1 = new LinearLayout(getApplicationContext());
        layout1.setOrientation(LinearLayout.HORIZONTAL);
        layout1.addView(new Math().getButton(this, position));
        layout1.addView(new accelerometer().getButton(this, position));
        layout1.addView(new foto().getButton(this, position));

        LinearLayout layout2 = new LinearLayout(getApplicationContext());
        layout2.setOrientation(LinearLayout.HORIZONTAL);
        layout2.addView(new fotoop().getButton(this, position));
        layout2.addView(new NewVariable().getButton(this, position));
        layout2.addView(new ShowVariable().getButton(this, position));

        LinearLayout layout3 = new LinearLayout(getApplicationContext());
        layout3.setOrientation(LinearLayout.HORIZONTAL);
        layout3.addView(new Stop().getButton(this, position));
        layout3.addView(new strings().getButton(this, position));

        layout.addView(layout1);
        layout.addView(layout2);
        layout.addView(layout3);
        setContentView(layout);


    }
}
