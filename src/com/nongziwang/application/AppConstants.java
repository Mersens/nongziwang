package com.nongziwang.application;

import android.annotation.SuppressLint;
/**
 * 
 * @title AppConstants
 * @description:常量类
 * @author Mersens
 * @time 2016年1月16日
 */
public class AppConstants {
	public static final String SERVICE_ADDRESS="http://appservice.nz101.com/";
	public static final String APP_ID = "222222";
	@SuppressLint("SdCardPath")
	public static final String MyAvatarDir = "/sdcard/ssdimg/avatar/";
	public static final String APP_KEY = "2045436852";
	public static final String REDIRECT_URL = "http://www.sina.com";
	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
}
