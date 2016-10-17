package com.ysm.weibo.showphoto.project.user.model;

import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册 M 数据层
 * Created by ysm on 2016/10/11 0011.
 */
public class RegisterModel extends MvpBaseModel<UserInfo> {
    @Override
    public void saveData(String[] params, SaveListener<UserInfo> saveListener) {
        super.saveData(params, saveListener);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(params[0]);
        userInfo.setPassword(params[1]);
        userInfo.setSex(params[2]);
        userInfo.setMobilePhoneNumber(params[3]);
        userInfo.signUp(saveListener);
    }
}
