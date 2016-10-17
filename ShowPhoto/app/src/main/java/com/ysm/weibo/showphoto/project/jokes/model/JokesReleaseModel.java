package com.ysm.weibo.showphoto.project.jokes.model;

import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.mvp.model.impl.MvpBaseModel;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * 发布段子 M数据层
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesReleaseModel extends MvpBaseModel<String> {
    @Override
    public void saveData(String[] params, SaveListener<String> saveListener) {
        super.saveData(params, saveListener);
        UserInfo user = BmobUser.getCurrentUser(UserInfo.class);
        JokesContent jokesContent = new JokesContent();
        jokesContent.setContent(params[0]);
        jokesContent.setUser(user);
        jokesContent.save(saveListener);
    }
}
