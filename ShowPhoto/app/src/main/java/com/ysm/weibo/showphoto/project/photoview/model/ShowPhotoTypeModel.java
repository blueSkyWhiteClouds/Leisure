package com.ysm.weibo.showphoto.project.photoview.model;

import com.ysm.weibo.showphoto.bean.ImageType;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 照片展示 M
 * Created by ysm on 2016/10/5 0005.
 */
public class ShowPhotoTypeModel extends MvpBaseModel<ImageType>{

    @Override
    public void loadQueryAllData(FindListener<ImageType> callback) {
        super.loadQueryAllData(callback);
        BmobQuery<ImageType> queryAll = new BmobQuery<>();
        queryAll.findObjects(callback);
    }
}

