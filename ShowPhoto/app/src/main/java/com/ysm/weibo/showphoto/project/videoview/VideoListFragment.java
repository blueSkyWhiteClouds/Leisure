package com.ysm.weibo.showphoto.project.videoview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.refresh.MaterialRefreshLayout;
import com.refresh.MaterialRefreshListener;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.VideoContent;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseFragment;
import com.ysm.weibo.showphoto.project.videoview.presenter.VideoListPresenter;
import com.ysm.weibo.showphoto.project.videoview.view.IVideoListView;
import com.ysm.weibo.showphoto.utils.ImageCacheUtils;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.BaseHolder;
import com.ysm.weibo.showphoto.widget.SimpleAdapterHelper;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 视频列表 类
 * Created by ysm on 2016/10/4 0004.
 */
public class VideoListFragment extends MvpBaseFragment<IVideoListView, VideoListPresenter> implements IVideoListView, OnItemClickListener {
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_NAME = "video_name";
    public static final String VIDEO_CONTENT = "video_content";

    private MaterialRefreshLayout mMaterialRefreshLayout;
    private RecyclerView mVideoList;
    private SimpleAdapterHelper<VideoContent> adapter;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_video, null);
        mVideoList = (RecyclerView) view.findViewById(R.id.video_recycler);
        mMaterialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_content);
        getBarLayout().setTitleText("视频中心");
        getBarLayout().hideBackImage();
        return view;
    }

    @Override
    public VideoListPresenter createPresenter() {
        return new VideoListPresenter(getContext());
    }

    @Override
    public IVideoListView createView() {
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        adapter = new SimpleAdapterHelper<VideoContent>(R.layout.item_video_list_item) {
            @Override
            public void convert(BaseHolder holder, VideoContent item, int position) {
                ImageView iconIv = holder.getView(R.id.video_icon_iv);
                ImageCacheUtils.loadUrl(mActivity, item.getIconUrl(), iconIv);

                holder.setText(R.id.video_title_tv, item.getName());
                holder.setText(R.id.video_content_tv, item.getContent());
                holder.setText(R.id.video_source_tv, "来源：" + item.getSource());

            }
        };
        mVideoList.setAdapter(adapter);
        mVideoList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mMaterialRefreshLayout.setLoadMore(true);
        mMaterialRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        adapter.setOnRecyclerItemClickListener(this);
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().videoDisposeResult(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getPresenter().videoDisposeResult(false);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        VideoContent item = adapter.getDatas().get(position);
        Intent intent = new Intent(mActivity, VideoPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_URL, item.getUrl());
        bundle.putString(VIDEO_NAME, item.getName());
        bundle.putString(VIDEO_CONTENT, item.getContent());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public void onVideoRefreshData(List<VideoContent> data) {
        mMaterialRefreshLayout.finishRefresh();
        adapter.addAll(data);
    }

    @Override
    public void onVideoLoadMore(List<VideoContent> data) {
        mMaterialRefreshLayout.finishRefreshLoadMore();
        adapter.addData(data);
    }
}
