package com.example.kidcode2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by marta on 08.04.14.
 */
public class MyMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void OnNewClick(View v) {
        try {
            startActivity(new Intent(MyMenu.this, MyActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnOpenClick(View v) {

    }

    public void OnLastClick(View v) {

    }
}
