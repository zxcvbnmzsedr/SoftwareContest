package com.czmec.softwarecontest.drawpannel.recognition;

import com.czmec.softwarecontest.drawpannel.dollar.Point;

import java.util.ArrayList;
import java.util.Vector;
public class Recognition {

    private final ArrayList<Point> mWindowPoints = new ArrayList<>();
    private final ArrayList<Point> mOutPoints = new ArrayList<>();
    private final ArrayList<Point> mResamplePoints = new ArrayList<>();

    public void addWindowPoint(double x, double y) {
        mWindowPoints.add(new Point(x, y));
    }

    public void clearWindowPoints() {
        mWindowPoints.clear();
    }

    public void clearOutPoints() {
        mOutPoints.clear();
    }
    public ArrayList<Point> getLine(){
        ArrayList<Point> list = new ArrayList<>();
        list.add(new Point(mWindowPoints.get(1).X,mWindowPoints.get(1).Y));
        list.add(new Point(mWindowPoints.get(mWindowPoints.size() -1 ).X,mWindowPoints.get(mWindowPoints.size() -1 ).Y));
        return list;
    }
    public void executeshortStraw(float x, float y) {
        mOutPoints.clear();
        mResamplePoints.clear();

        if (mWindowPoints.size() < 7) {
            try {
                throw new Throwable("Error: mWindowPoints.size() < 7 !");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else if (mWindowPoints.size() == 7) {
            mOutPoints.add(0, mWindowPoints.get(0));
            mOutPoints.add(1, mWindowPoints.get(3));
            mOutPoints.add(2, mWindowPoints.get(6));
        } else {
            float space = determineWindowSpace();

            if (mResamplePoints.isEmpty()) {
                resamplePoints(space);
            }
            getCorner();
        }
    }

    private float determineWindowSpace() {
        Point minp = null, maxp = null;
        for (int i = 0; i < mWindowPoints.size(); ++i) {
            if (0 == i) {
                minp = maxp = mWindowPoints.get(i);
                continue;
            }
            if (mWindowPoints.get(i).X < minp.X) minp.X = mWindowPoints.get(i).X;
            if (mWindowPoints.get(i).Y < minp.Y) minp.Y = mWindowPoints.get(i).Y;

            if (mWindowPoints.get(i).X > maxp.X) maxp.X = mWindowPoints.get(i).X;
            if (mWindowPoints.get(i).Y > maxp.Y) maxp.Y = mWindowPoints.get(i).Y;
        }
        return (float) (Math.sqrt(((maxp != null ? maxp.X : 0) - (minp != null ? minp.X : 0)) * ((maxp != null ? maxp.X : 0) - (minp != null ? minp.X : 0)) + ((maxp != null ? maxp.Y : 0) - (minp != null ? minp.Y : 0)) * (maxp.Y - minp.Y)) / 40.0f);
    }

    private float getDistance(Point p1, Point p2) {
        return (float) Math.sqrt((p1.X - p2.X) * (p1.X - p2.X) + (p1.Y - p2.Y) * (p1.Y - p2.Y));
    }

    private void resamplePoints(float space) {
        float dis = 0.0f;
        mResamplePoints.add(mWindowPoints.get(0));

        for (int i = 0; i < mWindowPoints.size() - 1; ++i) {
            float linesegdis = getDistance(mWindowPoints.get(i), mWindowPoints.get(i + 1));
            if (dis + linesegdis >= space) {
                float x = (float) (mWindowPoints.get(i).X + (mWindowPoints.get(i + 1).X - mWindowPoints.get(i).X) * (space - dis) / linesegdis);
                float y = (float) (mWindowPoints.get(i).Y + (mWindowPoints.get(i + 1).Y - mWindowPoints.get(i).Y) * (space - dis) / linesegdis);

                mResamplePoints.add(new Point(x, y));
                dis = 0.0f;
            } else {
                dis += linesegdis;
            }
        }

        mResamplePoints.add(mWindowPoints.get(mWindowPoints.size() - 1));
    }

    //---------------------------------------------------------
    private void getCorner() {
        assert (mResamplePoints.size() > 7 && mOutPoints.isEmpty());

        // 计算各个点的straw
        Vector<Float> straws = new Vector<>();
        int i;
        int interval = 3;
        for (i = interval; i < mResamplePoints.size() - interval; ++i) {
            float straw = getDistance(mResamplePoints.get(i - interval), mResamplePoints.get(i + interval));
            straws.add(straw);
        }

        assert (straws.size() == mResamplePoints.size() - 2 * interval);

        // 计算阀值
        float threshold = 0.0f;
        for (i = 0; i < straws.size(); ++i) {
            threshold += straws.get(i);
        }
        threshold = (float) (threshold / (float) (straws.size()) * 0.95);

        // 找到corner
        mOutPoints.add(0, mResamplePoints.get(0));

        for (i = interval; i < mResamplePoints.size() - interval; ++i) {
            if (straws.get(i - interval) < threshold) {
                int minCornerIndex = i;
                float minStraw = 0.0f / 0;
                while (i < straws.size() && straws.get(i - interval) < threshold) {
                    if (straws.get(i - interval) < minStraw) {
                        minCornerIndex = i;
                        minStraw = straws.get(i - interval);
                    }
                    ++i;
                }
                mOutPoints.add(mResamplePoints.get(minCornerIndex));
            }
        }
        mOutPoints.add(mResamplePoints.get(mResamplePoints.size() - 1));
        postProcessCorners();
    }

    public ArrayList<Point> getOutPoints() {
        return mOutPoints;
    }

    private void postProcessCorners() {
        for (int i = 0; i < mOutPoints.size(); i++) {
            Point corner0, corner1;
            corner0 = mOutPoints.get(i); // value
            ++i;
            if (i < mOutPoints.size()) {
                corner1 = mOutPoints.get(i);
            } else {
                break;
            }

            if (corner0 == corner1) {
                mOutPoints.remove(corner1);
                --i;
                continue;
            }
            postProcessAddCorners(mOutPoints.indexOf(corner0), corner0, mOutPoints.indexOf(corner1), corner1);
        }
    }


    private void postProcessAddCorners(int index0, Point corner0, int index1, Point corner1) {
        if (isLine(index0, corner0, index1, corner1)) {
            return;
        }

        int indexhalf = (int) ((index0 + index1) / 2.0f);
        if (indexhalf == index0 || indexhalf == index1) {
            return;
        }

        mOutPoints.add(mResamplePoints.get(indexhalf));

        if (index0 < index1) {
            postProcessAddCorners(index0, corner0, indexhalf, mResamplePoints.get(indexhalf));
            postProcessAddCorners(indexhalf, mResamplePoints.get(indexhalf), index1, corner1);
        } else {
            postProcessAddCorners(index1, corner1, indexhalf, mResamplePoints.get(indexhalf));
            postProcessAddCorners(indexhalf, mResamplePoints.get(indexhalf), index0, corner0);
        }
    }

    private boolean isLine(int index0, Point corner0, int index1, Point corner1) {
        float threshold = 0.95f;
        float lineDis = getDistance(corner0, corner1);
        float pathDis = pathDistance(index0, index1);
        return lineDis / pathDis > threshold;
    }

    private float pathDistance(int index0, int index1) {
        float totaldis = 0.0f;
        for (int i = index0; i < index1; ++i) {
            totaldis += getDistance(mResamplePoints.get(i), mResamplePoints.get(i + 1));
        }
        return totaldis;
    }

}
