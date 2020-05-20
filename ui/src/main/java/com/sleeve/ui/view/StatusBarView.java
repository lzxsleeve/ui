package com.sleeve.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sleeve.ui.R;

/**
 * 状态栏高度的View
 *
 * @deprecated 跳转Fragment后会计算高度为零，暂时弃用，可使用{@link ImmersionView}.
 * Create by lzx on 2020/05/19.
 */
public class StatusBarView extends View {

    private WindowInsetsCompat mLastInsets;
    private boolean mDrawStatusBarBackground = true;
    private Drawable mStatusBarBackground;

    public StatusBarView(Context context) {
        super(context);
        init();
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final Runnable REQUEST_LAYOUT_RUNNABLE = new Runnable() {
        @Override
        public void run() {
            requestLayout();
        }
    };

    private void init() {
        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v,
                                                          WindowInsetsCompat insets) {
                if (getVisibility() == View.GONE) {
                    mLastInsets = null;
                    return insets;
                }
                WindowInsetsCompat insetsCompat = new WindowInsetsCompat(insets);
                if (!insetsCompat.equals(mLastInsets)) {
                    mLastInsets = new WindowInsetsCompat(insets);
                    //Must post requestLayout otherwise ViewGroup's state will freeze in isLayoutRequested()==true,
                    //then following requestLayout will be ignored
                    post(REQUEST_LAYOUT_RUNNABLE);
                }
                return new WindowInsetsCompat(insets).replaceSystemWindowInsets(
                        insets.getSystemWindowInsetLeft(),
                        0,
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
            }
        });

        // 设置默认颜色
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        mStatusBarBackground = new ColorDrawable(color);

        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics());
        ViewCompat.setElevation(this, px);
    }

    public void setStatusBarBackground(@Nullable Drawable bg) {
        mStatusBarBackground = bg;
        invalidate();
    }

    @Nullable
    public Drawable getStatusBarBackgroundDrawable() {
        return mStatusBarBackground;
    }

    public void setStatusBarBackground(@DrawableRes int resId) {
        mStatusBarBackground = resId != 0 ? ContextCompat.getDrawable(getContext(), resId) : null;
        invalidate();
    }

    public void setStatusBarBackgroundColor(@ColorInt int color) {
        mStatusBarBackground = new ColorDrawable(color);
        invalidate();
    }

    private static int getDefaultSize2(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(size, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    protected void onMeasure2(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(
                getDefaultSize2(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize2(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mLastInsets != null) {
            onMeasure2(widthMeasureSpec, MeasureSpec.makeMeasureSpec(mLastInsets.getSystemWindowInsetTop(), MeasureSpec.EXACTLY));
        } else {
            onMeasure2(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawStatusBarBackground && mStatusBarBackground != null) {
            final int inset;
            if (Build.VERSION.SDK_INT >= 21) {
                inset = mLastInsets != null
                        ? mLastInsets.getSystemWindowInsetTop() : 0;
            } else {
                inset = 0;
            }
            if (inset > 0) {
                mStatusBarBackground.setBounds(0, 0, getWidth(), inset);
                mStatusBarBackground.draw(canvas);
            }
        }
    }
}
