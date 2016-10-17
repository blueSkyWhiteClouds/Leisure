package com.ysm.weibo.showphoto.project.photoview.view;

import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

/**
 * 图片详情 V 接口
 * Created by ysm on 2016/10/7 0007.
 */
public interface IPhotoDetailsView extends IBaseMvpView {
    void showImage(String url);

    void onFailed(String msg);
}
