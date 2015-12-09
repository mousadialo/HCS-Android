package com.hcs.common.popup;

import com.hcs.common.R;
import com.hcs.common.utils.ViewUtils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PopupMessage extends PopupWUtils {
	
	final private static ViewGroup NULL = null;
	
	public PopupMessage(Activity activity, View anchorView, String message) {
		super(activity, anchorView, getLayout(activity, message));
		this.showMessage(anchorView);
	}
	
	public PopupMessage(Activity activity, View anchorView, int resource) {
		super(activity, anchorView, getLayout(activity, ViewUtils.getStringResource(activity, resource)));
		this.showMessage(anchorView);
	}
	
	private static View getLayout(Activity activity, String message) {
		View layout = activity.getLayoutInflater().inflate(R.layout.popup_message, NULL);
		TextView tvCaption = (TextView) layout.findViewById(R.id.tvCaption);
		tvCaption.setText(message);
		return layout;
	}
	
	private void showMessage(final View anchorView) {
		getContentView().post(new Runnable() {
	        public void run() {
	        	showAsDropDown(anchorView);
	        }
	    });
	}
}
