package com.czmec.softwarecontest.mouse.presenler;

import android.view.MotionEvent;

/**
 * Created by tianzeng on 2016-05-11.
 */
public interface MousePresenler {
    void onMiddleButtonDown(MotionEvent ev);
    void onMiddleButtonMove(MotionEvent ev);
    void onLeftButton(String type);
    void onRightButton(String type);
    void moveMouseWithSecondFinger(MotionEvent event);
    void onMouseDown(MotionEvent event);
    void onMouseMove(MotionEvent event);
    void sendMouseEvent(float x, float y);
}
