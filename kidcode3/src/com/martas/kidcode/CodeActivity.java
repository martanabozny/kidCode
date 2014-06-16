package com.martas.kidcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
        deleteClicked = false;
        adapter.notifyDataSetChanged();
    }

    public void delete(View view) {
        deleteClicked = !deleteClicked;
        addClicked = false;
        adapter.notifyDataSetChanged();
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
        menu.add("clear");
        menu.add("files");
        menu.add("help");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Save")) {
            showDialog();
            return true;
        }else if (item.getTitle().toString().equals("clear")){
            return true;
        } else if (item.getTitle().toString().equals("files")){
            return true;
        }else if (item.getTitle().toString().equals("help")){
            return true;
        }else
            return super.onOptionsItemSelected(item);
        }



    public void save(String fileName) {
       /* MyScrollView code = (MyScrollView)findViewById(R.id.code);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        db.execSQL("DELETE FROM " + DataBase.FeedEntry2.TABLE_NAME + " WHERE " + DataBase.FeedEntry2.COLUMN_FILE_NAME + " = ?", new String[]{fileName});

        ContentValues values = new ContentValues();
        values.put(DataBase.FeedEntry2.COLUMN_NAME_JSON, code.toJson());
        values.put(DataBase.FeedEntry2.COLUMN_FILE_NAME, fileName);
        db.insert(DataBase.FeedEntry2.TABLE_NAME, null, values);*/
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


    public class MySimpleArrayAdapter extends ArrayAdapter<String> {
        private final Context context;

        public MySimpleArrayAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
            this.context = context;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            JSONObject obj;
            try {
                obj = new JSONObject(getItem(position));
            } catch (Exception e) {
                return  null;
            }

            FunctionStrip strip = JsonToStrip.fromJson(obj);

            strip.fromJson(obj);

            if (addClicked) {

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                Button plus = new Button(context);
                plus.setText("+");
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CodeActivity.this, Buttons.class);
                        intent.putExtra("position", String.valueOf(position));
                        startActivity(intent);

                    }
                });

                layout.addView(strip.getPreview(context));
                layout.addView(plus);
                return layout;

            } else if (deleteClicked && position != 0) {
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                Button remove = new Button(context);
                remove.setText("-");
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                layout.addView(strip.getPreview(context));
                layout.addView(remove);
                return layout;
            } else {
                return strip.getPreview(context);
            }
        }
    }
}
