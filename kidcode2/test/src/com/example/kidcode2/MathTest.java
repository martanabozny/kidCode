package com.example.kidcode2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by marta on 12.05.14.
 */
public class MathTest extends android.test.ActivityUnitTestCase<CodeActivity> {

    private CodeActivity activity;

    public MathTest() {
        super(CodeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), CodeActivity.class);
        String json = "[{\"type\":\"EmptyStrip\"},{\"result\":\"a\",\"type\":\"Math\",\"b\":\"5\",\"a\":\"5\",\"function\":0},{\"result\":\"b\",\"type\":\"Math\",\"b\":\"a\",\"a\":\"2\",\"function\":2},{\"type\":\"StopStrip\",\"result\":\"b\"}]";

        intent.putExtra("strips", json);

        startActivity(intent, null, null);
        activity = getActivity();
    }

    public void testLayout() {
        Button run = (Button) activity.findViewById(R.id.run);
        run.performClick();

        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent);

        String name = triggeredIntent.getExtras().getString("name");
        String value = triggeredIntent.getExtras().getString("value");

        assertEquals(name, "b");
        assertEquals(value, "20.0");
    }
}
