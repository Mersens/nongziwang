package com.nongziwang.utils;
import android.content.Context;
import android.content.SharedPreferences;
/**
 * 
 * @title SharePreferenceUtil
 * @description:SharePreference工具类，数据存储
 * @author Mersens
 * @time 2016年1月8日
 */
public class SharePreferenceUtil {
	private static SharePreferenceUtil sp;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor editor;
	private static final String PREFERENCE_NAME = "_sharedinfo";
	private static final String USER_ID="user_id";
	private static final String USER_NAME="user_name";
	private static final String USER_PSD="user_psd";
	private SharePreferenceUtil(){
		
	}
	public static synchronized SharePreferenceUtil getInstance(Context context){
		if(sp==null){
		sp=new SharePreferenceUtil();
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
		}
		return sp;
	}

	//用户id
	public String getUserId(){
		return mSharedPreferences.getString(USER_ID, null);
	}
	public void setUserId(String userid){
		editor.putString(USER_ID, userid);
		editor.commit();
	}
	//用户名
	public String getUserName(){
		return mSharedPreferences.getString(USER_NAME, null);
	}
	public void setUserName(String username){
		editor.putString(USER_NAME, username);
		editor.commit();
	}
	//密码
	public String getUserPsd(){
		return mSharedPreferences.getString(USER_PSD, null);
	}
	
	public void setUserPsd(String psd){
		editor.putString(USER_PSD, psd);
		editor.commit();
	}
	//清除数据
	public void clearData(){
		editor.clear().commit();
	}
}
