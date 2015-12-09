package com.hcs.common.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hcs.common.R;
import com.hcs.common.dialog.ViewDialog;
import com.hcs.common.utils.ViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fernando.ramirez
 * Class to create a progress dialog and execute a long task
 */
public class MapErrorProgressTask extends CommonProgressTask {
	
	private static final String TAG = "MapErrorProgressTask";
	private String methodOne, methodTwo;
    private Map<View, String> mapError;

	/**
	 * Constructor to receive the current activity (Send two methods doInBackground & onPostExecute to execute as Strings)
	 * @param activity
	 */
	public MapErrorProgressTask(Activity activity) {
		super(activity);
	}
	
	public MapErrorProgressTask(Activity activity, Object classObject) {
        super(activity, classObject);
	}
	
	@Override
	protected String doInBackground(String... methodName) {
		methodOne = methodName[0];
		methodTwo = methodName[1];
        Object value = super.executeMethod(methodOne);
        if (value != null && value instanceof HashMap) {
            mapError = (HashMap)value;
        }
		return (mapError != null && !mapError.isEmpty()) ? ViewUtils.uncompletedTask : ViewUtils.completedTask;
	}
	
	@Override
	protected void onPostExecute(String taskStatus) {
		super.closeDialogs();
        if(taskStatus.equals(ViewUtils.completedTask)) {
            executeMethod(methodTwo);
        } else {
            View focusView = null;
            for ( Map.Entry<View, String> entry : mapError.entrySet()) {
                EditText editText = (EditText)entry.getKey();
                String error = entry.getValue();
                editText.setError(error);
                focusView = editText;
            }
            focusView.requestFocus();
        }
        try {
            this.finalize();
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
	}
}