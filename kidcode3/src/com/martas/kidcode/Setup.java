package com.martas.kidcode;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 05.06.14.
 */
public class Setup extends Activity {
    FunctionStrip fstrip;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);


        String strip = getIntent().getStringExtra("strip");
        try {
            JSONObject obj = new JSONObject(strip);
            fstrip = JsonToStrip.fromJson(obj);

            FrameLayout frame = (FrameLayout)findViewById(R.id.strip_setup);
            JSONArray variables = new JSONArray(getIntent().getStringExtra("variables"));
            Log.e("Setup.onCreate", variables.toString());
            frame.addView(fstrip.getSetup(this, variables));
        } catch (Exception e) {
            Log.e("Setup.onCreate", e.toString());
        }

    }
    public void  okClicked(View view) {
        Intent intent = getIntent();
        int position = Integer.parseInt(intent.getStringExtra("position"));

        if (mPrefs != null) {
            try {
                JSONArray jarray = new JSONArray(mPrefs.getString("strips", "[]"));
                JSONArray newarray  = new JSONArray();

                for(int i =0; i<position; i++){
                    newarray.put(jarray.getString(i));
                }

                newarray.put(fstrip.toJson());

                for(int i = position; i < jarray.length(); i++) {
                    newarray.put(jarray.getString(i));
                }

                SharedPreferences.Editor ed = mPrefs.edit();
                ed.putString("strips", newarray.toString());
                ed.commit();
            } catch (Exception e) {

            }
        }
        Intent intent1 = new Intent(Setup.this, CodeActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
    }
}
