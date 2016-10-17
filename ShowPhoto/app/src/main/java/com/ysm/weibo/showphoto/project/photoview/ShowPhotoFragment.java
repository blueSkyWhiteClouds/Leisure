package com.ysm.weibo.showphoto.project.photoview;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.bean.ImageType;
import com.ysm.weibo.showphoto.mvp.view.impl.MvpBaseFragment;
import com.ysm.weibo.showphoto.project.photoview.presenter.ShowPhotoPresenter;
import com.ysm.weibo.showphoto.project.photoview.view.IShowPhotoView;
import com.ysm.weibo.showphoto.utils.ListUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.ParserJson;
import com.ysm.weibo.showphoto.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;

/**
 * 照片展示
 * Created by ysm on 2016/10/4 0004.
 */
public class ShowPhotoFragment extends MvpBaseFragment<IShowPhotoView, ShowPhotoPresenter> implements IShowPhotoView {
    public static final String PHOTO_TYPE = "type";
    public static final String PHOTO_TYPE_ID = "typeId";
    private View view;
    private TabLayout mPhotoTabLayout;
    private ViewPager mPhotoPager;
    private Map<String, PhotoListFragment> mFragmentMap = new HashMap<>();

    private  ProgressDialog progressDialog;
    @Override
    protected View initView() {
        view = View.inflate(mActivity, R.layout.fragment_photo, null);
        mPhotoTabLayout = (TabLayout) view.findViewById(R.id.photo_tab_layout);
        mPhotoPager = (ViewPager) view.findViewById(R.id.photo_viewpager);
        getBarLayout().setTitleText("图片展览");
        getBarLayout().hideBackImage();
        return view;
    }

    @Override
    public ShowPhotoPresenter createPresenter() {
        return new ShowPhotoPresenter(getContext());
    }

    @Override
    public IShowPhotoView createView() {
        return this;
    }

    @Override
    protected void initData() {
        super.initData();
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setMessage("正在加载图片类别...");
        progressDialog.setCancelable(false);
        mPhotoPager.setOffscreenPageLimit(3);
        getPresenter().getPhotoTypeResult();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void photoTypeCallback(List<ImageType> data) {
        progressDialog.dismiss();
        if (!ListUtils.isEmpty(data)) {
            for (int i = 0; i < data.size(); i++) {
                ImageType type = data.get(i);
                mPhotoTabLayout.addTab(mPhotoTabLayout.newTab().setText(type.getTypeName()));
            }
            PhotoPagerAdapter adapter = new PhotoPagerAdapter(getChildFragmentManager(), data);
            mPhotoPager.setAdapter(adapter);
            mPhotoTabLayout.setupWithViewPager(mPhotoPager);
        }
    }

    @Override
    public void onFailed(BmobException e) {
        progressDialog.dismiss();
        MyLogUtils.d("TAG", "加载图片类别失败! msg=" + e.getMessage() + ",errorCode=" + e.getErrorCode());
    }

    @Override
    public void onMessageCallback(String msg) {
        ToastUtil.showToast(getContext(), msg);
    }

    class PhotoPagerAdapter extends FragmentPagerAdapter {
        private List<ImageType> data = new ArrayList<>();

        public PhotoPagerAdapter(FragmentManager fm, List<ImageType> data) {
            super(fm);
            this.data.addAll(data);
        }

        @Override
        public Fragment getItem(int position) {
            ImageType imageType = data.get(position);
            return getFragment(imageType.getTypeName(), imageType.getTypeId());
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return data.get(position).getTypeName();
        }
    }

    /**
     * 获取fragment
     *
     * @param type
     * @return
     */
    private PhotoListFragment getFragment(String type, String typeId) {
        PhotoListFragment fragment;
        if (mFragmentMap.get(type) == null) {
            fragment = new PhotoListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(PHOTO_TYPE, type);
            bundle.putString(PHOTO_TYPE_ID, typeId);
            fragment.setArguments(bundle);
        } else {
            fragment = mFragmentMap.get(type);
        }
        return fragment;
    }
}
