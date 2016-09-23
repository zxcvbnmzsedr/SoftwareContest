package com.czmec.softwarecontest.signaturepad.views;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.socket.TCPSendMessage;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

/**
 * 签字板的监听器
 */
class SignaturePadActivityListener implements View.OnClickListener {
    private final Activity context;
    public SignaturePadActivityListener(Activity context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        SignaturePad signaturePad = (SignaturePad) context.findViewById(R.id.signature_main);
        switch (view.getId()){
            case R.id.signature_btn_clear:
                signaturePad.clear();
                UDPSendMessage.sendMessage(JsonUtil.formatJson("clear"));
                break;
            case R.id.signature_btn_ok:
                Bitmap bitmap = signaturePad.getTransparentSignatureBitmap();
                TCPSendMessage.sendMessage(bitmap);
                break;
        }
    }
}
