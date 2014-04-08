package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 04.04.14.
 */
public class While_Strip extends FunctionStrip {

    public While_Strip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.while_strip, this, true);

        returnedValue = new VarString();

        EditText condition  = (EditText)findViewById(R.id.condition);
        //condition.setOnDragListener(new MyDragListener());
    }

    public While_Strip(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("type", "While_Strip");

        } catch (JSONException e) {

        }
        return object;
    }
}
