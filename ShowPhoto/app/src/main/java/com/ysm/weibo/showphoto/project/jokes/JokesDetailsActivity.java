package com.ysm.weibo.showphoto.project.jokes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.refresh.MaterialRefreshLayout;
import com.refresh.MaterialRefreshListener;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.Comments;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.jokes.presenter.JokesDetailsPresenter;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesDetailsView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.SPUtil;
import com.ysm.weibo.showphoto.utils.ShareUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.BaseHolder;
import com.ysm.weibo.showphoto.widget.SimpleAdapterHelper;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12 0012.
 */
public class JokesDetailsActivity extends MvpBaseActivity<IJokesDetailsView, JokesDetailsPresenter> implements IJokesDetailsView, View.OnClickListener {
    private TextView mUserName;
    private TextView mCreateTime;
    private TextView mJokesContent;
    private TextView mCommentsTv;
    private ImageView mPraiseIv;
    private TextView mPraiseTv;
    private LinearLayout mPraiseLl;
    private LinearLayout mShareLl;

    private MaterialRefreshLayout mMaterialRefreshLayout;
    private RecyclerView mCommentsList;
    private EditText mWriteComments;
    private Button mSendComments;

    private SimpleAdapterHelper<Comments> adapter;

    private JokesContent jokesContent;
    private SPUtil praiseSp;
    private TitleBarLayout mBarLayout;
    private ShareUtils shareUtils;

    @Override
    protected JokesDetailsPresenter createPresenter() {
        return new JokesDetailsPresenter(this);
    }

    @Override
    protected IJokesDetailsView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_jokes_details;
    }

    @Override
    protected void findViews() {
        mBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mMaterialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh_content);
        mCommentsList = (RecyclerView) findViewById(R.id.jokes_details_recycler);
        mWriteComments = (EditText) findViewById(R.id.write_comments_et);
        mSendComments = (Button) findViewById(R.id.send_comments_btn);

        mUserName = (TextView) findViewById(R.id.jokes_list_item_user);
        mCreateTime = (TextView) findViewById(R.id.jokes_list_item_time);
        mJokesContent = (TextView) findViewById(R.id.jokes_list_item_content_tv);
        mCommentsTv = (TextView) findViewById(R.id.jokes_list_item_comments_tv);
        mPraiseTv = (TextView) findViewById(R.id.jokes_list_item_praise_tv);
        mPraiseIv= (ImageView) findViewById(R.id.jokes_list_item_praise_iv);
        mPraiseLl = (LinearLayout) findViewById(R.id.jokes_list_item_praise_ll);
        mShareLl = (LinearLayout) findViewById(R.id.jokes_list_item_share_ll);

    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        shareUtils = new ShareUtils(this);
        praiseSp = SPUtil.getSPUtilConfig(this, SPUtil.PRAISE_NAME);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jokesContent = (JokesContent) bundle.getSerializable(JokesFragment.JOKES_CONTENT_ITEM);
        }
        adapter = new SimpleAdapterHelper<Comments>(R.layout.item_comments_list_item) {
            @Override
            public void convert(BaseHolder holder, Comments item, int position) {
                UserInfo userInfo = item.getUserInfo();
                holder.setText(R.id.comments_user_tv, userInfo.getRealName());
                holder.setText(R.id.comments_content_tv, item.getContent());
            }
        };
        mCommentsList.setAdapter(adapter);
        mCommentsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //刷新
        refreshListener();

        mPraiseLl.setOnClickListener(this);
        mShareLl.setOnClickListener(this);
        mSendComments.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
        super.setViews();
        mBarLayout.setTitleText("评论");
        mBarLayout.setActivity(this);
        if (jokesContent != null) {
            setShowViews(jokesContent);
        }

        //加载评论列表数据
        getPresenter().QueryComments(jokesContent.getObjectId());
    }

    /**
     * 设置头布局内容
     *
     * @param jokesContent
     */
    private void setShowViews(JokesContent jokesContent) {
        UserInfo user = jokesContent.getUser();
        mUserName.setText(user.getRealName());
        mCreateTime.setText(jokesContent.getCreatedAt());
        mJokesContent.setText(jokesContent.getContent());
        mCommentsTv.setText("" + (jokesContent.getCommentsNum() == null ? 0 : jokesContent.getCommentsNum()));
        mPraiseTv.setText("" + (jokesContent.getPraiseNum() == null ? 0 : jokesContent.getPraiseNum()));
        if (this.jokesContent.isPraise()) {
            mPraiseTv.setTextColor(ContextCompat.getColor(this, R.color.orange_dark));
            mPraiseIv.setImageResource(R.mipmap.icon_praise_2);
        } else {
            mPraiseTv.setTextColor(ContextCompat.getColor(this, R.color.black));
            mPraiseIv.setImageResource(R.mipmap.icon_praise);
        }
    }

    private void refreshListener() {
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                getPresenter().QueryOfObjectJokes(jokesContent.getObjectId(), JokesDetailsPresenter.REFRESH_TYPE);
            }
        });
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jokes_list_item_praise_tv://点赞
                if (!jokesContent.isPraise()) {
                    getPresenter().savePraise(jokesContent.getObjectId());
                }
                break;
            case R.id.jokes_list_item_share_ll://分享
                shareUtils.startShare(jokesContent.getContent());
                break;
            case R.id.send_comments_btn://评论
                String comments = mWriteComments.getText().toString().trim();
                getPresenter().sendComments(jokesContent.getObjectId(), comments);
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mWriteComments.getWindowToken(), 0);
                break;
        }
    }

    @Override
    public void onSendCommentsSuccess() {
        mWriteComments.setText("");
        getPresenter().QueryComments(jokesContent.getObjectId());
    }

    @Override
    public void onQueryCommentsSuccess(List<Comments> data) {
        if (mMaterialRefreshLayout.isRefreshing()) {
            mMaterialRefreshLayout.finishRefresh();
        }
        adapter.addAll(data);
    }

    @Override
    public void onQuerySingleJokes(JokesContent jokesContent, int type) {
        switch (type) {
            case JokesDetailsPresenter.COMMENT_TYPE:
                mCommentsTv.setText("" + jokesContent.getCommentsNum());
                break;
            case JokesDetailsPresenter.PARISE_TYPE:
                jokesContent.setPraise(true);
                mPraiseTv.setText("" + jokesContent.getPraiseNum());
                mPraiseTv.setTextColor(ContextCompat.getColor(this, R.color.orange_dark));

                jokesContent.setCurrentTime(System.currentTimeMillis());
                String json = ParserJson.toJson(jokesContent);
                MyLogUtils.d(MyLogUtils.TAG, "json=" + json);
                praiseSp.putAndApply(jokesContent.getObjectId(), json);
                break;
            case JokesDetailsPresenter.REFRESH_TYPE:
                setShowViews(jokesContent);
                break;
        }
    }
}
