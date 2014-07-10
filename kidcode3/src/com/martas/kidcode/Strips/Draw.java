package com.martas.kidcode.Strips;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.martas.kidcode.Buttons;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import com.martas.kidcode.Setup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
* Created by marta on 01.06.14.
*/
public class Draw extends FunctionStrip {

    private String figureText = "";
    private String colorText = "";
    View view;
    String filename = "";


    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.math);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("draw");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.drawpreview, null);
        TextView result = (TextView)view.findViewById(R.id.result);
        result.setText(name);
        TextView figure = (TextView)view.findViewById(R.id.figure);
        figure.setText(figureText);
        Button button = (Button)view.findViewById(R.id.colorPreview);
        button.setBackgroundColor(Color.parseColor(colorText));

        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.draw, null);
        Button button = null;

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        addAutocomplete(context, result, previousVariables);

        ArrayList<String> list = new ArrayList<String>();
        GridView colors = (GridView)view.findViewById(R.id.colors);
        ColorsAdapter adapter = new ColorsAdapter(context, list);
        colors.setAdapter(adapter);
        colors.setNumColumns(view.getResources().getDisplayMetrics().widthPixels / 240);

        list.add("#FFFFFF");
        list.add("#FFFF00");
        list.add( "#FF0000");
        list.add("#808080");
        list.add("#800080");
        list.add( "#008000");
        list.add( "#0000FF");
        list.add("#00FFFF");
        list.add("#FFC0CB");

        adapter.notifyDataSetChanged();

        final Spinner figure = (Spinner)view.findViewById(R.id.figure);
        figureText = figure.getSelectedItem().toString();

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

        figure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                figureText = figure.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    public class ColorsAdapter extends ArrayAdapter<String> {
        public ColorsAdapter(Context context, ArrayList<String> list) {
            super(context, R.layout.codeactivity, list);
        }
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageButton button = new ImageButton(getContext());
            button.setBackgroundColor(Color.parseColor(getItem(position)));
            button.setMinimumHeight(80);
            button.setAdjustViewBounds(true);
            button.setPadding(5, 5, 5, 5);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorText = getItem(position);
                    Button colorPreview = (Button)view.findViewById(R.id.colorPreview);
                    colorPreview.setBackgroundColor(Color.parseColor(getItem(position)));

                }
            });

//            button.setAdjustViewBounds(false);
//            button.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//            button.setPadding(5, 5, 5, 5);

            return button;
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("figure", figureText);
            object.put("color", colorText);
            object.put("type", "Draw");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            figureText = object.get("figure").toString();
            colorText = object.get("color").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setColor(Color.parseColor(colorText));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        if (figureText.contains("triangle")) {

            Point point1_draw = new Point(50, 50);
            Point point2_draw = new Point(50,250);
            Point point3_draw = new Point(200, 150);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(point1_draw.x,point1_draw.y);
            path.lineTo(point2_draw.x,point2_draw.y);
            path.lineTo(point3_draw.x,point3_draw.y);
            path.lineTo(point1_draw.x,point1_draw.y);
            path.close();

            canvas.drawPath(path, paint);
        } else if (figureText.contains("rectangle")) {
            canvas.drawRect(100, 100, 200, 200, paint);

        } else if (figureText.contains("circle")) {
            canvas.drawCircle(100,100, 80,paint);
        }

        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode-pictures/");

        if (!f.exists()) {
            f.mkdir();
        }

        try {
             Calendar cal = Calendar.getInstance();
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
             filename = f.getAbsolutePath() + "/kidcode-" + sdf.format(cal.getTime()) + ".jpg";
             File output = new File(filename);
             output.createNewFile();
             FileOutputStream fos = new FileOutputStream(output);
             bitmap.compress(Bitmap.CompressFormat.JPEG, 99, fos);
             fos.close();
        } catch (Exception e) {
            Log.e("kidcode", Log.getStackTraceString(e));
        }

        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, filename);
        return r;
    }

    public void accelerometerVariable(int x,int y, int z) {

    }
}
