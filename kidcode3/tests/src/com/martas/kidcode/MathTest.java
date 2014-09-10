package com.martas.kidcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.ImageButton;

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
        String json = "[\"{\\\"b\\\":\\\"2\\\",\\\"type\\\":\\\"Math\\\",\\\"name\\\":\\\"a\\\",\\\"function\\\":\\\"x\\\",\\\"a\\\":\\\"7\\\"}\",\"{\\\"type\\\":\\\"Stop\\\",\\\"name\\\":\\\"a\\\"}\"]";

        SharedPreferences mPrefs = getInstrumentation().getTargetContext().getSharedPreferences("strips", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putString("name", "test");
        ed.putString("strips", json);
        ed.commit();

        startActivity(intent, null, null);
        activity = getActivity();
    }

    public void testLayout() {
        ImageButton play = (ImageButton) activity.findViewById(R.id.play);
        play.performClick();

        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", triggeredIntent);

        String name = triggeredIntent.getExtras().getString("result");
        String value = triggeredIntent.getExtras().getString("value");

        assertEquals(name, "a");
        assertEquals(value, "14");
    }
}
