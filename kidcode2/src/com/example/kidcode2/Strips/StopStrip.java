package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.kidcode2.R;
import com.example.kidcode2.StopException;
import com.example.kidcode2.Strips.FunctionStrip;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarImage;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 05.05.14.
 */
public class StopStrip extends FunctionStrip {

    public StopStrip(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stop_strip, this, true);

        returnedValue = null;

        final Button result = (Button) this.findViewById(R.id.result);

        result.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, result, false);
            }
        });

        final Button cancel = (Button) findViewById(R.id.cancel);
        final View realThis = this;

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((ViewManager)realThis.getParent()).removeView(realThis);
                    returnedValue = null;

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public StopStrip(Context context) {
        this(context, null);
    }

    public void run() throws StopException, UnknownVariableException {

        final Button result = (Button)findViewById(R.id.result);

        Variable var = getVariable(result.getText().toString(), "");

        throw new StopException(var);
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button result = (Button)this.findViewById(R.id.result);

            object.put("result", result.getText().toString());

            object.put("type", "StopStrip");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        try {
            Button result = (Button)this.findViewById(R.id.result);

            result.setText(object.getString("result"));

        } catch (JSONException e) {

        }
    }
}
