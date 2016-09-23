package com.czmec.softwarecontest.drawpannel;

import android.app.Activity;
import android.view.View;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.drawpannel.popwindows.PannelPopWindow;
import com.czmec.softwarecontest.drawpannel.view.DrawPannelView;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import java.io.Serializable;

/**
 * 画板监听
 */
class PannelListener implements View.OnClickListener, Serializable {
    private final Activity context;
    private PannelPopWindow pannelPopWindow;

    public PannelListener(Activity context) {
        this.context = context;
        if(pannelPopWindow == null){
            pannelPopWindow = new PannelPopWindow(context);
        }
    }

    @Override
    public void onClick(View v) {
        DrawPannelView drawPannelView;
        try {
            drawPannelView = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom);
            switch (v.getId()){
                // 画板设置
                case R.id.pannel_setting:
                    pannelPopWindow.showPopupWindow(context.findViewById(R.id.pannel_setting));
                    break;
                // 撤销
                case R.id.pannel_btn_redo:
                    drawPannelView.redo();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("redo"));
                    break;
                // 取消撤销操作
                case R.id.pannel_btn_undo:
                    drawPannelView.undo();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("undo"));
                    break;
                case R.id.pannel_clear:
                    drawPannelView.removeAllPaint();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("clearAll"));
                    break;
            }
        }catch (NullPointerException e){
            drawPannelView = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom_re);
            switch (v.getId()){
                // 画板设置
                case R.id.pannel_setting:
                    pannelPopWindow.showPopupWindow(context.findViewById(R.id.pannel_setting));
                    break;
                // 撤销
                case R.id.pannel_btn_redo:
                    drawPannelView.redo();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("redo"));
                    break;
                // 取消撤销操作
                case R.id.pannel_btn_undo:
                    drawPannelView.undo();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("undo"));
                    break;
                case R.id.pannel_clear:
                    drawPannelView.removeAllPaint();
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("clearAll"));
                    break;
            }
        }
    }

}
