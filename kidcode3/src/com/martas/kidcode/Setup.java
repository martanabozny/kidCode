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

    public void onPause(){
        super.onPause();
        Log.e("kidcode", "pause");
    }

    public void onResume(){
        super.onResume();

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
            Log.e("Setup.onCreate", Log.getStackTraceString(e));
        }
    }

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
            Log.e("Setup.onCreate", Log.getStackTraceString(e));
        }

    }
    public void  okClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getStringExtra("position"));
        intent.putExtra("strip", fstrip.toJson().toString());
        setResult(1, intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fstrip.onActivityResult(data);
    }
}
