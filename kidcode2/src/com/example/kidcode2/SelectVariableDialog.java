package com.example.kidcode2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by marta on 21.03.14.
 */
public class SelectVariableDialog extends Dialog {

    ScrollView scrollView;
    LinearLayout linearLayout;
    EditText editText;
    RadioGroup radioGroup;
    Button ok;

    public String selection;

    public SelectVariableDialog(ArrayList<String> list, Context context) {
        super(context);

        setTitle("Select variable");
        scrollView = new ScrollView(context);
        setContentView(scrollView);

        radioGroup = new RadioGroup(context);

        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(linearLayout);
        editText = new EditText(context);
        editText.setHint("New variable...");
        linearLayout.addView(editText);
        linearLayout.addView(radioGroup);

        for (String opis: list) {
            RadioButton radioButton = new RadioButton(context);

            /*String opis = "";
            try {
                opis = list.get(i).toString();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }*/

            radioButton.setText(opis);
            radioGroup.addView(radioButton);
        }

        ok =  new Button(context);
        ok.setText("ok");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!editText.getText().toString().equals("")){
                    selection = editText.getText().toString();
                } else {
                    int id = radioGroup.getCheckedRadioButtonId();
                    RadioButton button =(RadioButton) findViewById(id);
                    selection = button.getText().toString();
                }
                dismiss();
            }
        });
        linearLayout.addView(ok);
    }
}