package com.ysm.weibo.showphoto.project.jokes.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBaseRefreshPresenterImpl;
import com.ysm.weibo.showphoto.project.jokes.model.JokesContentModel;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesView;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 段子P层
 * Created by ysm on 2016/10/11 0011.
 */
public class JokesPresenter extends MvpBaseRefreshPresenterImpl<IJokesView, JokesContent> {
    private int position;

    public JokesPresenter(Context context) {
        super(context);
        baseModel = new JokesContentModel();
    }

    @Override
    protected void onRefreshData(List<JokesContent> data) {
        getView().onJokesContentRefresh(data);
    }

    @Override
    protected void onLoadMoreData(List<JokesContent> data) {
        super.onLoadMoreData(data);
        getView().onJokesContentLoadMore(data);
    }

    @Override
    protected void onLoadMoreIsNo() {
        super.onLoadMoreIsNo();
        getView().onMessageCallback("已经加载完了!");
    }

    @Override
    protected void onRefreshFailed(BmobException e) {
        getView().onMessageCallback("加载图片失败!");
        MyLogUtils.d("TAG", "JokesPresenter加载失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
    }

    public void getJokesContentResult(final boolean isRefresh) {
        judgeRefresh(isRefresh);
        baseModel.loadQueryAllData(new String[]{String.valueOf(count), String.valueOf(skip)}, new FindListener<JokesContent>() {
            @Override
            public void done(List<JokesContent> list, BmobException e) {
                refreshType(isRefresh, list, e);
            }
        });
    }

    /**
     * 保存点赞记录
     *
     * @param objectId
     * @param position
     */
    public void savePraise(final String objectId, int position) {
        this.position = position;
        baseModel.saveStringData(objectId, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "savePraise success");
                    updatePraise(objectId);
                } else {
                    MyLogUtils.d("TAG", "JokesPresenter 保存点赞记录 加载失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
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
        baseModel.updateObjectIdData(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "praise update success");
                    querySingleJokesContent(objectId);
                } else {
                    MyLogUtils.d("TAG", "JokesPresenter 点赞 加载失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 查询单条记录
     *
     * @param objectId
     */
    public void querySingleJokesContent(String objectId) {
        baseModel.onQuerySingleData(objectId, new QueryListener<JokesContent>() {
            @Override
            public void done(JokesContent jokesContent, BmobException e) {
                MyLogUtils.d(MyLogUtils.TAG, "jokesContent=" + jokesContent.toString());
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "querySingle JokesContent success");
                    getView().onPraiseUpdateCallback(position, jokesContent);
                } else {
                    MyLogUtils.d("TAG", "JokesPresenter 查询单条记录 加载失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
                }
            }
        });
    }
}
