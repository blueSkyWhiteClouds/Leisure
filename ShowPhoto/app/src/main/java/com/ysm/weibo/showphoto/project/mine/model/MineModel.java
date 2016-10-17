package com.ysm.weibo.showphoto.project.mine.model;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.ysm.weibo.showphoto.utils.ImageCacheUtils;
import com.ysm.weibo.showphoto.utils.SPUtil;

/**
 * Created by ysm on 2016/10/16 0016.
 */
public class MineModel {

    ClearImageDiskCache clearCacheThread;

    public void clearLocalFile(Context context) {
        SPUtil spUtil = SPUtil.getSPUtilConfig(context, SPUtil.NAME_LOGIN_INFO);
        spUtil.clear();
    }

    public void clearDiskCache(Context context) {
        ImageCacheUtils.clearImageCache(context);
        clearCacheThread = new ClearImageDiskCache(context);
        clearCacheThread.start();
    }

    public void stopClearDiskCache() {
        if (clearCacheThread != null) {
            clearCacheThread.interrupt();
            clearCacheThread = null;
        }
    }

    class ClearImageDiskCache extends Thread {
        private Context context;

        public ClearImageDiskCache(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            Glide.get(context).clearDiskCache();
        }
    }
}
