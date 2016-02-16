package com.nongziwang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nongziwang.main.MainActivity;
import com.nongziwang.main.R;
import com.nongziwang.utils.GuidedUtil;

public class SplashActivity extends BaseActivity {
    private static final long SPLASH_DELAY_MILLIS = 3000;
    boolean isFirst = false;
    private static final int GO_HOME = 0X00;
    private static final int GO_GUIDE = 0X01;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GO_HOME:
            	intentAction(SplashActivity.this,MainActivity.class);
            	finish();
            	break;
            case GO_GUIDE:
            	intentAction(SplashActivity.this,GuideActivity.class);
            	finish();
                break;
            }
            super.handleMessage(msg);
        }
    };
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_splash);
		init();
	}
	private void init() {
    isFirst=GuidedUtil.getInstance(getApplicationContext()).isFirst();
	if(isFirst){
		 mHandler.sendEmptyMessageDelayed(GO_GUIDE, SPLASH_DELAY_MILLIS);
	}else{
		 mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
	}
	}
}
