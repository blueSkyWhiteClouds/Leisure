package com.ysm.weibo.showphoto.widget;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.ysm.weibo.showphoto.callback.OnItemClickListener;


/**
 *
 * Created by ysm on 2015/9/11.
 */
public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final SparseArray<View> views;
    private OnItemClickListener mListener;

    public BaseHolder(View itemView, OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.views = new SparseArray<View>();
        this.mListener = listener;
    }

    public View getView() {
        return this.itemView;
    }

    public <T extends View> T getView(int viewId) {
        return retrieveView(viewId);
    }

    protected <T extends View> T retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = this.itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置TextView的文本
     */
    public BaseHolder setText(int viewId, String value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * 设置TextView的文本
     */
    public BaseHolder setText(int viewId, int value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public BaseHolder setTextColor(int viewId, int color) {
        TextView view = retrieveView(viewId);
        view.setTextColor(color);
        return this;
    }


    public BaseHolder setDrawable(int viewId, int left, int top, int right, int bottom) {
        TextView view = retrieveView(viewId);
        view.setCompoundDrawablePadding(10);
        view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    /**
     * 设置控件背景
     */
    public BaseHolder setBackgroundRes(int viewId, int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    /**
     * 设置图片背景
     */
    public BaseHolder setImageResource(int viewId, int imageResId) {
        ImageView view = retrieveView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    /**
     * 设置图片BitMap
     */
    public BaseHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置控件是否可见
     */
    public BaseHolder setVisible(int viewId, boolean visible) {
        View view = retrieveView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置TextView控件连接
     */
    public BaseHolder linkfy(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    /**
     * 设置TextView图标
     *
     * @param viewId
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @return
     */
    public BaseHolder setTextDrawable(int viewId, int left, int top, int right, int bottom) {
        TextView view = retrieveView(viewId);
        view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        return this;
    }

    /**
     * 设置控件点击
     */
    public BaseHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
//        view.setSelected(true);
        return this;
    }

    /**
     * 设置控件触摸
     */
    public BaseHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 设置控件点长按
     */
    public BaseHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
//            Log.i("INFO", "mListener=" + mListener);
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
}