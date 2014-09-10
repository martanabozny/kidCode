package com.martas.kidcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.martas.kidcode.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by marta on 24.06.14.
 */
public class Camera extends Activity implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    android.hardware.Camera camera;
    String filename = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        SurfaceView frame = (SurfaceView) findViewById(R.id.frame);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(null, null, myPictureCallback);
            }
        });
        mHolder = frame.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        camera = getCameraInstance();

        Intent intent = getIntent();
    }

    android.hardware.Camera.PictureCallback myPictureCallback = new android.hardware.Camera.PictureCallback() {

        public void onPictureTaken(byte[] bytes, android.hardware.Camera arg1) {
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap picture = Bitmap.createBitmap(bitmapPicture, 0, 0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), null, true);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode-pictures/");

            if (!f.exists()) {
                f.mkdir();
            }

            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

                filename = "kidcode-" + sdf.format(cal.getTime());
                File output = new File(f.getAbsolutePath() + "/" + filename + ".jpg");
                Log.e("kidcode", output.getAbsolutePath());
                output.createNewFile();

                FileOutputStream fos = new FileOutputStream(output);
                //picture.compress(Bitmap.CompressFormat.JPEG, 1, fos);
                fos.write(bytes);
                fos.close();

                SurfaceView frame = (SurfaceView) findViewById(R.id.frame);
                frame.setVisibility(View.GONE);
                ImageView img = (ImageView) findViewById(R.id.preview);
                img.setVisibility(View.VISIBLE);
                img.setImageBitmap(picture);
            } catch (Exception e) {
                Log.e("kidcode", Log.getStackTraceString(e));
            }
        }
    };

    public static android.hardware.Camera getCameraInstance() {
        android.hardware.Camera c = null;
        try {
            c = android.hardware.Camera.open();
        } catch (Exception e) {

        }
        return c;
    }

    public void okClicked(View view) {
        if (filename.equals("")) {
            Toast.makeText(getApplicationContext(), "Create picture first!", Toast.LENGTH_LONG).show();
        } else {
            finish();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            if (camera != null) {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            }
        } catch (IOException e) {

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // IfForInt your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();

        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }
}
