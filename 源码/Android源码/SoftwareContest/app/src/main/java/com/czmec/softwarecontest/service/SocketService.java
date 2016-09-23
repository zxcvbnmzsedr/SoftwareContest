package com.czmec.softwarecontest.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.czmec.softwarecontest.setting.ConnectionSetting;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SocketService extends Service {
    private DatagramSocket clintSocker = null;

    public SocketService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("socketService", "socket service created");
        try {
            clintSocker = new DatagramSocket(Integer.parseInt(ConnectionSetting.PORT));
            new Thread() {
                @Override
                public void run() {
                    receive();
                }
            }.start();

        } catch (SocketException e) {
            Log.w("Exception", e);
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void receive() {
        while (true) {
            byte[] data = new byte[100];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                clintSocker.receive(packet);
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                Bundle bundle = new Bundle();
                bundle.putByteArray("location", data);
                intent.putExtras(bundle);
                sendBroadcast(intent);
            } catch (Exception e) {
                Log.w("Exception", e);
            }
        }
    }
}
