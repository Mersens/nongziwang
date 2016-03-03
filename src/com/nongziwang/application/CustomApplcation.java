package com.nongziwang.application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * 
 * @title CustomApplcation
 * @description:Android的Applcation设置和初始化全局配置
 * @author Mersens
 * @time 2016年2月16日
 */

public class CustomApplcation extends Application {
	// Applcation实例
	public static CustomApplcation mInstance;
	// 运用list来保存们每一个activity是关键,传说这是安卓应用最优雅的退出方式
	private List<Activity> mList = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化系统级的异常监控
		CrashHandler.getInstance().init(getApplicationContext());
		// 初始化ImageLoader
		initImageLoader(getApplicationContext());
		initPlatformConfig();
	}

	private void initPlatformConfig() {
		PlatformConfig.setWeixin("wx26dc3a645ff5132e",
				"2d22dbe08a570fb390b26aaf60d52e53");
		// 微信 appid appsecret
		PlatformConfig.setSinaWeibo("587556322",
				"81787b013235b2de6c63788ed58e7fb7");
		// 新浪微博 appkey appsecret
		PlatformConfig.setQQZone("100424468",
				"c7394704798a158208a74ab60104f0ba");
		// QQ和Qzone appid appkey   
		PlatformConfig.setAlipay("2016030201175920");
		// 支付宝 appid
		PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
	}

	// 单例模式，双重校验锁
	public static CustomApplcation getInstance() {
		if (mInstance == null) {
			synchronized (CustomApplcation.class) {
				if (mInstance == null) {
					mInstance = new CustomApplcation();
				}
			}
		}
		return mInstance;
	}

	/**
	 * 
	 * @Title: initImageLoader
	 * @Description: 初始化ImageLoader
	 * @author Mersens
	 * @param context
	 * @throws
	 */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");// 获取到缓存的目录地址
		// 创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// 线程池内加载的数量
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	// 关闭每一个list内的activity
	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

}
