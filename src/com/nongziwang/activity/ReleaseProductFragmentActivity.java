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
import com.nongziwang.fragment.ReleaseProductJyxxFragment;
import com.nongziwang.fragment.ReleaseProductMsgFragment;
import com.nongziwang.fragment.ReleaseProductTypeFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class ReleaseProductFragmentActivity extends BaseActivity {
	public static final String ACTION_PRODUCT_MSG = "action_product_msg";
	public static final String ACTION_PRODUCT_JYXX = "action_product_jyxx";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_release_pro_main);
		registerBoradcastReceiver();
		initViews();
		initEvent();
		addFragment(Style.PRODUCT_TYPE, null, false);
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "发布产品",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
	}

	private void initEvent() {

	}

	public enum Style {
		PRODUCT_TYPE, PRODUCT_MSG, PRODUCT_JYXX;
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case PRODUCT_TYPE:
			fragment = ReleaseProductTypeFragment.getInstance(params);
			break;
		case PRODUCT_MSG:
			fragment =ReleaseProductMsgFragment.getInstance(params);
			break;
		case PRODUCT_JYXX:
			fragment =ReleaseProductJyxxFragment.getInstance(params);
			break;
		}
		return fragment;
	}

	public void addFragment(Style style, String params, boolean isAnim) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm
				.findFragmentById(R.id.fragment_release_pro_container);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_release_pro_container, fragment);
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
		}
		return false;
	}

	/**
	 * 定义广播接收者
	 */
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_PRODUCT_MSG)) {
				addFragment(Style.PRODUCT_MSG, null, true);
			} else if (action.equals(ACTION_PRODUCT_JYXX)) {
				addFragment(Style.PRODUCT_JYXX, null, true);
			}
		}
	};

	/**
	 * 注册广播
	 */
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_PRODUCT_MSG);
		myIntentFilter.addAction(ACTION_PRODUCT_JYXX);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	/**
	 * Activity销毁时，注销广播接收者
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
