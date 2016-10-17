package com.ysm.weibo.showphoto.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;

/**
 * titlebar
 * Created by ysm on 2016/10/13 0013.
 */
public class TitleBarLayout extends LinearLayout implements View.OnClickListener {
    private RelativeLayout mTitleBarRl;
    private ImageView mBackIv;
    private TextView mTitleTv;
    private TextView mRightTv_1;
    private ImageView mRightImage_1;
    public FrameLayout mContentFl;

    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TitleBarLayout(Context context) {
        super(context);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.title_bar, null);
        if (view == null) {
            return;
        }
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mTitleBarRl = (RelativeLayout) view.findViewById(R.id.title_bar_rl);
        mBackIv = (ImageView) view.findViewById(R.id.title_bar_back_iv);
        mTitleTv = (TextView) view.findViewById(R.id.title_bar_tv);
        mRightTv_1 = (TextView) view.findViewById(R.id.title_bar_right_tv_1);
        mRightImage_1 = (ImageView) view.findViewById(R.id.title_bar_right_iv_1);
        mContentFl = (FrameLayout) view.findViewById(R.id.content_fl);
        mBackIv.setOnClickListener(this);
        mRightImage_1.setOnClickListener(this);
        addView(view);
    }

    /**
     * 设置内容布局
     *
     * @param view
     */
    public void setContentView(View view) {
        if (mContentFl.getChildCount() > 0) {
            mContentFl.removeAllViews();
        }
        mContentFl.addView(view);
    }

    public void setContentView(int layout) {
        if (mContentFl.getChildCount() > 0) {
            mContentFl.removeAllViews();
        }
        View view = View.inflate(getContext(), layout, null);

        mContentFl.addView(view);
    }

    /**
     * 隐藏titleBar
     */
    public void hideTitle() {
        mTitleBarRl.setVisibility(GONE);
    }

    /**
     * 设置返回图片
     *
     * @param resId
     */
    public void setBackImageResources(int resId) {
        mBackIv.setVisibility(VISIBLE);
        mBackIv.setImageResource(resId);
    }

    public void hideBackImage() {
        mBackIv.setVisibility(GONE);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitleText(String title) {
        mTitleTv.setVisibility(VISIBLE);
        mTitleTv.setText(title);
    }

    /**
     * 设置右边文本
     *
     * @param rightText
     */
    public void setRightText_1(String rightText) {
        mRightTv_1.setVisibility(VISIBLE);
        mRightTv_1.setText(rightText);
    }

    /**
     * 设置右边图片
     *
     * @param resId
     */
    public void setRightImage_1(int resId) {
        mRightImage_1.setVisibility(VISIBLE);
        mRightImage_1.setBackgroundResource(resId);
    }

    /**
     * 右边图片点击事件
     *
     * @param listener
     */
    public void setRightImageOnClickListener(OnClickListener listener) {
        if (listener != null) {
            mRightImage_1.setOnClickListener(listener);
        }
    }

    public void setRightTextOnClickListener(OnClickListener listener) {
        if (listener != null) {
            mRightTv_1.setOnClickListener(listener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_back_iv:
                if (activity != null) {
                    activity.onBackPressed();
                }
                break;
        }
    }
}
