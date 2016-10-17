package com.ysm.weibo.showphoto.project.photoview.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.bean.ImageType;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.photoview.model.ShowPhotoTypeModel;
import com.ysm.weibo.showphoto.project.photoview.view.IShowPhotoView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 照片展示 Presenter
 * Created by ysm on 2016/10/5 0005.
 */
public class ShowPhotoPresenter extends MvpBasePresenterImpl<IShowPhotoView> {
    private ShowPhotoTypeModel photoTypeModel;

    public ShowPhotoPresenter(Context context) {
        super(context);
        photoTypeModel = new ShowPhotoTypeModel();
    }

    /**
     * 获取图片类别 数据
     */
    public void getPhotoTypeResult() {
        getView().showLoading();
        photoTypeModel.loadQueryAllData(new FindListener<ImageType>() {
            @Override
            public void done(List<ImageType> list, BmobException e) {
                if (e == null) {
                    //请求数据返回成功
                    getView().photoTypeCallback(list);
                } else {
                    //请求数据异常
                    getView().onMessageCallback("加载图片类别失败");
                    getView().onFailed(e);
                }
            }
        });
    }

}
