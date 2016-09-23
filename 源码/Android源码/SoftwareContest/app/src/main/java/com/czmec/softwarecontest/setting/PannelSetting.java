package com.czmec.softwarecontest.setting;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;

/**
 * 画板参数
 */
public class PannelSetting {
    public static int PAINT_COLOR ; // 画笔颜色
    public static int PAINT_WIDTH ;// 画笔粗细
    public static int PANNEL_SCALE;
    public static Bitmap mBitmap; // 用于显示的bitmap
    public static final ArrayList<DrawPath> savePath; // 存储路径
    public static final ArrayList<DrawPath> deletePath; // 撤销路径
    public static DrawPath dp;
    //路径对象
    public static class DrawPath {
        public Paint paint;
        public Path path;
    }

    public static void init() {
        PannelSetting.mBitmap = Bitmap.createBitmap(Screen.HEIGHTPIELS, Screen.WIDTHPIELS-35,
                Bitmap.Config.ARGB_8888);
        PAINT_WIDTH = 10;
        PANNEL_SCALE = 100;
        PAINT_COLOR = Color.BLACK;
    }
    static {
        PannelSetting.mBitmap = Bitmap.createBitmap(Screen.HEIGHTPIELS, Screen.WIDTHPIELS-35,
                Bitmap.Config.ARGB_8888);
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();
        PAINT_WIDTH = 10;
        PANNEL_SCALE = 100;
        PAINT_COLOR = Color.BLACK;
    }

}
