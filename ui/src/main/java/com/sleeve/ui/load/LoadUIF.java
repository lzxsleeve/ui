package com.sleeve.ui.load;

import android.content.Context;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.viewbinding.ViewBinding;

import com.sleeve.net.throwable.LoadStatus;
import com.sleeve.ui.base.BaseUIF;

/**
 * 没有侧滑关闭的 LoadUIF
 */
public abstract class LoadUIF<T extends ViewBinding> extends BaseUIF<T> implements LoadStatus {

    protected LoadStatus mLoader;

    /**
     * 弹出加载中
     *
     * @param hasWhiteBG 是否有白色背景
     */
    @Override
    public void loading(boolean hasWhiteBG) {
        LoadStatus loadView = getLoader();
        loadView.loading(hasWhiteBG);
    }

    @Override
    public void loading(boolean hasWhiteBG, boolean hasHeadBar) {
        LoadStatus loadView = getLoader();
        loadView.loading(hasWhiteBG, hasHeadBar);
    }

    @Override
    public void loadNoData(@DrawableRes int resId, String msg) {
        LoadStatus loadView = getLoader();
        loadView.loadNoData(resId, msg);
    }

    @Override
    public void loadError(String msg) {
        LoadStatus loadView = getLoader();
        loadView.loadError(msg);
    }

    @Override
    public void loadRemoveAll() {
        LoadStatus loadView = getLoader();
        loadView.loadRemoveAll();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        if (mLoader != null) {
            // 方便其他不同的Loader处理相应的操作
            loadOnDestroy();
            // 避免内存泄漏
            mLoader = null;
        }
        super.onDestroyView();
    }

    @Override
    public Context getContext() {
        return _mActivity;
    }

    /**
     * 获取 Loader
     */
    protected LoadStatus getLoader() {
        if (mLoader == null) {
            mLoader = new Loader(mViewGroup);
        }
        return mLoader;
    }

    /**
     * Loader进行一些释放操作
     */
    protected void loadOnDestroy() {

    }
}
