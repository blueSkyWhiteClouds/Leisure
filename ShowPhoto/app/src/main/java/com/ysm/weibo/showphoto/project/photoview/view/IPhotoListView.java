package com.ysm.weibo.showphoto.project.photoview.view;

import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 图片列表 V 接口
 * Created by ysm on 2016/10/5 0005.
 */
public interface IPhotoListView extends IBaseMvpView {
    void onPhotoDataRefresh(List<ImageContent> data);

    void onPhotoDataLoadMore(List<ImageContent> data);

    void onFailed(BmobException e);
}
