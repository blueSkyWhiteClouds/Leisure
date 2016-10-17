package com.ysm.weibo.showphoto.project.jokes;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.jokes.presenter.JokesReleasePresenter;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesReleaseView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

import cn.bmob.v3.exception.BmobException;

/**
 * 发布段子
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesReleaseActivity extends MvpBaseActivity<IJokesReleaseView, JokesReleasePresenter> implements IJokesReleaseView, View.OnClickListener {
    private EditText mJokesContentEt;
    private Button mReleaseBtn;
    private Button mCancelBtn;
    private TitleBarLayout mBarLayout;

    @Override
    protected JokesReleasePresenter createPresenter() {
        return new JokesReleasePresenter(this);
    }

    @Override
    protected IJokesReleaseView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_release_jokes;
    }

    @Override
    protected void findViews() {
        mBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mJokesContentEt = (EditText) findViewById(R.id.jokes_content_et);
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        mBarLayout.setRightTextOnClickListener(this);
    }

    @Override
    protected void setViews() {
        super.setViews();
        mBarLayout.setTitleText("发布段子");
        mBarLayout.setRightText_1("发布");
        mBarLayout.setActivity(this);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onClick(View v) {
        String content = mJokesContentEt.getText().toString().trim();
        getPresenter().getReleaseResult(content);
    }

    @Override
    public void onReleaseSuccess() {
        this.onBackPressed();
    }

    @Override
    public void onFailed(BmobException e) {
        MyLogUtils.d(MyLogUtils.TAG, "jokesRelease msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
    }
}
