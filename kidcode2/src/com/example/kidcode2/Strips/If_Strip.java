package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.kidcode2.R;
import com.example.kidcode2.Strips.FunctionStrip;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class If_Strip extends FunctionStrip {

    public If_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.if_strip, this, true);

        returnedValue = new VarString();

        EditText condition  = (EditText)findViewById(R.id.condition);
        //condition.setOnDragListener(new MyDragListener());
    }

    public If_Strip(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("type", "If_Strip");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){

    }
}
