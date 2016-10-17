package com.ysm.weibo.showphoto.project.photoview.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.photoview.view.IPhotoDetailsView;

/**
 * 图片详情 P 层
 * Created by Administrator on 2016/10/7 0007.
 */
public class PhotoDetailsPresenter extends MvpBasePresenterImpl<IPhotoDetailsView> {
    public PhotoDetailsPresenter(Context context) {
        super(context);
    }

    /**
     * 加载原始图
     *
     * @param url
     */
    public void showOriginalImage(String url) {
        if (!TextUtils.isEmpty(url)) {
            getView().showImage(url);
        } else {
            getView().onFailed("url is null");
            getView().onMessageCallback("获取原图失败!!!");
        }
    }
}
