package com.martas.kidcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import org.json.JSONArray;

/**
 * Created by marta on 08.04.14.
 */
public class MyMenu extends Activity {

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void OnNewClick(View v) {
        try {
            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("name", "");
            ed.commit();
            startActivity(new Intent(MyMenu.this, CodeActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnOpenClick(View v) {
        try {
            startActivity(new Intent(MyMenu.this, Open.class));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }



}
