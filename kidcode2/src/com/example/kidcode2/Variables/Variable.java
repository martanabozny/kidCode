package com.example.kidcode2.Variables;

import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by marta on 10.03.14.
 */
public abstract class Variable {

    public String name;
    public String type;

    public abstract boolean compare(Variable v, String operation);
    public abstract boolean check(String operation);
    public abstract String[] showCheckMethods();
    public abstract String[] showCompareMethods();
}
