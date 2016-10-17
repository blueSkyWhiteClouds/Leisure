package com.ysm.weibo.showphoto.mvp.model.impl;

import com.ysm.weibo.showphoto.bean.Comments;
import com.ysm.weibo.showphoto.mvp.model.IMvpModel;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * M 数据层 基类
 * Created by ysm on 2016/10/5 0005.
 */
public class MvpBaseModel<T> implements IMvpModel {

    public void loadQueryAllData(FindListener<T> callback) {

    }

    public void loadQueryAllData(String[] params, FindListener<T> callback) {
    }

    public void saveData(String[] params, SaveListener<T> saveListener) {
    }

    public void onQuerySingleData(String object, QueryListener<T> callback) {

    }

    @Override
    public void saveStringData(String[] params, SaveListener<String> saveListener) {

    }

    @Override
    public void saveStringData(String objectId, SaveListener<String> saveListener) {

    }

    @Override
    public void updateObjectIdData(String objectId, UpdateListener callback) {

    }

    @Override
    public void updateData(String[] params, UpdateListener callback) {

    }

    @Override
    public void queryComments(String objectId, FindListener<Comments> callback) {

    }
}
