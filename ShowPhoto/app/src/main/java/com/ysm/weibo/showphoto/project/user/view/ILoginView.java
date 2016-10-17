package com.ysm.weibo.showphoto.project.user.view;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import cn.bmob.v3.exception.BmobException;

/**
 * 登录 V 视图层接口
 * Created by ysm on 2016/10/11 0011.
 */
public interface ILoginView extends IBaseMvpView {
    void showDialog();

    void hideDialog();

    void onLoginSuccess();

    void onFailed(BmobException e);
}
