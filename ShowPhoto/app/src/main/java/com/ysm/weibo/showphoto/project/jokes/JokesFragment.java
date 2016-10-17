package com.ysm.weibo.showphoto.project.jokes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.refresh.MaterialRefreshLayout;
import com.refresh.MaterialRefreshListener;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;
import com.ysm.weibo.showphoto.common.SystemUtils;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseFragment;
import com.ysm.weibo.showphoto.project.jokes.presenter.JokesPresenter;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesView;
import com.ysm.weibo.showphoto.project.user.LoginActivity;
import com.ysm.weibo.showphoto.utils.DateUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.SPUtil;
import com.ysm.weibo.showphoto.utils.ShareUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.BaseHolder;
import com.ysm.weibo.showphoto.widget.SimpleAdapterHelper;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 段子
 * Created by ysm on 2016/10/11 0011.
 */
public class JokesFragment extends MvpBaseFragment<IJokesView, JokesPresenter> implements IJokesView, View.OnClickListener, OnItemClickListener {
    public static final String JOKES_CONTENT_ITEM = "jokes";
    private RecyclerView mJokesList;
    private MaterialRefreshLayout mMaterialRefreshLayout;
    private SimpleAdapterHelper<JokesContent> adapter;
    private SPUtil praiseSp;
    private ShareUtils shareUtils;

    @Override
    protected View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_jokes, null);
        mJokesList = (RecyclerView) view.findViewById(R.id.jokes_recycler);
        mMaterialRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_content);
        getBarLayout().setTitleText("搞笑段子");
        getBarLayout().hideBackImage();
        getBarLayout().setRightImage_1(R.mipmap.icon_release);
        return view;
    }

    @Override
    public JokesPresenter createPresenter() {
        return new JokesPresenter(mActivity);
    }

    @Override
    public IJokesView createView() {
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        shareUtils = new ShareUtils(mActivity);
        praiseSp = SPUtil.getSPUtilConfig(mActivity, SPUtil.PRAISE_NAME);

        mMaterialRefreshLayout.setLoadMore(true);
        adapter = new SimpleAdapterHelper<JokesContent>(R.layout.item_jokes_list_item) {
            @Override
            public void convert(BaseHolder holder, final JokesContent item, final int position) {
                UserInfo user = item.getUser();
                holder.setText(R.id.jokes_list_item_user, user.getRealName());
                holder.setText(R.id.jokes_list_item_time, item.getCreatedAt());
                holder.setText(R.id.jokes_list_item_content_tv, item.getContent());
                holder.setText(R.id.jokes_list_item_comments_tv, "" + (item.getCommentsNum() == null ? 0 : item.getCommentsNum()));
                holder.setText(R.id.jokes_list_item_praise_tv, "" + (item.getPraiseNum() == null ? 0 : item.getPraiseNum()));

                String praiseItem = (String) praiseSp.get(item.getObjectId(), "");
                if (!TextUtils.isEmpty(praiseItem)) {
                    JokesContent jokesContent = ParserJson.fromJson(praiseItem, JokesContent.class);
                    if (jokesContent == null) {
                        return;
                    }
                    if (item.getObjectId().equals(jokesContent.getObjectId())) {
                        int day = DateUtils.getDateDay(jokesContent.getCurrentTime());
                        if (day > 0) {
                            item.setPraise(false);
                        } else {
                            item.setPraise(true);
                        }
                    }
                }
                holder.setTextColor(R.id.jokes_list_item_praise_tv,
                        item.isPraise() ? ContextCompat.getColor(mActivity, R.color.orange_dark) : ContextCompat.getColor(mActivity, R.color.black));
                holder.setImageResource(R.id.jokes_list_item_praise_iv, item.isPraise() ? R.mipmap.icon_praise_2 : R.mipmap.icon_praise);

                holder.setOnClickListener(R.id.jokes_list_item_comments_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpActivity(item);
                    }
                });
                holder.setOnClickListener(R.id.jokes_list_item_share_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareUtils.startShare(item.getContent());
                    }
                });
                holder.setOnClickListener(R.id.jokes_list_item_praise_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.isPraise()) {
                            getPresenter().savePraise(item.getObjectId(), position);
                        }
                    }
                });
            }
        };
        mJokesList.setAdapter(adapter);
        mJokesList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mMaterialRefreshLayout.autoRefresh();
    }

    @Override
    protected void initListener() {
        super.initListener();
        getBarLayout().setRightImageOnClickListener(this);
        adapter.setOnRecyclerItemClickListener(this);
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().getJokesContentResult(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                getPresenter().getJokesContentResult(false);
            }
        });
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public void onClick(View v) {
        UserInfo userInfo = SystemUtils.getLoginInfoBean(mActivity);
        if (userInfo == null) {
            startActivity(new Intent(mActivity, LoginActivity.class));
        }else{
            startActivity(new Intent(mActivity, JokesReleaseActivity.class));
        }
    }

    @Override
    public void onJokesContentRefresh(List<JokesContent> data) {
        mMaterialRefreshLayout.finishRefresh();
        MyLogUtils.d(MyLogUtils.TAG, "onPhotoDataRefresh data=" + data.toString());
        adapter.addAll(data);
    }

    @Override
    public void onJokesContentLoadMore(List<JokesContent> data) {
        mMaterialRefreshLayout.finishRefreshLoadMore();
        MyLogUtils.d(MyLogUtils.TAG, "onPhotoDataRefresh data=" + data.toString());
        adapter.addData(data);
    }

    @Override
    public void onPraiseUpdateCallback(int position, JokesContent jokesContent) {
        jokesContent.setPraise(true);
        adapter.set(position, jokesContent);

        jokesContent.setCurrentTime(System.currentTimeMillis());
        String json = ParserJson.toJson(jokesContent);
        MyLogUtils.d(MyLogUtils.TAG, "json=" + json);
        praiseSp.putAndApply(jokesContent.getObjectId(), json);
    }

    @Override
    public void onItemClick(View view, int position) {
        jumpActivity(adapter.getDatas().get(position));
    }

    private void jumpActivity(JokesContent jokesContent) {
        Intent intent = new Intent(mActivity, JokesDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(JOKES_CONTENT_ITEM, jokesContent);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
