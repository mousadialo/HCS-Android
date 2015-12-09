package com.hcs.common.activity;

/**
 * Created by fernando.ramirez on 22/06/2015.
 */
public abstract class CommonFullActivity extends CommonActivity {

    public CommonFullActivity(int layout) {
        super(layout, true);
    }

    protected abstract void initViews();
}
