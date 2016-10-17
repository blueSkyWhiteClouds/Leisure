package com.ysm.weibo.showphoto.mvp.view.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ysm.weibo.showphoto.mvp.presenter.IMvpPresenter;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public abstract class MvpLinearLayout<V extends IBaseMvpView, P extends IMvpPresenter<V>> extends LinearLayout {
    //控制器
    private P presenter;
    //视图
    private V view;

    public P getPresenter() {
        return presenter;
    }

    public MvpLinearLayout(Context context) {
        super(context);
        init();
    }

    public MvpLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MvpLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (view == null) {
            view = createView();
        }
        if (this.presenter != null && this.view != null) {
            this.presenter.attachView(view);
        }
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.presenter != null) {
            this.presenter.detachView();
            this.presenter = null;
        }
    }
}
