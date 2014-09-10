package com.martas.kidcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

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
                Drawable d = Drawable.createFromPath(f.getAbsolutePath());
                img.setImageDrawable(d);
                //img.setImageBitmap(BitmapFactory.decodeFile(f.getAbsolutePath()));
                //img.setImageURI(Uri.fromFile(f));
                //Bitmap bmp = BitmapFactory.decodeFile(f.getAbsolutePath());
//                img.setMinimumHeight(100);
//                img.setMinimumWidth(100);
//                img.setImageBitmap(bmp);
            }
        } else {
            Log.e("kidcode", "File " + value + "does not exist");
        }
    }
}
