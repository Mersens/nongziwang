package com.nongziwang.utils;

import android.os.CountDownTimer;
import android.os.Handler;
/**
 * 
 * @title RegisterCodeTimer
 * @description:��ȡ��֤���ʱ��
 * @author Mersens
 * @time 2016��1��8��
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
    //��ʱ����
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		if (mHandler != null)
			mHandler.obtainMessage(END_RUNNING, "��ȡ��֤��").sendToTarget();
	}
    //���ڵ���ʱ
	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		if (mHandler != null)
			mHandler.obtainMessage(IN_RUNNING,
					(millisUntilFinished / 1000) + "������»�ȡ").sendToTarget();
	}

}
