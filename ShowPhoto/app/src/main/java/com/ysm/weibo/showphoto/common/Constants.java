package com.ysm.weibo.showphoto.common;

/**
 * Created by ysm on 2016/5/20 0020.
 */
public interface Constants {

    //姓名输入错误
    String NAME_NO = "请输入正确格式的姓名（汉字）";
    //姓名为空
    String NAME_NULL = "姓名不能为空";
    //用户名输入错误
    String USERNAME_NO = "请输入至少6-16为用户名，必须以字母开头";
    //用户名不能为空
    String USERNAME_NULL = "用户名不能为空";
    //密码输入错误
    String PASSWORD_NO = "请输入8位以上字母、数字或者字母和数字组合的密码";
    //密码不能为空
    String PASSWORD_NULL = "密码不能为空";
    //手机输入错误
    String PHONE_NO = "请输入正确的手机号码";
    //手机不能为空
    String PHONE_NULL = "手机号码不能为空";
    //年龄输入错误
    String AGE_NO = "请输入正确的年龄0~100";
    //年龄不能为空
    String AGE_NULL = "年龄信息不能为空";
    //邮箱输入错误
    String EMAIL_NO = "请输入正确的邮箱格式";
    //邮箱不能为空
    String EMAIL_NULL = "邮箱不能为空";
    //验证码为空
    String CODE_NULL = "验证码不能为空";
    //身份证号码为空
    String IDCARD_NULL = "身份证号码不能为空";
    //身份证号码输入错误
    String IDCARD_NO = "身份证号码输入错误";
    //日期为空
    String DATE_NULL = "日期不能为空";
    //日期输入错误
    String DATE_NO = "日期输入错误";
    String PASSWORD_DISAFFINITY = "两次密码输入不相同";

    //目标跳转的位置
    String INTENT_KEY_TO_CLASS = "login";
    //bean 内容
    String INTENT_KEY_BEAN = "bean";
}
