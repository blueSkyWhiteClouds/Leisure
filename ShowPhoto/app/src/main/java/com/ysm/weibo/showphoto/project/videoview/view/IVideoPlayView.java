package com.ysm.weibo.showphoto.project.videoview.view;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

/**
 * 视频播放 V 视图层接口
 * Created by ysm on 2016/10/9 0009.
 */
public interface IVideoPlayView extends IBaseMvpView {

    void onControllerShow();

    void onControllerHide();

    void onPrepared();

    void onShowProgress();

    void onHideProgress();
}
