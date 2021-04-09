package com.sleeve.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.sleeve.ui.R;
import com.sleeve.ui.view.HeadBar;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Fragment 的基类--不可滑动
 * <p>
 * Create by lzx on 2019/9/23
 */
public abstract class BaseUIF<VB extends ViewBinding> extends SupportFragment {

    /**
     * 显示内容的根布局
     */
    protected FrameLayout mViewGroup;
    /**
     * 装载 RxJava 的观察者
     */
    protected CompositeDisposable mCompositeDisposable;
    /**
     * ViewBinding
     */
    protected VB mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 显示内容的根布局
        mViewGroup = (FrameLayout) inflater.inflate(R.layout.base_uif, container, false);
        // 通过ViewBinding添加内容布局
        mBinding = getViewBinding(inflater);
        mViewGroup.addView(mBinding.getRoot());

        // 需要支持SwipeBack则这里必须调用toSwipeBackFragment(view);
        return mViewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    protected abstract VB getViewBinding(@NonNull LayoutInflater inflater);

    protected abstract void initView();

    /**
     * 一般不能侧滑有标题的只有首页用到，首页的标题根据规范居中，首页进入的其他标题靠左
     */
    protected void setHeadBar(CharSequence title) {
        HeadBar headBar = findViewId(R.id.head_bar);
        headBar.setCenterTitle(title);
    }

    protected <T extends View> T findViewId(@IdRes int id) {
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
        mBinding = null;
        super.onDestroyView();
    }
}
