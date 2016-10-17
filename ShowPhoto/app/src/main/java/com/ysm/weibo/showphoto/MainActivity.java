package com.ysm.weibo.showphoto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ysm.weibo.showphoto.bean.Tab;
import com.ysm.weibo.showphoto.project.jokes.JokesFragment;
import com.ysm.weibo.showphoto.project.mine.MineFragment;
import com.ysm.weibo.showphoto.project.photoview.ShowPhotoFragment;
import com.ysm.weibo.showphoto.project.videoview.VideoListFragment;
import com.ysm.weibo.showphoto.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面
 * Created by ysm on 2016/10/4 0004.
 */
public class MainActivity extends AppCompatActivity implements TabHost.OnTabChangeListener {
    private List<Tab> tabList; //底部导航Item集合
    private View view;  //视图
    private LayoutInflater mInflater;  //视图加载器
    private ImageView mBottomIv; //底部导航图片
    private TextView mBottomTv;  //底部导航文本
    private Bundle mBundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInflater = LayoutInflater.from(this);
        initTabData();
        initTabHost();
    }

    //初始化Tab数据
    private void initTabData() {
        tabList = new ArrayList<>();
        //添加照片Tab
        tabList.add(new Tab(R.drawable.main_photo_selector
                , R.string.main_photo_text, ShowPhotoFragment.class));
        //添加视频Tab
        tabList.add(new Tab(R.drawable.main_video_selector
                , R.string.main_video_text, VideoListFragment.class));
        //添加栏目Tab
        tabList.add(new Tab(R.drawable.main_programa_selector, R.string.main_programa_text, JokesFragment.class));
        //添加我的Tab
        tabList.add(new Tab(R.drawable.main_mine_selector, R.string.main_mine_text, MineFragment.class));
    }

    //初始化主页选项卡视图
    private void initTabHost() {
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        //绑定tabhost
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setDividerDrawable(null);
        for (int i = 0; i < tabList.size(); i++) {
            Tab tab = tabList.get(i);
            //讲fragmnet添加到FragmentTabHost组件上
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(getStringTitle(tab)).setIndicator(getView(tab));
            //添加子页面Fragment
            tabHost.addTab(tabSpec, tab.getFragmentClass(), getBundle(tab));
        }
        tabHost.getTabWidget().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        tabHost.setOnTabChangedListener(this);
    }

    /**
     * 初始化底部导航，tab内容
     *
     * @param tab
     * @return
     */
    private View getView(Tab tab) {
        view = mInflater.inflate(R.layout.view_tab_indicator, null);
        mBottomIv = (ImageView) view.findViewById(R.id.iv_tab);
        mBottomTv = (TextView) view.findViewById(R.id.tv_tab);
        //判断文本是否存在，不存在就隐藏
        if (tab.getTitle() == 0) {
            mBottomTv.setVisibility(View.GONE);
        } else {
            mBottomTv.setVisibility(View.VISIBLE);
            mBottomTv.setText(getString(tab.getTitle()));
        }
        mBottomIv.setImageResource(tab.getImage());
        return view;
    }

    /**
     * 存放传递数据
     *
     * @param tab
     * @return
     */
    private Bundle getBundle(Tab tab) {
        if (mBundle == null) {
            mBundle = new Bundle();
        }
        mBundle.putString("titile", getStringTitle(tab));
        return mBundle;
    }

    public String getStringTitle(Tab tab) {
        if (tab.getTitle() == 0) {
            return "";
        } else {
            return getString(tab.getTitle());
        }
    }

    @Override
    public void onTabChanged(String tabId) {
//        ToastUtil.showToast(this,tabId);
    }

}
