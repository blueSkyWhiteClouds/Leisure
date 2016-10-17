package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class ImageTypeBean extends BmobObject {
    private String typeId;
    private String typeName;

    public ImageTypeBean() {
        this.setTableName("imageType_tab");
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "ImageTypeBean{" +
                "typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}
