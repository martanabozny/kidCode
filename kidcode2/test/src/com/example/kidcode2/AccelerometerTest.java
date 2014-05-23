package com.example.kidcode2;

import android.content.Intent;
import android.widget.Button;

/**
 * Created by marta on 12.05.14.
 */
public class AccelerometerTest extends android.test.ActivityUnitTestCase<CodeActivity> {

    private CodeActivity activity;

    public AccelerometerTest() {
        super(CodeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), CodeActivity.class);
        String json = "[{\"type\":\"EmptyStrip\"},{\"type\":\"Accelerometer\",\"result\":\"r\",\"accels\":0},{\"type\":\"StopStrip\",\"result\":\"r\"}]";

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
        assertEquals(name, "r");

    }
}
