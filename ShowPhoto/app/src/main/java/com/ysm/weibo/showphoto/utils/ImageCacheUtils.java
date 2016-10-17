package com.ysm.weibo.showphoto.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by Administrator on 2016/10/7 0007.
 */
public class ImageCacheUtils {

    /**
     * 加载图片到ImageView
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadUrl(Context context, String url, ImageView imageView) {
        int screenW = DisplayUtils.getScreenW(context);
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .crossFade()
                .override(screenW / 2, screenW / 2)
                .into(imageView);
    }

    /**
     * 加载图片到PhotoView
     *
     * @param context
     * @param url
     * @param photoView
     */
    public static void loadUrlToPhotoView(Context context, String url, PhotoView photoView) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoView);
    }

    public static void clearImageCache(Context context){
        Glide.get(context).clearMemory();
    }
}
