package com.nongziwang.activity;

import com.nongziwang.fragment.SellerAccountVipFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

public class GongsiFragmentActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mycompany);
		initViews();
		addFragment("");
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "公司管理",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
	}

	public Fragment creatFragment(String params) {
		return SellerAccountVipFragment.getInstance(params);
	}

	public void addFragment(String params) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(params);
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_container, fragment);
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
