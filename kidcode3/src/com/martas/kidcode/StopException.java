package com.martas.kidcode;

/**
 * Created by marta on 23.06.14.
 */
public class StopException extends Exception {
    String result = "";
    String value = "";
    public StopException(String result, String value){
        this.result = result;
        this.value = value;
    }

}
