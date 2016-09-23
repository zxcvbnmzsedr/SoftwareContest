package com.czmec.softwarecontest.launcher.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.service.CheckConnectionService;
import com.czmec.softwarecontest.setting.ConnectionListener;
import com.czmec.softwarecontest.setting.ConnectionSetting;
import com.czmec.softwarecontest.setting.Screen;
import com.czmec.softwarecontest.util.JsonUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

/**
 * 第一次尝试连接，如果连接成功把标识符更改为true，并进行下一步的操作
 */
public class SocketConnection extends AsyncTask<Void, Integer, Boolean> implements ConnectionListener {
    private DatagramSocket socket;
    private final Activity context;
    private ProgressDialog pg;
    public static int count = 0;
    private TextView textView;

    public SocketConnection(Activity context) {
        this.context = context;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            Log.w("Exception", e.toString());
        }
    }


    @Override // 初始化操作
    protected void onPreExecute() {
        super.onPreExecute();
        pg = ProgressDialog.show(context, "提示", "正在连接");
        new BroadCastUdp();
        ConnectionSetting.setListener(this);
        textView = (TextView) context.findViewById(R.id.btn_connect);
    }

    @Override // 执行完毕后调用
    protected void onPostExecute(Boolean result) {
        if (result) {
            // 有返回值
            ConnectionSetting.changeCoon(true);
        } else {
            ConnectionSetting.changeCoon(false);
        }

    }

    @Override // 处理耗时操作
    protected Boolean doInBackground(Void... params) {
        BroadCastUdp.st();
        return SocketConnection.count < 1 && startConnect();
    }

    private boolean startConnect() {
        try {
            Thread.sleep(500);
            InetAddress inetAddress = InetAddress.getByName(ConnectionSetting.IP);
            HashMap dpi = new HashMap<>();
            dpi.put("result", "ok");
            dpi.put("y", String.valueOf(((Screen.WIDTHPIELS - 50) / Screen.DPI)));
            dpi.put("x", String.valueOf((Screen.HEIGHTPIELS / Screen.DPI)));
            dpi.put("sy", String.valueOf(Screen.WIDTHPIELS - 50));
            dpi.put("sx", String.valueOf((Screen.HEIGHTPIELS)));
            byte[] data = (JsonUtil.formatJson(dpi)).toString().getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, inetAddress, Integer.parseInt(ConnectionSetting.PORT));
            socket.send(packet);
            return respond();
        } catch (Exception e) {
            Log.w("Exception", e);
            return false;
        }

    }

    private boolean respond() {
        byte[] data = new byte[128];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        try {
            socket.setSoTimeout(5000); // 设置socket超时时间
            socket.receive(packet);
            Log.i("respond", new String(data, 0, data.length));
        } catch (IOException e) {
            Log.w("Exception", e);
            return false;
        }
        return true;
    }


    @Override
    public void connectionChange(boolean b) {
        Intent intent = new Intent(context, CheckConnectionService.class);
        TextView textView = (TextView) context.findViewById(R.id.btn_connect);
        if (b) {
            pg.dismiss();
            textView.setText(R.string.soft_connect_success);
            textView.setBackgroundResource(R.drawable.soft_connected_btn_selector);
            context.startService(intent);
        } else {
            pg.dismiss();
            context.stopService(intent);
            mHandler.sendEmptyMessage(1);
        }
        ConnectionSetting.isCoon = b;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            textView.setText(R.string.soft_connect);
            textView.setBackgroundResource(R.drawable.soft_connect_btn_selector);
            Toast.makeText(context, R.string.soft_connect_failure, Toast.LENGTH_SHORT).show();
        }
    };
}
