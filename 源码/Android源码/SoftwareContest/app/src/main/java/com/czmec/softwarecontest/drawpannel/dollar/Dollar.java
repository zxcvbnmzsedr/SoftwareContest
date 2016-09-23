package com.czmec.softwarecontest.drawpannel.dollar;

/*
 *	Java / Java ME port of the $1 Gesture Recognizer by  
 *	Jacob O. Wobbrock, Andrew D. Wilson, Yang Li.
 * 
 *	A quick port that needs to be polished, documented and optimized...!
 *	Send me an e-mail (address can be found at olwal.com) if you'd like to get an update when the library is updated, 
 *  and feel free to send any updates or changes you make!  
 *
 *	@author Alex Olwal
 *
 *	@version 0.1
 *
 *	@see http://depts.washington.edu/aimgroup/proj/dollar/
 *
 */

import android.graphics.Canvas;
import android.graphics.Paint;

import com.czmec.softwarecontest.drawpannel.touch.TouchListener;

import java.util.Vector;


public class Dollar implements TouchListener {
    protected int x, y;
    protected int state;

    protected int _key = -1;

    protected boolean gesture = true;
    private final Vector points = new Vector(1000);

    private final Recognizer recognizer;
    private Result result = new Result("no gesture", 0, -1);

    private boolean active = false;

    private DollarListener listener = null;

    public static final int GESTURES_DEFAULT = 1;
    private static final int GESTURES_SIMPLE = 2;
    public static final int GESTURES_CIRCLES = 3;

    public Dollar() {
        this(GESTURES_SIMPLE);
    }

    public Dollar(int gestureSet) {
        recognizer = new Recognizer(gestureSet);
    }

    public void setListener(DollarListener listener) {
        this.listener = listener;
    }

    public void render(Canvas g) {
        if (!active)
            return;

        Point p1, p2;

//		g.setColor(0x999999);
        Paint paint = new Paint();
        for (int i = 0; i < points.size() - 1; i++) {
            p1 = (Point) points.elementAt(i);
            p2 = (Point) points.elementAt(i + 1);
            g.drawLine((float) p1.X, (float) p1.Y, (float) p2.X, (float) p2.Y, paint);
        }
    }

    private void addPoint(float x, float y) {
        if (!active)
            return;

        points.addElement(new Point(x, y));
    }

    private void recognize() {
        if (!active)
            return;

        if (points.size() == 0) //the recognizer will crash if we try to process an empty set of points...
            return;

        result = recognizer.Recognize(points);
//		points.removeAllElements();

        if (listener != null)
            listener.dollarDetected(this);
    }

    public Rectangle getBoundingBox() {
        return recognizer.boundingBox;
    }

    public int[] getBounds() {
        return recognizer.bounds;
    }

    public Point getPosition() {
        return recognizer.centroid;
    }

    public String getName() {
        return result.Name;
    }

    public double getScore() {
        return result.Score;
    }

    public int getIndex() {
        return result.Index;
    }

    public void setActive() {
        active = true;
    }

    public boolean getActive() {
        return active;
    }

    /**
     * 按下按钮
     */
    public void pointerPressed(float x, float y) {
        clear();
    }

    /**
     * 按钮释放
     *
     */
    public void pointerReleased() {
        recognize();
    }

    /**
     * 按钮移动
     *
     */
    public void pointerDragged(float x, float y) {
        addPoint(x, y);
    }

    /**
     * 清空之前用于识别的元素
     */
    private void clear() {
        points.removeAllElements();
        result.Name = "";
        result.Score = 0;
        result.Index = -1;
    }

}


