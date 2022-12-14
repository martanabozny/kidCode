package com.martas.kidcode.Strips;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Camera;
import com.martas.kidcode.FunctionStrip;
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
    int ICON_WIDTH = 200;

    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.foto);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("camera");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setBackgroundResource(R.drawable.foto_background);

        TextView tv = new TextView(context);
        tv.setText(name + " " + " = " + " ");
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);

        layout.addView(tv);
        try {
            final ImageView image = new ImageView(context);

            Bitmap thumbnail = BitmapFactory.decodeFile(path);
            image.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, ICON_WIDTH, ICON_WIDTH * thumbnail.getHeight() / thumbnail.getWidth(), false));

            layout.addView(image);
        } catch (Exception e) {

        }

        return layout;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.foto, null);

        AutoCompleteTextView result = (AutoCompleteTextView) view.findViewById(R.id.result);
        TextView pathText = (TextView) view.findViewById(R.id.path);
        GridView fotos = (GridView) view.findViewById(R.id.fotos);
        Button takePicture = (Button) view.findViewById(R.id.newPicture);

        result.setText(name);
        pathText.setText(path);

        addAutocomplete(context, result, previousVariables);

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

        ArrayList<String> list = new ArrayList<String>();
        ImagesAdapter adapter = new ImagesAdapter(context, list);
        fotos.setAdapter(adapter);
        fotos.setNumColumns(context.getResources().getDisplayMetrics().widthPixels / ICON_WIDTH);

        File kidcode_files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode-pictures/");
        File camera_files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera");


        if (camera_files != null && camera_files.exists()) {
            for (File f : camera_files.listFiles()) {
                if (f.isFile()) {
                    String name = f.getName();
                    list.add(camera_files.getAbsolutePath() + "/" + name);
                }
            }
        }

        if (kidcode_files != null && kidcode_files.exists()) {
            for (File f : kidcode_files.listFiles()) {
                if (f.isFile()) {
                    String name = f.getName();
                    list.add(0, kidcode_files.getAbsolutePath() + "/" + name);
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
            final ImageView image = new ImageView(getContext());
            image.setPadding(5, 5, 5, 5);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    path = getItem(position);
                    TextView pathname = (TextView) view.findViewById(R.id.path);
                    pathname.setText(getItem(position));
                }
            });

            try {
                ExifInterface exif = new ExifInterface(getItem(position));
                byte[] imageData = exif.getThumbnail();

                if (imageData != null) {
                    Bitmap thumbnail = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                    image.setImageBitmap(thumbnail);
                } else {
                    Bitmap thumbnail = BitmapFactory.decodeFile(getItem(position));
                    image.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, ICON_WIDTH, ICON_WIDTH * thumbnail.getHeight() / thumbnail.getWidth(), false));
                }

                return image;
            } catch (Exception e) {
                TextView tv = new TextView(getContext());
                tv.setText("");
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

    public void accelerometerVariable(int x, int y, int z) {

    }
}
