package com.hcs.common.task;

import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.utils.ViewUtils;

import android.app.Activity;
import android.util.Log;

/**
 * @author fernando.ramirez
 * Class to create a progress dialog and execute a long task
 */
public class MsgProgressTask extends CommonProgressTask {
	
	private static final String TAG = "MsgProgressTask";
	private String methodOne, methodTwo;
	
	/**
	 * Constructor to receive the current activity (Send two methods doInBackground & onPostExecute to execute as Strings)
	 * @param activity
	 */
	public MsgProgressTask(Activity activity) {
        super(activity);
	}
	
	public MsgProgressTask(Activity activity, Object classObject) {
        super(activity, classObject);
	}

	@Override
	protected String doInBackground(String... methodsName) {
		methodOne = methodsName[0];
		methodTwo = methodsName[1];
        Object value = super.executeMethod(methodOne);
		return value != null ? value.toString() : "";
	}
	
	@Override
	protected void onPostExecute(String stringMessage) {
		super.closeDialogs();
        if(stringMessage.equals(ViewUtils.completedTask)) {
            executeMethod(methodTwo);
        } else if (!stringMessage.equals(ViewUtils.uncompletedTask)) {
            new ViewDialog(activity).showInfoDialog(stringMessage);
        }
        try {
			this.finalize();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}
}