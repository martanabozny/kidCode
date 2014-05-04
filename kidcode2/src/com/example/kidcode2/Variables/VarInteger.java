package com.example.kidcode2.Variables;

import android.content.res.Resources;
import android.widget.Toast;
import com.example.kidcode2.R;

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
        String options[] = {"equals", "different", "less than", "grather than"};
        return options;
    }

    public boolean compare(Variable v, String operation) {
        VarInteger varint = (VarInteger) v;
        if (operation.contains("equals")) {
            return varint.value == value;
        } else if (operation.contains("different")) {
            return varint.value != value;
        } else if (operation.contains("less")) {
            if (value < varint.value)
                return true;
            else
                return false;
        } else if (operation.contains("grather")) {
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