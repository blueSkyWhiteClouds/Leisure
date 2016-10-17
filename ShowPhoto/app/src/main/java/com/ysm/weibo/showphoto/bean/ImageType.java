package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;

/**
 * 图片类别Bean
 * Created by ysm on 2016/10/5 0005.
 */
public class ImageType extends BmobObject {
    private String typeName;
    private String typeId;
    private String backgroundUrl;

    public ImageType() {
    }

    public ImageType(String typeName, String typeId) {
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    @Override
    public String toString() {
        return "ImageType{" +
                "typeName='" + typeName + '\'' +
                ", typeId='" + typeId + '\'' +
                ", backgroundUrl='" + backgroundUrl + '\'' +
                '}';
    }
}
