package com.ysm.weibo.showphoto.project.jokes.view;

import com.ysm.weibo.showphoto.bean.Comments;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 段子详情
 * Created by ysm on 2016/10/12 0012.
 */
public interface IJokesDetailsView extends IBaseMvpView {
    void onSendCommentsSuccess();

    void onQueryCommentsSuccess(List<Comments> data);

    void onQuerySingleJokes(JokesContent jokesContent, int type);
}
