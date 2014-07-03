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
            } else if (type.equals("Foto")) {
                return new Foto();
            } else if (type.equals("Fotoop")) {
                return new Fotoop();
            } else if (type.equals("accelerometer")) {
                return new Accelerometer();
            } else if (type.equals("NewVariable")) {
                return new NewVariable();
            } else if (type.equals("Stop")) {
                return new Stop();
            } else if (type.equals("Strings")) {
                return new Strings();
            } else if (type.equals("IfForInt")) {
                return new IfForInt();
            } else if (type.equals("Draw")) {
                return new Draw();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
