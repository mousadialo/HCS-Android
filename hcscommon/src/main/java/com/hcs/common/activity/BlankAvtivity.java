package com.hcs.common.activity;


import android.app.Activity;
import android.os.Bundle;

import com.hcs.common.R;

public abstract class BlankAvtivity extends Activity {
	
	protected Activity currentActivity = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blank_layout);
		initViews();
	}
	
	protected abstract void initViews();
}
