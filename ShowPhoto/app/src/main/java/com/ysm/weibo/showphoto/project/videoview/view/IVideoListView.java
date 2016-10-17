package com.ysm.weibo.showphoto.project.videoview.view;

import com.ysm.weibo.showphoto.bean.VideoContent;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.util.List;

/**
 * 视频列表V 层接口
 * Created by ysm on 2016/10/9 0009.
 */
public interface IVideoListView extends IBaseMvpView {

    void onVideoRefreshData(List<VideoContent> data);

    void onVideoLoadMore(List<VideoContent> data);
}
