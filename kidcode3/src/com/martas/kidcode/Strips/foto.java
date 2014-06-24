package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by marta on 01.06.14.
 */
public class foto extends FunctionStrip implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    Boolean isPictrueTaken = false;
    Camera camera;
    Bitmap pictrue;
    SurfaceView frame;
    ImageView preview;

    public View getButton(final Context context, final int position) {

        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.foto);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Setup.class);
                intent.putExtra("strip", toJson().toString());
                intent.putExtra("position", String.valueOf(position));
                context.startActivity(intent);
            }
        });
        return button;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setText("" + name + " = ");
        return view;
    }

    public View getSetup(Context context, Map<String, String> previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.foto, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        frame = (SurfaceView)view.findViewById(R.id.frame);
        preview = (ImageView)view.findViewById(R.id.preview);
        preview.setVisibility(View.GONE);
        mHolder = frame.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        camera = getCameraInstance();

        result.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(camera != null)
                {
                    camera.takePicture(null, null, myPictureCallback);
                }
            }
        });

        return view;
    }

    Camera.PictureCallback myPictureCallback = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] bytes, Camera arg1) {
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            Bitmap correctBmp = Bitmap.createBitmap(bitmapPicture, 0, 0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), null, true);
            pictrue = correctBmp;
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera=null;
            frame.setVisibility(View.GONE);
            preview.setVisibility(View.VISIBLE);
            preview.setImageBitmap(correctBmp);

        }};

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {

            object.put("type", "foto");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {

            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(HashMap<String, String> previousVariables) {
        return  null;
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
            if (camera != null){
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            }
        } catch (IOException e) {

        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null){
        camera.stopPreview();
        camera.setPreviewCallback(null);
        camera.release();
        camera=null;
        }
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