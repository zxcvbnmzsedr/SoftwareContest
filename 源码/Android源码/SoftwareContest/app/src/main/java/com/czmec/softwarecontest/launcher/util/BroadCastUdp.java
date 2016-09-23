package com.czmec.softwarecontest.launcher.util;

import android.util.Log;

import com.czmec.softwarecontest.socket.UDPSendMessage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 向局域网发送广播包
 */
public class BroadCastUdp extends Thread {
    private DatagramSocket udpSocket;
    public static boolean start = true;
    private static final int DEFAULT_PORT = 9898;
    public static BroadCastUdp broad;

    public BroadCastUdp() {

    }

    public static void st() {
        //启动
        Thread thread = new Thread(new TcpReceive());
        thread.start();
        start = true;
        broad = new BroadCastUdp();
        broad.start();
    }

    public void stoat() {
        //关闭
        start = false;
    }

    public void run() {
        if(udpSocket == null){
            udpSocket = UDPSendMessage.getSocket();
        }
        try {
            InetAddress broadcastAdar;
            broadcastAdar = InetAddress.getByName("255.255.255.255");
            String dataString = "";
            byte[] data = dataString.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length,
                    broadcastAdar, DEFAULT_PORT);
            udpSocket.send(packet);
        } catch (Exception e) {
            Log.w("Exception",e.toString());
        }

    }
}



