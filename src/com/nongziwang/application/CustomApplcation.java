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
 * @description:Android��Applcation���úͳ�ʼ��ȫ������
 * @author Mersens
 * @time 2016��2��16��
 */

public class CustomApplcation extends Application {
	// Applcationʵ��
	public static CustomApplcation mInstance;
	// ����list��������ÿһ��activity�ǹؼ�,��˵���ǰ�׿Ӧ�������ŵ��˳���ʽ
	private List<Activity> mList = new LinkedList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		// ��ʼ��ϵͳ�����쳣���
		CrashHandler.getInstance().init(getApplicationContext());
		// ��ʼ��ImageLoader
		initImageLoader(getApplicationContext());
		initPlatformConfig();
	}

	private void initPlatformConfig() {
		PlatformConfig.setWeixin("wx26dc3a645ff5132e",
				"2d22dbe08a570fb390b26aaf60d52e53");
		// ΢�� appid appsecret
		PlatformConfig.setSinaWeibo("587556322",
				"81787b013235b2de6c63788ed58e7fb7");
		// ����΢�� appkey appsecret
		PlatformConfig.setQQZone("100424468",
				"c7394704798a158208a74ab60104f0ba");
		// QQ��Qzone appid appkey   
		PlatformConfig.setAlipay("2016030201175920");
		// ֧���� appid
		PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
	}

	// ����ģʽ��˫��У����
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
	 * @Description: ��ʼ��ImageLoader
	 * @author Mersens
	 * @param context
	 * @throws
	 */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"imageloader/Cache");// ��ȡ�������Ŀ¼��ַ
		// ��������ImageLoader(���е�ѡ��ǿ�ѡ��,ֻʹ����Щ������붨��)����������趨��APPLACATION���棬����Ϊȫ�ֵ����ò���
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// �̳߳��ڼ��ص�����
				.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// �������ʱ���URI������MD5 ����
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))// �Զ��建��·��
				// .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// ȫ�ֳ�ʼ��������
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	// �ر�ÿһ��list�ڵ�activity
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
