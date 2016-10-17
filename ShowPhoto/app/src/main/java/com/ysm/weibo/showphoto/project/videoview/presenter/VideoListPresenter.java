package com.ysm.weibo.showphoto.project.videoview.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.VideoContent;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBaseRefreshPresenterImpl;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesView;
import com.ysm.weibo.showphoto.project.videoview.model.VideoListModel;
import com.ysm.weibo.showphoto.project.videoview.view.IVideoListView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 视频列表 P层
 * Created by ysm on 2016/10/9 0009.
 */
public class VideoListPresenter extends MvpBaseRefreshPresenterImpl<IVideoListView, VideoContent> {
    public VideoListPresenter(Context context) {
        super(context);
        baseModel = new VideoListModel();
    }

    @Override
    protected void onRefreshData(List<VideoContent> data) {
        getView().onVideoRefreshData(data);
    }

    @Override
    protected void onLoadMoreData(List<VideoContent> data) {
        super.onLoadMoreData(data);
        getView().onVideoLoadMore(data);
    }

    @Override
    protected void onLoadMoreIsNo() {
        super.onLoadMoreIsNo();
        getView().onMessageCallback("已经没有更多了");
    }

    @Override
    protected void onRefreshFailed(BmobException e) {
        getView().onMessageCallback("获取视频信息失败");
        MyLogUtils.d(MyLogUtils.TAG, "Exception e=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
    }

    public void videoDisposeResult(final boolean isRefresh) {
        judgeRefresh(isRefresh);
        baseModel.loadQueryAllData(new String[]{String.valueOf(count), String.valueOf(skip)}, new FindListener<VideoContent>() {
            @Override
            public void done(List<VideoContent> list, BmobException e) {
                refreshType(isRefresh, list, e);
            }
        });
    }
}
