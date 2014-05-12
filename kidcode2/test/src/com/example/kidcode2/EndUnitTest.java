package com.example.kidcode2;

import android.content.Intent;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import android.widget.EditText;
import com.example.kidcode2.End;
import com.example.kidcode2.R;

public class EndUnitTest extends
        android.test.ActivityUnitTestCase<End> {


    private End activity;

    public EndUnitTest() {
        super(End.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                End.class);
        intent.putExtra("name", "jakas zmienna");
        intent.putExtra("value", "jakas wartosc");
        startActivity(intent, null, null);
        activity = getActivity();
    }

    public void testLayout() {
        assertNotNull(activity.findViewById(R.id.name));
        assertNotNull(activity.findViewById(R.id.value));
        EditText name = (EditText) activity.findViewById(R.id.name);
        EditText value = (EditText) activity.findViewById(R.id.value);

        assertEquals("jakas zmienna", name.getText().toString());
        assertEquals("jakas wartosc", value.getText().toString());
    }
}