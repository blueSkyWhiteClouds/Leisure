package com.ysm.weibo.showphoto.project.jokes.model;

import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.Praise;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 段子内容
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesContentModel extends MvpBaseModel<JokesContent> {

    @Override
    public void loadQueryAllData(String[] params, FindListener<JokesContent> callback) {
        super.loadQueryAllData(params, callback);
        BmobQuery<JokesContent> queryAll = new BmobQuery<>();
        queryAll.setLimit(Integer.valueOf(params[0]));
        queryAll.setSkip(Integer.valueOf(params[1]));
        queryAll.order("-createdAt");
        queryAll.include("user");
        queryAll.findObjects(callback);
    }

    @Override
    public void saveStringData(String objectId, SaveListener<String> saveListener) {
        super.saveStringData(objectId, saveListener);
        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(objectId);
        Praise praise = new Praise();
        praise.setUserInfo(userInfo);
        praise.setJokesContent(jokesContent);
        praise.save(saveListener);
    }

    @Override
    public void updateObjectIdData(String objectId, UpdateListener callback) {
        super.updateObjectIdData(objectId, callback);
        //更新点赞数量
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(objectId);
        jokesContent.increment("praiseNum");
        jokesContent.update(callback);
    }

    @Override
    public void onQuerySingleData(String object, QueryListener<JokesContent> callback) {
        super.onQuerySingleData(object, callback);
        BmobQuery<JokesContent> query=new BmobQuery<>();
        query.include("user");
        query.getObject(object,callback);
    }
}
