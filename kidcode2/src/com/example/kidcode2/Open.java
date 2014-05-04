package com.example.kidcode2;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.kidcode2.DataBase;
import com.example.kidcode2.R;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marta on 04.05.14.
 */
public class Open extends ListActivity {
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

        db = dataBase.getReadableDatabase();

        ArrayList<String> list = new ArrayList<String>();
        String selectQuery = "SELECT " + DataBase.FeedEntry2.COLUMN_FILE_NAME + " FROM " + DataBase.FeedEntry2.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();

        do {
            String filename = c.getString(c.getColumnIndex(DataBase.FeedEntry2.COLUMN_FILE_NAME));
            list.add(filename);
        } while (c.moveToNext());
    }

    public void openfile(String filename) {
        try {
            String selectQuery = "SELECT " + DataBase.FeedEntry2.COLUMN_FILE_NAME + ", " +
                    DataBase.FeedEntry2.COLUMN_NAME_JSON +" FROM " + DataBase.FeedEntry2.TABLE_NAME + " WHERE name = ?";

            Cursor c = db.rawQuery(selectQuery, new String[]{filename});

            c.moveToFirst();
            String json = c.getString(c.getColumnIndexOrThrow(DataBase.FeedEntry2.COLUMN_NAME_JSON));

            Intent intent = new Intent(Open.this, CodeActivity.class);
            intent.putExtra("strips", json);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
