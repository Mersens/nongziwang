package com.nongziwang.utils;

import android.os.CountDownTimer;
import android.os.Handler;
/**
 * 
 * @title RegisterCodeTimer
 * @description:获取验证码计时器
 * @author Mersens
 * @time 2016年1月8日
 */
public class RegisterCodeTimer extends CountDownTimer {
	private static Handler mHandler;
	public static final int IN_RUNNING = 1001;
	public static int END_RUNNING = 1002;

	public RegisterCodeTimer(long millisInFuture, long countDownInterval,
			Handler handler) {
		super(millisInFuture, countDownInterval);
		mHandler = handler;
	}
    //计时结束
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		if (mHandler != null)
			mHandler.obtainMessage(END_RUNNING, "获取验证码").sendToTarget();
	}
    //正在倒计时
	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		if (mHandler != null)
			mHandler.obtainMessage(IN_RUNNING,
					(millisUntilFinished / 1000) + "秒后重新获取").sendToTarget();
	}

}
