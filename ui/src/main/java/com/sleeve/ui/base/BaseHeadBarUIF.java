package com.sleeve.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.sleeve.ui.R;
import com.sleeve.ui.view.HeadBar;
import com.sleeve.ui.view.TopOccupyLinearLayout;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * 有头部的 Fragment--可以右滑关闭
 * <p>
 * Create by lzx on 2019/9/23
 */
public abstract class BaseHeadBarUIF extends BaseSwipeBackUIF {

    protected HeadBar mHeadBar;
    // 装载 RxJava 的观察者
    protected CompositeDisposable mCompositeDisposable;
    protected TopOccupyLinearLayout mTopOccupy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutParent = inflater.inflate(R.layout.base_uif_toolbar, container, false);
        // 显示内容的根布局
        mViewGroup = layoutParent.findViewById(R.id.frame_layout);
        mTopOccupy = layoutParent.findViewById(R.id.top_occupy_layout);
        // 设置头部
        setToolbar(layoutParent);
        // 添加头部以下内容布局
        int contentLayout = getContentLayout();
        if (contentLayout != 0) {
            inflater.inflate(contentLayout, mViewGroup, true);
        }

        // 需要支持SwipeBack则这里必须调用 attachToSwipeBack(view);
        return attachToSwipeBack(layoutParent);
    }

    /**
     * 返回键的点击监听，如需实现特殊需求重写此方法即可
     */
    protected void onToolbarBackClick(View view) {
        _mActivity.onBackPressed();
    }

    /**
     * 设置标题
     */
    protected abstract void initHeadBar(@NonNull HeadBar headBar);

    private void setToolbar(View layoutParent) {
        mHeadBar = layoutParent.findViewById(R.id.head_bar);
        // 添加状态栏的高度view
//        ImmersionBar.setTitleBar(_mActivity, mHeadBar);
        initHeadBar(mHeadBar);
        mHeadBar.setOnBackClick(this::onToolbarBackClick);
    }

    /**
     * 设置沉浸式，沉浸式头部会多出状态栏高度
     */
    protected void setImmersion() {
        mTopOccupy.setVisibility(View.VISIBLE);
    }

    /**
     * 设置头部的背景颜色资源
     */
    protected void setToolbarBackground(@DrawableRes int id) {
        ((AppBarLayout) mHeadBar.getParent()).setBackground(ContextCompat.getDrawable(_mActivity, id));
    }

    /**
     * 设置状态栏部分的背景颜色资源
     */
    protected void setStatusBarBackground(@DrawableRes int id) {
        mTopOccupy.setBackground(ContextCompat.getDrawable(_mActivity, id));
    }

    /**
     * 添加网络请求NetCallback，关闭界面的时候释放
     */
    protected <T> Observer<T> addDisposable(DisposableObserver<T> d) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
        return d;
    }

    /**
     * 添加Disposable，关闭界面的时候释放
     */
    protected void addDisposable(Disposable d) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(d);
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        if (mCompositeDisposable != null && mCompositeDisposable.size() > 0) {
            mCompositeDisposable.clear();
        }
        super.onDestroyView();
    }
}
