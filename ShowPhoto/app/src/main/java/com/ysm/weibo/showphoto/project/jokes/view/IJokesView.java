package com.ysm.weibo.showphoto.project.jokes.view;

import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * 段子 V 视图层接口
 * Created by ysm on 2016/10/11 0011.
 */
public interface IJokesView extends IBaseMvpView {
    void onJokesContentRefresh(List<JokesContent> data);

    void onJokesContentLoadMore(List<JokesContent> data);

    void onPraiseUpdateCallback(int position, JokesContent jokesContent);

}
