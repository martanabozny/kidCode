package com.example.kidcode2;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.*;

import java.util.ArrayList;

/**
 * Created by marta on 21.03.14.
 */
public class SelectVariable extends Dialog {

    ScrollView scrollView;
    LinearLayout linearLayout;
    EditText editText;

    public SelectVariable(String[] list, Context context) {
        super(context);

        scrollView = new ScrollView(context);
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        editText = new EditText(context);

        for (int i = 0; i<list.length; i++) {
            RadioButton radioButton = new RadioButton(context);
            linearLayout.addView(radioButton);
            radioButton.setText(list[i]);
        }
    }
}
