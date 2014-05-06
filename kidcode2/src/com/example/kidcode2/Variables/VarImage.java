package com.example.kidcode2.Variables;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.example.kidcode2.R;

import java.util.ArrayList;

/**
 * Created by marta on 18.03.14.
 */
public class VarImage extends Variable {

    public Bitmap value;

    public VarImage() {
        this.type = "VarImage";
    }

    public String[] showCheckMethods(){
        String options[] = {};
        return options;
    }


    public String[] showCompareMethods(){
        String options[] = {};
        return options;
    }

    public  boolean compare(Variable v, String operation){
        return  true;
    }

    public boolean check(String operation){
        return true;
    }

    public Variable fromString(String string) {
        VarImage varim = new VarImage();
        return varim;
    }

    public String toString() {
        return "";
    }
}
