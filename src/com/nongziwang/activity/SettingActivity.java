package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;

public class SettingActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting);
		initViews();
		initEvent();
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "…Ë÷√", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
	}

	private void initEvent() {
		
	}

}
