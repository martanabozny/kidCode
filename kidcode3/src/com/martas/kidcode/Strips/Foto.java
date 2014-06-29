package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;
import com.martas.kidcode.Camera;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.Open;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Foto extends FunctionStrip {
    String path = "";
    View view;

    public View getButton(final Context context, final int position, final JSONArray variables) {
        ImageButton button = getMyButton(context, position, variables);
        button.setBackgroundResource(R.drawable.foto);
        return button;
    }

    public View getPreview(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        TextView tv = new TextView(context);
        tv.setText(name + " = ");

        layout.addView(tv);
        try {
            ExifInterface exif = new ExifInterface(path);
            byte[] imageData = exif.getThumbnail();
            Bitmap thumbnail= BitmapFactory.decodeByteArray(imageData,0,imageData.length);

            final ImageView image = new ImageView(context);
            image.setImageBitmap(thumbnail);

            layout.addView(image);
        } catch (Exception e) {

        }

        return layout;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.foto, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        GridView fotos = (GridView)view.findViewById(R.id.fotos);
        Button takePicture = (Button)view.findViewById(R.id.newPicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureClicked(view);
            }
        });

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
        addAutocomplete(context, result, previousVariables);

        ArrayList<String> list = new ArrayList<String>();
        ImagesAdapter adapter = new ImagesAdapter(context, list);
        fotos.setAdapter(adapter);
        fotos.setNumColumns(context.getResources().getDisplayMetrics().widthPixels / 200);

        File kidcode_files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode-pictures/");
        File camera_files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera");
        if (kidcode_files != null && kidcode_files.exists()) {
            for (File f : kidcode_files.listFiles()) {
                if (f.isFile()){
                    String name = f.getName();
                    list.add(kidcode_files.getAbsolutePath() + "/" + name);
                    Log.e("plik", name);
                }
            }
        }

        if (camera_files != null && camera_files.exists()) {
            for (File f : camera_files.listFiles()) {
                if (f.isFile()){
                    String name = f.getName();
                    list.add(camera_files.getAbsolutePath() + "/" + name);
                    Log.e("plik", name);
                }
            }
        }

        adapter.notifyDataSetChanged();
        return view;
    }

    public void takePictureClicked(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), Camera.class));
    }

    class ImagesAdapter extends ArrayAdapter<String> {
        public ImagesAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.fotoelement, list);
        }

        public View getView(final int position, View convertView, final ViewGroup parent) {
            try {
                ExifInterface exif = new ExifInterface(getItem(position));
                byte[] imageData = exif.getThumbnail();
                Bitmap thumbnail= BitmapFactory.decodeByteArray(imageData,0,imageData.length);

                final ImageView image = new ImageView(getContext());
                image.setImageBitmap(thumbnail);
                image.setPadding(5, 5, 5, 5);

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        path = getItem(position);
                        TextView pathname = (TextView)view.findViewById(R.id.path);
                        pathname.setText(getItem(position));
                    }
                });

                return image;
            } catch (Exception e) {
                TextView tv = new TextView(getContext());
                return tv;
            }
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("type", "Foto");
            object.put("name", name);
            object.put("path", path);
        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object) {
        try {
            name = object.getString("name");
            path = object.getString("path");
        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, path);
        return r;
    }
}
