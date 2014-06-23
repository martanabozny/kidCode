package com.martas.kidcode;

import com.martas.kidcode.Strips.*;
import com.martas.kidcode.Strips.Math;
import org.json.JSONObject;

/**
 * Created by marta on 05.06.14.
 */
public class JsonToStrip {
    public static FunctionStrip fromJson(JSONObject obj) {
        try {
            String type = obj.getString("type");
            if (type.equals("Math")) {
                return new Math();
            } else if (type.equals("Empty")) {
                return new Empty();
            } else if (type.equals("foto")) {
                return new foto();
            } else if (type.equals("fotoop")) {
                return new fotoop();
            } else if (type.equals("accelerometer")) {
                return new Accelerometer();
            } else if (type.equals("NewVariable")) {
                return new NewVariable();
            } else if (type.equals("ShowVariable")) {
                return new ShowVariable();
            } else if (type.equals("Stop")) {
                return new Stop();
            } else if (type.equals("strings")) {
                return new strings();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
