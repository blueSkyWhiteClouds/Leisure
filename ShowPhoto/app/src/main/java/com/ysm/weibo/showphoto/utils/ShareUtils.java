package com.ysm.weibo.showphoto.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

/**
 * Created by ysm on 2016/7/20 0020.
 */
public class ShareUtils {
    private Context mContext;
//    private IWXAPI api;

    public ShareUtils(Context context) {
        this.mContext = context;
//        api = WXAPIFactory.createWXAPI(context, APPID, false);
//        api.registerApp(APPID);
    }

    public void startShare(Uri uri) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(uri.toString()));
//        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "FASDFASDFSAF");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "123452512");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shareIntent.setType("image/*");
//        shareIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    public void startShare(String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

//    /**
//     * 微信
//     * 发送到聊天界面
//     *
//     * @param url
//     * @param title
//     * @param description
//     */
//    public void SendMessageToWXSceneSession(String url, String title, String description) {
//        //初始化一个WXWebpageObject 对象，填写url
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;//网页标题
//        msg.description = description;//网页描述
//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//        msg.thumbData = WXUtil.bmpToByteArray(thumb, true);
//        //构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneSession;
//        api.sendReq(req);
//    }

//    /**
//     * 发送到朋友圈
//     *
//     * @param url
//     * @param title
//     * @param description
//     */
//    public void SendMessageToWXSceneTimeline(String url, String title, String description) {
//        //初始化一个WXWebpageObject 对象，填写url
//        WXWebpageObject webpage = new WXWebpageObject();
//        webpage.webpageUrl = url;
//        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
//        WXMediaMessage msg = new WXMediaMessage(webpage);
//        msg.title = title;//网页标题
//        msg.description = description;//网页描述
//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//        msg.thumbData = WXUtil.bmpToByteArray(thumb, true);
//        //构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");
//        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
//        api.sendReq(req);
//    }

//    private String buildTransaction(final String type) {
//        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
//    }
}
