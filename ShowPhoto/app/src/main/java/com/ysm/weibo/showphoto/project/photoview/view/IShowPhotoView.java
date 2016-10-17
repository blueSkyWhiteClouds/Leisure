package com.ysm.weibo.showphoto.project.photoview.view;

import com.ysm.weibo.showphoto.bean.ImageType;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 照片展示 View 接口
 * Created by ysm on 2016/10/5 0005.
 */
public interface IShowPhotoView extends IBaseMvpView {
    void showLoading();

    void photoTypeCallback(List<ImageType> data);

    void onFailed(BmobException e);
}
