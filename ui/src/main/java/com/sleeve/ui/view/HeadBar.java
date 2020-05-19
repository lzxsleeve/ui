package com.sleeve.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.sleeve.ui.R;

/**
 * 头部的Toolbar
 * <p>
 * Create by lzx on 2019/9/23
 */
public class HeadBar extends FrameLayout implements View.OnClickListener {

    public Toolbar mToolbar;
    public TextView mCenterTitle;
    public TextView mCenterSmallTitle;
    public LinearLayout mCenterMultiTitle;
    public TextView mRightTv;
    private OnHeadBarClickListener mOnRightClick;

    /**
     * 右边View的Tag
     */
    private int mRightViewTag = -1;

    /**
     * 包裹右边View的父布局
     */
    private LinearLayout mRightParentLayout;

    /**
     * 返回键布局(包含分割线)
     */
    private LinearLayout mBackLayout;

    /**
     * 返回键右边的分割线
     */
    private ImageView mDivider;

    public HeadBar(Context context) {
        super(context);
        initView();
    }

    public HeadBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HeadBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutParams lp = new LayoutParams(-1, -2);
        mToolbar = (Toolbar) LayoutInflater.from(getContext()).inflate(R.layout.headbar_toolbar, this, false);
        mToolbar.setLayoutParams(lp);
        this.addView(mToolbar);
    }

    /**
     * 这个可以配合下面两个方法使用
     * {@link #addRightTextView(String)}
     * {@link #addRightImageView(Integer)}
     * 如果调用上面的方法，最后必须调用
     * {@link #build()}
     */
    public HeadBar setTitle(CharSequence title) {
        mToolbar.setTitle(title);
        setBackView();

        // 防止重复设置右边的布局异常
        if (mRightParentLayout != null) {
            if (mRightParentLayout.getChildCount() > 0) {
                mRightParentLayout.removeAllViews();
            }
            if (this.indexOfChild(mRightParentLayout) != -1) {
                this.removeView(mRightParentLayout);
            }
        }
        return this;
    }

    /**
     * 设置标题跟右边的文字
     *
     * @param title        标题
     * @param rightTextStr 右边的文字
     */
    public HeadBar setTitle(CharSequence title, String rightTextStr) {
        setTitle(title);
        getRightTextLayout(rightTextStr);
        build();
        return this;
    }

//    /**
//     * 设置标题跟右边的文字
//     *
//     * @param title        标题
//     * @param rightTextStr 右边的文字
//     */
//    public BaseToolbar setTitle(CharSequence title, String... rightTextStr) {
//        mToolbar.setTitle(title);
//        if (checkRightView(rightTextStr)) {
//            this.addView(getRightTextLayout(rightTextStr));
//        }
//        return this;
//    }

    /**
     * 设置标题跟右边的图片
     *
     * @param title         标题
     * @param rightImgResId 图片的资源id
     */
    public HeadBar setTitle(CharSequence title, @DrawableRes Integer... rightImgResId) {
        setTitle(title);
        if (checkRightView(rightImgResId)) {
            getRightImgLayout(rightImgResId);
            build();
        }
        return this;
    }

    /**
     * 设置中间标题
     *
     * @param title 标题
     */
    public HeadBar setCenterTitle(CharSequence title) {
        getCenterTitle(title);
        return this;
    }

    /**
     * 设置中间标题还有小标题
     */
    public HeadBar setCenterTitle(CharSequence title, CharSequence smallTitle) {
        getCenterMultiTitle(title, smallTitle);
        return this;
    }

    /**
     * 设置中间标题跟右边的文字
     *
     * @param title        标题
     * @param rightTextStr 右边的文字
     */
    public HeadBar setCenterTitle(CharSequence title, @Size(min = 1, max = 4) String rightTextStr) {
        setCenterTitle(title);
        getRightTextLayout(rightTextStr);
        build();
        return this;
    }

    /**
     * 设置中间标题还有小标题跟右边的文字
     */
    public HeadBar setCenterTitle(CharSequence title, CharSequence smallTitle, @Size(min = 1, max = 4) String rightTextStr) {
        setCenterTitle(title, smallTitle);
        getRightTextLayout(rightTextStr);
        build();
        return this;
    }

    /**
     * 设置中间标题跟右边的图片
     *
     * @param title         标题
     * @param rightImgResId 图片的资源id
     */
    public HeadBar setCenterTitle(CharSequence title, @DrawableRes Integer... rightImgResId) {
        setCenterTitle(title);
        if (checkRightView(rightImgResId)) {
            getRightImgLayout(rightImgResId);
            build();
        }
        return this;
    }

    /**
     * 设置中间标题还有小标题跟右边的图片
     */
    public HeadBar setCenterTitle(CharSequence title, CharSequence smallTitle, @DrawableRes Integer... rightImgResId) {
        setCenterTitle(title, smallTitle);
        if (checkRightView(rightImgResId)) {
            getRightImgLayout(rightImgResId);
            build();
        }
        return this;
    }

    /**
     * 添加右边的单个TextView
     * (为了解决右边图片文字混合显示)
     */
    public HeadBar addRightTextView(String rightTextStr) {
        mRightViewTag++;
        if (mRightParentLayout == null) {
            mRightParentLayout = getRightParentLayout();
        }
        mRightTv = getRightText(mRightParentLayout);
        mRightTv.setText(rightTextStr);
        mRightTv.setTag(mRightViewTag);
        mRightTv.setOnClickListener(this);
        mRightParentLayout.addView(mRightTv);
        return this;
    }

    /**
     * 添加右边的单个ImageView
     * (为了解决右边图片文字混合显示)
     */
    public HeadBar addRightImageView(@DrawableRes Integer rightImgResId) {
        mRightViewTag++;
        if (mRightParentLayout == null) {
            mRightParentLayout = getRightParentLayout();
        }
        if (rightImgResId != 0) {
            ImageView rightImg = getRightImg(mRightParentLayout);
            rightImg.setImageResource(rightImgResId);
            rightImg.setTag(mRightViewTag);
            rightImg.setOnClickListener(this);
            mRightParentLayout.addView(rightImg);
        }
        return this;
    }

    /**
     * 添加完右边的View之后，把右边的父布局添加到Toolbar上面
     */
    public HeadBar build() {
        if (mRightParentLayout != null) {
            int childCount = mRightParentLayout.getChildCount();
            if (childCount > 2) {
                throw new RuntimeException("Toolbar右边的view不能大于2个");
            }
            if (this.indexOfChild(mRightParentLayout) == -1) {
                this.addView(mRightParentLayout);
            }
        }
        return this;
    }

    /**
     * 设置返回键，分割线
     */
    public void setBackView() {
        if (mBackLayout == null) {
            mBackLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.headbar_back_view,
                    this, false);
        }
        if (this.indexOfChild(mBackLayout) == -1) {
            this.addView(mBackLayout);
        }
    }

    /**
     * 设置分割线颜色
     * 如果想隐藏分割线可以把颜色设为透明
     *
     * @param color 16 进制颜色代码
     */
    public HeadBar setDividerColor(@ColorInt int color) {
        if (mDivider == null) {
            mDivider = mBackLayout.findViewById(R.id.headbar_divider);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDivider.setImageTintList(ColorStateList.valueOf(color));
        } else {
            mDivider.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置中间的标题
     */
    private void getCenterTitle(CharSequence title) {
//        判断是否为空，防止重复添加
        if (mCenterTitle == null) {
            mCenterTitle = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.headbar_center_title, this, false);
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.gravity = Gravity.CENTER;
            lp.leftMargin = getDP(getContext(), 72);
            lp.rightMargin = getDP(getContext(), 72);
            mCenterTitle.setLayoutParams(lp);
            this.addView(mCenterTitle);
        }
        mCenterTitle.setText(title);

        // 防止重复设置右边的布局异常
        if (mRightParentLayout != null) {
            if (mRightParentLayout.getChildCount() > 0) {
                mRightParentLayout.removeAllViews();
            }
            if (this.indexOfChild(mRightParentLayout) != -1) {
                this.removeView(mRightParentLayout);
            }
        }
    }

    /**
     * 设置中间的标题，包含小标题
     */
    private void getCenterMultiTitle(CharSequence title, CharSequence smallTitle) {
//        判断是否为空，防止重复添加
        if (mCenterMultiTitle == null) {
            mCenterMultiTitle = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.headbar_center_multi_title, this, false);
            mCenterTitle = mCenterMultiTitle.findViewById(R.id.headbar_center_text);
            mCenterSmallTitle = mCenterMultiTitle.findViewById(R.id.headbar_center_small_text);

            LayoutParams lp = (LayoutParams) mCenterMultiTitle.getLayoutParams();
            lp.gravity = Gravity.CENTER;
            lp.leftMargin = getDP(getContext(), 72);
            lp.rightMargin = getDP(getContext(), 72);
            mCenterMultiTitle.setLayoutParams(lp);
            this.addView(mCenterMultiTitle);
        }
        mCenterTitle.setText(title);
        mCenterSmallTitle.setText(smallTitle);

        // 防止重复设置右边的布局异常
        if (mRightParentLayout != null) {
            if (mRightParentLayout.getChildCount() > 0) {
                mRightParentLayout.removeAllViews();
            }
            if (this.indexOfChild(mRightParentLayout) != -1) {
                this.removeView(mRightParentLayout);
            }
        }
    }

    /**
     * 设置右边要显示的TextView
     */
    private LinearLayout getRightTextLayout(String rightStr) {
        // 下面这个方法会初始化 mRightParentLayout
        addRightTextView(rightStr);
        checkRightParentLayout();
        return mRightParentLayout;
    }

//    /**
//     * 设置右边要显示的TextView
//     */
//    private LinearLayout getRightTextLayout(String[] rightStr) {
//        for (String aRightStr : rightStr) {
//            // 下面这个方法会初始化 mRightParentLayout
//            addRightTextView(aRightStr);
//        }
//        if (mRightParentLayout == null) {
//            Log.e("zwonb", "mRightParentLayout 为 null");
//        }
//        return mRightParentLayout;
//    }

    /**
     * 设置右边要显示的ImageView
     */
    private LinearLayout getRightImgLayout(@DrawableRes Integer[] imgResId) {
        for (Integer anImgResId : imgResId) {
            // 下面这个方法会初始化 mRightParentLayout
            addRightImageView(anImgResId);
        }
        checkRightParentLayout();
        return mRightParentLayout;
    }

    /**
     * 设置一个父布局来包裹右边的View
     */
    @NonNull
    private LinearLayout getRightParentLayout() {
        LinearLayout rightLayout = new LinearLayout(getContext());
        rightLayout.setGravity(Gravity.CENTER_VERTICAL);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.gravity = GravityCompat.END | Gravity.CENTER_VERTICAL;
        rightLayout.setLayoutParams(lp);
        return rightLayout;
    }

    /**
     * 设置右边的单个TextView
     */
    @NonNull
    private TextView getRightText(LinearLayout parent) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
        lp.rightMargin = (int) getRightMargin();
        TextView rightText = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.headbar_right_text, parent, false);
        rightText.setLayoutParams(lp);
        return rightText;
    }

    /**
     * 设置右边的单个ImageView
     */
    @NonNull
    private ImageView getRightImg(LinearLayout parent) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) getRightWidth(), (int) getRightWidth());
        lp.rightMargin = (int) getRightMargin();
        ImageView rightImg = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.headbar_right_img, parent, false);
        rightImg.setLayoutParams(lp);
        return rightImg;
    }

    /**
     * 设置右边ImageView的宽度
     */
    private float getRightWidth() {
        return getDP(getContext(), 48);
    }

    /**
     * 设置右边View的间距
     */
    private float getRightMargin() {
        return getDP(getContext(), 8);
    }

    /**
     * 检查右边的View的数量
     *
     * @return True表示需要添加右边的view, 否则就不需要添加
     */
    private boolean checkRightView(Object[] rightViews) {
        int length = rightViews.length;
        if (length > 2)
            throw new RuntimeException("Toolbar右边的view不能大于2个");
        else
            return length > 0;
    }

    /**
     * 检查包裹右边view的父布局
     */
    private void checkRightParentLayout() {
        if (mRightParentLayout == null) {
            throw new NullPointerException("包裹右边view的父布局为 null");
        }
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag != null && mOnRightClick != null) {
            mOnRightClick.onHeadBarClick((int) tag, v);
        }
    }

    public HeadBar setOnRightClick(OnHeadBarClickListener rightClick) {
        mOnRightClick = rightClick;
        return this;
    }

    public HeadBar setOnBackClick(OnClickListener onBackClick) {
        if (mBackLayout != null) {
            mBackLayout.getChildAt(0).setOnClickListener(onBackClick);
        }
        return this;
    }

    public static int getDP(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
