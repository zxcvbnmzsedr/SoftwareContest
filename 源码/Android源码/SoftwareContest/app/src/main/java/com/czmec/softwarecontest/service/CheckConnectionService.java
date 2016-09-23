package com.czmec.softwarecontest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.czmec.softwarecontest.crashexception.CrashApplication;
import com.czmec.softwarecontest.setting.ConnectionSetting;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by tianzeng on 2016-05-12.
 */
public class CheckConnectionService extends Service {
    private boolean isChecking = false;
    private DatagramSocket clintSocker = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("socketService", "socket service created");
        isChecking = false;
        if (clintSocker == null) {
            clintSocker = ((CrashApplication) getApplication()).getClintSocket();
        }
        new Thread() {
            @Override
            public void run() {
                check();
            }
        }.start();
    }

    private void check() {
        while (!isChecking) {
            try {
                Thread.sleep(30000);
                UDPSendMessage.sendMessage(JsonUtil.formatJson("check"));
                byte[] data = new byte[100];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                clintSocker.setSoTimeout(10000);
                clintSocker.receive(packet);
                if (!new String(data, 0, data.length).equals("")) {
                    Log.w("connect", "the connection is start");
                } else {
                    ConnectionSetting.IP = "";
                    ConnectionSetting.isCoon = false;
                    PannelSetting.init();
                    isChecking = true;
                    ConnectionSetting.changeCoon(false);
                    Log.w("connect", "the connection is closed");
                }
            } catch (Exception e) {
                ConnectionSetting.IP = "";
                ConnectionSetting.isCoon = false;
                PannelSetting.init();
                isChecking = true;
                ConnectionSetting.changeCoon(false);
                Log.w("connect", "the connection is closed" + e);
            }

        }
    }
}
