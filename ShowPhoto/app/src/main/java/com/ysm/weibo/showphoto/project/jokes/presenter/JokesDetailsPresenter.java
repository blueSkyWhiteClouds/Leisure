package com.ysm.weibo.showphoto.project.jokes.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.bean.Comments;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.jokes.model.JokesDetailsModel;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesDetailsView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesDetailsPresenter extends MvpBasePresenterImpl<IJokesDetailsView> {
    private JokesDetailsModel baseModel;
    public static final int COMMENT_TYPE = 1;
    public static final int PARISE_TYPE = 2;
    public static final int REFRESH_TYPE = 3;

    public JokesDetailsPresenter(Context context) {
        super(context);
        baseModel = new JokesDetailsModel();
    }

    /**
     * 发送评论
     *
     * @param objectId
     * @param comments
     */
    public void sendComments(final String objectId, String comments) {
        baseModel.saveStringData(new String[]{objectId, comments}, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                MyLogUtils.d(MyLogUtils.TAG, "objectId=" + objectId);
                if (e == null) {
                    getView().onMessageCallback("发送评论成功!");
                    getView().onSendCommentsSuccess();
                    updateCommentsSize(objectId);
                } else {
                    getView().onMessageCallback("发送评论失败!");
                    MyLogUtils.d(MyLogUtils.TAG, "发送评论失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 更新评论数量
     *
     * @param objectId
     */
    public void updateCommentsSize(final String objectId) {
        MyLogUtils.d(MyLogUtils.TAG, "update objectId=" + objectId);
        baseModel.updateData(new String[]{objectId, "commentsNum"}, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "update success");
                    QueryOfObjectJokes(objectId, COMMENT_TYPE);
                } else {
                    MyLogUtils.d(MyLogUtils.TAG, "更新评论失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 保存点赞记录
     *
     * @param objectId
     */
    public void savePraise(final String objectId) {
        baseModel.saveStringData(objectId, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "savePraise success");
                    updatePraise(objectId);
                } else {
                    MyLogUtils.d(MyLogUtils.TAG, "保存点赞记录失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 点赞
     *
     * @param objectId
     */
    public void updatePraise(final String objectId) {
        baseModel.updateData(new String[]{objectId, "praiseNum"}, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "praise update success");
                    QueryOfObjectJokes(objectId, PARISE_TYPE);
                } else {
                    MyLogUtils.d(MyLogUtils.TAG, "更新点赞失败失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询单条段子记录
     *
     * @param objectId
     */
    public void QueryOfObjectJokes(final String objectId, final int type) {
        baseModel.onQuerySingleData(objectId, new QueryListener<JokesContent>() {
            @Override
            public void done(JokesContent jokesContent, BmobException e) {
                if (e == null) {
                    if (type == REFRESH_TYPE) {
                        QueryComments(objectId);
                    }
                    getView().onQuerySingleJokes(jokesContent, type);
                } else {
                    MyLogUtils.d(MyLogUtils.TAG, "查询JokesContent内容失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询评论列表
     *
     * @param objectId
     */
    public void QueryComments(String objectId) {
        MyLogUtils.d(MyLogUtils.TAG, "comments objectId=" + objectId);
        baseModel.queryComments(objectId, new FindListener<Comments>() {
            @Override
            public void done(List<Comments> list, BmobException e) {
                MyLogUtils.d(MyLogUtils.TAG, "comments=" + list.toString());
                if (e == null) {
                    getView().onQueryCommentsSuccess(list);
                } else {
                    MyLogUtils.d(MyLogUtils.TAG, "查询评论列表失败 message=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
                }
            }
        });
    }
}
