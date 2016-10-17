package com.ysm.weibo.showphoto.project.videoview.model;

import com.ysm.weibo.showphoto.bean.VideoContent;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 视频列表 M 数据层
 * Created by ysm on 2016/10/9 0009.
 */
public class VideoListModel extends MvpBaseModel<VideoContent> {
    @Override
    public void loadQueryAllData(String[] params, FindListener<VideoContent> callback) {
        super.loadQueryAllData(params, callback);
        BmobQuery<VideoContent> queryAll = new BmobQuery<>();
        queryAll.setLimit(Integer.valueOf(params[0]));
        queryAll.setSkip(Integer.valueOf(params[1]));
        queryAll.findObjects(callback);
    }
}
