package com.example.kidcode2;

import com.example.kidcode2.Variables.Variable;

/**
 * Created by marta on 05.05.14.
 */
public class StopException extends Exception {

    private Variable var;

    public StopException(Variable variable){
        var = variable;
    }
    public String toString(){
        return var.name;
    }
    public String getValue(){
        return var.toString();
    }
}
