package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.example.kidcode2.Strips.*;
import com.example.kidcode2.Strips.Math;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.sql.SQLException;


public class MyActivity extends Activity {

    DataBase dataBase;

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

        String savedJSON = "";
        try {
            savedJSON = getIntent().getStringExtra("strips");
        } catch (Exception e) {}

        try {
            savedJSON = savedInstanceState.getString("strips");
        } catch (Exception e) {}

        if (savedJSON != null && !savedJSON.equals("")) {
            try {
                MyScrollView code = (MyScrollView)findViewById(R.id.code);
                code.fromJson(savedJSON);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }

        dataBase = new DataBase(getApplicationContext());
        dataBase.createDataBase();

        try {
            dataBase.openDataBase();
        } catch (SQLException mSQLException)
        {
            Toast.makeText(getApplicationContext(), mSQLException.toString(), Toast.LENGTH_LONG).show();
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
                    MyScrollView code = (MyScrollView)findViewById(R.id.code);
                    SQLiteDatabase db = dataBase.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DataBase.FeedEntry2.COLUMN_NAME_JSON, code.toJson());
                    values.put(DataBase.FeedEntry2.COLUMN_FILE_NAME, value);
                    db.insert(DataBase.FeedEntry2.TABLE_NAME, null, values);
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

    @Override
    protected void onStop() {
        super.onStop();
        try {
            long newRowId = 0;

            MyScrollView code = (MyScrollView)findViewById(R.id.code);

            // Gets the data repository in write mode
            SQLiteDatabase db = dataBase.getWritableDatabase();

            db.delete(DataBase.FeedEntry.TABLE_NAME, null, null);

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();


            values.put(DataBase.FeedEntry.COLUMN_NAME_JSON, code.toJson());
            values.put(DataBase.FeedEntry.COLUMN_NAME_NAME, "autosave");

            // Insert the new row, returning the primary key value of the new row
            newRowId = db.insert(DataBase.FeedEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    protected void onRestart() {
        super.onRestart();

        try {
            SQLiteDatabase db = dataBase.getReadableDatabase();

            String selectQuery = "SELECT " + DataBase.FeedEntry.COLUMN_NAME_NAME + ", " + DataBase.FeedEntry.COLUMN_NAME_JSON +
                    " FROM " + DataBase.FeedEntry.TABLE_NAME + " ORDER BY " + DataBase.FeedEntry.COLUMN_NAME_ID + " DESC LIMIT 0,1";

            Cursor c = db.rawQuery(selectQuery, null);

            c.moveToFirst();
            String json = c.getString(c.getColumnIndexOrThrow(DataBase.FeedEntry.COLUMN_NAME_JSON));

            MyScrollView code = (MyScrollView)findViewById(R.id.code);
            code.fromJson(json);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}