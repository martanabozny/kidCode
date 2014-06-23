package com.martas.kidcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.*;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marta on 01.06.14.
 */
public class CodeActivity extends Activity {
    private SharedPreferences mPrefs;
    ArrayList<String> list = new ArrayList<String>();
    MySimpleArrayAdapter adapter;
    Boolean addClicked = false;
    Boolean deleteClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codeactivity);

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
            } catch (Exception e) {}
        } else {
            Empty empty = new Empty();
            list.add(empty.toJson().toString());
        }

        ListView lv = (ListView) findViewById(R.id.Code);

        adapter = new MySimpleArrayAdapter(this, list);
        lv.setAdapter(adapter);
    }

    public void add(View view) {
        addClicked = !addClicked;
        if (addClicked) {
            adapter.setMode(MySimpleArrayAdapter.Mode.MODE_ADD);
        } else {
            adapter.setMode(MySimpleArrayAdapter.Mode.MODE_NORMAL);
        }
    }

    public void delete(View view) {
        deleteClicked = !deleteClicked;
        if (deleteClicked) {
            adapter.setMode(MySimpleArrayAdapter.Mode.MODE_DELETE);
        } else {
            adapter.setMode(MySimpleArrayAdapter.Mode.MODE_NORMAL);
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
        } else if (item.getTitle().toString().equals("Save as")){
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
        }else if (item.getTitle().toString().equals("clear")){
            list.clear();
            return true;
        } else if (item.getTitle().toString().equals("files")){
            startActivity(new Intent(CodeActivity.this, Open.class));
            return true;
        }else if (item.getTitle().toString().equals("help")){
            return true;
        }else
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
            String eol = System.getProperty("line.separator");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(openFileOutput(path + name, Context.MODE_PRIVATE)));
                writer.write(newJson);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Save changes?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        save();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void run(View view) {
        HashMap<String,String> results = new HashMap<String, String>();
        for(int i =0; i<list.size(); i++) {
            try {
                JSONObject strip = new JSONObject(list.get(i));
                FunctionStrip fstrip = JsonToStrip.fromJson(strip);
                HashMap<String,String> result = fstrip.run(results);

            } catch (Exception e) {

            }
        }
    }

}
