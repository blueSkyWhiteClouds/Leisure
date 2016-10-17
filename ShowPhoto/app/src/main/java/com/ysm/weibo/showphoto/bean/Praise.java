package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/13 0013.
 */
public class Praise extends BmobObject {
    private UserInfo userInfo;
    private JokesContent jokesContent;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public JokesContent getJokesContent() {
        return jokesContent;
    }

    public void setJokesContent(JokesContent jokesContent) {
        this.jokesContent = jokesContent;
    }

    @Override
    public String toString() {
        return "Praise{" +
                "userInfo=" + userInfo +
                ", jokesContent=" + jokesContent +
                '}';
    }
}
