package com.ysm.weibo.showphoto.project.videoview.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.videoview.view.IVideoPlayView;
import com.ysm.weibo.showphoto.widget.VideoController;

import io.vov.vitamio.widget.VideoView;

/**
 * 视频播放 P层
 * Created by ysm on 2016/10/9 0009.
 */
public class VideoPlayPresenter extends MvpBasePresenterImpl<IVideoPlayView> {
    public VideoPlayPresenter(Context context) {
        super(context);
    }

    /**
     * 控制 Controller显示隐藏
     *
     * @param mVideoController
     */
    public void onVideoViewClick(VideoController mVideoController) {
        if (mVideoController.isShowing()) {
            getView().onControllerHide();
        } else {
            getView().onControllerShow();
        }
    }

    /**
     * 消息
     *
     * @param message
     */
    public void getMessageResult(String message) {
        getView().onMessageCallback(message);
    }

    /**
     * 视频准备完成
     */
    public void videoPreparedSuccess() {
        getView().onHideProgress();
        getView().onPrepared();
    }

    /**
     * 正在缓冲
     *
     * @param mVideoView
     */
    public void getIsBuffer(VideoView mVideoView) {
        if (mVideoView != null) {
            if (mVideoView.isPlaying()) {
                mVideoView.pause();
            }
        }
        getView().onShowProgress();
    }

    /**
     * 缓冲完成
     *
     * @param mVideoView
     */
    public void getBufferSuccess(VideoView mVideoView) {
        if (mVideoView != null) {
            mVideoView.start();
        }
    }

    /**
     * 视频播放出错
     */
    public void videoError() {
        getView().onHideProgress();
    }
}
