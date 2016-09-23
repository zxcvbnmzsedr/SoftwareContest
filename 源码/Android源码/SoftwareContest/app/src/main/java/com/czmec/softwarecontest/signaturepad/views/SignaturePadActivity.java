package com.czmec.softwarecontest.signaturepad.views;

import android.app.Activity;
import android.os.Bundle;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

/**
 * 监听板主界面
 */
public class SignaturePadActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signaturepad);
        SignaturePadActivityListener listener = new SignaturePadActivityListener(this);
        findViewById(R.id.signature_btn_clear).setOnClickListener(listener);
        findViewById(R.id.signature_btn_ok).setOnClickListener(listener);
    }

    @Override
    public void onBackPressed() {
        UDPSendMessage.sendMessage(JsonUtil.formatJson("exit"));
        super.onBackPressed();
    }
}
