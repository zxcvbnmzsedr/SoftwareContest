package com.czmec.softwarecontest.drawpannel.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.setting.Screen;
import com.czmec.softwarecontest.util.scaleutil.MultiTouchListener;

/**
 * 两个画板的父类
 */
public class DrawPannelView extends View{
    Canvas mCanvas;
    Paint mPaint;
    Path mPath;
    public Paint getmPaint() {
        return mPaint;
    }

    public DrawPannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCanvas();
        initPaint();
    }
    public void registrarListener(){
        this.setOnTouchListener(new MultiTouchListener());
    }
    public void unregistrateListener(){
        this.setOnTouchListener(null);
    }
    public DrawPannelView(Context context) {
        super(context);
        initCanvas();
        initPaint();
    }
    /**
     * 初始化画布
     */
    void initCanvas() {
        mCanvas = new Canvas();
        mCanvas.setBitmap(PannelSetting.mBitmap);
        initPaint();
    }

    /**
     * 初始化画笔
     */

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);//设置为空心
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 结合处风格
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(PannelSetting.PAINT_WIDTH);
        mPaint.setColor(PannelSetting.PAINT_COLOR);
        mPaint.setAlpha(0xff);
    }

    /**
     * 撤销的核心思想就是将画布清空，
     * 将保存下来的Path路径最后一个移除掉，
     * 重新将路径画在画布上面。
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public void undo() {
        System.out.println(PannelSetting.savePath.size() + "--------------");
        if (PannelSetting.savePath != null && PannelSetting.savePath.size() > 0) {
            //调用初始化画布函数以清空画布
            PannelSetting.mBitmap = Bitmap.createBitmap(Screen.HEIGHTPIELS, Screen.WIDTHPIELS-35,
                    Bitmap.Config.ARGB_8888);
            initCanvas();
            //将路径保存列表中的最后一个元素删除 ,并将其保存在路径删除列表中
            PannelSetting.DrawPath drawPath = PannelSetting.savePath.get(PannelSetting.savePath.size() - 1);
            PannelSetting.deletePath.add(drawPath);
            PannelSetting.savePath.remove(PannelSetting.savePath.size() - 1);
            for (PannelSetting.DrawPath dp : PannelSetting.savePath) {
                mCanvas.drawPath(dp.path, dp.paint);
            }
            invalidate();// 刷新
        }
    }

    /**
     * 恢复的核心思想就是将撤销的路径保存到另外一个列表里面(栈)，
     * 然后从redo的列表里面取出最顶端对象，
     * 画在画布上面即可
     */
    public void redo() {
        if (PannelSetting.deletePath.size() > 0) {
            //将删除的路径列表中的最后一个，也就是最顶端路径取出（栈）,并加入路径保存列表中
            PannelSetting.DrawPath dp = PannelSetting.deletePath.get(PannelSetting.deletePath.size() - 1);
            PannelSetting.savePath.add(dp);
            //将取出的路径重绘在画布上
            mCanvas.drawPath(dp.path, dp.paint);
            //将该路径从删除的路径列表中去除
            PannelSetting.deletePath.remove(PannelSetting.deletePath.size() - 1);
            invalidate();
        }
    }

    /**
     * 清空的主要思想就是初始化画布
     * 将保存路径的两个List清空
     */
    @SuppressWarnings("SuspiciousNameCombination")
    public void removeAllPaint() {
        //调用初始化画布函数以清空画布
        PannelSetting.mBitmap = Bitmap.createBitmap(Screen.HEIGHTPIELS, Screen.WIDTHPIELS-35,
                Bitmap.Config.ARGB_8888);
        initCanvas();
        PannelSetting.savePath.clear();
        PannelSetting.deletePath.clear();
        invalidate();
    }
}
