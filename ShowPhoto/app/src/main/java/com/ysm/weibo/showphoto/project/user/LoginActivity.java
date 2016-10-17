package com.ysm.weibo.showphoto.project.user;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.user.presenter.LoginPresenter;
import com.ysm.weibo.showphoto.project.user.view.ILoginView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

import cn.bmob.v3.exception.BmobException;

/**
 * 登录
 * Created by ysm on 2016/10/11 0011.
 */
public class LoginActivity extends MvpBaseActivity<ILoginView, LoginPresenter> implements ILoginView, View.OnClickListener {
    private EditText mUserEt;
    private EditText mPasswordEt;
    private TextView mRegisterTv;
    private Button mLoginBtn;
    private ProgressDialog progressDialog;
    private TitleBarLayout mBarLayout;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected ILoginView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        mBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mUserEt = (EditText) findViewById(R.id.login_user_et);
        mPasswordEt = (EditText) findViewById(R.id.login_password_et);
        mRegisterTv = (TextView) findViewById(R.id.login_register_tv);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        progressDialog = new ProgressDialog(this);
        mLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void setViews() {
        super.setViews();
        mBarLayout.setTitleText("登录");
        mBarLayout.setActivity(this);
        progressDialog.setMessage("正在登录...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void onLoginSuccess() {
        this.onBackPressed();
    }

    @Override
    public void onFailed(BmobException e) {
        MyLogUtils.d(MyLogUtils.TAG, "login msg=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onClick(View v) {
        String user = mUserEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        getPresenter().getLoginResult(user, password, progressDialog);
    }
}
