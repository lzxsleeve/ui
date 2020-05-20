package com.sleeve.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sleeve.ui.R;

/**
 * 内含状态栏高度LinearLayout,用于沉浸占位
 * <p>
 * Create by lzx on 2019/10/12
 */
public class ImmersionLinearLayout extends LinearLayout {

    private int statusBarHeight;
    private int type;

    public ImmersionLinearLayout(Context context) {
        super(context);
    }

    public ImmersionLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImmersionLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }
        } else {
            //低版本 直接设置0
            statusBarHeight = 0;
        }
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImmersionLinearLayout);
            type = typedArray.getInt(R.styleable.ImmersionLinearLayout_use_type, 0);
            typedArray.recycle();
        }
        if (type == 1) {
            setPadding(getPaddingLeft(), statusBarHeight, getPaddingRight(), getPaddingBottom());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (type == 0) {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    statusBarHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
