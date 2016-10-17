package com.ysm.weibo.showphoto.mvp.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ysm.weibo.showphoto.mvp.presenter.IMvpPresenter;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

/**
 * Activity 基类
 * Created by Administrator on 2016/10/4 0004.
 */
public abstract class MvpBaseActivity<V extends IBaseMvpView, P extends IMvpPresenter<V>> extends AppCompatActivity {
    //控制器
    private P presenter;
    //视图
    private V view;

    public P getPresenter() {
        return presenter;
    }

    public V getView() {
        return view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (this.presenter != null && this.view != null) {
            this.presenter.attachView(view);
        }
        findViews();
        beforeSetViews();
        setViews();
    }

    /**
     * 创建P层， 调合器
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 创建View层  视图
     *
     * @return
     */
    protected abstract V createView();

    /**
     * 如果没有布局，那么就返回0
     *
     * @return activity的布局文件
     */
    protected abstract int getContentViewId();

    /**
     * 找到所有的views
     */
    protected abstract void findViews();

    /**
     * 在这里初始化设置view的各种资源，比如适配器或各种变量
     */
    protected void beforeSetViews() {
    }

    /**
     * 设置所有的view
     */
    protected void setViews() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.presenter != null) {
            this.presenter.detachView();
            this.presenter = null;
        }
    }
}
