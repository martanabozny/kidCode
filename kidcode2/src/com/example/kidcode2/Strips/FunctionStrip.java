package com.example.kidcode2.Strips;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.kidcode2.SelectVariableDialog;
import com.example.kidcode2.StopException;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.VariableConvertException;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 10.03.14.
 */
public abstract class FunctionStrip extends LinearLayout {
    public Variable returnedValue;
    public FunctionStrip previous;

    public FunctionStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0, 0, 0, 10);
    }

    public abstract void run() throws UnknownVariableException, VariableConvertException, StopException;

    public abstract JSONObject toJson();
    public abstract void fromJson(JSONObject object);

    public Variable getVariable(String name, String type) throws UnknownVariableException {
        FunctionStrip tmp = previous;

        while (tmp != null) {
            if (tmp.returnedValue != null && tmp.returnedValue.name != null && tmp.returnedValue.name.equals(name) && tmp.returnedValue.type.contains(type)) {
                return tmp.returnedValue;
            }
            tmp = tmp.previous;
        }

        throw new UnknownVariableException(name);
    }

    public ArrayList collectVariables(String type) {
        ArrayList<String> list = new ArrayList<String>();
        FunctionStrip tmp = previous;

        while (tmp != null) {

            if (tmp.returnedValue != null && tmp.returnedValue.type.contains(type) && tmp.returnedValue.name != null && !tmp.returnedValue.name.equals("")) {
                boolean contains = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).toString().equals(tmp.returnedValue.name)) {
                        contains = true;
                    }
                }

                if (!contains)
                    list.add(tmp.returnedValue.name);
            }
            tmp = tmp.previous;
        }
        return list;
    }

    public void selectVariable(ArrayList list, final Button button, final boolean setReturnedValue){
        final SelectVariableDialog dialog = new SelectVariableDialog(list, getContext());

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (dialog.selection != null) {
                    button.setText(dialog.selection.toString());
                    if (setReturnedValue) {
                        returnedValue.name = dialog.selection.toString();
                        if (!dialog.selection.matches("^[a-zA-Z].*")) {
                            button.setBackgroundColor(Color.RED);
                            Toast.makeText(getContext(), "Wrong variable name. Should start with character", Toast.LENGTH_LONG).show();
                        } else {
                            button.setBackgroundResource(android.R.drawable.btn_default);
                        }
                    }
                }
            }
        });

        dialog.show();
    }

    public void makeShadow() {
        setPadding(0, 0, 0, 40);
    }

    public void makeNormal() {
        setPadding(0, 0, 0, 10);
    }

}
