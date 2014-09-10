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

    public void showStrip() {
        String strip = getIntent().getStringExtra("strip");
        Log.e("kidcode", strip);
        try {
            JSONObject obj = new JSONObject(strip);
            fstrip = JsonToStrip.fromJson(obj);
            fstrip.fromJson(obj);

            FrameLayout frame = (FrameLayout) findViewById(R.id.strip_setup);
            JSONArray variables = new JSONArray(getIntent().getStringExtra("variables"));
            frame.addView(fstrip.getSetup(this, variables));
        } catch (Exception e) {
            Log.e("kidcode", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);
        showStrip();
    }

    public void okClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("position", getIntent().getStringExtra("position"));
        intent.putExtra("mode", getIntent().getStringExtra("mode"));
        intent.putExtra("strip", fstrip.toJson().toString());
        setResult(1, intent);
        finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        fstrip.onActivityResult(data);
    }
}
