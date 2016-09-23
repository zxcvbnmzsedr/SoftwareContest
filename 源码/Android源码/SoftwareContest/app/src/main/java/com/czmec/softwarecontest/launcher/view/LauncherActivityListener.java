package com.czmec.softwarecontest.launcher.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.drawpannel.PannelActivity;
import com.czmec.softwarecontest.mouse.view.MouseActivity;
import com.czmec.softwarecontest.ppt.PPTActivity;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.signaturepad.views.SignaturePadActivity;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.launcher.util.SocketConnection;
import com.czmec.softwarecontest.util.JsonUtil;
import com.dd.CircularProgressButton;

/**
 * 主界面监听器
 */
class LauncherActivityListener implements View.OnClickListener {
    private final Activity context;
    public LauncherActivityListener(Activity context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                new SocketConnection(context).execute();
                PannelSetting.init();
                break;
            case R.id.btn_mouse:
                UDPSendMessage.sendMessage(JsonUtil.formatJson("mouse"));
                context.startActivity(new Intent(context, MouseActivity.class));
                break;
            case R.id.btn_screen:
                Intent intent = new Intent(context, PannelActivity.class);
                context.startActivity(intent);
                break;
            case R.id.btn_write:
                context.startActivity(new Intent(context, SignaturePadActivity.class));
                UDPSendMessage.sendMessage(JsonUtil.formatJson("sign"));
                break;
            case R.id.btn_ppt:
                context.startActivity(new Intent(context, PPTActivity.class));
                UDPSendMessage.sendMessage(JsonUtil.formatJson("mouse"));
                break;
            default:

                break;
        }
    }
}
