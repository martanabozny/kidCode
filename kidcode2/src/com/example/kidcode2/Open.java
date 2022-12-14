package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
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
    private MyArrayAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open);

        dataBase = new DataBase(this);
        dataBase.createDataBase();

        try {
            dataBase.openDataBase();
        } catch (SQLException mSQLException)
        {
            Toast.makeText(this, mSQLException.toString(), Toast.LENGTH_LONG).show();
        }

        db = dataBase.getReadableDatabase();

        adapter = new MyArrayAdapter(this, list);
        setListAdapter(adapter);
        updateList();

        EditText search = (EditText)findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                adapter.getFilter().filter(charSequence.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void updateList() {
        String selectQuery = "SELECT " + DataBase.FeedEntry2.COLUMN_FILE_NAME + " FROM " + DataBase.FeedEntry2.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.getCount() == 0) {
            return;
        }
        c.moveToFirst();

        list.clear();
        do {
            String filename = c.getString(c.getColumnIndex(DataBase.FeedEntry2.COLUMN_FILE_NAME));
            list.add(filename);
        } while (c.moveToNext());
        adapter.notifyDataSetChanged();
    }

    class MyArrayAdapter extends ArrayAdapter<String> {
        Context context;

        public MyArrayAdapter(Context context, ArrayList<String> values) {
            super(context, R.layout.element, values);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.element, parent, false);

            Button fileName = (Button) rowView.findViewById(R.id.fileName);
            fileName.setText(getItem(position));

            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openStrips(getItem(position));
                }
            });

            Button delete = (Button) rowView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                    alert.setMessage("Delete selected item?");

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteStrips(getItem(position));
                        }
                    });

                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Canceled.
                        }
                    });
                    alert.show();
                }
            });

            return rowView;
        }
    }

    public void openStrips(String filename) {
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

    public void deleteStrips(String filename) {
        try {
            String selectQuery = "DELETE FROM " + DataBase.FeedEntry2.TABLE_NAME + " WHERE name = ?";
            db.execSQL(selectQuery, new String[]{filename});
            updateList();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
