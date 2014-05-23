package com.example.kidcode2;

import android.content.Intent;
import android.widget.Button;

/**
 * Created by marta on 12.05.14.
 */
public class ifTest extends android.test.ActivityUnitTestCase<CodeActivity> {

    private CodeActivity activity;

    public ifTest() {
        super(CodeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent intent = new Intent(getInstrumentation().getTargetContext(), CodeActivity.class);
        String json = "[{\"type\":\"EmptyStrip\"},{\"type\":\"NewVariable\",\"value\":\"2\",\"variables_types\":1,\"name\":\"a\"},{\"compareWith\":\"4\",\"code\":\"[{\\\"type\\\":\\\"EmptyStrip\\\"},{\\\"type\\\":\\\"StopStrip\\\",\\\"result\\\":\\\"a\\\"}]\",\"type\":\"IfStrip\",\"condition\":2,\"variable\":\"a\",\"function\":3}]";

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

        assertEquals(name, "a");
        assertEquals(value, "2");
    }
}
