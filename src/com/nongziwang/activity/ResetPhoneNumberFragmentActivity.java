package com.nongziwang.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.nongziwang.fragment.GetCodeForNewPhoneNumberFragment;
import com.nongziwang.fragment.GetCodeForOldPhoneNumberFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class ResetPhoneNumberFragmentActivity extends BaseActivity{
	public static final String ACTION_GETNEWCODE="action_getnewcode";
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_reset_phone_number);
		registerBoradcastReceiver();
		initViews();
		initEvent();
		addFragment(Style.GETOLDCODE,null, false);
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "’À∫≈π‹¿Ì",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
	}

	private void initEvent() {

	}

	public enum Style{
		GETOLDCODE,GETNEWCODE;
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment=null;
		switch (style) {
		case GETOLDCODE:
			fragment=GetCodeForOldPhoneNumberFragment.getInstance(params);
			break;
		case GETNEWCODE:
			fragment=GetCodeForNewPhoneNumberFragment.getInstance(params);
			break;
		}
		return fragment;
	}

	public void addFragment(Style style, String params, boolean isAnim) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_reset_numcontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_reset_numcontainer, fragment);
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
			return true;
		}
		return false;
	}

	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(ACTION_GETNEWCODE);  
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
	
	
	
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals(ACTION_GETNEWCODE)){
            	addFragment(Style.GETNEWCODE,null, true);
            }
        }  
    };
	
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(mBroadcastReceiver);
    };
	
}
