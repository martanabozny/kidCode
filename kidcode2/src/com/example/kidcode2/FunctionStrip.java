package com.example.kidcode2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by marta on 10.03.14.
 */
public abstract class FunctionStrip extends LinearLayout {
    public Variable returnedValue;
    public FunctionStrip previous;

    public FunctionStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void run() throws UnknownVariableException;

    public Variable getVariable(String name, String type) throws UnknownVariableException {
        FunctionStrip tmp = previous;

        /*AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Terefere");
        builder.setPositiveButton("Ok", null);

        AlertDialog dialog = builder.create();
        dialog.show();*/


        while (tmp != null) {
            if (tmp.returnedValue != null && tmp.returnedValue.name.equals(name) && tmp.returnedValue.type.equals(type)) {
                return tmp.returnedValue;
            }
            tmp = tmp.previous;
        }

        throw new UnknownVariableException(name);
    }

    public String selectVariable(String type){
        ArrayList list = new ArrayList<String>();
        FunctionStrip tmp = previous;

        while (tmp != null) {
            if(tmp.returnedValue.type.equals(type)){
               list.add(tmp.returnedValue.name);
            }
        }

        String variable;

        SelectVariableDialog dialog = new SelectVariableDialog(list, getContext());
        dialog.show();


        return dialog.selection;
    }
}
