package com.ysm.weibo.showphoto.project.photoview.model;

import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * 图片列表数据
 * Created by ysm on 2016/10/5 0005.
 */
public class PhotoListModel extends MvpBaseModel<ImageContent> {

    @Override
    public void loadQueryAllData(String[] param, FindListener<ImageContent> callback) {
        super.loadQueryAllData(param, callback);
        BmobQuery<ImageContent> query = new BmobQuery<>();
        query.addWhereEqualTo("typeName", param[0]);
        query.setLimit(Integer.valueOf(param[1]));
        query.setSkip(Integer.valueOf(param[2]));
        MyLogUtils.d(MyLogUtils.TAG, "limit=" + Integer.valueOf(param[1]) + ", skip=" + Integer.valueOf(param[2]));
        query.findObjects(callback);
    }
}
