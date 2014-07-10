package com.martas.kidcode.Strips;

import android.content.Context;
import android.graphics.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.martas.kidcode.FunctionStrip;
import com.martas.kidcode.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marta on 01.06.14.
 */
public class Fotoop extends FunctionStrip {

    private String functionText = "";
    private String variableText = "";
    Bitmap newFoto;



    public ImageButton getButton(final Context context) {
        ImageButton button = new ImageButton(context);
        button.setBackgroundResource(R.drawable.fotoop);
        return button;
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
        Spinner function = (Spinner)view.findViewById(R.id.function);
        functionText = function.getSelectedItem().toString();


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
    public HashMap<String, String> run(Context context, HashMap<String, String> previousVariables) {
        String var = previousVariables.get(variableText);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(var, options);

        String result = "";

        if (functionText.contains("get height")) {
            result = "" + bitmap.getHeight();
        } else if(functionText.contains("get weight")){
            result = "" + bitmap.getWidth();
        }else if(functionText.contains("convert to black and white")) {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
            Bitmap blackAndWhiteBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Paint paint = new Paint();
            paint.setColorFilter(colorMatrixFilter);
            Canvas canvas = new Canvas(blackAndWhiteBitmap);
            canvas.drawBitmap(blackAndWhiteBitmap, 0, 0, paint);
            //return blackAndWhiteBitmap;
            return null;
        } else if(functionText.contains("negative")) {
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

        HashMap<String, String> r = new HashMap<String, String>();
        r.put(name, result);
        return r;
    }

    public void accelerometerVariable(int x,int y, int z) {

    }
}
