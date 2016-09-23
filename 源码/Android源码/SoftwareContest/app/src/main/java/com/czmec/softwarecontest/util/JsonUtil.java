package com.czmec.softwarecontest.util;

import android.graphics.RectF;
import android.util.Log;

import com.czmec.softwarecontest.drawpannel.dollar.Point;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Json工具类
 */
public class JsonUtil {
    /**
     * 根据map生成jason
     * @param map key value 参数
     * @return JSONObject
     */
    public static JSONObject formatJson(Map map) {
        JSONObject object = new JSONObject();
        for (HashMap.Entry entry : (Iterable<HashMap.Entry>) map.entrySet()) {
            try {
                object.put((String) entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                Log.w("Exception", e.toString());
            }
        }
        return object;
    }

    public static JSONObject formatJson(float color) {
        JSONObject object = new JSONObject();
        try {
            object.put("RGB", color);
        } catch (JSONException e) {
            Log.w("Exception",e);
        }
        return object;
    }

    public static JSONObject formatJson(String action) {
        JSONObject object = new JSONObject();
        try {
            object.put("type", action);
        } catch (JSONException e) {
            Log.w("Exception",e.toString());
        }
        return object;
    }

    public static JSONArray formatJson(String type, ArrayList<Point> points) {
        JSONArray jsonStrs = new JSONArray();
        JSONObject jsonObject;
        jsonStrs.put(type);
        try {
            for (Point p : points) {
                jsonObject = new JSONObject();
                jsonObject.put("x",  p.X);
                jsonObject.put("y", p.Y);
                jsonStrs.put(jsonObject);
            }
            jsonObject = new JSONObject();
            jsonObject.put("x", points.get(0).X);
            jsonObject.put("y", points.get(0).Y);
            jsonStrs.put(jsonObject);
        } catch (Exception e) {
            Log.w("Exception",e.toString());
        }

        return jsonStrs;
    }

    public static JSONArray formatJson(String type, int[] points,RectF rectF) {
        JSONArray jsonStrs = new JSONArray();
        JSONObject jsonObject;
        jsonStrs.put(type);
        try {
            jsonObject = new JSONObject();
            jsonObject.put("x", points[0]);
            jsonObject.put("y", points[1]);
            jsonStrs.put(jsonObject);
            jsonObject = new JSONObject();
            jsonObject.put("x", rectF.width());
            jsonObject.put("y", rectF.height());
            jsonStrs.put(jsonObject);
        } catch (Exception e) {
            Log.w("Exception",e.toString());
        }
        return jsonStrs;
    }
}
