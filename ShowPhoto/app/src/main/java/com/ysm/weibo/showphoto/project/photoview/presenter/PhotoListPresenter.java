package com.ysm.weibo.showphoto.project.photoview.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.mvp.model.IMvpModel;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.photoview.model.PhotoListModel;
import com.ysm.weibo.showphoto.project.photoview.view.IPhotoListView;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 图片列表 P
 * Created by ysm on 2016/10/5 0005.
 */
public class PhotoListPresenter extends MvpBasePresenterImpl<IPhotoListView> {
    private PhotoListModel photoListModel;
    private int page;//页码
    private int count;//每页加载数量
    private int skip;//忽略的量

    public PhotoListPresenter(Context context) {
        super(context);
        photoListModel = new PhotoListModel();
        page = 0;
        count = 10;
    }

    public void loadPhotoDataResult(String type, final boolean isRefresh) {
        if (isRefresh) {
            page = 0;
            skip = page;
        } else {
            // 跳过之前页数并去掉重复数据
            skip = page * count + 1;
            MyLogUtils.d(MyLogUtils.TAG, "skip=" + skip);
        }
        photoListModel.loadQueryAllData(new String[]{type, String.valueOf(count), String.valueOf(skip)}, new FindListener<ImageContent>() {
            @Override
            public void done(List<ImageContent> list, BmobException e) {
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "loadQuerySingleData list=" + list);
                    if (ListUtils.isEmpty(list)) {
                        if (!isRefresh) {
                            getView().onMessageCallback("没有更多图片了!");
                        }
                    }
                    if (!isRefresh) {
                        getView().onPhotoDataLoadMore(list);
                    } else {
                        getView().onPhotoDataRefresh(list);
                    }
                    page++;
                } else {
                    getView().onMessageCallback("加载图片失败!");
                    getView().onFailed(e);
                }
            }
        });
    }
}
