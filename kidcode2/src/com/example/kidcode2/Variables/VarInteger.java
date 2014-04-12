package com.example.kidcode2.Variables;

import android.content.res.Resources;
import com.example.kidcode2.R;
import com.example.kidcode2.Strips.FunctionStrip;

import java.util.ArrayList;

/**
 * Created by marta on 10.03.14.
 */
public class VarInteger extends Variable{

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
        return Resources.getSystem().getStringArray(R.array.compare_integer);
    }


    public String[] showCompareMethods() {
        return Resources.getSystem().getStringArray(R.array.check_integer);
    }

    public  boolean compare(Variable v, String operation) {
        VarInteger varint = (VarInteger) v;
        if (operation.equals("equals")) {
            return varint.value == value;
        } else if (operation.equals("different")) {
            return varint.value != value;
        } else if (operation.equals("less than")) {
            return value < varint.value;
        } else if (operation.equals("grather than")) {
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
}