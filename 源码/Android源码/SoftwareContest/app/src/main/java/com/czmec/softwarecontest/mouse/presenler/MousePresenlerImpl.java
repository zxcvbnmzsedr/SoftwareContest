package com.czmec.softwarecontest.mouse.presenler;

import android.view.MotionEvent;

import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import java.util.HashMap;

/**
 * Created by tianzeng on 2016-05-11.
 */
public class MousePresenlerImpl implements MousePresenler{
    private float my = 0;
    private float lx; // 记录上次鼠标的位置
    private float ly;
    private float lbx = 0; // 鼠标左键移动初始化坐标
    private float lby = 0;
    @Override
    public void onMiddleButtonDown(MotionEvent ev) {
        ly = ev.getY();
    }

    @Override
    public void onMiddleButtonMove(MotionEvent ev) {
        float y = ev.getY();
        my = y - ly;
        ly = y;
        if (my > 3 || my < -3) { // 减少发送次数 滑轮移动慢点
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("type", "mousewheel");
            hashMap.put("mousewheel", String.valueOf(my));
            UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
        }
    }

    @Override
    public void onLeftButton(String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "leftButton");
        hashMap.put("state", type);
        UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
    }

    @Override
    public void onRightButton(String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", "rightButton");
        hashMap.put("state", type);
        UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
    }

    @Override
    public void moveMouseWithSecondFinger(MotionEvent event) {
        int count = event.getPointerCount();
        if (count == 2) {
            if (lbx == 0 && lby == 0) {
                lbx = event.getX(1);
                lby = event.getY(1);
                return;
            }
            float x = event.getX(1);
            float y = event.getY(1);
            sendMouseEvent(x - lbx, y - lby);
            lbx = x;
            lby = y;
        }
        if (count == 1) {
            lbx = 0;
            lby = 0;
        }
    }

    @Override
    public void onMouseDown(MotionEvent event) {
        lx = event.getX(); // 当手机第一放入时 把当前坐标付给lx
        ly = event.getY();
    }

    @Override
    public void onMouseMove(MotionEvent event) {
        float x = event.getX();
        float mx = x - lx;
        lx = x; // 把当前鼠标的位置付给lx 以备下次使用
        float y = event.getY();
        my = y - ly;
        ly = y;
        if (mx != 0 && my != 0)
            this.sendMouseEvent(mx, my);
    }

    @Override
    public void sendMouseEvent(float x, float y) {
        HashMap hashMap = new HashMap<>();
        hashMap.put("type", "mouse");
        hashMap.put("x",String.valueOf(x));
        hashMap.put("y",String.valueOf(y));
        UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
    }
}
