package com.ysm.weibo.showphoto.utils;

import android.util.Log;

import com.ysm.weibo.showphoto.common.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 * Created by Administrator on 2016/2/24.
 */
public class Check {
    private String checkText;

    public String getCheckText() {
        return checkText;
    }

    public void setCheckText(String checkText) {
        this.checkText = checkText;
    }

    public Check() {
    }

    /**
     * 姓名
     *
     * @param name
     * @return
     */
    public boolean checkName(String name) {
        if (!Check.isEmpty(name)) {
            String regex = "^[\\u4e00-\\u9fa5]*$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(name);
            Log.i("INFO", "name=" + name);
            if (!m.matches()) {
                Log.i("INFO", "matches=" + m.matches());
                checkText = Constants.NAME_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.NAME_NULL;
        }
        return false;
    }

    /**
     * 用户名
     *
     * @param userName
     * @return
     */
    public boolean checkUserName(String userName) {
        if (!Check.isEmpty(userName)) {
            String all = "^[a-zA-Z][a-zA-Z0-9_]{5,16}";
            Pattern p = Pattern.compile(all);
            Matcher m = p.matcher(userName);
            if (!m.matches()) {
                checkText = Constants.USERNAME_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.USERNAME_NULL;
        }
        return false;
    }

    /**
     * 密码
     *
     * @param password
     * @return
     */
    public boolean isPasswordNo(String password) {
        if (!Check.isEmpty(password)) {
            //不能为中文
            String regex = "[0-9a-zA-Z_]{6,20}";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            if (!m.matches()) {
                checkText = Constants.PASSWORD_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.PASSWORD_NULL;
        }
        return false;
    }

    /**
     * 手机号码
     *
     * @param mobiles
     * @return
     */
    public boolean isMobileNO(String mobiles) {
        if (!isEmpty(mobiles)) {
            //^((1[358][0-9])|(14[57])|(17[0678]))\\d{8}$
            Pattern p = Pattern.compile("^((1[358][0-9])|(14[57])|(17[03678]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            if (!m.matches()) {
                checkText = Constants.PHONE_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.PHONE_NULL;
        }
        return false;
    }

    /**
     * 年龄验证
     *
     * @param str
     * @return
     */
    public boolean checkAge(String str) {
        if (!Check.isEmpty(str)) {
            Pattern p = Pattern.compile("^([0-9]|[0-9]{2}|100)$");
            Matcher m = p.matcher(str);
            if (!m.matches()) {
                checkText = Constants.AGE_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.AGE_NULL;
        }
        return false;
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     */
    public boolean checkEmail(String email) {
        if (!Check.isEmpty(email)) {
            String s = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            Pattern p = Pattern.compile(s);
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                checkText = Constants.EMAIL_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.EMAIL_NULL;
        }
        return false;
    }

    /**
     * 身份证
     *
     * @param idCard
     * @return
     */
    public boolean checkIdCard(String idCard) {
        if (!Check.isEmpty(idCard)) {
            Pattern p = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
            Matcher m = p.matcher(idCard);
            if (!m.matches()) {
                checkText = Constants.AGE_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.AGE_NULL;
        }
        return false;
    }

    /**
     * 判断日期
     *
     * @param idCard
     * @return
     */
    public boolean checkDate(String idCard) {
        if (!Check.isEmpty(idCard)) {
            Pattern p = Pattern.compile("^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$");
            Matcher m = p.matcher(idCard);
            if (!m.matches()) {
                checkText = Constants.DATE_NO;
            }
            return m.matches();
        } else {
            checkText = Constants.DATE_NULL;
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
