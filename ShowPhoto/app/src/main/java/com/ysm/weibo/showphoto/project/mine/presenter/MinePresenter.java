package com.ysm.weibo.showphoto.project.mine.presenter;

import android.content.Context;

import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.mine.model.MineModel;
import com.ysm.weibo.showphoto.project.mine.view.IMineView;
import com.ysm.weibo.showphoto.utils.SPUtil;

/**
 * 我的 Presenter 调合器类
 * Created by ysm on 2016/10/4 0004.
 */
public class MinePresenter extends MvpBasePresenterImpl<IMineView> {
    private MineModel mineModel;

    public MinePresenter(Context context) {
        super(context);
        mineModel = new MineModel();
    }

    public void clearLocation(Context context) {
        mineModel.clearLocalFile(context);
    }

    public void clearImageCache(Context context) {
        mineModel.clearDiskCache(context);
    }

    public void stopClearImageCache(Context context) {
        mineModel.stopClearDiskCache();
    }
}
