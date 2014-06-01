package com.example.kidcode2.Strips;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewManager;
import android.widget.*;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarImage;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import com.example.kidcode2.Variables.Variable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by marta on 17.04.14.
 */
public class ShowVariable extends FunctionStrip {

    public ShowVariable(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.show_variable, this, true);

        final Button variable = (Button)findViewById(R.id.variable);

        variable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList list = collectVariables("");
                selectVariable(list, variable, false);
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

    public ShowVariable(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {

        try {
            final Button variable = (Button)findViewById(R.id.variable);
            ImageView value_ = (ImageView)findViewById(R.id.value);

            Variable var = getVariable(variable.getText().toString(), "");
            Bitmap map = var.toImage();
            value_.setImageBitmap(map);



        } catch (UnknownVariableException e) {
            Toast.makeText(getContext(), "Unknown variable: " + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button variable = (Button)this.findViewById(R.id.variable);


            object.put("variable", variable.getText().toString());
            object.put("type", "ShowVariable");
        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        try {
            Button variable = (Button)this.findViewById(R.id.variable);
            variable.setText(object.getString("variable"));
        } catch (JSONException e) {

        }
    }
}
