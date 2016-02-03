package com.nongziwang.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nongziwang.main.MainActivity;
import com.nongziwang.main.R;

public class SplashActivity extends BaseActivity {
    private static final long SPLASH_DELAY_MILLIS = 3000;
    private static final int GO_HOME = 0X00;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case GO_HOME:
            	intentAction(SplashActivity.this,MainActivity.class);
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
	mHandler.sendEmptyMessageDelayed(GO_HOME, SPLASH_DELAY_MILLIS);
	}
}
