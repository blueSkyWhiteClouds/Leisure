package com.ysm.weibo.showphoto.widget;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.utils.ViewUtil;

import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by ysm on 2016/10/7 0007.
 */
public class VideoController implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnKeyListener {
    private static final int S_DEFAULT_TIMEOUT = 5000;
    private static final int FADE_OUT = 1;
    private static final int SHOW_PROGRESS = 2;
    private Activity mContext;
    private VideoView mVideoView;
    private PopupWindow mPopupWindow;
    private View mAnchor;
    private View mContentView;
    private String title;
    private ImageView mBackIv;//返回按钮
    private TextView mTitleTv;//标题
    private SeekBar mSeekBar;//进度
    private ImageView mPlayAndPauseIv;//暂停播放按钮
    private TextView mCurrentTimeTv;//当前播放时间
    private TextView mTotalTimeTv;//播放时长
    private ImageView mFullIv;//全屏模式按钮

    private long mDuration;//播放总时长
    private long mCurrentTime;//当前时间
    private boolean mShowing;//controller是否显示
    private boolean mDragging;//进度条是否被拖拽

    private OnVideoClickListener onVideoClickListener;

    public void setOnVideoClickListener(OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = onVideoClickListener;
    }

    public VideoController(View mAnchor, VideoView mVideoView, Activity activity, String title) {
        this.mContext = activity;
        this.mAnchor = mAnchor;
        this.mVideoView = mVideoView;
        this.title = title;
    }

    public void setAnchor(View mAnchor) {
        this.mAnchor = mAnchor;
    }

    public void setVideoView(VideoView mVideoView) {
        this.mVideoView = mVideoView;
    }

    public void setViews(View mAnchor, VideoView mVideoView, Activity activity, String title) {
        this.mAnchor = mAnchor;
        this.mVideoView = mVideoView;
        this.title = title;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FADE_OUT:
                    // 隐藏布局
                    hide();
                    break;
                case SHOW_PROGRESS:
                    // 获取进度
                    long pos = setProgress();
                    if (!mDragging && mShowing) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 1000 - (pos % 1000));
                    }
                    break;
            }
        }
    };

    /**
     * 设置进度条和时间
     *
     * @return
     */
    private long setProgress() {
        if (mVideoView == null || mDragging)
            return 0;

        long position = mVideoView.getCurrentPosition();
        long duration = mVideoView.getDuration();
        if (mSeekBar != null) {
            if (duration > 0) {
                long pos = 1000L * position / duration;
                mSeekBar.setProgress((int) pos);
            }
            int percent = mVideoView.getBufferPercentage();
            mSeekBar.setSecondaryProgress(percent * 10);
        }

        mDuration = duration;

        if (mTotalTimeTv != null)
            mTotalTimeTv.setText(StringUtils.generateTime(mDuration));
        if (mCurrentTimeTv != null)
            mCurrentTimeTv.setText(StringUtils.generateTime(position));
        return position;
    }

    public void hide() {
        if (mPopupWindow != null && isShowing() && mAnchor.getWindowToken() != null) {
            handler.removeMessages(SHOW_PROGRESS);
            handler.removeMessages(FADE_OUT);
            mPopupWindow.dismiss();
            mShowing = false;
        }
    }

    public void show() {
        if (mPopupWindow != null && mShowing && mContentView != null) {
            return;
        } else if (mPopupWindow != null && !mShowing) {
            showPopupWindow();
            return;
        }


        initPopupWindowView();
        showPopupWindow();

    }

    private void showPopupWindow() {
        if (!mShowing && mAnchor != null && mAnchor.getWindowToken() != null) {
            mShowing = true;
            mPopupWindow.showAsDropDown(mAnchor,
                    (mAnchor.getMeasuredWidth() - mContentView.getMeasuredWidth()) / 2,
                    -mAnchor.getMeasuredHeight());
        }

        updatePausePlay();

    }

    private void initPopupWindowView() {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.layout_video_controller, null);
        final RelativeLayout topView = (RelativeLayout) mContentView.findViewById(R.id.controller_top_rl);
        final LinearLayout bottomView = (LinearLayout) mContentView.findViewById(R.id.controller_bottom_ll);
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getY() > topView.getMeasuredHeight() || event.getY() < bottomView.getMeasuredHeight()) {
                        hide();
                        return true;
                    }
                }
                return false;
            }
        });
        mContentView.setFocusableInTouchMode(true);
        mContentView.setOnKeyListener(this);

        mBackIv = (ImageView) mContentView.findViewById(R.id.controller_back);
        mTitleTv = (TextView) mContentView.findViewById(R.id.controller_title);
        mSeekBar = (SeekBar) mContentView.findViewById(R.id.controller_seek_bar);
        mPlayAndPauseIv = (ImageView) mContentView.findViewById(R.id.controller_play_iv);
        mCurrentTimeTv = (TextView) mContentView.findViewById(R.id.controller_current_time_tv);
        mTotalTimeTv = (TextView) mContentView.findViewById(R.id.controller_total_time_tv);
        mFullIv = (ImageView) mContentView.findViewById(R.id.controller_full_iv);
        mTitleTv.setText(this.title);
        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(null);
        mPopupWindow.setTouchable(true);

        mSeekBar.setOnSeekBarChangeListener(this);
        mBackIv.setOnClickListener(this);
        mPlayAndPauseIv.setOnClickListener(this);
        mFullIv.setOnClickListener(this);
    }

    private void updatePausePlay() {
        if (mPlayAndPauseIv == null) {
            return;
        }
        if (mVideoView.isPlaying()) {
            mPlayAndPauseIv.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_play));
        } else {
            mPlayAndPauseIv.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.icon_pause));
        }
        // 设置进度条
        handler.sendEmptyMessage(SHOW_PROGRESS);
        // 3秒后关闭布局
        handler.removeMessages(FADE_OUT);
        handler.sendMessageDelayed(handler.obtainMessage(FADE_OUT), S_DEFAULT_TIMEOUT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.controller_back:
                if (onVideoClickListener != null) {
                    onVideoClickListener.onBackClick();
                }
                break;
            case R.id.controller_play_iv:
                if (!v.isSelected()) {
                    // 正在paly，点击暂停的情况
                    mVideoView.pause();
                } else {
                    if (mVideoView.getCurrentPosition() == mVideoView.getDuration()) {
                        // 播放完了，重播
                        mVideoView.seekTo(0);
                    } else {
                        mVideoView.start();
                    }
                }
                v.setSelected(!v.isSelected());
                updatePausePlay();
                break;
            case R.id.controller_full_iv:
                ViewUtil.rotateScreen(mContext);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //判断是否是用户进行拖拽， false return
        if (!fromUser) {
            return;
        }
        long newposition = (mDuration * progress) / 1000;
        String time = StringUtils.generateTime(newposition);
        mVideoView.seekTo(newposition);
        if (mCurrentTimeTv != null) {
            mCurrentTimeTv.setText(time);
        }
        handler.removeMessages(FADE_OUT);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mDragging = true;
        show();
        handler.removeMessages(SHOW_PROGRESS);
//        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,
//                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mVideoView.seekTo((mDuration * mSeekBar.getProgress()) / 1000);
        show();
        handler.removeMessages(SHOW_PROGRESS);
//        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,
//                AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        mDragging = false;
        handler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);

        handler.sendMessageDelayed(handler.obtainMessage(FADE_OUT), S_DEFAULT_TIMEOUT);
    }

    public boolean isShowing() {
        return mShowing;
    }

    public void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        mPopupWindow.dismiss();
        mPopupWindow = null;
        mContext = null;
        mAnchor = null;
        mVideoView = null;
        mContentView = null;
        handler = null;
//        WindowManager.removeView(mPopupWindow);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isShowing()) {
            hide();
        }
        return true;
    }

    public interface OnVideoClickListener {
        void onBackClick();
    }
}
