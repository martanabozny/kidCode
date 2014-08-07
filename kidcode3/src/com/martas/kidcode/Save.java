package com.martas.kidcode;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

import java.util.ArrayList;

/**
 * Created by marta on 04.05.14.
 */

public class Save extends ListActivity {

    private MyArrayAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private SharedPreferences mPrefs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save);
        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);

        adapter = new MyArrayAdapter(this, list);
        setListAdapter(adapter);
        updateList();

        EditText name = (EditText)findViewById(R.id.name);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {  }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                EditText textname = (EditText)findViewById(R.id.name);

                if (!charSequence.toString().matches("^[a-zA-Z0-9_\\-+]+$")) {
                    textname.setBackgroundColor(Color.RED);
                } else {
                    textname.setBackgroundColor(android.R.drawable.btn_default);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }
    public void savefile(String filename, String contains) {
        String eol = System.getProperty("line.separator");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            fos.write(contains.getBytes());

        } catch (Exception e) {
            // e.printStackTrace();
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

    public void okClicked(View view) {
        final String contains = mPrefs.getString("strips", "");
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode/";
        EditText textname = (EditText)findViewById(R.id.name);
        final String name = textname.getText().toString();
        File file = new File(path + name);

        if (!file.exists()) {
            savefile(path+name, contains);
            finish();
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("File already exist. Should be ovveride?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            savefile(path+name, contains);
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    private void updateList() {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode";
        File kidCodeDir = new File(path);
        if (!kidCodeDir.exists()) {
            kidCodeDir.mkdir();
        }

        if (kidCodeDir != null) {
            Log.e("kidcode", kidCodeDir.toString());

            list.clear();
            File[] files = kidCodeDir.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String name = files[i].getName();
                        list.add(name);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    class MyArrayAdapter extends ArrayAdapter<String> {
        Context context;

        public MyArrayAdapter(Context context, ArrayList<String> values) {
            super(context, R.layout.elementtosave, values);
            this.context = context;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.elementtosave, parent, false);

            Button fileName = (Button) rowView.findViewById(R.id.fileName);
            fileName.setText(getItem(position));

            fileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    saveStrips(getItem(position));
                }
            });
            return rowView;
        }
    }

    public void saveStrips(String filename) {
        EditText textname = (EditText)findViewById(R.id.name);
        textname.setText(filename);
    }
}

