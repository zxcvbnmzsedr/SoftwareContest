package com.czmec.softwarecontest.setting;

/**
 * 连接设置
 */
public class ConnectionSetting {
    private static ConnectionListener mListener;
    public static String IP; // 扫描到的ip地址
    public static final String PORT = "1987"; // 指定端口号
    public static boolean isCoon = false; // 是否连接的标识符

    public static void setListener(ConnectionListener listener) {
        mListener = listener;
    }

    public static void changeCoon(boolean b) {
        if (mListener != null) {
            mListener.connectionChange(b);
        }
    }
}
