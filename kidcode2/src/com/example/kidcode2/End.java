package com.example.kidcode2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by marta on 05.05.14.
 */
public class End extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);

        String name = getIntent().getStringExtra("name");
        String value = getIntent().getStringExtra("value");

        EditText name_ = (EditText)findViewById(R.id.name);
        name_.setText(name);
        EditText value_ = (EditText)findViewById(R.id.value);
        value_.setText(value);

    }
}
