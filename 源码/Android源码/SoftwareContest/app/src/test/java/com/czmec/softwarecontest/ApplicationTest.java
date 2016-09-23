package com.czmec.softwarecontest;

import android.test.AndroidTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {
    private final String TAG = "MyTest";

    public ApplicationTest() {
    }
    public void test() throws Exception {
        final int expected = 1;
        final int reality = 1;
        assertEquals(expected, reality);
    }

}