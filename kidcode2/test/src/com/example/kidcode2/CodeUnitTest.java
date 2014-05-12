package com.example.kidcode2;

import android.content.Intent;
import android.test.TouchUtils;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

import com.example.kidcode2.CodeActivity;
import com.example.kidcode2.MyScrollView;
import com.example.kidcode2.R;

public class CodeUnitTest extends
        android.test.ActivityUnitTestCase<CodeActivity> {

    private int buttonId;
    private CodeActivity activity;

    public CodeUnitTest() {
        super(CodeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(),
                CodeActivity.class);
        startActivity(intent, null, null);
        activity = getActivity();
    }

    public void testLayout() {
        assertNotNull(activity.findViewById(R.id.code));
        MyScrollView code = (MyScrollView) activity.findViewById(R.id.code);
    }
}