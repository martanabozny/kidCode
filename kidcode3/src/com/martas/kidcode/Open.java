package com.martas.kidcode;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;

import java.util.ArrayList;

/**
 * Created by marta on 04.05.14.
 */
public class Open extends ListActivity {

    private MyArrayAdapter adapter;
    private ArrayList<String> list = new ArrayList<String>();
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open);
        mPrefs = getSharedPreferences("strips", MODE_PRIVATE);


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

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode";
        File kidCodeDir = new File(path);
        if (!kidCodeDir.exists()) {
            kidCodeDir.mkdir();
        }

        if (kidCodeDir != null) {
            list.clear();
            for (File f : kidCodeDir.listFiles()) {
                if (f.isFile()){
                    String name = f.getName();
                    list.add(name);
                }
            }
            adapter.notifyDataSetChanged();
        }
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
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode/";
        try {
            String strips = "";
            File input = new File(path + filename);
            FileInputStream inputStream = new FileInputStream(input);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                strips = stringBuilder.toString();
            }


            SharedPreferences.Editor ed = mPrefs.edit();
            ed.putString("name",filename);
            ed.putString("strips", strips);
            ed.commit();

            Intent intent = new Intent(Open.this, CodeActivity.class);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void deleteStrips(String filename) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode/" + filename;
            File file = new File(path);
            file.delete();
            updateList();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
