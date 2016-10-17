package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;

/**
 * 评论
 * Created by ysm on 2016/10/12 0012.
 */
public class Comments extends BmobObject {
    private String content;//评论内容
    private UserInfo userInfo;//评论的用户
    private JokesContent jokesContent;//评论的段子

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
        return "Comments{" +
                "content='" + content + '\'' +
                ", userInfo=" + userInfo +
                ", jokesContent=" + jokesContent +
                '}';
    }
}
