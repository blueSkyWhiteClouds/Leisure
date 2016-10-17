package com.ysm.weibo.showphoto.common;

import android.content.Context;
import android.text.TextUtils;

import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.SPUtil;

public class SystemUtils {

    public static UserInfo mUserInfo = null;

    public static UserInfo getLoginInfoBean(Context context) {
        if (!isLogin()) {
            //每一次判断 本地是否有数据，从本地取数据
            SPUtil spUtil = SPUtil.getSPUtilConfig(context, SPUtil.NAME_LOGIN_INFO);
            String loginInfo = (String) spUtil.get(SPUtil.LOGIN_IFNO_KEY, "");
            if (!TextUtils.isEmpty(loginInfo)) {
                mUserInfo = ParserJson.fromJson(loginInfo, UserInfo.class);
            }
//            else {
//                ToastUtil.showToast(context, "请先登录!");
//            }
        }
        return mUserInfo;
    }

    public static void clearLogin(Context context) {
        SPUtil spUtilConfig = SPUtil.getSPUtilConfig(context, SPUtil.NAME_LOGIN_INFO);
        spUtilConfig.putAndApply(SPUtil.LOGIN_IFNO_KEY, "");
        spUtilConfig.putAndApply(SPUtil.LOGIN_USER_NAME, "");
        spUtilConfig.putAndApply(SPUtil.LOGIN_PASSWORD, "");
        SystemUtils.mUserInfo = null;
    }

    /****
     * 获取令牌
     *
     * @return
     */
    public static String getToKen() {
        if (isLogin()) {
            return mUserInfo.getSessionToken();
        } else {
            return null;
        }
    }


    /****
     * 当前用户是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return !(mUserInfo == null || TextUtils.isEmpty(mUserInfo.getSessionToken()));
    }

}
