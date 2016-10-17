package com.ysm.weibo.showphoto.mvp.presenter.impl;

import android.content.Context;

import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public abstract class MvpBaseRefreshPresenterImpl<T extends IBaseMvpView, M extends Serializable> extends MvpBasePresenterImpl<T> {
    private int page;//页码
    protected int count;//每页加载数量
    protected int skip;//忽略的量
    protected MvpBaseModel<M> baseModel;

    public MvpBaseRefreshPresenterImpl(Context context) {
        super(context);
        page = 0;
        count = 10;
    }

    public void judgeRefresh(boolean isRefresh) {
        if (isRefresh) {
            page = 0;
            skip = page;
        } else {
            // 跳过之前页数并去掉重复数据
            skip = page * count + 1;
            MyLogUtils.d(MyLogUtils.TAG, "skip=" + skip);
        }
    }

    /**
     * 判断刷新还是加载更多
     *
     * @param isRefresh
     * @param list
     * @param e
     */
    public void refreshType(boolean isRefresh, List<M> list, BmobException e) {
        if (e == null) {
            MyLogUtils.d(MyLogUtils.TAG, "onRefreshResult list=" + list);
            if (ListUtils.isEmpty(list)) {
                if (!isRefresh) {
                    onLoadMoreIsNo();
                }
            }
            if (!isRefresh) {
                onLoadMoreData(list);
            } else {
                onRefreshData(list);
            }
            page++;
        } else {
            onRefreshFailed(e);
        }
    }

    /**
     * 刷新
     *
     * @param data
     */
    protected abstract void onRefreshData(List<M> data);

    /**
     * 加载更多
     *
     * @param data
     */
    protected void onLoadMoreData(List<M> data) {
    }

    /**
     * 没有更多了
     */
    protected void onLoadMoreIsNo() {
    }

    /**
     * 刷新失败
     */
    protected abstract void onRefreshFailed(BmobException e);
}
