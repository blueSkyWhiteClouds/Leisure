package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by ysm on 2016/10/11 0011.
 */
public class UserInfo extends BmobUser {
    private String sex;
    private String realName;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "sex='" + sex + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }
}
