package com.martas.kidcode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;

/**
 * Created by marta on 05.06.14.
 */
public class Buttons extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableLayout layout = new TableLayout(getApplicationContext());

        int position = Integer.valueOf(getIntent().getStringExtra("position"));
        layout.addView(new Math().getButton(this, position));
        setContentView(layout);
    }
}
