package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.*;
import android.widget.*;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by marta on 18.03.14.
 */
public class Foto extends FunctionStrip implements SurfaceHolder.Callback{

    private SurfaceHolder mHolder;
    private Camera camera;



    public Foto(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.foto, this, true);

        returnedValue = new VarImage();

        SurfaceView frame = (SurfaceView)findViewById(R.id.frame);
        mHolder = frame.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        camera = getCameraInstance();

        final Button result = (Button)findViewById(R.id.text);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("VarImage");
                selectVariable(list, result, true);
            }
        });

        final Button cancel = (Button) findViewById(R.id.cancel);
        final View realThis = this;

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((ViewManager)realThis.getParent()).removeView(realThis);
                    returnedValue = null;

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Foto(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {
        camera.stopPreview();
        camera.takePicture(null, null, new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Toast.makeText(getContext(), "Len:" + String.valueOf(bytes.length), Toast.LENGTH_SHORT).show();
                /*Button result = (Button) findViewById(R.id.result);
                SurfaceView frame = (SurfaceView) findViewById(R.id.frame);

                returnedValue.name = result.toString();
                ((VarImage)returnedValue).value = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                //frame.setImageBitmap(((VarImage) returnedValue).value);
                File file = new File(Environment.getExternalStorageDirectory().toString(), "foto.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Exception: " + e.toString(), Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        camera.startPreview();
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button variable = (Button) findViewById(R.id.result);

            object.put("variable", variable.toString());
            object.put("type", "Foto");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        Button variable = (Button) findViewById(R.id.result);;

        try {
            variable.setText(object.getString("variable"));

        } catch (JSONException e) {

        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e){

        }
        return c;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {

            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (IOException e) {

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();

        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();

        } catch (Exception e){

        }
    }


}
