package com.example.kidcode2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by marta on 08.04.14.
 */
public class MyMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
    }

    public void OnNewClick(View v) {
        try {
            startActivity(new Intent(MyMenu.this, MyActivity.class));
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void OnOpenClick(View v) {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Open");
        alert.setMessage("Files");
        final LinearLayout layout= new LinearLayout(getApplicationContext());
        final String[] list = fileList();
        for (int i = 0; i < list.length; i ++) {
            final Button button = new Button(getApplicationContext());
            button.setText(list[i].toString());
            layout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(MyMenu.this, MyActivity.class);
                        FileInputStream fis = openFileInput(button.getText().toString());
                        byte[] bufer = new byte[100];
                        int i = fis.read(bufer);

                        Toast.makeText(getApplicationContext(), "Returned" + i, Toast.LENGTH_LONG).show();
                        intent.putExtra("strips", bufer.toString());
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
    }

    public void OnLastClick(View v) {

    }
}
