package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
import java.util.ArrayList;

/**
 * Created by marta on 18.03.14.
 */
public class Foto extends FunctionStrip {

    private Camera camera;

    public Foto(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.foto, this, true);

        returnedValue = new VarImage();

        camera = Camera.open();
        Toast.makeText(this.getContext(), "Jest!", Toast.LENGTH_SHORT).show();

        final Button result = (Button)findViewById(R.id.text);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, result, false);
            }
        });
    }

    public Foto(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {
        camera.startPreview();
        camera.takePicture(null, null, new PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                Toast.makeText(getContext(), "Len:" + String.valueOf(bytes.length), Toast.LENGTH_SHORT).show();
                Button result = (Button) findViewById(R.id.result);
                ImageView preview = (ImageView) findViewById(R.id.preview);

                returnedValue.name = result.toString();
                ((VarImage)returnedValue).value = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                preview.setImageBitmap(((VarImage)returnedValue).value);
                File file = new File(Environment.getExternalStorageDirectory().toString(), "foto.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(bytes);
                    fos.close();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Exception: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        camera.stopPreview();
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

}
