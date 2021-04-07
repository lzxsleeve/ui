package com.sleeve.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.sleeve.ui.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Fragment 的基类--可以滑动
 * <p>
 * Create by lzx on 2019/9/23
 */
public abstract class BaseSwipeBackUIF<VB extends ViewBinding> extends SwipeBackFragment {

    /**
     * 显示内容的根布局
     */
    protected FrameLayout mViewGroup;
    /**
     * ViewBinding
     */
    protected VB mBinding;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.26f);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 显示内容的根布局
        mViewGroup = (FrameLayout) inflater.inflate(R.layout.base_uif, container, false);
        // 通过ViewBinding添加内容布局
        mBinding = getViewBinging(inflater);
        mViewGroup.addView(mBinding.getRoot());

        // 需要支持SwipeBack则这里必须调用 attachToSwipeBack(view);
        return attachToSwipeBack(mViewGroup);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract VB getViewBinging(@NonNull LayoutInflater inflater);

    protected abstract void initView();

    protected <V extends View> V findViewId(@IdRes int id) {
        return mViewGroup.findViewById(id);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onPause() {
        hideSoftInput();
        super.onPause();
    }
}
