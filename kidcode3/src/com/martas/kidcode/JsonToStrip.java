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
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
