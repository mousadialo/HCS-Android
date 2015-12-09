package com.hcs.common.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.hcs.common.exception.ViewException;

/**
 * @author fernando.ramirez
 * Class for Views
 */
public class ViewUtils {

    private static final String TAG = "ViewUtils";
	final public static String completedTask = "COMPLETED";
	final public static String uncompletedTask = "UNCOMPLETED";
	
	/**
	 * Method to get a String resource
	 * @param activity (Current activity)
	 * @param resource (Resource to get, e.g. R.string.txtClose = Close)
	 * @return String text
	 */
	public static String getStringResource(Activity activity, int resource) {
		Resources res = activity.getResources();
		String text = String.format(res.getString(resource));
		return text;
	}
	
	/**
	 * Method to get a String resource with args
	 * @param activity (Current activity)
	 * @param resource (Resource to get, e.g. R.string.txtClose = Close)
	 * @return String text
	 */
	public static String getStringResource(Activity activity, int resource, Object... args) {
		Resources res = activity.getResources();
		String text = String.format(res.getString(resource), args);
		return text;
	}
	
	/**
	 * Method to get a String resource with args
	 * @param activity (Current activity)
	 * @param resource (Resource to get, e.g. R.string.txtClose = Close)
	 * @return String text
	 */
	public static String getStringResource(Activity activity, int resource, int includeResouce) {
		String inMessage = getStringResource(activity, includeResouce);
		return getStringResource(activity, resource, inMessage);
	}
	
	/**
	 * Method to get text of the EditText View
	 * @param editText
	 * @return String text
	 * @throws ViewException
	 */
	public static String getText(EditText editText) {
        String text = "";
        try {
            validNull(editText);
            text = editText.getText().toString().trim();
        } catch (ViewException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
		return text;
	}
		
	/**
	 * Method to validate if the EditText View is blank
	 * @param editText
	 * @return Boolean
	 * @throws ViewException
	 */
	public static boolean isBlank(EditText editText) {
		String text = getText(editText);
		if (text.equalsIgnoreCase("")){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to validate if the list EditText Views is blank
	 * @param editTexts
	 * @return Boolean
	 * @throws ViewException
	 */
	public static boolean isBlank(EditText... editTexts) {
		
		if(editTexts == null || editTexts.length == 0) {
            try {
                throw new ViewException(ViewException.MessageType.NULL);
            } catch (ViewException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
		}
		
		for (EditText editText : editTexts) {
			String text = getText(editText);
			if (text.equalsIgnoreCase("")) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method to verify if a editText is number
	 * @param editTexts
	 * @return
	 * @throws ViewException
	 */
	public static boolean isNumber(EditText... editTexts) {
		
		if(editTexts == null || editTexts.length == 0) {
            try {
                throw new ViewException(ViewException.MessageType.NULL);
            } catch (ViewException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
		}
		
		for (EditText editText : editTexts) {
			String text = getText(editText);
			try {
				Integer.parseInt(text);
			} catch (Exception e) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method to verify if a editText is number
	 * @param editText
	 * @return
	 * @throws ViewException
	 */
	public static boolean isNumber(EditText editText) {
		String text = getText(editText);
		try {
			Integer.parseInt(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Method to verify if a String is number
	 * @param str
	 * @return
	 * @throws ViewException
	 */
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Method to validate if the View is null
	 * @param view
	 * @throws ViewException
	 */
	private static void validNull(View view) throws ViewException {
		if(view == null){
			throw new ViewException(ViewException.MessageType.NULL);
		}
	}
	
	/**
	 * Method to set blank to editTexts
	 * @param editTexts
	 * @throws ViewException
	 */
	public static void setBlank(EditText... editTexts) {
		
		if(editTexts == null || editTexts.length == 0) {
            try {
                throw new ViewException(ViewException.MessageType.NULL);
            } catch (ViewException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
		}
		
		for (EditText editText : editTexts) {
			editText.setText(null);
		}
	}

	/**
	 * Method to set blank error to editTexts
	 * @param editTexts
	 * @throws ViewException
	 */
	public static void setBlankError(EditText... editTexts) {

		if(editTexts == null || editTexts.length == 0) {
            try {
                throw new ViewException(ViewException.MessageType.NULL);
            } catch (ViewException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
		}

		for (EditText editText : editTexts) {
			editText.setError(null);
		}
	}
}