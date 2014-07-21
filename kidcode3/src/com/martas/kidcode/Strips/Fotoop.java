package com.martas.kidcode.Strips;

import android.content.Context;
import android.graphics.*;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.ConvertException;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Fotoop extends FunctionStrip {

    private String functionText = "";
    private String variableText = "";
    Bitmap newFoto;



    public LinearLayout getButton(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.fotoop);
        layout.addView(button);
        TextView text = new TextView(context);
        text.setText("foto operations");
        layout.addView(text);
        return layout;
    }

    public View getPreview(Context context) {
        TextView view = new TextView(context);
        view.setBackgroundResource(R.drawable.fotoop_background);
        view.setText("" + name + " = " + variableText + "." +  " " + functionText);
        view.setTextColor(Color.BLACK);
        view.setTextSize(20);
        return view;
    }

    public View getSetup(Context context, JSONArray previousVariables) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.fotoop, null);

        AutoCompleteTextView result = (AutoCompleteTextView)view.findViewById(R.id.result);
        AutoCompleteTextView variable = (AutoCompleteTextView)view.findViewById(R.id.variable);
        final Spinner function = (Spinner)view.findViewById(R.id.function);
        functionText = function.getSelectedItem().toString();

        result.setText(name);
        variable.setText(variableText);

        addAutocomplete(context, result, previousVariables);
        addAutocomplete(context, variable, previousVariables);

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

        variable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                variableText = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        function.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                functionText = function.getSelectedItem().toString();
                Log.d("Math.itemListener", "Selected function: " + function);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();
        try {
            object.put("variable", variableText);
            object.put("function", functionText);
            object.put("type", "Fotoop");
            object.put("name", name);

        } catch (JSONException e) {

        }
        return object;
    }
    public void fromJson(JSONObject object) {
        try {
            functionText = object.get("function").toString();
            variableText = object.get("variable").toString();
            name = object.get("name").toString();

        } catch (JSONException e) {

        }
    }
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) throws ConvertException {
        Bitmap bitmap = variableToBitmap(variableText, previousVariables);

        String result = null;

        if (functionText.contains("get height")) {
            result = "" + bitmap.getHeight();
        } else if (functionText.contains("get weight")){
            result = "" + bitmap.getWidth();
        } else if (functionText.contains("convert to black and white")) {
            Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
            Paint paint = new Paint();
            paint.setColorFilter(colorMatrixFilter);
            Canvas canvas = new Canvas(copy);
            canvas.drawBitmap(bitmap, 0, 0, paint);

            File kidcode_files = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/kidCode-pictures/");
            try {
                FileOutputStream fos = new FileOutputStream(kidcode_files.getAbsolutePath() + "/" + name + ".jpg");
                copy.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
                result = kidcode_files.getAbsolutePath() + "/" + name + ".jpg";
            } catch (Exception e) {

            }
        } else if (functionText.contains("negative")) {
            ColorMatrix negativeMatrix =new ColorMatrix();
            float[] negMat={-1, 0, 0, 0, 255, 0, -1, 0, 0, 255, 0, 0, -1, 0, 255, 0, 0, 0, 1, 0 };
            negativeMatrix.set(negMat);
            final ColorMatrixColorFilter colorFilter= new ColorMatrixColorFilter(negativeMatrix);
            Bitmap rBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Paint paint=new Paint();
            paint.setColorFilter(colorFilter);
            Canvas myCanvas =new Canvas(rBitmap);
            myCanvas.drawBitmap(rBitmap, 0, 0, paint);
            //return rBitmap;
            return null;
        }

        if (result != null) {
            HashMap<String, String> r = new HashMap<String, String>();
            r.put(name, result);
            return r;
        } else {
            return null;
        }
    }

    public void accelerometerVariable(int x,int y, int z) {

    }
}
