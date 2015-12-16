package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;

import android.os.Bundle;

public class IndustryInfoDetialActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_industry_info_detial);
		initViews();
		initEvent();
	}

	private void initViews() {
		setDefaultViewMethod(R.drawable.ic_menu_back, "行业资讯", R.drawable.icon_fenxiang, new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finishActivity();
				
			}
		}, new OnRightClickListener() {
			
			@Override
			public void onClick() {
				ShowToast("点击了分享");
			}
		});
		setHeadViewBg(R.color.white_color);
		
	}

	private void initEvent() {
		
	}

}
