package com.czmec.softwarecontest.mouse.view;

import android.app.Activity;
import android.os.Bundle;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

/**
 * 鼠标操作类，进行鼠标的控制
 */
public class MouseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mouse);
        MouseActivityListener listener = new MouseActivityListener();
        this.findViewById(R.id.mouse_touch).setOnTouchListener(listener);
        this.findViewById(R.id.mouse_leftButton).setOnTouchListener(listener);
        this.findViewById(R.id.mouse_rightButton).setOnTouchListener(listener);
        this.findViewById(R.id.mouse_middleButton).setOnTouchListener(listener);
    }

    @Override
    public void onBackPressed() {
        UDPSendMessage.sendMessage(JsonUtil.formatJson("break"));
        super.onBackPressed();
    }
}
