package com.ysm.weibo.showphoto.mvp.presenter;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

/**
 * P层 接口
 * Created by ysm on 2016/10/4 0004.
 */
public interface IMvpPresenter<V extends IBaseMvpView> {
    /**
     * 绑定视图
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 解除视图绑定
     */
    void detachView();
}
