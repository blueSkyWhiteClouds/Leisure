package com.ysm.weibo.showphoto.project.user.view;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by ysm on 2016/10/11 0011.
 */
public interface IRegisterView extends IBaseMvpView {
    void showDialog();

    void hideDialog();

    void onRegisterSuccess();

    void onFailed(BmobException e);
}
