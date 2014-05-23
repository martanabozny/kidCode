package com.example.kidcode2.Variables;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Toast;
import com.example.kidcode2.R;
import com.example.kidcode2.Strips.Strings;
import com.example.kidcode2.VariableConvertException;

/**
 * Created by marta on 10.03.14.
 */
public class VarInteger extends Variable {

    public double value;

    public VarInteger() {
        this.type = "VarInteger";
    }

    /*@Override
    public boolean codition(Variable v, String operation) {
        if (operation.equals("==")) {
            return ((VarInteger)v).value == value;
        }
        else if (operation.equals("<")) {
            return ((VarInteger)v).value < value;
        }
        else if (operation.equals("<")) {
            return ((VarInteger)v).value < value;
        }

        return false;
    }*/

    public String[] showCheckMethods() {
        String options[] = {"is odd", "is even"};
        return options;
    }


    public String[] showCompareMethods() {
        String options[] = {"==", "!=", "<", ">"};
        return options;
    }

    public boolean compare(Variable v, String operation) {
        VarInteger varint = (VarInteger) v;
        if (operation.contains("==")) {
            return varint.value == value;
        } else if (operation.contains("!=")) {
            return varint.value != value;
        } else if (operation.contains("<")) {
            if (value < varint.value)
                return true;
            else
                return false;
        } else if (operation.contains(">")) {
            return value > varint.value;
        }
        return true;
    }

    public boolean check(String operation) {
        if (operation.equals("is odd")) {
            return value % 2 != 0;
        } else if (operation.equals("is even")) {
            return value % 2 == 0;
        }
        return  true;
    }

    public Variable fromString(String string) throws VariableConvertException {
        VarInteger varint = new VarInteger();
        try {
            varint.value = Integer.valueOf(string);
        } catch (Exception e) {
            throw new VariableConvertException("Cannot convert " + string + " to string");
        }
        return varint;
    }

    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public Bitmap toImage() {
        Paint paint = new Paint();
        paint.setTextSize(20);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);

        int width = (int) (paint.measureText(String.valueOf(value)) + 0.5f); // round
        float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(100, 30, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(String.valueOf(value), 0, baseline, paint);
        return image;
    }
}