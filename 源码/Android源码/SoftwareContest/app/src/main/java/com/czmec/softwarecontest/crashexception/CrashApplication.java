package com.czmec.softwarecontest.crashexception;

import android.app.Application;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 启动程序时候加载，用于收集程序出现的异常
 */
public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
    private static DatagramSocket clintSocket;
    static {
        try {
            clintSocket = new DatagramSocket(1989);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public DatagramSocket getClintSocket(){
        return clintSocket;
    }

}
