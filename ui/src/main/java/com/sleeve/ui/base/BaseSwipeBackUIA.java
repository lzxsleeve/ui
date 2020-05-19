package com.sleeve.ui.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sleeve.ui.R;
import com.sleeve.ui.util.Keyboard;

import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Activity 的基类--可以滑动
 * <p>
 * Create by lzx on 2019/9/23
 */
public abstract class BaseSwipeBackUIA extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addContentView() {
        setContentView(R.layout.base_uif);
    }

    /**
     * 必须调用 {@link #addContentView()} 之后才能使用此方法
     */
    protected void loadFragment(@NonNull ISupportFragment toFragment) {
        loadRootFragment(R.id.frame_layout, toFragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    protected void onDestroy() {
        Keyboard.fixInputMethodManagerLeak(this);
        super.onDestroy();
    }

}
