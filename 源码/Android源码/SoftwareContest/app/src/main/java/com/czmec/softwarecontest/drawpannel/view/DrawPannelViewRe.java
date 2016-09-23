package com.czmec.softwarecontest.drawpannel.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.czmec.softwarecontest.drawpannel.dollar.Dollar;
import com.czmec.softwarecontest.drawpannel.dollar.DollarListener;
import com.czmec.softwarecontest.drawpannel.dollar.Point;
import com.czmec.softwarecontest.drawpannel.recognition.Recognition;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import java.util.ArrayList;

/**
 * ????view
 */
public class DrawPannelViewRe extends DrawPannelView implements DollarListener {
    private float mX = 0, mY = 0;
    private int state;
    private final Dollar dollar = new Dollar(Dollar.GESTURES_DEFAULT);
    private final Recognition recoize = new Recognition();

    public DrawPannelViewRe(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCanvas();
        dollar.setListener(this);
        dollar.setActive();
    }

    public DrawPannelViewRe(Context context) {
        super(context);
        initCanvas();
        dollar.setListener(this);
        dollar.setActive();
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
    public void dollarDetected(Dollar dollar) {
        String name = dollar.getName();
        switch (name) {
            case "polygon":
                rePolygon();
                break;
            case "circle":
                reCircle();
                break;
            default:
                reLine();
                break;
        }
        Log.i("name","???????????"+ name);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initCanvas();
                touch_start(x, y);
                recoize.clearOutPoints();
                recoize.clearWindowPoints();
                recoize.addWindowPoint(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                recoize.addWindowPoint(x, y);
                break;
            case MotionEvent.ACTION_UP:
                recoize.executeshortStraw(x, y);
                touch_up(x, y);
                break;
        }
        postInvalidate();
        return true;
    }

    private void touch_start(float x, float y) {
        mPath = new Path();
        state = 1;
        dollar.pointerPressed(x, y);
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

        private void reCircle() {
        mPath = new Path();
        PannelSetting.dp = new PannelSetting.DrawPath();
        PannelSetting.dp.path = mPath;
        PannelSetting.dp.paint = mPaint;
        int[] pp = dollar.getBounds();
        RectF rectF = new RectF(pp[0], pp[1], pp[2], pp[3]);
        mPath.addArc(rectF, 0, 360);
        mCanvas.drawPath(mPath, mPaint);
        PannelSetting.savePath.add(PannelSetting.dp);
        UDPSendMessage.sendMessage(JsonUtil.formatJson("circle",pp,rectF));
        invalidate();
    }
//
    private void reLine() {
        mPath = new Path();
        PannelSetting.dp = new PannelSetting.DrawPath();
        PannelSetting.dp.path = mPath;
        PannelSetting.dp.paint = mPaint;
        ArrayList<Point> p = recoize.getLine();
        mPath.moveTo((int) p.get(0).X, (int) p.get(0).Y);
        mPath.lineTo((int) p.get(1).X, (int) p.get(1).Y);
        mCanvas.drawPath(mPath, mPaint);
        PannelSetting.savePath.add(PannelSetting.dp);
        UDPSendMessage.sendMessage(JsonUtil.formatJson("line",p));
        invalidate();
    }
    private void rePolygon() {
        mPath = new Path();
        PannelSetting.dp = new PannelSetting.DrawPath();
        PannelSetting.dp.path = mPath;
        PannelSetting.dp.paint = mPaint;
        ArrayList<Point> points = recoize.getOutPoints();
        if (points.size() <= 0) {
            return;
        }
        mPath.moveTo((int) points.get(0).X, (int) points.get(0).Y);
        for (int i = 1; i < points.size(); i++) {
            Point t = points.get(i);
            mPath.lineTo((int) t.X, (int) t.Y);
        }
        PannelSetting.savePath.add(PannelSetting.dp);
        UDPSendMessage.sendMessage(JsonUtil.formatJson("line",points));
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= 4 || dy >= 4) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
        state = 2;
        dollar.pointerDragged(x, y);
    }

    private void touch_up(float x, float y) {
        mPath.lineTo(mX, mY);
        state = 0;
        dollar.pointerReleased();
        invalidate();
        mPath = new Path();
    }


}
