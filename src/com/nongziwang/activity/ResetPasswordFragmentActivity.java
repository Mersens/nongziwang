package com.nongziwang.activity;

import com.nongziwang.fragment.ResetPasswordFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

public class ResetPasswordFragmentActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_reset_psd);
		initViews();
		initEvent();
		addFragment(null, false);
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

	public Fragment creatFragment( String params) {
		return ResetPasswordFragment.getInstance(params);
	}

	public void addFragment( String params, boolean isAnim) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_reset_psdcontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment( params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_reset_psdcontainer, fragment);
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
