package com.ysm.weibo.showphoto.project.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysm.weibo.showphoto.MainActivity1;
import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.common.SystemUtils;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseFragment;
import com.ysm.weibo.showphoto.project.mine.presenter.MinePresenter;
import com.ysm.weibo.showphoto.project.mine.view.IMineView;
import com.ysm.weibo.showphoto.project.user.LoginActivity;
import com.ysm.weibo.showphoto.utils.ImageCacheUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.SPUtil;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.ParallaxListView;

/**
 * 我的
 * Created by ysm on 2016/10/4 0004.
 */
public class MineFragment extends MvpBaseFragment<IMineView, MinePresenter> implements IMineView, View.OnClickListener, AdapterView.OnItemClickListener {
    private View head;
    private ParallaxListView mListView;
    private LinearLayout mUserLl;
    private ImageView mUserIcon;
    private ImageView mBackGroundIv;
    private TextView mUserName;
    private boolean isShowLoginActivity = false;
    private String[] items = new String[]{"夜间模式", "上传图片", "清除图片缓存", "退出登录"};
    private AlertDialog.Builder alertDialog;

    protected View initView() {
        MyLogUtils.d(MyLogUtils.TAG, "initView ");
        View view = View.inflate(mActivity, R.layout.fragment_mine, null);
        mListView = (ParallaxListView) view.findViewById(R.id.mine_list);

        head = View.inflate(mActivity, R.layout.item_list_head_view, null);
        mUserLl = (LinearLayout) head.findViewById(R.id.mine_user_ll);
        mBackGroundIv = (ImageView) head.findViewById(R.id.mine_background_iv);
        mUserIcon = (ImageView) head.findViewById(R.id.mine_user_icon);
        mUserName = (TextView) head.findViewById(R.id.mine_user_name);

        getBarLayout().setTitleText("我的");
        getBarLayout().hideBackImage();
        return view;
    }

    @Override
    public MinePresenter createPresenter() {
        return new MinePresenter(getContext());
    }

    @Override
    public IMineView createView() {
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        alertDialog = new AlertDialog.Builder(mActivity);
        alertDialog.setMessage("清除缓存成功！");
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
        MyLogUtils.d(MyLogUtils.TAG, "initData ");
        UserInfo userInfo = SystemUtils.getLoginInfoBean(getContext());
        if (userInfo != null) {
            mUserName.setText(userInfo.getRealName());
        }
        mListView.addHeaderView(head);
        mListView.setParallaxImageView(mBackGroundIv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        MyLogUtils.d(MyLogUtils.TAG, "mine onResume");
        if (isShowLoginActivity) {
            MyLogUtils.d(MyLogUtils.TAG, "isShowLoginActivity=" + isShowLoginActivity);
            UserInfo userInfo = SystemUtils.getLoginInfoBean(mActivity);
            if (userInfo != null) {
                mUserName.setText(userInfo.getRealName());
                mUserLl.setOnClickListener(null);
            }
            isShowLoginActivity = false;
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        String realName = mUserName.getText().toString().trim();
        if (TextUtils.isEmpty(realName)) {
            mUserLl.setOnClickListener(this);
        }
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }

    @Override
    public void onClick(View v) {
        isShowLoginActivity = true;
        startActivity(new Intent(mActivity, LoginActivity.class));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1://设置
                ToastUtil.showToast(mActivity, "请等待下个版本");
                break;
            case 2:
                startActivity(new Intent(mActivity, MainActivity1.class));
                break;
            case 3://清除缓存
                getPresenter().clearImageCache(mActivity);
                showDialog();
                break;
            case 4://退出登录
                showOutUserDialog();
                break;
        }
    }

    private void showDialog() {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(mActivity);
            alertDialog.setMessage("清除缓存成功！");
        }
        alertDialog.show();
    }

    AlertDialog.Builder builder = null;

    private void showOutUserDialog() {
        if (builder == null) {
            builder = new AlertDialog.Builder(mActivity);
            builder.setMessage("确认退出嘛？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getPresenter().clearLocation(mActivity);
                    ToastUtil.showToast(mActivity, "退出成功");
                    mUserName.setText("");
                    mUserLl.setOnClickListener(MineFragment.this);
                    dialog.dismiss();
                }
            });

        }
        builder.show();
    }

    @Override
    public void onDestroy() {
        getPresenter().stopClearImageCache(mActivity);
        super.onDestroy();
    }
}
