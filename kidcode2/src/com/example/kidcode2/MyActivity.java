package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.example.kidcode2.Strips.*;
import com.example.kidcode2.Strips.Math;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;


public class MyActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        findViewById(R.id.Math_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Accelerometer_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.While_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.If_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Foto_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.FotoOp_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Show_Variable_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Strings_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.New_Variable_Button).setOnTouchListener(new MyTouchListener());

        if (savedInstanceState != null) {
            try {
                String strips = savedInstanceState.getString("strips");
                MyScrollView code = (MyScrollView)findViewById(R.id.code);
                code.fromJson(strips);
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                Toast.makeText(this, "Param: " + getIntent().getStringExtra("strips"), Toast.LENGTH_LONG).show();
                MyScrollView code = (MyScrollView)findViewById(R.id.code);
                code.fromJson(getIntent().getStringExtra("strips"));
            } catch (Exception e) {
                Toast.makeText(this, "error: " + e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }


    public void runCode(View view) {
        MyScrollView code = (MyScrollView)findViewById(R.id.code);
        code.runCode();
    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("save");
        menu.add("save as");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("save as")) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Save as");
            alert.setMessage("File name");
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();

                    try {
                        FileOutputStream fos = openFileOutput(value, Context.MODE_PRIVATE);
                        MyScrollView code = (MyScrollView)findViewById(R.id.code);
                        fos.write(code.toJson().getBytes());
                        fos.close();
                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Problem: " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        MyScrollView code = (MyScrollView)findViewById(R.id.code);
        bundle.putSerializable("strips", code.toJson());
    }
}