package com.ysm.weibo.showphoto.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesContent extends BmobObject {
    private String title;//标题
    private String content;//内容
    private UserInfo user;//发布者
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户
    private Integer commentsNum;//评论数
    private Integer praiseNum;//赞
    private Praise praiseRecord;//
    private boolean isPraise=false;
    private long currentTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Integer getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(Integer praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Praise getPraiseRecord() {
        return praiseRecord;
    }

    public void setPraiseRecord(Praise praiseRecord) {
        this.praiseRecord = praiseRecord;
    }

    public boolean isPraise() {
        return isPraise;
    }

    public void setPraise(boolean praise) {
        isPraise = praise;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public String toString() {
        return "JokesContent{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", likes=" + likes +
                ", commentsNum=" + commentsNum +
                ", praiseNum=" + praiseNum +
                ", praiseRecord=" + praiseRecord +
                '}';
    }
}
