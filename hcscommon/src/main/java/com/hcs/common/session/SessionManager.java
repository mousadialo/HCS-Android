package com.hcs.common.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

public class SessionManager {
	
	private Activity activity;
	private Context context;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String DTO_USER = "dtoUser"; 
	SharedPreferences sharedpreferences;

	/**
	 * Constructor to init the session variables
	 * @param activity
	 */
	public SessionManager(Activity activity) {
		this.activity = activity;
		this.context = activity.getApplicationContext();
		sharedpreferences = context.getSharedPreferences(MyPREFERENCES,	Context.MODE_PRIVATE);
	}

	/**
	 * Create login session
	 * @param dtoUser DtoUser to save in session
	 */
	public void createLoginSession(Object dtoUser) {
		Editor editor = sharedpreferences.edit();
	    editor.putString(DTO_USER, this.convertDtoUserToJson(dtoUser));
	    editor.commit();
	}
	
	/**
	 * Update user session
	 * @param dtoUser DtoUser to update the session
	 */
	public void updateUserSession(Object dtoUser) {
		Editor editor = sharedpreferences.edit();
		editor.remove(DTO_USER);
		editor.commit();
	    editor.putString(DTO_USER, this.convertDtoUserToJson(dtoUser));
	    editor.commit();
	}
	
	/**
	 * Method to convert DtoUser in Json String
	 * @param dtoUser
	 * @return String 
	 */
	private String convertDtoUserToJson(Object dtoUser) {
		Gson gson = new Gson();
	    String json = gson.toJson(dtoUser);
	    return json;
	}

	/**
	 * Check login method will check user login status 
	 * If false it will redirect user to login page Else won't do anything
	 * @param targetClass Class to redirect if TRUE
	 */
	public void checkLogin(Class<?> targetClass, Class<?> classDtoUser) {
		if (sharedpreferences.contains(DTO_USER)) {
			Gson gson = new Gson();
			String json = sharedpreferences.getString(DTO_USER, "");
			Object dtoUser = gson.fromJson(json, classDtoUser);
			Intent i = new Intent(activity,	targetClass);
			activity.startActivity(i);
		}
	}

	/**
	 * Get stored session data
	 * @return DtoUser
	 */
	public Object getUserDetails(Class<?> targetClass) {
		Gson gson = new Gson();
		String json = sharedpreferences.getString(DTO_USER, "");
		Object dtoUser = gson.fromJson(json, targetClass);
		return dtoUser;
	}

	/**
	 * Method to clear session details for logout
	 * @param returnedClass
	 */
	public void logoutUser(Class<?> returnedClass) {
		Editor editor = sharedpreferences.edit();
		editor.clear();
		editor.commit();
		Intent i = new Intent(activity, returnedClass);
		activity.startActivity(i);
	}
}