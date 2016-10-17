package com.ysm.weibo.showphoto.utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * json解析
 */
public class ParserJson {

    /**
     * Json解析公用方法 数组
     *
     * @param json  解析参数
     * @param token 解析的类型 new TypeToken<T>(){}
     *              ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList<T>>(){});
     * @return
     */
    public static <T> T fromJson(JsonElement json, TypeToken<T> token) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Json解析公用方法 数组
     *
     * @param json 解析参数
     * @param type 解析的类型 new TypeToken<T>(){}.getType()
     *             ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList<T>>(){});
     * @return
     */
    public static <T> T fromJson(JsonElement json, Type type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Json解析公用方法 单个类
     *
     * @param json 解析参数
     * @param t    解析的类。 class
     * @return
     */
    public static <T> T fromJson(JsonElement json, Class<T> t) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, t);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Json解析公用方法 数组
     *
     * @param json  解析参数
     * @param token 解析的类型 new TypeToken<T>(){}
     *              ArrayList<T> temp = ParserJson.fromJson(data, new TypeToken<ArrayList<T>>(){});
     * @return
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, token.getType());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Json解析公用方法 单个类
     *
     * @param json 解析参数
     * @param t    解析的类。 class
     * @return
     */
    public static <T> T fromJson(String json, Class<T> t) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, t);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 把数据转换成Json结构
     *
     * @param mType Object的数据，支持所有类型
     * @return
     */
    public static String toJson(Object mType) {
        Gson mGson = new Gson();
        String mResult = mGson.toJson(mType);
        return mResult;
    }
}
