package com.ysm.weibo.showphoto.project.jokes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.JokesContent;
import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ysm on 2016/10/12 0012.
 */
public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.JokesHolder> {
    private Context mContext;
    private LayoutInflater inflater;
    private List<JokesContent> mDatas;
    private OnItemClickListener onItemClickListener;
    private ExecutorService executorService = Executors
            .newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public JokesAdapter(Context context, List<JokesContent> data) {
        this.mContext = context;
        this.mDatas = data == null ? new ArrayList<JokesContent>() : data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public JokesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        JokesHolder jokesHolder = new JokesHolder(inflater.inflate(R.layout.item_jokes_list_item, parent, false));
        return jokesHolder;
    }

    @Override
    public void onBindViewHolder(JokesHolder holder, int position) {
        JokesContent jokesContent = mDatas.get(position);
        UserInfo user = jokesContent.getUser();
        holder.mUserTv.setText(user.getRealName());
        holder.mTimeTv.setText(jokesContent.getCreatedAt());
        holder.mContentTv.setText(jokesContent.getContent());
        holder.mCommentsTv.setTag(jokesContent.getObjectId());
//        this.executorService.
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class JokesHolder extends RecyclerView.ViewHolder {
        TextView mUserTv;
        TextView mTimeTv;
        TextView mContentTv;
        TextView mCommentsTv;

        public JokesHolder(View itemView) {
            super(itemView);
            mUserTv = (TextView) itemView.findViewById(R.id.jokes_list_item_user);
            mTimeTv = (TextView) itemView.findViewById(R.id.jokes_list_item_time);
            mContentTv = (TextView) itemView.findViewById(R.id.jokes_list_item_content_tv);
            mCommentsTv = (TextView) itemView.findViewById(R.id.jokes_list_item_comments_tv);
        }
    }

}
