package com.czmec.softwarecontest.socket;

import android.graphics.Bitmap;

import com.czmec.softwarecontest.setting.ConnectionSetting;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 */
public class TCPSendMessage {
    private static Socket socket;

    public static void sendMessage(final Bitmap bitmap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ConnectionSetting.IP, 1988);
                    if (socket.isClosed()) {
                        socket.connect(new InetSocketAddress(ConnectionSetting.IP, 1988));
                    }
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();
                    byteArrayOutputStream.close();
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    int l = b.length;
                    dos.write(b, 0, l);
                    dos.flush();
                    socket.close();
                } catch (IOException e) {

                }
            }
        }).start();

    }
}