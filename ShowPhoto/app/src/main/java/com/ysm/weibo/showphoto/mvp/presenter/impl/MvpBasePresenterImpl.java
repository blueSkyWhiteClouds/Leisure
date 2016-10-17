package com.ysm.weibo.showphoto.mvp.presenter.impl;

import android.content.Context;

import com.ysm.weibo.showphoto.mvp.presenter.IMvpPresenter;
import com.ysm.weibo.showphoto.mvp.view.IBaseMvpView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 统一管理View层绑定和解除绑定
 * Created by ysm on 2016/10/4 0004.
 */
public class MvpBasePresenterImpl<V extends IBaseMvpView> implements IMvpPresenter<V> {
    private WeakReference<Context> mWeakContext;
    private WeakReference<V> mWeakView;
    private V proxyView;

    public MvpBasePresenterImpl(Context context) {
        this.mWeakContext = new WeakReference<>(context);
    }

    public Context getContext() {
        return this.mWeakContext.get();
    }

    public V getView() {
        return proxyView;
    }

    /**
     * 检测view是否为空对象
     *
     * @return
     */
    public boolean isAttachView() {
        if (this.mWeakView != null && this.mWeakView.get() != null) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void attachView(V view) {
        this.mWeakView = new WeakReference<>(view);
        MvpViewInvocationHandler invocationHandler = new MvpViewInvocationHandler(this.mWeakView.get());
        // 采用动态代理
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(),
                view.getClass().getInterfaces(), invocationHandler);
    }

    @Override
    public void detachView() {
        if (this.mWeakView != null) {
            this.mWeakView.clear();
            this.mWeakView = null;
        }
    }

    private class MvpViewInvocationHandler implements InvocationHandler {
        private IBaseMvpView mvpView;

        public MvpViewInvocationHandler(IBaseMvpView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (isAttachView()) {
                return method.invoke(mvpView, args);
            }
            return null;
        }
    }
}
