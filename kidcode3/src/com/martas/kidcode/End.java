package com.martas.kidcode;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

/**
 * Created by marta on 05.05.14.
 */
public class End extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);

        String name = getIntent().getStringExtra("result");
        String value = getIntent().getStringExtra("value");

        EditText name_ = (EditText) findViewById(R.id.name);
        name_.setKeyListener(null);
        name_.setText(name);
        EditText value_ = (EditText) findViewById(R.id.value);
        value_.setKeyListener(null);
        value_.setText(value);

        File f = new File(value);
        if (f.exists()) {
            if (value.endsWith(".jpg") || value.endsWith(".JPG")) {
                ImageView img = (ImageView) findViewById(R.id.image);

                Bitmap thumbnail = BitmapFactory.decodeFile(f.getAbsolutePath());
                img.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 400, 400 * thumbnail.getHeight() / thumbnail.getWidth(), false));
            }
        } else {
            Log.e("kidcode", "File " + value + "does not exist");
        }
    }
}
