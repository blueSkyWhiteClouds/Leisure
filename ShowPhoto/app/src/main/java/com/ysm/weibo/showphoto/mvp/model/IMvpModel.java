package com.ysm.weibo.showphoto.mvp.model;

import com.ysm.weibo.showphoto.bean.Comments;

import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * M 层接口
 * Created by ysm on 2016/10/4 0004.
 */
public interface IMvpModel {
    void saveStringData(String[] params, SaveListener<String> saveListener);

    void saveStringData(String objectId, SaveListener<String> saveListener);

    void updateObjectIdData(String objectId, UpdateListener callback);

    void updateData(String[] params, UpdateListener callback);

    void queryComments(String objectId, FindListener<Comments> callback);
}
