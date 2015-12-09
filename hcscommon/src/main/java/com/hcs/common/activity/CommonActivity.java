package com.hcs.common.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public abstract class CommonActivity extends Activity {

    private int layout;
    private boolean fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        if (fullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        this.initViews();
    }

    protected CommonActivity(int layout) {
        this.layout = layout;
        this.fullScreen = false;
    }

    protected CommonActivity(int layout, boolean fullScreen) {
        this.layout = layout;
        this.fullScreen = fullScreen;
    }

    protected abstract void initViews();

    protected void goToActivity(Activity currentActivity, Class<?> targetClass) {
        Intent i = new Intent(currentActivity, targetClass);
        startActivity(i);
    }

    protected void goToActivityAndFinishThis(Activity currentActivity, Class<?> targetClass) {
        this.goToActivity(currentActivity, targetClass);
        currentActivity.finish();
    }
}