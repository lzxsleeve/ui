package com.sleeve.ui.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.appbar.AppBarLayout;
import com.sleeve.ui.R;
import com.sleeve.ui.view.HeadBar;
import com.sleeve.ui.view.ImmersionView;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * 有头部的 Fragment--可以右滑关闭
 * <p>
 * Create by lzx on 2019/9/23
 */
public abstract class BaseHeadBarUIF<VB extends ViewBinding> extends BaseSwipeBackUIF<VB> {

    protected HeadBar mHeadBar;
    // 装载 RxJava 的观察者
    protected CompositeDisposable mCompositeDisposable;

    // 模拟的状态栏占位View
    protected @Nullable ImmersionView mStartBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layoutParent = inflater.inflate(R.layout.base_uif_toolbar, container, false);
        // 设置头部
        setToolbar(layoutParent);
        // 显示内容的根布局
        mViewGroup = layoutParent.findViewById(R.id.frame_layout);
        // 通过ViewBinding添加内容布局
        mBinding = getViewBinding(inflater);
        mViewGroup.addView(mBinding.getRoot());

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
        initHeadBar(mHeadBar);
        mHeadBar.setOnBackClick(this::onToolbarBackClick);
    }

    /**
     * 设置HeadBar的背景颜色资源
     */
    protected void setToolbarBackground(@DrawableRes int id) {
        setToolbarBackground(ContextCompat.getDrawable(_mActivity, id));
    }

    /**
     * 设置HeadBar的背景颜色资源
     */
    protected void setToolbarBackground(Drawable drawable) {
        ((AppBarLayout) mHeadBar.getParent()).setBackground(drawable);
    }

    /**
     * 设置模拟的状态栏
     * 因为AppBarLayout上层有阴影，所以将view添加在AppBarLayout内
     */
    protected void setStatusBarView() {
        if (mStartBar == null) {
            mStartBar = new ImmersionView(getContext());
            AppBarLayout appBar = (AppBarLayout) mHeadBar.getParent();
            appBar.addView(mStartBar, 0);
            AppBarLayout.LayoutParams lp = new AppBarLayout.LayoutParams(-1, -2);
            mStartBar.setLayoutParams(lp);
        }
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
