package com.hcs.common.popup;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class PopupOnNewActivity extends PopupWUtils {

	public PopupOnNewActivity(final Activity currentActivity, final View layout) {
		super(currentActivity, layout);
		this.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				dismiss();
				currentActivity.finish();
			}
		});

		layout.post(new Runnable() {
			public void run() {
				if (!currentActivity.isFinishing()) {
					showAtLocation(layout, Gravity.CENTER, 0, 0);
				}
			}
		});
	}
}
