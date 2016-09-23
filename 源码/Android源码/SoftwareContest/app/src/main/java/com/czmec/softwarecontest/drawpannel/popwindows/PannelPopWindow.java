package com.czmec.softwarecontest.drawpannel.popwindows;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.czmec.softwarecontest.R;
import com.czmec.softwarecontest.setting.Screen;

/**
 * 画板的设置弹窗
 */
public class PannelPopWindow extends PopupWindow {
    public PannelPopWindow(Activity context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.popmenu_setting, null);
        this.setContentView(contentView);
        this.setWidth(Screen.WIDTHPIELS / 2 + 50);
        this.setHeight(Screen.HEIGHTPIELS / 2);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable drawable = new ColorDrawable(0);
        this.setBackgroundDrawable(drawable);

        new PannelPopWIndowListener(contentView, context, this);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
}
