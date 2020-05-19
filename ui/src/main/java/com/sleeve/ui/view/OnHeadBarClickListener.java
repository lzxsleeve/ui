package com.sleeve.ui.view;

import android.view.View;

/**
 * Toolbar右边的点击监听
 * <p>
 * Create by lzx on 2019/9/23
 */
public interface OnHeadBarClickListener {

    /**
     * HeadBar 右边点击事件
     *
     * @param index 第几个
     * @param v     点击的view
     */
    void onHeadBarClick(int index, View v);
}
