package com.example.kidcode2;

/**
 * Created by marta on 19.03.14.
 */
public class VariableConvertException extends Exception {
    private String des = new String();

    public VariableConvertException(String description){
        des = description;
    }
    public String toString(){
        return des;
    }
}
