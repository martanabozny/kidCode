package com.example.kidcode2;

import android.content.Context;
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

        while (tmp != null) {
            if (tmp.returnedValue != null && tmp.returnedValue.name.equals(name) && tmp.returnedValue.type.equals(type)) {
                return tmp.returnedValue;
            }
            tmp = tmp.previous;
        }

        throw new UnknownVariableException(name);
    }
}
