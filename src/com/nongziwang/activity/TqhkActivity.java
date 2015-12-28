package com.nongziwang.activity;

import android.os.Bundle;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class TqhkActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_tqck);
		initViews();
		initEvent();
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "提取货款", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		
	}

	private void initEvent() {
		
	}
}
