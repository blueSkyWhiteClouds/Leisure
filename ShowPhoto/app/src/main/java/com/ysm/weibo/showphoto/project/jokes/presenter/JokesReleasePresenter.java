package com.ysm.weibo.showphoto.project.jokes.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.ysm.weibo.showphoto.mvp.presenter.impl.MvpBasePresenterImpl;
import com.ysm.weibo.showphoto.project.jokes.model.JokesReleaseModel;
import com.ysm.weibo.showphoto.project.jokes.view.IJokesReleaseView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 发布段子P层
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesReleasePresenter extends MvpBasePresenterImpl<IJokesReleaseView> {
    private JokesReleaseModel releaseModel;

    public JokesReleasePresenter(Context context) {
        super(context);
        releaseModel = new JokesReleaseModel();
    }

    /**
     * 获取发布结果
     *
     * @param content
     */
    public void getReleaseResult(String content) {
        if (TextUtils.isEmpty(content)) {
            getView().onMessageCallback("内容不能为空!");
            return;
        }
        releaseModel.saveData(new String[]{content}, new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    getView().onMessageCallback("发布成功!!");
                    getView().onReleaseSuccess();
                } else {
                    getView().onMessageCallback("发布失败!");
                    getView().onFailed(e);
                }
            }
        });
    }
}
