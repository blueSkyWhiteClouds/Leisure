package com.ysm.weibo.showphoto;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.widget.VideoController;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by Administrator on 2016/10/7 0007.
 */
public class VideoViewActivity extends Activity implements View.OnTouchListener, VideoController.OnVideoClickListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener {
    String urls = "http://baobab.wdjcdn.com/1461125455181_6724_854x480.mp4";
    private VideoView mVideoView;
    private VideoController mVideoController;
    private RelativeLayout mRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_test);
        MyLogUtils.d(MyLogUtils.TAG, "onCreate");
        mVideoView = (VideoView) findViewById(R.id.video);
        mRl = (RelativeLayout) findViewById(R.id.rl);
        if (Vitamio.isInitialized(getApplicationContext())) {
            mVideoView.setVideoPath(urls);
//        mVideoView.setMediaController(new MediaController(this));
            mVideoController = new VideoController(mRl, mVideoView, this, "Video");
            mVideoController.setOnVideoClickListener(this);
            mVideoView.setOnInfoListener(this);
            mVideoView.setOnTouchListener(this);
            mVideoView.requestFocus();
            mVideoView.setOnBufferingUpdateListener(this);
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
//                Presente.getVideoSuccess();
                    mVideoController.show();

                }
            });
            mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
//                hideProgress();

//                Presente.getVideoFailer("视频播放出错了╮(╯Д╰)╭");
                    return true;
                }
            });
            mVideoView.start();
//            mVideoController.show();
//        mVideoView.setVideoLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyLogUtils.d(MyLogUtils.TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        MyLogUtils.d(MyLogUtils.TAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyLogUtils.d(MyLogUtils.TAG, "onRestart");

    }

    @Override
    protected void onPause() {
        super.onPause();
        MyLogUtils.d(MyLogUtils.TAG, "onPause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        MyLogUtils.d(MyLogUtils.TAG, "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLogUtils.d(MyLogUtils.TAG, "onDestroy");

        if (mVideoController != null) {
            mVideoController.onDestroy();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mVideoController.isShowing()) {
                    mVideoController.hide();
                } else {
                    mVideoController.show();
                }
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
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (extra) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                //正在缓存，暂停播放

                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                // 缓存完成，继续播放

                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                // 显示 下载速度
                break;
        }
        return true;
    }
}
