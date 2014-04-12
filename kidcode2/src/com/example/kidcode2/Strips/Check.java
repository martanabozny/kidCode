package com.example.kidcode2.Strips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import com.example.kidcode2.R;
import com.example.kidcode2.UnknownVariableException;
import com.example.kidcode2.Variables.VarInteger;
import com.example.kidcode2.Variables.VarString;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marta on 12.04.14.
 */
public class Check extends FunctionStrip {

    public Check(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.check, this, true);

        returnedValue = boolean;

        final Button button = (Button) this.findViewById(R.id.button);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        if (button.getText().type == "VarInteger"){
            VarString.showCheckMethods(spinner);
        }
        else if (button.getText().type == "VarString"){
            VarString.showCheckMethods(spinner);
        }
        else if (button.getText().type == "VarImage"){
            VarString.showCheckMethods(spinner);
        }


        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVariable("VarInteger", button, true);
            }
        });
    }

    public Check(Context context) {
        this(context, null);
    }

    public void run() throws UnknownVariableException {}

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            Button button = (Button) this.findViewById(R.id.button);
            Spinner spinner = (Spinner)findViewById(R.id.spinner);


            object.put("function", spinner.getSelectedItemPosition());
            object.put("variable", button.getText().toString());
            object.put("type", "Check");

        } catch (JSONException e) {

        }
        return object;
    }

    public void fromJson(JSONObject object){
        Button button = (Button) this.findViewById(R.id.button);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);

        try {
            spinner.setSelection(object.getInt("function"));
            button.setText(object.getString("variable"));


        } catch (JSONException e) {

        }
    }

}
