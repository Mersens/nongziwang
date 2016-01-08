package com.nongziwang.utils;

import android.content.Context;

/**
 * 
 * @title DataTools
 * @description:dp与px的相互转化
 * @author Mersens
 * @time 2016年1月8日
 */
public class DataTools {
	/**
	 * dp转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 *  px 转为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
