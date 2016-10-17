package com.ysm.weibo.showphoto.bean;

/**
 * 底部导航
 * Created by Administrator on 2016/5/28.
 */
public class Tab {
    private int image;
    private int title;
    private Class fragmentClass; //子页面

    public Tab() {
    }

    public Tab(int image, int title, Class fragmentClass) {
        this.image = image;
        this.title = title;
        this.fragmentClass = fragmentClass;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class fragmentClass) {
        this.fragmentClass = fragmentClass;
    }
}
