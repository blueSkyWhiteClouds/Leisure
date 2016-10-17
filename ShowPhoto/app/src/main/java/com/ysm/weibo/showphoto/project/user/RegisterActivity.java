package com.ysm.weibo.showphoto.project.user;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseActivity;
import com.ysm.weibo.showphoto.project.user.presenter.RegisterPresenter;
import com.ysm.weibo.showphoto.project.user.view.IRegisterView;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ToastUtil;
import com.ysm.weibo.showphoto.widget.TitleBarLayout;

import cn.bmob.v3.exception.BmobException;

/**
 * 注册
 * Created by ysm on 2016/10/11 0011.
 */
public class RegisterActivity extends MvpBaseActivity<IRegisterView, RegisterPresenter> implements IRegisterView, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private EditText mUserEt;
    private EditText mPasswordEt_1;
    private EditText mPasswordEt_2;
    private EditText mPhoneEt;
    private RadioGroup radioGroup;
    private Button mRegisterBtn;
    private ProgressDialog progressDialog;
    private String sexText;
    private TitleBarLayout mBarLayout;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected IRegisterView createView() {
        return this;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void findViews() {
        mBarLayout = (TitleBarLayout) findViewById(R.id.title_bar_layout);
        mUserEt = (EditText) findViewById(R.id.register_user_et);
        mPasswordEt_1 = (EditText) findViewById(R.id.register_password_et);
        mPasswordEt_2 = (EditText) findViewById(R.id.register_password_et_2);
        mPhoneEt = (EditText) findViewById(R.id.register_phone_et);
        radioGroup = (RadioGroup) findViewById(R.id.sex_group);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);
    }

    @Override
    protected void beforeSetViews() {
        super.beforeSetViews();
        progressDialog = new ProgressDialog(this);
        mRegisterBtn.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected void setViews() {
        super.setViews();
        mBarLayout.setTitleText("注册");
        mBarLayout.setActivity(this);
        progressDialog.setMessage("注册中...");
        progressDialog.setCancelable(false);
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onClick(View v) {
        String user = mUserEt.getText().toString().trim();
        String password_1 = mPasswordEt_1.getText().toString().trim();
        String password_2 = mPasswordEt_2.getText().toString().trim();
        String phone = mPhoneEt.getText().toString().trim();
//        getPresenter().setParams();
        getPresenter().getRegisterResult(user, password_1, password_2, sexText, phone, progressDialog);
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
    public void onRegisterSuccess() {
        this.onBackPressed();
    }

    @Override
    public void onFailed(BmobException e) {
        MyLogUtils.d(MyLogUtils.TAG, "register msg=" + e.getMessage() + ", errorCode=" + e.getErrorCode());
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.register_man_rb:
                sexText = "男";
                break;
            case R.id.register_woman_rb:
                sexText = "女";
                break;
        }
    }
}
