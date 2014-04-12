package com.example.kidcode2.Variables;

import android.content.res.Resources;
import com.example.kidcode2.R;

import java.util.ArrayList;

/**
 * Created by marta on 17.03.14.
 */
public class VarString extends Variable {

    public String value;

    public VarString() {
        this.type = "VarString";
    }

    public String[] showCheckMethods(){
        return Resources.getSystem().getStringArray(R.array.compare_String);
    }


    public String[] showCompareMethods(){
        return Resources.getSystem().getStringArray(R.array.check_String);
    }

    public  boolean compare(Variable v, String operation){
        VarString varstring = (VarString) v;
        if (operation.equals("equals")) {
            return varstring.equals(value);
        } else if (operation.equals("contains")) {
            return varstring.value.contains(value);
        } else if (operation.equals("longer")) {
            return varstring.value.length() == value.length();
        }
        return  true;
    }

    public boolean check(String operation){
        if (operation.equals("is lover")) {
            return true;
        } else if (operation.equals("is upper")) {
            return true;
        }
        return  true;
    }
}
