package com.ysm.weibo.showphoto.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ysm.weibo.showphoto.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/11.
 *
 * RecyclerView of Adapter
 */
public abstract class SimpleAdapterHelper<T> extends RecyclerView.Adapter<BaseHolder> {
    private int layoutId;

    private List<T> mDatas;

    private OnItemClickListener mListener;


    public void setOnRecyclerItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public SimpleAdapterHelper(int layoutId) {
        this.layoutId = layoutId;
        this.mDatas = new ArrayList<T>();
    }

    public SimpleAdapterHelper(int layoutId, List<T> data) {
        this.layoutId = layoutId;
        this.mDatas = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        BaseHolder mAdapter = new BaseHolder(mView, mListener);
        return mAdapter;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
//        LogUtils.w("data size : " + mDatas.size());
        return mDatas.size();
    }

    /***
     * 设置View 内容
     * @param holder
     * @param item
     * @param position
     */
    public abstract void convert(BaseHolder holder, T item, int position);

    /***
     * 取得指定位置的数据
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (position >= mDatas.size())
            return null;

        return mDatas.get(position);
    }

    /***
     * 取得数据组
     * @return
     */
    public List<T> getDatas() {
        return mDatas;
    }

    /***
     * 设置，替换 指定的内容，
     * @param oldElem
     * @param newElem
     */
    public void set(T oldElem, T newElem) {
        set(mDatas.indexOf(oldElem), newElem);
    }

    /***
     * 设置，替换 指定位置的内容，
     * @param index
     * @param elem
     */
    public void set(int index, T elem) {
        mDatas.set(index, elem);
        notifyDataSetChanged();
    }

    /***
     * 添加 内容
     * @param elem
     */
    public void add(T elem) {
        mDatas.add(elem);
        notifyItemInserted(0);
    }


    public void addSize(int size,List<T> elem){
        mDatas.clear();
        if(size > elem.size()){
            size = elem.size();
        }
        for (int i = 0; i < size; i++) {
            mDatas.add(i, elem.get(i));
        }
        notifyDataSetChanged();
    }

    /***
     * 添加内容到指定位置
     * @param position
     * @param elem
     */
    public void add(int position, T elem) {
        mDatas.add(position, elem);
        notifyItemInserted(position);
    }

    /***
     * 添加所有内容，清掉之前的数据。
     * @param elem
     */
    public void addAll(List<T> elem) {
        if(mDatas == null){
            mDatas = new ArrayList<>();
        }

        mDatas.clear();
        if(elem != null){
            mDatas.addAll(elem);
        }
        notifyDataSetChanged();
    }

    /***
     * 添加内容数据
     * @param datas
     */
    public void addData(List<T> datas) {
        int size = mDatas.size();
        addData(size, datas);
    }

    /***
     * 添加内容数据到指定位置
     * @param datas
     */
    public void addData(int position, List<T> datas) {
        if (datas != null && datas.size() > 0) {
            this.mDatas.addAll(datas);
            this.notifyItemRangeChanged(position, datas.size());
        }
    }

    /****
     * 清除指定内容
     * @param elem
     */
    public void remove(T elem) {
        mDatas.remove(elem);
        notifyDataSetChanged();
    }

    /****
     * 清除指定位置的内容
     * @param position
     */
    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /***
     * 当前数据里是否包含
     * @param elem
     * @return
     */
    public boolean contains(T elem) {
        return mDatas.contains(elem);
    }

    /**
     * Clear data list
     */
    public void clear() {
        int itemCount = mDatas.size();
        mDatas.clear();
        this.notifyItemRangeRemoved(0, itemCount);
    }

}
