package com.ysm.weibo.showphoto.mvp.view.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ysm.weibo.showphoto.mvp.presenter.IMvpPresenter;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

/**
 * Fragment 基类
 * Created by ysm on 2016/10/4 0004.
 */
public abstract class MvpBaseFragment<V extends IBaseMvpView, P extends IMvpPresenter<V>> extends Fragment {
    public Activity mActivity;
    private P presenter;
    private V view;
    private TitleBarLayout barLayout;

    public TitleBarLayout getBarLayout() {
        return barLayout;
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        barLayout = new TitleBarLayout(mActivity);
        barLayout.setContentView(initView());
        return barLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.presenter == null) {
            this.presenter = createPresenter();
        }
        if (this.view == null) {
            this.view = createView();
        }
        if (this.presenter != null && this.view != null) {
            this.presenter.attachView(view);
        }

        initData();
        initListener();
    }

    protected abstract View initView();

    /**
     * 创建P层， 调合器
     *
     * @return
     */
    public abstract P createPresenter();

    /**
     * 创建View层  视图
     *
     * @return
     */
    public abstract V createView();

    protected void initData() {
    }

    protected void initListener() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.detachView();
            this.presenter = null;
        }
    }
}
