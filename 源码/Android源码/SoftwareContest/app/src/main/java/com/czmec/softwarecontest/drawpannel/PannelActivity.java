package com.czmec.softwarecontest.drawpannel;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.drawpannel.view.DrawPannelMain;
import com.czmec.softwarecontest.service.SocketService;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

/**
 * 画板的主界面，撤销，重做，设置等按钮
 */
public class PannelActivity extends Activity {
    private PannelListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawpannel);
        // 设置按钮的监听事件
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.pannel_main, new DrawPannelMain());
        transaction.commit();
        if (listener == null) {
            listener = new PannelListener(this);
        }
        findViewById(R.id.pannel_setting).setOnClickListener(listener);
        findViewById(R.id.pannel_btn_undo).setOnClickListener(listener);
        findViewById(R.id.pannel_btn_redo).setOnClickListener(listener);
        findViewById(R.id.pannel_clear).setOnClickListener(listener);
        SocketService socketService = new SocketService();
        Intent intent = new Intent(this, socketService.getClass());
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PannelSetting.init();
        UDPSendMessage.sendMessage(JsonUtil.formatJson("norecognize"));
        UDPSendMessage.sendMessage(JsonUtil.formatJson("exitdraw"));

    }




}