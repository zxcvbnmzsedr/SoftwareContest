package com.czmec.softwarecontest.mouse.view;

import android.view.MotionEvent;
import android.view.View;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.mouse.presenler.MousePresenler;
import com.czmec.softwarecontest.mouse.presenler.MousePresenlerImpl;

public class MouseActivityListener implements View.OnTouchListener {
    private MousePresenler mMousePresenler = new MousePresenlerImpl();
    @Override
    public boolean onTouch(View v, MotionEvent ev) {
        switch (v.getId()) {
            case R.id.mouse_middleButton:
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMousePresenler.onMiddleButtonDown(ev);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mMousePresenler.onMiddleButtonMove(ev);
                        break;
                }
                break;
            case R.id.mouse_rightButton:
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMousePresenler.onRightButton("down");
                        break;
                    case MotionEvent.ACTION_UP:
                        mMousePresenler.onRightButton("release");
                        break;
                }
                break;
            case R.id.mouse_leftButton:
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mMousePresenler.onLeftButton("down");
                        break;
                    case MotionEvent.ACTION_UP:
                        mMousePresenler.onLeftButton("release");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mMousePresenler.moveMouseWithSecondFinger(ev);
                        break;
                }
                break;
            case R.id.mouse_touch:
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mMousePresenler.onMouseMove(ev);
                        break;
                    case MotionEvent.ACTION_DOWN:
                        mMousePresenler.onMouseDown(ev);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                break;
        }
        return true;
    }

}
