package com.nongziwang.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
/**
 * 
 * @title BitmapUtils
 * @description:等比例压缩图片
 * @author Mersens
 * @time 2016年1月16日
 */
public class BitmapUtils {
	public static Bitmap getBitMap(String path){
		BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(path,options);
		double ratio=Math.max(options.outWidth * 1.0d/1024f, options.outHeight * 1.0d/1024f);
		options.inSampleSize=(int) Math.ceil(ratio);
		options.inJustDecodeBounds=false;
		return BitmapFactory.decodeFile(path, options);
	}

}
