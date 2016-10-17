package com.ysm.weibo.showphoto.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * 类型转换 工具
 */
public class ParseFormUtils {

    /***
     * String to int
     *
     * @param str
     * @return
     */
    public static int parseInt(String str) {
        try {
//            LogUtils.d("TAG","parseInt=" + str);
            str = str.replaceAll(",", "");
            return Integer.parseInt(str);
        } catch (Exception e) {
            if (TextUtils.isEmpty(str)) {
                MyLogUtils.i(MyLogUtils.TAG,"内容为空");
            } else {
                MyLogUtils.i(MyLogUtils.TAG,str);
            }
            return 0;
        }
    }

    /****
     * String to float
     *
     * @param str
     * @return
     */
    public static float parseFloat(String str) {
        try {
            str = str.replaceAll(",", "");
            return Float.parseFloat(str);
        } catch (Exception e) {
            if (TextUtils.isEmpty(str)) {
                MyLogUtils.i(MyLogUtils.TAG,"内容为空");
            } else {
                MyLogUtils.i(MyLogUtils.TAG,str);
            }
            return 0.0f;
        }
    }

    /****
     * String to float
     *
     * @param str
     * @return
     */
    public static double parseDouble(String str) {
        try {
            str = str.replaceAll(",", "");
            return Double.parseDouble(str);
        } catch (Exception e) {
            if (TextUtils.isEmpty(str)) {
                MyLogUtils.i(MyLogUtils.TAG,"内容为空");
            } else {
                MyLogUtils.i(MyLogUtils.TAG,str);
            }
            return 0.0d;
        }
    }

    /****
     * String to float
     *
     * @param str
     * @return
     */
    public static double parseDouble_2(String str) {
        try {
            str = str.replaceAll(",", "");
            return Double.parseDouble(str);
        } catch (Exception e) {
            if (TextUtils.isEmpty(str)) {
                MyLogUtils.i(MyLogUtils.TAG,"内容为空");
            } else {
                MyLogUtils.i(MyLogUtils.TAG,str);
            }
            return 0.0d;
        }
    }

    /****
     * String to float
     *
     * @param str
     * @return
     */
    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            if (TextUtils.isEmpty(str)) {
                MyLogUtils.i(MyLogUtils.TAG,"内容为空");
            } else {
                MyLogUtils.i(MyLogUtils.TAG,str);
            }
            return 0;
        }
    }

    /****
     * 传换值为金额的格式
     *
     * @param value
     * @return
     */
    public static String moneyForm(double value) {
        if (value == 0) {
            return "0.00";
        }

        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /****
     * 把数据内容按 “，”拆分
     *
     * @param str
     * @return
     */
    public static String[] getArrays(String str) {
        if (!TextUtils.isEmpty(str))
            return str.split(",");
        else
            return null;
    }

    /***
     * 取得 TextView 的值
     *
     * @param editText
     * @return
     */
    public static String getTextName(TextView editText) {
        String text = editText.getText().toString().trim();
        return TextUtils.isEmpty(text) ? "" : text;
    }
}
