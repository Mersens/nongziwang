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
	public static final String PREFERENCE_NAME = "_sharedinfo";
	
	public static synchronized SharePreferenceUtil getInstance(Context context){
		if(sp==null){
		sp=new SharePreferenceUtil();
		mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
		}
		return sp;
	}
}
