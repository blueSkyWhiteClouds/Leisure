package com.ysm.weibo.showphoto.project.jokes.view;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import cn.bmob.v3.exception.BmobException;

/**
 * 发布段子 V 视图层
 * Created by ysm on 2016/10/12 0012.
 */
public interface IJokesReleaseView extends IBaseMvpView {
    void onReleaseSuccess();

    void onFailed(BmobException e);
}
