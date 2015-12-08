package com.nongziwang.utils;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoadOptions {
	public static DisplayImageOptions getOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				// // 设置图片在下载期间显示的图片
				// .showImageOnLoading(R.drawable.small_image_holder_listpage)
				// // 设置图片Uri为空或是错误的时候显示的图片
				// .showImageForEmptyUri(R.drawable.small_image_holder_listpage)
				// // 设置图片加载/解码过程中错误时候显示的图片
				// .showImageOnFail(R.drawable.small_image_holder_listpage)
				.cacheInMemory(true)
				// 设置下载的图片是否缓存在内存�?				.cacheOnDisc(true)
				// 设置下载的图片是否缓存在SD卡中
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类�?				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//设置图片的解码配�?				.considerExifParams(true)
				// 设置图片下载前的延迟
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的延迟时�?				// 设置图片加入缓存前，对bitmap进行设置
				// 。preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复�?				// .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多�?				.displayer(new FadeInBitmapDisplayer(100))// 淡入
				.build();
		
		return options;
	}

}
