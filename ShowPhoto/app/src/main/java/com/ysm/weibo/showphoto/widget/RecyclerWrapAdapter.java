package com.ysm.weibo.showphoto.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class RecyclerWrapAdapter extends Adapter {

	private Adapter mAdapter;
	private ArrayList<View> mHeaderViewInfos;
	private ArrayList<View> mFooterViewInfos;

	public RecyclerWrapAdapter(ArrayList<View> mHeaderViewInfos,
			ArrayList<View> mFooterViewInfos, Adapter adapter) {
		this.mAdapter = adapter;
		if(mHeaderViewInfos==null){
			this.mHeaderViewInfos = new ArrayList<View>();
		}else{
			this.mHeaderViewInfos = mHeaderViewInfos;
		}
		if(mFooterViewInfos==null){
			this.mFooterViewInfos = new ArrayList<View>();
		}else{
			this.mFooterViewInfos = mFooterViewInfos;
		}
	}
	
    public int getHeadersCount() {
        return mHeaderViewInfos.size();
    }

    public int getFootersCount() {
        return mFooterViewInfos.size();
    }

    public boolean isEmpty() {
        return mAdapter == null;
    }

	@Override
	public int getItemCount() {
        if (mAdapter != null) {
            return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
        } else {
            return getFootersCount() + getHeadersCount();
        }
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		//绑定数据
		//header
		int numHeaders = getHeadersCount();
        if (position < numHeaders) {
        	View headerView = mHeaderViewInfos.get(position);
            return ;
        }
        //adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
            	mAdapter.onBindViewHolder(holder, adjPosition);
                return ;
            }
        }
        //footer
	}
	
	@Override
	public int getItemViewType(int position) {
		//判断position位置对应的View是什么类型的。
		//header
		int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return RecyclerView.INVALID_TYPE;
        }
        //adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
		return RecyclerView.INVALID_TYPE-1;
	}
	
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//header
        if (viewType==RecyclerView.INVALID_TYPE) {
            return new HeaderViewHolder(mHeaderViewInfos.get(0));
        }else if(viewType==RecyclerView.INVALID_TYPE-1){
        	//footer
        	return new HeaderViewHolder(mFooterViewInfos.get(0));
        }
        //adapter
		return mAdapter.onCreateViewHolder(parent, viewType);
	}
	
	private static class HeaderViewHolder extends ViewHolder{

		public HeaderViewHolder(View view) {
			super(view);
		}
		
	}

}
