package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class VideoContent extends BmobObject {
    private String name;//标题
    private String content;//视频内容
    private String url;//视频url
    private String source;//来源
    private String playCount;//播放次数
    private String iconUrl;//视频缩略图

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String toString() {
        return "VideoContent{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", source='" + source + '\'' +
                ", playCount='" + playCount + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                '}';
    }
}
