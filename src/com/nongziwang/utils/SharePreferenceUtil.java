package com.nongziwang.utils;
import android.content.Context;
import android.content.SharedPreferences;




public class SharePreferenceUtil {
	private static SharePreferenceUtil sp;
	private static SharedPreferences mSharedPreferences;
	private static SharedPreferences.Editor editor;
	public static final String PREFERENCE_NAME = "_sharedinfo";
	
	
	
	
	/**
	 * @author Mersens
	 * 线程安全的单例模式
	 * @param context
	 * @return
	 */
	public static synchronized SharePreferenceUtil getInstance(Context context){
		if(sp==null){
		sp=new SharePreferenceUtil();
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
		}
		return sp;
	}
}
