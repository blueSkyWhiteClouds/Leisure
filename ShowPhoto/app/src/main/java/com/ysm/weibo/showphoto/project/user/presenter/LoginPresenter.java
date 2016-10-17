package com.ysm.weibo.showphoto.project.user.presenter;

import android.app.ProgressDialog;
import android.content.Context;

import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;
import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.user.model.LoginModel;
import com.ysm.weibo.showphoto.project.user.view.ILoginView;
import com.ysm.weibo.showphoto.utils.Check;
import com.ysm.weibo.showphoto.utils.Md5;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.SPUtil;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 登录P层
 * Created by ysm on 2016/10/11 0011.
 */
public class LoginPresenter extends MvpBasePresenterImpl<ILoginView> {
    private LoginModel loginModel;
    private Check check;

    public LoginPresenter(Context context) {
        super(context);
        loginModel = new LoginModel();
        check = new Check();
    }

    public void getLoginResult(final String user, final String password, final ProgressDialog progressDialog) {
        if (!check.checkUserName(user)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        if (!check.isPasswordNo(password)) {
            getView().onMessageCallback(check.getCheckText());
            return;
        }
        getView().showDialog();
        loginModel.saveData(new String[]{user, Md5.d5(password)}, new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (null != progressDialog && progressDialog.isShowing()) {
                    getView().hideDialog();
                }
                if (e == null) {
                    MyLogUtils.d(MyLogUtils.TAG, "userInfo=" + userInfo);
                    getView().onMessageCallback("登录成功!");
                    String json = ParserJson.toJson(userInfo);
                    SPUtil config = SPUtil.getSPUtilConfig(getContext(), SPUtil.NAME_LOGIN_INFO);
                    config.putAndApply(SPUtil.LOGIN_IFNO_KEY, json);
                    config.putAndApply(SPUtil.LOGIN_USER_NAME, user);
                    config.putAndApply(SPUtil.LOGIN_PASSWORD, Md5.d5(password));
                    MyLogUtils.d(MyLogUtils.TAG, "loginInfo=" + config.get(SPUtil.LOGIN_IFNO_KEY, ""));
                    MyLogUtils.d(MyLogUtils.TAG, "userName=" + config.get(SPUtil.LOGIN_USER_NAME, ""));
                    MyLogUtils.d(MyLogUtils.TAG, "password=" + config.get(SPUtil.LOGIN_PASSWORD, ""));
                    getView().onLoginSuccess();
                } else {
                    getView().onMessageCallback("登录失败!");
                    getView().onFailed(e);
                }
            }
        });
    }
}
