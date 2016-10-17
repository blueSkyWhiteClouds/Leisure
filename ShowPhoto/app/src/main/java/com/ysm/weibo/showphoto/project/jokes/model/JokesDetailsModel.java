package com.ysm.weibo.showphoto.project.jokes.model;

import com.ysm.weibo.showphoto.bean.Comments;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.Praise;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 评论M数据层
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesDetailsModel extends MvpBaseModel<JokesContent> {

    @Override
    public void saveStringData(String[] params, SaveListener<String> saveListener) {
        super.saveStringData(params, saveListener);
        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(params[0]);

        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(userInfo);
        jokesContent.setLikes(bmobRelation);
        MyLogUtils.d(MyLogUtils.TAG, "bmobRelation=" + bmobRelation.getObjects().toArray());
        MyLogUtils.d(MyLogUtils.TAG, "bmobRelation size=" + bmobRelation.getObjects().size());
        MyLogUtils.d(MyLogUtils.TAG, "bmobRelation item=" + bmobRelation.getObjects().get(0).getObjectId());

        Comments comments = new Comments();
        comments.setContent(params[1]);
        comments.setJokesContent(jokesContent);
        comments.setUserInfo(userInfo);
        comments.save(saveListener);
    }

    @Override
    public void updateData(String[] params, UpdateListener callback) {
        super.updateData(params, callback);
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(params[0]);
        jokesContent.increment(params[1]);
        jokesContent.update(callback);
    }

    /**
     * 保存点赞记录
     *
     * @param objectId
     * @param saveListener
     */
    @Override
    public void saveStringData(String objectId, SaveListener<String> saveListener) {
        UserInfo userInfo = BmobUser.getCurrentUser(UserInfo.class);
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(objectId);
        Praise praise = new Praise();
        praise.setUserInfo(userInfo);
        praise.setJokesContent(jokesContent);
        praise.save(saveListener);
    }

    @Override
    public void onQuerySingleData(String object, QueryListener<JokesContent> callback) {
        super.onQuerySingleData(object, callback);
        BmobQuery<JokesContent> query = new BmobQuery<>();
        query.include("user");
        query.getObject(object, callback);
    }

    /**
     * 查询评论
     *
     * @param objectId
     * @param callback
     */
    @Override
    public void queryComments(String objectId, FindListener<Comments> callback) {
        super.queryComments(objectId, callback);
        BmobQuery<Comments> queryAll = new BmobQuery<>();
        JokesContent jokesContent = new JokesContent();
        jokesContent.setObjectId(objectId);
        queryAll.addWhereEqualTo("jokesContent", new BmobPointer(jokesContent));
        queryAll.order("-createdAt");
        queryAll.include("userInfo,jokesContent.user");
        queryAll.findObjects(callback);
    }
}
