package com.hcs.common.popup;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class PopupWUtils extends PopupWindow {
	
	@SuppressWarnings("deprecation")
	protected PopupWUtils (Activity activity, View layout) {
		super(activity);
		this.setContentView(layout);
		// Set content width and height
		this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		// Closes the popup window when touch outside of it - when looses focus
		this.setOutsideTouchable(true);
		this.setFocusable(true);
		// Show anchored to button
		this.setBackgroundDrawable(new BitmapDrawable());
	}
	
	public PopupWUtils (Activity activity, final View anchorView, View layout) {
		this(activity, layout);
		layout.post(new Runnable() {
	        public void run() {
	        	showAsDropDown(anchorView);
	        }
	    });
	}
	
	public PopupWUtils(Activity activity, View anchorView, int layoutId) {
		this (activity, anchorView, activity.getLayoutInflater().inflate(layoutId, null));
	}
}
