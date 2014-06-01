package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import java.sql.SQLException;


public class CodeActivity extends Activity {
    DataBase dataBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code);

        findViewById(R.id.Math_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Accelerometer_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.While_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.If_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Foto_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.FotoOp_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Show_Variable_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Strings_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.New_Variable_Button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.Stop_Button).setOnTouchListener(new MyTouchListener());

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
                Toast.makeText(getApplicationContext(), "CodeActivity Scrollview from json: " + e.toString(), Toast.LENGTH_LONG).show();
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

        try {
            code.runCode();
        } catch (UnknownVariableException e) {
            Toast.makeText(this, "Cannot find variable " + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (VariableConvertException e) {
            Toast.makeText(this, "Cannot convert variable " + e.toString(), Toast.LENGTH_SHORT).show();
        } catch (StopException e) {
            Toast.makeText(this, "Program stopped " + e.toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CodeActivity.this, End.class);
            intent.putExtra("name", e.toString());
            intent.putExtra("value", e.getValue());
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(this, "Something has gone wrong: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.save:
                showDialog();
                return true;
            case R.id.clear:
                MyScrollView code = (MyScrollView)findViewById(R.id.code);
                code.clear();
                return true;
            case R.id.files:
                startActivity(new Intent(CodeActivity.this, Open.class));
                finish();
                return true;
            case R.id.help:
                //tu otworzymy stronę
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Save");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().toString().equals("Save")) {

            showDialog();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }

    }*/

    public void save(String fileName) {
        MyScrollView code = (MyScrollView)findViewById(R.id.code);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        db.execSQL("DELETE FROM " + DataBase.FeedEntry2.TABLE_NAME + " WHERE " + DataBase.FeedEntry2.COLUMN_FILE_NAME + " = ?", new String[]{fileName});

        ContentValues values = new ContentValues();
        values.put(DataBase.FeedEntry2.COLUMN_NAME_JSON, code.toJson());
        values.put(DataBase.FeedEntry2.COLUMN_FILE_NAME, fileName);
        db.insert(DataBase.FeedEntry2.TABLE_NAME, null, values);
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
            Toast.makeText(this, "onRestart: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void showDialog() {
        final EditText input = new EditText(this);
        final AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Save changes?")
                .setMessage("File name")
                .setView(input)
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", null)
                .create();
        alert.show();

        Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().toString().matches("^[a-zA-Z].*")) {
                    input.setBackgroundColor(Color.RED);

                } else {
                    save(input.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        final EditText input = new EditText(this);
        final AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle("Save changes?")
                .setMessage("File name")
                .setView(input)
                .setPositiveButton("Yes", null)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create();
        alert.show();

        Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!input.getText().toString().matches("^[a-zA-Z].*")) {
                    input.setBackgroundColor(Color.RED);

                } else {
                    save(input.getText().toString());
                    finish();
                }
            }
        });
    }

}