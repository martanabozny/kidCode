package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marta on 08.04.14.
 */
public class MyMenu extends Activity {

    DataBase dataBase;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        dataBase = new DataBase(this);
        dataBase.createDataBase();

        try {
            dataBase.openDataBase();
        } catch (SQLException mSQLException)
        {
            Toast.makeText(this, mSQLException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnNewClick(View v) {
        try {
            startActivity(new Intent(MyMenu.this, MyActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnOpenClick(View v) {
        try {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Open file");
            final LinearLayout layout= new LinearLayout(getApplicationContext());

            db = dataBase.getReadableDatabase();

            List<String> list = new ArrayList<String>();
            String selectQuery = "SELECT " + DataBase.FeedEntry2.COLUMN_FILE_NAME + " FROM " + DataBase.FeedEntry2.TABLE_NAME;
            Cursor c = db.rawQuery(selectQuery, null);
            c.moveToFirst();

            do {
                String filename = c.getString(c.getColumnIndex(DataBase.FeedEntry2.COLUMN_FILE_NAME));
                list.add(filename);
                Toast.makeText(this, filename, Toast.LENGTH_LONG).show();
            } while (c.moveToNext());

            for (int i = 0; i < list.size(); i ++) {
                final Button button = new Button(getApplicationContext());
                button.setText(list.get(i).toString());
                layout.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String text = ((Button)view).getText().toString();


                            String selectQuery = "SELECT " + DataBase.FeedEntry2.COLUMN_FILE_NAME + ", " +
                                    DataBase.FeedEntry2.COLUMN_NAME_JSON +" FROM " + DataBase.FeedEntry2.TABLE_NAME + " WHERE name = ?";

                            Cursor c = db.rawQuery(selectQuery, new String[]{text});

                            c.moveToFirst();
                            String json = c.getString(c.getColumnIndexOrThrow(DataBase.FeedEntry2.COLUMN_NAME_JSON));

                            Intent intent = new Intent(MyMenu.this, MyActivity.class);
                            intent.putExtra("strips", json);
                            startActivity(intent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            alert.setView(layout);

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });
            alert.show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
