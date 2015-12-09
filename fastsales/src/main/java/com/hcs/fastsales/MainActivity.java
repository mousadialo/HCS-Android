package com.hcs.fastsales;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hcs.common.activity.CommonFullActivity;


public class MainActivity extends CommonFullActivity {

    ImageView ivLogo;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void initViews() {
        ivLogo =  (ImageView) findViewById(R.id.ivLogo);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ivLogo.setVisibility(View.INVISIBLE);
                goToActivityAndFinishThis(MainActivity.this, LoginActivity.class);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ivLogo.startAnimation(animation);
    }
}
