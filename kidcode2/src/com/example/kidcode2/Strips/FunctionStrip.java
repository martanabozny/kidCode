package com.example.kidcode2.Strips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.kidcode2.SelectVariableDialog;
import com.example.kidcode2.UnknownVariableException;
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
    }

    public abstract void run() throws UnknownVariableException;

    public abstract JSONObject toJson();

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

    public void selectVariable(String type, final Button button){
        ArrayList<String> list = new ArrayList<String>();
        FunctionStrip tmp = previous;

        while (tmp != null) {

            if (tmp.returnedValue != null && tmp.returnedValue.type.equals(type)){
               list.add(tmp.returnedValue.name);
            }
            tmp = tmp.previous;
        }

        final SelectVariableDialog dialog = new SelectVariableDialog(list, getContext());
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                button.setText(dialog.selection.toString());
                returnedValue.name = dialog.selection.toString();
            }
        });

        dialog.show();

//
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
//        builder.setCancelable(true);
//        builder.setMessage("abc");
//        builder.setInverseBackgroundForced(true);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
////                button.setText(dialog.selection.toString());
////                returnedValue.name = dialog.selection.toString();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        builder.show();

    }
}