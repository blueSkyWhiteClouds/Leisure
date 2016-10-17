package com.ysm.weibo.showphoto;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.ysm.weibo.showphoto.bean.UserInfo;
import com.ysm.weibo.showphoto.common.SystemUtils;
import com.ysm.weibo.showphoto.utils.MyLogUtils;
import com.ysm.weibo.showphoto.utils.SPUtil;

import java.util.Map;

/**
 * Created by ysm on 2016/10/12 0012.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView target = (ImageView) findViewById(R.id.splash_iv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "alpha", 0.5f, 1.0f);
        animator.setDuration(2000);
        animator.start();

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
//                SystemUtils.getLoginInfoBean(SplashActivity.this);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        });
    }
}
