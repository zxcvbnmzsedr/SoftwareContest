package com.czmec.softwarecontest.drawpannel.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.czmec.softwarecontest.launcher.view.LauncherActivity;
import com.czmec.softwarecontest.setting.ConnectionSetting;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.setting.Screen;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 正常画板view
 */
public class DrawPannelViewUs extends DrawPannelView{
    private float mX = 0, mY = 0;
    private boolean mAttached = false;

    public DrawPannelViewUs(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public DrawPannelViewUs(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFAAAAAA);
        Paint bmpPaint = new Paint();
        canvas.drawBitmap(PannelSetting.mBitmap, 0, 0, bmpPaint);
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint);
        }
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initCanvas();
                touch_start(x, y);
                sendMouseEvent(x, y, "down");
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                sendMouseEvent(x, y, "move");
                break;
            case MotionEvent.ACTION_UP:
                touch_up(x, y);
                sendMouseEvent(x, y, "up");
                break;
        }
        postInvalidate();

        return true;
    }


    private void touch_start(float x, float y) {
        mPath = new Path();
        PannelSetting.dp = new PannelSetting.DrawPath();
        PannelSetting.dp.path = mPath;
        PannelSetting.dp.paint = mPaint;
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= 4 || dy >= 4) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }


    private void touch_up(float x, float y) {
        mCanvas.drawPath(mPath, mPaint);
        PannelSetting.savePath.add(PannelSetting.dp);
        mPath = null;
    }

    private void sendMouseEvent(float x, float y, String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("type", type);
        hashMap.put("x", String.valueOf(x));
        hashMap.put("y",String.valueOf(y));
        UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mAttached) {
            mAttached = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.CART_BROADCAST");
            getContext().registerReceiver(mItemViewListClickReceiver, intentFilter, null, getHandler());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            getContext().unregisterReceiver(mItemViewListClickReceiver);
            mAttached = false;
        }
    }

    /**
     * 自定义广播接收器
     * 从服务端接受数据，并且更新到ui
     */
    private final BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle a = intent.getExtras();
            byte[] data = (byte[]) a.get("location");
            try {
                JSONObject jsonObject = new JSONObject(new String(data, 0, data.length));
                float x = 0;
                float y = 0;
                float color = 0;
                float border = 0;
                String type = "";

                if(!jsonObject.isNull("x")){
                    x = (float) (jsonObject.getDouble("x") * Screen.HEIGHTPIELS+8);
                }
                if(!jsonObject.isNull("y")){
                    y = (float) jsonObject.getDouble("y") * (Screen.WIDTHPIELS-100);
                }
                if(!jsonObject.isNull("color")){
                    color = (float) jsonObject.getDouble("color");
                }
                if(!jsonObject.isNull("border")){
                    border = (float) jsonObject.getDouble("border");
                }
                if(!jsonObject.isNull("type")){
                    type = jsonObject.getString("type");
                }
                Log.i("receiveData",jsonObject.toString());
                PannelSetting.PAINT_COLOR = (int) color;
                PannelSetting.PAINT_WIDTH = (int) border;
                switch (type) {
                    case "press":
                        initCanvas();
                        touch_start(x, y);
                        Log.i("remote", "x的坐标" + x + "     " + "y的坐标" + y + "");
                        break;
                    case "drag":
                        touch_move(x, y);
                        Log.i("remote", "x的坐标" + x + "     " + "y的坐标" + y + "");
                        break;
                    case "up":
                        touch_up(x, y);
                        Log.i("remote", "x的坐标" + x + "     " + "y的坐标" + y + "");
                        break;
                    case "break":
                        removeAllPaint();
                        ConnectionSetting.IP = "";
                        ConnectionSetting.isCoon = false;
                        context.startActivity(new Intent(getContext(), LauncherActivity.class));
                        break;
                    case "undo":
                        undo();
                        break;
                    case "redo":
                        redo();
                        break;
                    case "clear":
                        removeAllPaint();
                        break;
                }
                postInvalidate();
            } catch (Exception e) {
                Log.w("exception",e);
            }
        }
    };



}