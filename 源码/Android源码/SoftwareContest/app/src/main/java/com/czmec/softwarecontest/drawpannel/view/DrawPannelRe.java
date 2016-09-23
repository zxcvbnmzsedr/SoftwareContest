package com.czmec.softwarecontest.drawpannel.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.czmec.softwarecontest.R;

/**
 * 加载识别画板的按钮
 */
public class DrawPannelRe extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.drawpannel_re,null);
    }
}
