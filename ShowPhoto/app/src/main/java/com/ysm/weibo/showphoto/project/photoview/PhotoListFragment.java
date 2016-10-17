package com.ysm.weibo.showphoto.project.photoview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.refresh.MaterialRefreshLayout;
import com.refresh.MaterialRefreshListener;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseFragment;
import com.ysm.weibo.showphoto.project.photoview.adapter.PhotoRecyclerAdapter;
import com.ysm.weibo.showphoto.project.photoview.presenter.PhotoListPresenter;
import com.ysm.weibo.showphoto.project.photoview.view.IPhotoListView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 图片列表 类
 * Created by ysm on 2016/10/5 0005.
 */
public class PhotoListFragment extends MvpBaseFragment<IPhotoListView, PhotoListPresenter> implements IPhotoListView, OnItemClickListener {
    public static final String URL = "url";
    private MaterialRefreshLayout mRefreshLayout;
    private RecyclerView mPhotoList;
    private PhotoRecyclerAdapter adapter;
    private Bundle bundle;
    private String type = "";
    private List<ImageContent> data;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_photo_list, null);
        getBarLayout().hideTitle();
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_content);
        mPhotoList = (RecyclerView) view.findViewById(R.id.photo_recycler);
        bundle = this.getArguments();
        if (bundle != null) {
            type = bundle.getString(ShowPhotoFragment.PHOTO_TYPE);
//            typeId = Integer.valueOf(bundle.getString(ShowPhotoFragment.PHOTO_TYPE_ID));
        }
        adapter = new PhotoRecyclerAdapter(getContext(), data);
        mPhotoList.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false));
        mPhotoList.setAdapter(adapter);
        return view;
    }

    @Override
    public PhotoListPresenter createPresenter() {
        return new PhotoListPresenter(getContext());
    }

    @Override
    public IPhotoListView createView() {
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        mRefreshLayout.setLoadMore(true);
        getPresenter().loadPhotoDataResult(type, true);
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnItemClickListener(this);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().loadPhotoDataResult(type, true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getPresenter().loadPhotoDataResult(type, false);
            }
        });
    }

    @Override
    public void onPhotoDataRefresh(List<ImageContent> data) {
        mRefreshLayout.finishRefresh();
        MyLogUtils.d(MyLogUtils.TAG, "onPhotoDataRefresh data=" + data.toString());
        this.data = data;
        adapter.addAll(data);
    }

    @Override
    public void onPhotoDataLoadMore(List<ImageContent> data) {
        mRefreshLayout.finishRefreshLoadMore();
        MyLogUtils.d(MyLogUtils.TAG, "onPhotoDataRefresh data=" + data.toString());
        this.data = data;
        adapter.addData(data);
    }

    @Override
    public void onFailed(BmobException e) {
        MyLogUtils.d("TAG", "加载图片失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
    }

    @Override
    public void onItemClick(View view, int position) {
//        ToastUtil.showToast(getContext(), "position=" + position);
        Intent intent = new Intent(mActivity, PhotoDetailsActivity.class);
        intent.putExtra(URL, data.get(position).getIamgeUrl());
        startActivity(intent);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(getContext(), msg);
    }
}
