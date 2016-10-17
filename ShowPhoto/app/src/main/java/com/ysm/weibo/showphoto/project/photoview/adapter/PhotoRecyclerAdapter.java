package com.ysm.weibo.showphoto.project.photoview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.ImageContent;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;
import com.ysm.weibo.showphoto.utils.ImageCacheUtils;
import com.ysm.weibo.showphoto.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片列表适配器
 * Created by ysm on 2016/10/6 0006.
 */
public class PhotoRecyclerAdapter extends RecyclerView.Adapter<PhotoRecyclerAdapter.PhotoViewHolder> {

    private LayoutInflater inflater;
    private List<ImageContent> mData;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhotoRecyclerAdapter(Context context, List<ImageContent> data) {
        this.mContext = context;
        this.mData = ListUtils.isEmpty(data) ? new ArrayList<ImageContent>() : data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final PhotoViewHolder holder = new PhotoViewHolder(inflater.inflate(R.layout.recycler_item_photo, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
        final ImageContent item = mData.get(position);
        ImageCacheUtils.loadUrl(mContext, item.getIamgeUrl(), holder.imageView);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(v, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_photo_iv);
        }
    }

    public void addAll(List<ImageContent> data) {
        mData.clear();
        mData.addAll(data);
        this.notifyDataSetChanged();
    }

    /***
     * 添加内容数据
     *
     * @param datas
     */
    public void addData(List<ImageContent> datas) {
        int size = mData.size();
        addData(size, datas);
    }

    /***
     * 添加内容数据到指定位置
     *
     * @param datas
     */
    public void addData(int position, List<ImageContent> datas) {
        if (datas != null && datas.size() > 0) {
            this.mData.addAll(datas);
            this.notifyItemRangeChanged(position, datas.size());
        }
    }
}
