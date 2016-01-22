package com.nongziwang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.text.TextUtils;

public class DateUtil {
	@SuppressLint("SimpleDateFormat")
	public static String getStamp2Time(String str){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		if(TextUtils.isEmpty(str)){
			return sdf.format(new Date());
		}
		return sdf.format(new Date(Long.parseLong(str)));
	}

}
