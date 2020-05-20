package com.sleeve.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.sleeve.ui.R;

/**
 * 内含状态栏高度ConstraintLayout,用于沉浸占位
 * <p>
 * Create by lzx on 2019/10/12
 */
public class ImmersionConstraintLayout extends ConstraintLayout {

    private int statusBarHeight;
    private int type;

    public ImmersionConstraintLayout(Context context) {
        super(context);
    }

    public ImmersionConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ImmersionConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ImmersionConstraintLayout);
            type = typedArray.getInt(R.styleable.ImmersionConstraintLayout_use_type, 0);
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
