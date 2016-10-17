package com.ysm.weibo.showphoto.project.photoview;

import com.bm.library.PhotoView;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.photoview.presenter.PhotoDetailsPresenter;
import com.ysm.weibo.showphoto.project.photoview.view.IPhotoDetailsView;
import com.ysm.weibo.showphoto.utils.ImageCacheUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

/**
 * 图片详情
 * Created by ysm on 2016/10/7 0007.
 */
public class PhotoDetailsActivity extends MvpBaseActivity<IPhotoDetailsView, PhotoDetailsPresenter> implements IPhotoDetailsView {
    private TitleBarLayout mBarLayout;
    private PhotoView mPhotoView;
    private String imageUrl;

    @Override
    protected PhotoDetailsPresenter createPresenter() {
        return new PhotoDetailsPresenter(this);
    }

    @Override
    protected IPhotoDetailsView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo_details;
    }

    @Override
    protected void findViews() {
        mBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mPhotoView = (PhotoView) findViewById(R.id.details_photoview);
    }

    @Override
    protected void beforeSetViews() {
        imageUrl = getIntent().getStringExtra(PhotoListFragment.URL);
        // 启用图片缩放功能
        mPhotoView.enable();
    }

    @Override
    protected void setViews() {
        mBarLayout.setTitleText("图片详情");
        mBarLayout.setActivity(this);
        getPresenter().showOriginalImage(imageUrl);
    }

    @Override
    public void showImage(String url) {
        ImageCacheUtils.loadUrlToPhotoView(this, url, mPhotoView);
    }

    @Override
    public void onFailed(String msg) {
        MyLogUtils.d(MyLogUtils.TAG, msg);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }
}
