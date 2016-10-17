package com.ysm.weibo.showphoto.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class WrapRecyclerView extends RecyclerView {
	private ArrayList<View> mHeaderViewInfos =  new ArrayList<View>();
    private ArrayList<View> mFooterViewInfos =  new ArrayList<View>();
	private Adapter mAdapter;

	public ArrayList<View> getHeaderViewInfos() {
		return mHeaderViewInfos;
	}

	public WrapRecyclerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public WrapRecyclerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 为RecyclerView添加头部
	 * @param view
	 */
	public void addHeaderView(View view){
		mHeaderViewInfos.add(view);
		 if (mAdapter != null) {
	            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
	                mAdapter = new RecyclerWrapAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
	            }
		 }
	}
	
	/**
	 * 为RecyclerView添加尾部
	 * @param view
	 */
	public void addFooterView(View view){
		mFooterViewInfos.add(view);
		 // Wrap the adapter if it wasn't already wrapped.
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecyclerWrapAdapter)) {
                mAdapter = new RecyclerWrapAdapter(mHeaderViewInfos, mFooterViewInfos, mAdapter);
            }
        }
	}

	/**
	 * 设置适配器
	 */
	public void setAdapter(Adapter adapter){
		if(isEmptyHF()){
			super.setAdapter(adapter);
		}else{
			adapter = new RecyclerWrapAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
			super.setAdapter(adapter);
		}
		mAdapter = adapter;
	}

	public boolean isEmptyHF(){
		return mHeaderViewInfos.isEmpty() && mFooterViewInfos.isEmpty();
	}
}
