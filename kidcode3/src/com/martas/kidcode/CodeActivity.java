package com.martas.kidcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class CodeActivity extends Activity implements SensorEventListener {

    private SharedPreferences mPrefs;
    ArrayList<String> list = new ArrayList<String>();
    CodeListAdapter adapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;
    int x_, y_, z_;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeactivity);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        try {
            Intent myIntent = new Intent();
            myIntent.setClass(this, AccelerometerService.class);
            startService(myIntent);
            Log.e("service", "start");
        } catch (Exception e) {
            Log.e("service", Log.getStackTraceString(e));
        }


        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);
        if (mPrefs != null) {
            list.clear();

            Empty empty = new Empty();
            list.add(empty.toJson().toString());

            String strips = mPrefs.getString("strips", "[]");
            try {
                JSONArray jarray = new JSONArray(strips);
                for (int i = 0; i < jarray.length(); i++) {
                    list.add(jarray.getString(i));
                }
            } catch (Exception e) {
            }
        } else {
            Empty empty = new Empty();
            list.add(empty.toJson().toString());
        }

        ListView lv = (ListView) findViewById(R.id.Code);

        adapter = new CodeListAdapter(this, list);
        lv.setAdapter(adapter);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {

        x_ = (int) (event.values[0] * 5 + 50);
        y_ = (int) (event.values[1] * 5 + 50);
        z_ = (int) (event.values[2] * 5 + 50);

        //Log.e("Accel.run","" + x_+ " " + y_ + " " + z_);
    }

    public void add(View view) {
        addClicked = !addClicked;
        if (addClicked) {
            adapter.setMode(CodeListAdapter.Mode.MODE_ADD);
        } else {
            adapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
        }
    }

    public void delete(View view) {
        deleteClicked = !deleteClicked;
        if (deleteClicked) {
            adapter.setMode(CodeListAdapter.Mode.MODE_DELETE);
        } else {
            adapter.setMode(CodeListAdapter.Mode.MODE_NORMAL);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            int position = Integer.parseInt(data.getStringExtra("position"));
            if (data.getStringExtra("mode").equals("edit")) {
                list.remove(position);
                list.add(position, data.getStringExtra("strip"));
            } else if (data.getStringExtra("mode").equals("add")) {
                list.add(position + 1, data.getStringExtra("strip"));
            }
            adapter.notifyDataSetChanged();
        }
    }

    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor ed = mPrefs.edit();


        JSONArray jarray = new JSONArray();
        for (int i = 1; i < list.size(); i++) {
            jarray.put(list.get(i));
        }

        ed.putString("strips", jarray.toString());
        ed.commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Save");
        menu.add("Save as");
        menu.add("clear");
        menu.add("files");
        menu.add("help");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Save")) {
            save();
            return true;
        } else if (item.getTitle().toString().equals("Save as")) {
            JSONArray jarray = new JSONArray();
            for (int i = 1; i < list.size(); i++) {
                jarray.put(list.get(i));
            }
            String newJson = jarray.toString();
            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("strips", newJson);
            ed.commit();
            Intent intent = new Intent(CodeActivity.this, Save.class);
            startActivity(intent);
            return true;
        } else if (item.getTitle().toString().equals("clear")) {
            list.clear();
            Empty empty = new Empty();
            list.add(empty.toJson().toString());
            adapter.notifyDataSetChanged();

            return true;
        } else if (item.getTitle().toString().equals("files")) {
            startActivity(new Intent(CodeActivity.this, Open.class));
            return true;
        } else if (item.getTitle().toString().equals("help")) {
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    public void save() {
        JSONArray jarray = new JSONArray();
        for (int i = 1; i < list.size(); i++) {
            jarray.put(list.get(i));
        }
        String newJson = jarray.toString();

        String name = mPrefs.getString("name", "");

        if (name.equals("")) {
            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("strips", newJson);
            ed.commit();
            Intent intent = new Intent(CodeActivity.this, Save.class);
            startActivity(intent);
        } else {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode/";
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(path + name);
                fos.write(newJson.getBytes());

            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        if (list.size() == 1) {
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Save changes?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            save();
                            finish();
                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public void run(View view) {

        HashMap<String, String> results = new HashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            try {
                JSONObject strip = new JSONObject(list.get(i));
                FunctionStrip fstrip = JsonToStrip.fromJson(strip);
                fstrip.fromJson(strip);
                fstrip.accelerometerVariable(x_, y_, z_);
                HashMap<String, String> result = fstrip.run(this, results);
                if (result != null) {
                    results.putAll(result);
                }
            } catch (StopException e) {
                Intent intent = new Intent(CodeActivity.this, End.class);
                intent.putExtra("result", e.result);
                intent.putExtra("value", e.value);
                startActivity(intent);
                return;

            } catch (VariableLackException e) {
                Toast.makeText(getApplicationContext(), "No variable!", Toast.LENGTH_LONG).show();
                return;
            } catch (ConvertException e) {
                Toast.makeText(getApplicationContext(), "Cannot convert variable!", Toast.LENGTH_LONG).show();
                return;
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Something is wrong!", Toast.LENGTH_LONG).show();
                Log.e("kidcode", Log.getStackTraceString(e));
                return;
            }
        }

        Toast.makeText(getApplicationContext(), "Your program is done. To show result add STOP button", Toast.LENGTH_LONG).show();
    }

}
