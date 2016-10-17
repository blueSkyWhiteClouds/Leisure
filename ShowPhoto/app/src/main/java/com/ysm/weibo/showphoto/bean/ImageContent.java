package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class ImageContent extends BmobObject {
    private String imageName;
    private BmobFile imageFile;
    private String iamgeUrl;
    private String typeId;
    private String typeName;
    private String imageIntroduce;


    public ImageContent() {
        this.setTableName("imageContent_tab");
    }

    public ImageContent(String imageName, BmobFile imageFile, String iamgeUrl) {
        this.imageName = imageName;
        this.imageFile = imageFile;
        this.iamgeUrl = iamgeUrl;
    }

    public ImageContent(String imageName, BmobFile imageFile, String iamgeUrl, String typeId, String typeName, String imageIntroduce) {
        this.imageName = imageName;
        this.imageFile = imageFile;
        this.iamgeUrl = iamgeUrl;
        this.typeId = typeId;
        this.typeName = typeName;
        this.imageIntroduce = imageIntroduce;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public BmobFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(BmobFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getIamgeUrl() {
        return iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
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

    public String getImageIntroduce() {
        return imageIntroduce;
    }

    public void setImageIntroduce(String imageIntroduce) {
        this.imageIntroduce = imageIntroduce;
    }

    @Override
    public String toString() {
        return "ImageContent{" +
                "imageName='" + imageName + '\'' +
                ", imageFile=" + imageFile +
                ", iamgeUrl='" + iamgeUrl + '\'' +
                ", typeId='" + typeId + '\'' +
                ", typeName='" + typeName + '\'' +
                ", imageIntroduce='" + imageIntroduce + '\'' +
                '}';
    }
}

