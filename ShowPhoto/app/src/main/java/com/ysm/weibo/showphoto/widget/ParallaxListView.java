package com.ysm.weibo.showphoto.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

import com.ysm.weibo.showphoto.R;
import com.ysm.weibo.showphoto.utils.MyLogUtils;

/**
 * Created by ysm on 2016/10/15 0015.
 */
public class ParallaxListView extends ListView {
    private ImageView mImageView;
    //定义ImageView 最大可拉伸的高度
    private int mDrawableMaxHeight = -1;
    //定义ImageView 初始加载的高度
    private int mImageViewHeight = -1;
    //图片默认高度
    private int mDefaultImageViewHeight = 0;

    public ParallaxListView(Context context) {
        super(context);
        init(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mDefaultImageViewHeight = context.getResources().getDimensionPixelSize(R.dimen.user_background_img);
        mImageViewHeight = mDefaultImageViewHeight;
//        mDrawableMaxHeight = mDefaultImageViewHeight * 2;
    }

    public void setParallaxImageView(ImageView imageView) {
        this.mImageView = imageView;
    }

//    /**
//     * 设置缩放级别，控制图片的最大拉伸高度
//     * 在界面加载完毕的时候调用
//     *
//     * @param zoomRatio
//     */
//    public void setViewsBounds(double zoomRatio) {
//        if (mImageViewHeight == -1) {
//            mImageViewHeight = mImageView.getHeight();
//            if (mImageViewHeight < 0) {
//                mImageViewHeight = mDefaultImageViewHeight;
//            }
//        }
//        MyLogUtils.d(MyLogUtils.TAG, "height=" + mImageViewHeight);
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //如何控制图片减小的高度？---监听listView的头部滑出去的距离
        View header = (View) mImageView.getParent();
        //头部滑出去的距离<0
        //大于初始高度
        if (header.getTop() > 0 && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = Math.max(mImageView.getHeight() + header.getTop(), mImageViewHeight);
            //跳转ImageView 所在容器的高度
            header.layout(header.getLeft(), 0, header.getRight(), header.getHeight());
            mImageView.requestLayout();
        }
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //当listview , scrollview 滑动过头的时候回调此方法
        boolean isCollapse = resizeOverScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        return isCollapse ? true : super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    private boolean resizeOverScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //deltaY 超出滑动的时候没毫秒滑动的距离  （-往下拉，+往上拉）
        //下拉的过程当中，不断地控制ImageView的高度
        if (deltaY < 0) {
            mImageView.getLayoutParams().height =
                    mImageView.getHeight() >= mDefaultImageViewHeight * 2 ? mDefaultImageViewHeight * 2 : mImageView.getHeight() - deltaY;
            //重新摆放ImageView的高度
            mImageView.requestLayout();
        } else {
            //不松开往上拉的时候应该要将图片的高度不断减小
            if (mImageView.getHeight() > mImageViewHeight) {
                mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY > mImageViewHeight ? mImageView.getHeight() - deltaY : mImageViewHeight;
                mImageView.requestLayout();
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //手指松开 让图片变回原大小
            ResetAnimation animation = new ResetAnimation(mImageView, mImageViewHeight);
            mImageView.startAnimation(animation);

        }
        return super.onTouchEvent(ev);
    }

    public class ResetAnimation extends Animation {

        private ImageView imageView;
        private int targetHeight;
        private final int originalHeight;
        private int extraHeight;

        public ResetAnimation(ImageView imageView, int targetHieght) {
            this.imageView = imageView;
            //动画执行之后的高度
            this.targetHeight = targetHieght;
            //动画执行之前的高度
            this.originalHeight = mImageView.getHeight();
            //高度差
            extraHeight = originalHeight - targetHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            //
            this.imageView.getLayoutParams().height = (int) (originalHeight - extraHeight * interpolatedTime);
            this.imageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
