package com.hcs.common.dialog;

import com.hcs.common.R;
import com.hcs.common.utils.ViewUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * @author fernando.ramirez Class to show dialogs
 */
public class ViewDialog extends AlertDialog {

	private AlertDialog alertDialog = null;
	private AlertDialog.Builder alertDialogBuilder = null;
	private Activity activity = null;

	public ViewDialog(Activity activity) {
		super(activity.getApplicationContext());
		this.alertDialog = this;
		this.alertDialogBuilder = new AlertDialog.Builder(activity);
		this.activity = activity;
	}

	/**
	 * Method to show information dialog with a close button
	 * 
	 * @param activity
	 *            (Current activity)
	 * @param message
	 *            (Message to show)
	 */
	public void showInfoDialog(String message) {
		this.closeDialog();
		alertDialogBuilder.setMessage(message);
		String txtClose = ViewUtils.getStringResource(activity,	R.string.a_close);
		alertDialogBuilder.setPositiveButton(txtClose, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						closeDialog();
					}
				});
		alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	/**
	 * Method to show information dialog with a close button
	 * 
	 * @param activity
	 *            (Current activity)
	 * @param resource
	 *            (Resource to show, e.g. R.string.txtClose = Close)
	 */
	public void showInfoDialog(int resource) {
		showInfoDialog (ViewUtils.getStringResource(this.activity, resource));
	}
	
	private void closeDialog() {
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.dismiss();
			alertDialog.cancel();
		}
	}

	@Override
	public void dismiss() {
		super.dismiss();
		this.closeDialog();
	}
}