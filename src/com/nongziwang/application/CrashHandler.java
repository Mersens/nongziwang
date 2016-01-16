package com.nongziwang.application;

import java.lang.Thread.UncaughtExceptionHandler;
import android.content.Context;

/**
 * 
 * @title CrashHandler
 * @description:系统级的异常处理
 * @author Mersens
 * @time 2016年1月16日
 */
public class CrashHandler implements UncaughtExceptionHandler {
	// 系统默认的UncaughtException处理类
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = null;
	// 程序的Context对象
	private Context mContext;

	/** 保证只有一个CrashHandler实例 */
	private CrashHandler() {

	}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashHandler getInstance() {
		if(INSTANCE==null){
			synchronized (CrashHandler.class) {
				if(INSTANCE==null){
					INSTANCE=new CrashHandler();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// 获取系统默认的UncaughtException处理器
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (mDefaultHandler != null) {
			 //程序出现异常时，进入该函数
			 //CustomApplcation.getInstance().exit();
			 mDefaultHandler.uncaughtException(thread, ex); 
		}
	}

}
