package com.hcs.common.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.hcs.common.R;
import com.hcs.common.utils.ViewUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by fernando.ramirez on 23/06/2015.
 */
class CommonProgressTask extends AsyncTask<String, String, String> {

    private static final String TAG = "CommonProgressTask";
    protected Activity activity;
    private ProgressDialog progressDialog;
    private Object classObject;

    /**
     * Constructor to receive the current activity (Send two methods doInBackground & onPostExecute to execute as Strings)
     * @param activity
     */
    protected CommonProgressTask(Activity activity) {
        this.activity = activity;
    }

    protected CommonProgressTask(Activity activity, Object classObject) {
        this.activity = activity;
        this.classObject = classObject;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(ViewUtils.getStringResource(activity, R.string.txt_MsgTask));
        progressDialog.setTitle(ViewUtils.getStringResource(activity,	R.string.txt_TitleTask));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            throw new Exception("Implement doInBackground");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String stringMessage) {
        try {
            throw new Exception("Implement doInBackground");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method to execute the methods received as Strings
     * @param methodName
     * @return boolean
     */
    protected Object executeMethod(final String methodName) {
        Object value = null;
        try {
            Method method = null;
            if (classObject == null) {
                method = activity.getClass().getMethod(methodName, new Class<?>[0]);
                value = method.invoke(activity);
            } else {
                method = classObject.getClass().getMethod(methodName, new Class<?>[0]);
                value = method.invoke(classObject);
            }
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return value;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        this.closeDialogs();
    }

    protected void closeDialogs() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog.cancel();
        }
    }
}
