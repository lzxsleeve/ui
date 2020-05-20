package com.sleeve.ui.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.sleeve.ui.R;

/**
 * 具有状态栏高度的View
 * <p>
 * Create by lzx on 2020/05/20.
 */
public class ImmersionView extends View {

    private int statusBarHeight;

    public ImmersionView(Context context) {
        super(context);
        init();
    }

    public ImmersionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImmersionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ImmersionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        } else {
            //低版本 直接设置0
            statusBarHeight = 0;
        }

        // 设置默认主题颜色
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), statusBarHeight);
    }
}
