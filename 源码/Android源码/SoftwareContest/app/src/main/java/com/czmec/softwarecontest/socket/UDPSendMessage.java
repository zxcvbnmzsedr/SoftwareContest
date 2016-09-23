package com.czmec.softwarecontest.socket;

import android.util.Log;

import com.czmec.softwarecontest.setting.ConnectionSetting;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * 将信息发送到服务器
 * Created by 78537 on 2016-03-31.
 */
public class UDPSendMessage {
    private static DatagramSocket socket;

    static {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            Log.w("Exception", e.toString());
        }
    }

    public static DatagramSocket getSocket() {
        return socket;
    }

    /**
     * 向目标主机发送消息
     *
     * @param object 需要被传输的对象
     */
    public static void sendMessage(final Object object) {
        if (ConnectionSetting.isCoon) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InetAddress serverAddress;
                    try {
                        serverAddress = InetAddress.getByName(ConnectionSetting.IP);
                        byte data[] = object.toString().getBytes();
                        // 创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号
                        DatagramPacket packet = new DatagramPacket(data, data.length,
                                serverAddress, Integer.parseInt(ConnectionSetting.PORT));
                        // 调用socket对象的send方法，发送数据
                        socket.send(packet);
                    } catch (IOException e) {
                        Log.w("Exception", e);
                    }

                }
            }).start();
        }
    }

}
