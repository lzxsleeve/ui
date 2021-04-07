package com.sleeve.ui.load;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.sleeve.net.throwable.LoadStatus;
import com.sleeve.ui.R;

/**
 * 加载转圈以及一些其他情况
 */
public class Loader implements LoadStatus {
    /**
     * 显示内容的根布局
     */
    private FrameLayout mViewGroup;
    /**
     * 加载中的布局
     */
    private View mLoadingLayout;

    protected View mNoDataLayout;

    protected View mErrorLayout;

    /**
     * 是否有头部，loading的时候赋值，所以必须先loading，其他情况才能正常显示
     */
    protected boolean mHasHeadBar;

    /**
     * 转圈顶部的偏移量
     */
    protected int mLoaderOffset = 52;

    private Context mContext;

    public Loader(FrameLayout viewGroup) {
        if (viewGroup == null) {
            throw new NullPointerException("viewGroup is null");
        }
        mViewGroup = viewGroup;
        mContext = mViewGroup.getContext();
    }

    /**
     * 弹出加载中
     *
     * @param hasWhiteBG 是否有白色背景
     */
    @Override
    public void loading(boolean hasWhiteBG) {
        loading(hasWhiteBG, mHasHeadBar);
    }

    /**
     * 弹出加载中
     *
     * @param hasWhiteBG 是否有白色背景
     * @param hasHeadBar 是否有 HeadBar
     */
    @Override
    public void loading(boolean hasWhiteBG, boolean hasHeadBar) {
        mHasHeadBar = hasHeadBar;
        if (mLoadingLayout == null) {
            mLoadingLayout = LayoutInflater.from(mContext)
                    .inflate(R.layout.load_loading, mViewGroup, false);
        }
        setOffset(mLoadingLayout);
        if (hasWhiteBG) {
            mLoadingLayout.setBackgroundColor(Color.WHITE);
        } else {
            mLoadingLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        loadRemoveAll();
        mViewGroup.addView(mLoadingLayout);
    }

    @Override
    public void loadNoData(@DrawableRes int resId, String msg) {
        if (mNoDataLayout == null) {
            mNoDataLayout = LayoutInflater.from(mContext)
                    .inflate(R.layout.load_layout, mViewGroup, false);
        }
        setOffset(mNoDataLayout);
        ImageView img = mNoDataLayout.findViewById(R.id.load_img);
        if (resId != 0) {
            img.setImageResource(resId);
        } else {
            img.setImageResource(R.mipmap.pic_load_no_data);
        }
        TextView text = mNoDataLayout.findViewById(R.id.load_text);
        text.setText(msg);
        loadRemoveAll();
        mViewGroup.addView(mNoDataLayout);
    }

    @Override
    public void loadError(String msg) {
        if (mErrorLayout == null) {
            mErrorLayout = LayoutInflater.from(mContext)
                    .inflate(R.layout.load_layout, mViewGroup, false);
        }
        setOffset(mErrorLayout);
        ImageView img = mErrorLayout.findViewById(R.id.load_img);
        img.setImageResource(R.mipmap.pic_load_error);
        TextView text = mErrorLayout.findViewById(R.id.load_text);
        text.setText(msg);
        loadRemoveAll();
        mViewGroup.addView(mErrorLayout);
    }

    /**
     * 加载完成、重新加载。去除所有 Layout
     */
    @Override
    public void loadRemoveAll() {
        mViewGroup.removeView(mLoadingLayout);

        mViewGroup.removeView(mNoDataLayout);

        mViewGroup.removeView(mErrorLayout);
    }

    /**
     * 判断是否需要偏移出头部的距离
     */
    private void setOffset(View layout) {
        // mHasHeadBar必须先赋值,否则其他情况无效
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) layout.getLayoutParams();
        if (mHasHeadBar) {
            lp.topMargin = dp2px(mLoaderOffset);
        } else {
            lp.topMargin = 0;
        }
        layout.setLayoutParams(lp);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * dp2px
     */
    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                mContext.getResources().getDisplayMetrics());
    }
}
