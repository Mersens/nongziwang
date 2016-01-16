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
import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class CustomApplcation extends Application {
	public static CustomApplcation mInstance;
	// ����list��������ÿһ��activity�ǹؼ�
	private List<Activity> mList = new LinkedList<Activity>();
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		CrashHandler.getInstance().init(getApplicationContext());
		initImageLoader(getApplicationContext());
	}
	//����ģʽ��˫��У����
	public static CustomApplcation getInstance() {
		if(mInstance==null){
			synchronized (CustomApplcation.class) {
				if(mInstance==null){
					mInstance=new CustomApplcation();	
				}
			}
		}
		return mInstance;
	}
	/** ��ʼ��ImageLoader */
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
