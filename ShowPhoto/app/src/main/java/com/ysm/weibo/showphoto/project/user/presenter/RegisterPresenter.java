package com.ysm.weibo.showphoto.project.user.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.common.Constants;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.user.model.RegisterModel;
import com.ysm.weibo.showphoto.project.user.view.IRegisterView;
import com.ysm.weibo.showphoto.utils.Check;
import com.ysm.weibo.showphoto.utils.Md5;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册 P层
 * Created by ysm on 2016/10/11 0011.
 */
public class RegisterPresenter extends MvpBasePresenterImpl<IRegisterView> {
    private Check check;
    private RegisterModel userModel;

    public RegisterPresenter(Context context) {
        super(context);
        check = new Check();
        userModel = new RegisterModel();
    }

    public void getRegisterResult(String user, String password_1, String password_2, String sexText, String phone, final ProgressDialog progressDialog) {
        if (!check.checkUserName(user)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        if (!check.isPasswordNo(password_1)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        if (!check.isPasswordNo(password_2)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        if (!password_1.equals(password_2)) {
            getView().onMessageCallback(Constants.PASSWORD_DISAFFINITY);
            return;
        }
        if (!check.isMobileNO(phone)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        getView().showDialog();
        userModel.saveData(new String[]{user, Md5.d5(password_1), sexText, phone}, new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (null != progressDialog && progressDialog.isShowing()) {
                    getView().hideDialog();
                }
                if (e == null) {
                    getView().onMessageCallback("注册成功!");
                    getView().onRegisterSuccess();
                } else {
                    getView().onFailed(e);
                    getView().onMessageCallback("注册失败!");
                }
            }
        });
    }
}
