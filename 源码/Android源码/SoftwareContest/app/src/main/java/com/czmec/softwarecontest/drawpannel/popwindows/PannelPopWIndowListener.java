package com.czmec.softwarecontest.drawpannel.popwindows;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.drawpannel.view.ColorPickerDialog;
import com.czmec.softwarecontest.drawpannel.view.DrawPannelMain;
import com.czmec.softwarecontest.drawpannel.view.DrawPannelRe;
import com.czmec.softwarecontest.drawpannel.view.DrawPannelView;
import com.czmec.softwarecontest.setting.PannelSetting;
import com.czmec.softwarecontest.socket.UDPSendMessage;
import com.czmec.softwarecontest.util.JsonUtil;
import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

import java.util.HashMap;

/**
 * 设置弹框的监听
 */
public class PannelPopWIndowListener implements Slider.OnPositionChangeListener, View.OnClickListener, ColorPickerDialog.OnColorChangedListener, Switch.OnCheckedChangeListener{
    private final Slider paintSeekbarScale;
    private final EditText paintEditWidth;
    private final EditText paintEditScale;
    private final Slider paintSeekbarWidth;
    private final Activity context;
    private final PannelPopWindow pannelPopWindow;

    public PannelPopWIndowListener(View contentView, Activity context, PannelPopWindow pannelPopWindow) {
        this.context = context;
        this.pannelPopWindow = pannelPopWindow;
        paintEditWidth = (EditText) contentView.findViewById(R.id.pannel_edit_paintwidth);
        paintEditScale = (EditText) contentView.findViewById(R.id.pannel_edit_scale);
        addListener();
        paintSeekbarWidth = (Slider) contentView.findViewById(R.id.pannel_seekbar_paintwidth);
        paintSeekbarWidth.setOnPositionChangeListener(this);

        paintSeekbarScale = (Slider) contentView.findViewById(R.id.pannel_seekbar_scale);
        paintSeekbarScale.setOnPositionChangeListener(this);

        LinearLayout pickColor = (LinearLayout) contentView.findViewById(R.id.pannel_btn_pickcolor);
        pickColor.setOnClickListener(this);
        Switch reco = (Switch) contentView.findViewById(R.id.pannel_btn_recognize);
        reco.setOnCheckedChangeListener(this);

        Switch scale = (Switch) contentView.findViewById(R.id.pannel_btn_scale);
        scale.setOnCheckedChangeListener(this);
        initData();

    }

    private void addListener() {
        paintEditWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 3) {
                        paintEditWidth.setText(String.valueOf(100));
                        PannelSetting.PAINT_WIDTH = 100;
                        paintSeekbarWidth.setValue(100, true);
                    } else {
                        PannelSetting.PAINT_WIDTH = Integer.parseInt(s.toString());
                        paintSeekbarWidth.setValue(Integer.parseInt(s.toString()), true);
                        paintEditWidth.setSelection(s.length());
                    }
                }

            }
        });


        paintEditScale.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (s.length() > 3) {
                        paintEditScale.setText(String.valueOf(100));
                        paintSeekbarScale.setValue(100, true);
                    } else {
                        PannelSetting.PANNEL_SCALE = Integer.parseInt(s.toString());
                        paintSeekbarScale.setValue(Integer.parseInt(s.toString()), true);
                        paintEditScale.setSelection(s.length());
                    }
                }

            }
        });
    }

    private void initData() {
        paintEditWidth.setText(String.valueOf(PannelSetting.PAINT_WIDTH));
        paintEditScale.setText(String.valueOf(PannelSetting.PANNEL_SCALE));
        paintSeekbarWidth.setValue(PannelSetting.PAINT_WIDTH, true);
        paintSeekbarScale.setValue(PannelSetting.PANNEL_SCALE, true);
    }



    /*@Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        HashMap<String, String> hashMap;
        switch (seekBar.getId()) {
            case R.id.pannel_seekbar_paintwidth:
                hashMap = new HashMap<>();
                hashMap.put("border", String.valueOf(PannelSetting.PAINT_WIDTH));
                UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
                break;
            case R.id.pannel_seekbar_scale:
                hashMap = new HashMap<>();
                hashMap.put("type", "scale");
                PannelSetting.PANNEL_SCALE = seekBar.getProgress();
                hashMap.put("x", String.valueOf(seekBar.getProgress() / 100f));
                Log.i("scale", "缩放比例" + hashMap.get("x"));
                UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
                break;
        }
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 选择颜色
             */
            case R.id.pannel_btn_pickcolor:
                try {
                    pannelPopWindow.dismiss();
                    DrawPannelView view = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom);
                    new ColorPickerDialog(context, "dd", view.getmPaint().getColor(), this, R.style.dialog).show();
                } catch (NullPointerException e) {
                    pannelPopWindow.dismiss();
                    DrawPannelView view = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom_re);
                    new ColorPickerDialog(context, "dd", view.getmPaint().getColor(), this, R.style.dialog).show();
                }
                break;
        }
    }


    @Override
    public void colorChanged(int color) {
        PannelSetting.PAINT_COLOR = color;
        UDPSendMessage.sendMessage(JsonUtil.formatJson(color));
    }

    @Override
    public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
        if (newValue == 0) {
            newValue++;
        }
        HashMap<String, String> hashMap;
        switch (view.getId()) {
            case R.id.pannel_seekbar_paintwidth:
                paintEditWidth.setText(String.valueOf(newValue));
                PannelSetting.PAINT_WIDTH = newValue;
                hashMap = new HashMap<>();
                hashMap.put("border", String.valueOf(PannelSetting.PAINT_WIDTH));
                UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
                break;
            case R.id.pannel_seekbar_scale:
                paintEditScale.setText(String.valueOf(newValue));
                hashMap = new HashMap<>();
                hashMap.put("type", "scale");
                PannelSetting.PANNEL_SCALE = newValue;
                hashMap.put("x", String.valueOf(newValue / 100f));
                Log.i("scale", "缩放比例" + hashMap.get("x"));
                UDPSendMessage.sendMessage(JsonUtil.formatJson(hashMap));
                break;
        }
    }

    @Override
    public void onCheckedChanged(Switch view, boolean checked) {
        switch (view.getId()) {
            case R.id.pannel_btn_recognize:
                if (checked) {
                    FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.pannel_main, new DrawPannelRe());
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("recognize"));
                    transaction.commitAllowingStateLoss();
                } else {
                    FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
                    transaction.replace(R.id.pannel_main, new DrawPannelMain());
                    UDPSendMessage.sendMessage(JsonUtil.formatJson("norecognize"));
                    transaction.commitAllowingStateLoss();
                }
                break;
            case R.id.pannel_btn_scale:
                DrawPannelView drawPannelView;
                try {
                    drawPannelView = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom);
                    if (checked) {
                        drawPannelView.registrarListener();
                    } else {
                        drawPannelView.unregistrateListener();
                    }
                } catch (Exception e) {
                    drawPannelView = (DrawPannelView) context.findViewById(R.id.pannel_main_bottom_re);
                    if (checked) {
                        drawPannelView.registrarListener();
                    } else {
                        drawPannelView.unregistrateListener();
                    }
                }
                break;
        }
    }
}
