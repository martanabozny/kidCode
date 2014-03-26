package com.example.kidcode2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by marta on 21.03.14.
 */
public class SelectVariableDialog extends AlertDialog {

    ScrollView scrollView;
    LinearLayout linearLayout;
    EditText editText;
    RadioGroup radioGroup;

    public String selection;

    public SelectVariableDialog(ArrayList list, Context context) {
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

        for (int i = 0; i < list.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioGroup.addView(radioGroup);
            radioButton.setText(list.get(i).toString());
        }

        setButton(BUTTON_POSITIVE, "Ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioButton radio = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                selection = radio.getText().toString();
            }
        });

        setButton(BUTTON_NEGATIVE, "Anuluj", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selection = "";
            }
        });
    }
}
