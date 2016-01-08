package com.nongziwang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import com.nongziwang.fragment.MyAccountSellerFeagment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class MyAccountSellerFragmentActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_myaccount);
		initViews();
		initEvent();
		addFragment(Style.ACCOUNT_MAIN, null, false);
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

	public enum Style {
		ACCOUNT_MAIN;
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case ACCOUNT_MAIN:
			fragment = MyAccountSellerFeagment.getInstance(params);
			break;
		}
		return fragment;
	}

	public void addFragment(Style style, String params, boolean isAnim) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_accountcontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_accountcontainer, fragment);
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
}
