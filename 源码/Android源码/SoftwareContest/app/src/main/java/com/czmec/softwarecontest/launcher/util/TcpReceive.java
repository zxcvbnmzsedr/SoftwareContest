package com.czmec.softwarecontest.launcher.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

import com.czmec.softwarecontest.setting.ConnectionSetting;
import com.czmec.softwarecontest.util.StringUtil;

class TcpReceive implements Runnable {

    public void run() {
        Socket socket = null;
        ServerSocket ss = null;
        BufferedReader in = null;
        try {
            ss = new ServerSocket(9999);
        } catch (IOException e) {
            Log.w("Exception", e);
        }
        while (BroadCastUdp.start) {
            try {
                Log.i("TcpReceive", "ServerSocket +++++++");
                if(ss != null){
                    socket = ss.accept();
                }
                Log.i("TcpReceive", "connect +++++++");
                if (socket != null) {
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    sb.append(socket.getInetAddress().getHostAddress());
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.i("TcpReceive", "connect :" + sb.toString());
                    //IP地址
                    String ipString = sb.toString().trim();
                    if (StringUtil.isIpAddress(ipString)) {
                        SocketConnection.count = 0;
                        ConnectionSetting.IP = ipString;
                    } else {
                        SocketConnection.count = 1;
                        break;
                    }
                }
            } catch (Exception e) {
                Log.w("Exception", e);
            } finally {
                try {
                    BroadCastUdp.broad.stoat();
                    if (in != null)
                        in.close();
                    if (socket != null)
                        socket.close();
                    if (ss != null)
                        ss.close();
                } catch (IOException e) {
                    Log.w("Exception", e);
                }
            }
        }

    }
}
