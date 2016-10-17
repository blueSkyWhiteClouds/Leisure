package com.ysm.weibo.showphoto.project.videoview;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.videoview.presenter.VideoPlayPresenter;
import com.ysm.weibo.showphoto.project.videoview.view.IVideoPlayView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.utils.ViewUtil;
import com.ysm.weibo.showphoto.widget.VideoController;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by ysm on 2016/10/9 0009.
 */
public class VideoPlayActivity extends MvpBaseActivity<IVideoPlayView, VideoPlayPresenter> implements IVideoPlayView,
        VideoController.OnVideoClickListener, MediaPlayer.OnInfoListener, View.OnTouchListener {
    private RelativeLayout mAnchorView;
    private VideoView mVideoView;
    private ProgressBar mProgressBar;
    private VideoController mVideoController;
    private String videoUrl;
    private String videoName;
    private String videoContent;

    @Override
    protected VideoPlayPresenter createPresenter() {
        return new VideoPlayPresenter(this);
    }

    @Override
    protected IVideoPlayView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_paly_video;
    }

    @Override
    protected void findViews() {
        mVideoView = (VideoView) findViewById(R.id.video_play_view);
        mAnchorView = (RelativeLayout) findViewById(R.id.video_play_rl);
        mProgressBar = (ProgressBar) findViewById(R.id.video_progress);
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            videoUrl = bundle.getString(VideoListFragment.VIDEO_URL);
            videoName = bundle.getString(VideoListFragment.VIDEO_NAME);
            videoContent = bundle.getString(VideoListFragment.VIDEO_CONTENT);
        }
        ViewUtil.setFullScreen(this);
    }

    @Override
    protected void setViews() {
        super.setViews();
        if (Vitamio.isInitialized(getApplicationContext())) {
            mVideoView.setVideoPath(videoUrl);
            mVideoController = new VideoController(mAnchorView, mVideoView, this, videoName);
            mVideoController.setOnVideoClickListener(this);

            mVideoView.requestFocus();
            mVideoController.setOnVideoClickListener(this);
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnTouchListener(this);
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    getPresenter().videoPreparedSuccess();
                }
            });
            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    getPresenter().videoError();
                    getPresenter().getMessageResult("视频播放出错了!!!");
                    return true;
                }
            });
        } else {
            getPresenter().getMessageResult("播放器还未初始化完!!!");
        }
        mVideoView.start();
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onControllerShow() {
        mVideoController.show();
    }

    @Override
    public void onControllerHide() {
        mVideoController.hide();
    }

    @Override
    public void onPrepared() {
        mVideoController.show();
    }

    @Override
    public void onShowProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (extra) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //正在缓存，暂停播放
                getPresenter().getIsBuffer(mVideoView);
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                // 缓存完成，继续播放
                getPresenter().getBufferSuccess(mVideoView);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                // 显示 下载速度
                break;
        }
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getPresenter().onVideoViewClick(mVideoController);
                break;
        }
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 保持屏幕比例正确
        mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        mVideoController.hide();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !mVideoController.isShowing()) {
            if (ViewUtil.isLandscape(this)) {
                ViewUtil.setPortrati(this);
            } else {
                this.onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLogUtils.d(MyLogUtils.TAG, "onDestroy");
        if (mVideoController != null) {
            mVideoController.onDestroy();
        }
    }
}
