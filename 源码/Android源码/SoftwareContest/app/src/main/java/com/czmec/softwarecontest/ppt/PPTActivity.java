package com.czmec.softwarecontest.ppt;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PPTActivity extends Activity {
    private SensorManager sensorManager;
    private Vibrator vibrator;
    private static final String TAG = "TestSensorActivity";
    private static final int SENSOR_SHAKE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppt);
        PPTActivityListener listener = new PPTActivityListener();
        findViewById(R.id.ppt_play).setOnTouchListener(listener);
        findViewById(R.id.ppt_left).setOnTouchListener(listener);
        findViewById(R.id.ppt_right).setOnTouchListener(listener);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
// 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
// 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            int medumValue = 19;
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENSOR_SHAKE:
                    Log.i(TAG, "检测到摇晃，执行操作！");
                    Map map = new HashMap();
                    map.put("type","keyboard");
                    JSONObject ob=new JSONObject();
                    try {
                        ob.put("type","ppt");
                        ob.put("whichkey","nextpage");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    map.put("key",ob);
                    UDPSendMessage.sendMessage(JsonUtil.formatJson(map));
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        UDPSendMessage.sendMessage(JsonUtil.formatJson("break"));
    }
}
