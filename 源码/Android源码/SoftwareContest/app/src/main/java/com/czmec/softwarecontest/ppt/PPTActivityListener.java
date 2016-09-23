package com.czmec.softwarecontest.ppt;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class PPTActivityListener implements View.OnTouchListener {
    private boolean isPlay = false;

    public PPTActivityListener() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.ppt_left:
                Map map = new HashMap();
                map.put("type","keyboard");
                JSONObject ob = new JSONObject();
                try {
                    ob.put("type","ppt");
                    ob.put("whichkey","lastpage");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                map.put("key", ob);
                UDPSendMessage.sendMessage(JsonUtil.formatJson(map));
                break;
            case R.id.ppt_right:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        map = new HashMap();
                        map.put("type","keyboard");
                        ob =new JSONObject();
                        try {
                            ob.put("type","ppt");
                            ob.put("whichkey","nextpage");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        map.put("key", ob);
                        UDPSendMessage.sendMessage(JsonUtil.formatJson(map));
                        break;
                }
                break;
            case R.id.ppt_play:
                ImageView imageView = (ImageView) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        if(!isPlay){
                            imageView.setBackgroundResource(R.drawable.ppt_stop_selector);
                            map = new HashMap();
                            map.put("type","keyboard");
                            ob =new JSONObject();
                            try {
                                ob.put("type","ppt");
                                ob.put("whichkey","showfromnow");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            map.put("key", ob);
                            isPlay = true;
                        }else {
                            imageView.setBackgroundResource(R.drawable.ppt_play_selector);
                            map = new HashMap();
                            map.put("type","keyboard");
                            ob =new JSONObject();
                            try {
                                ob.put("type","ppt");
                                ob.put("whichkey","exitplay");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            map.put("key", ob);
                            isPlay = false;
                        }
                        UDPSendMessage.sendMessage(JsonUtil.formatJson(map));
                        break;
                }
                break;
        }
        return false;
    }
}
