package com.nongziwang.activity;

import com.nongziwang.fragment.CommonOrderFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;


public class CommonOrderFragmentActivity extends BaseActivity {
	public static final String YMDCP = "已买到的产品";
	public static final String YMCCP = "已卖出的产品";
	public static final String DQR = "待确认订单";
	public static final String DFK = "待付款订单";
	public static final String DSH = "待收货订单";
	public static final String YFH = "已发货订单";
	public static final String JYCG = "交易成功";
	private String type;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_common_order);
		type = getIntent().getStringExtra("params");
		initViews();
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, type, new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		setHeadViewBg(R.color.white_color);
		addFragment(type);
	}

	public Fragment creatFragment(String params) {
		return CommonOrderFragment.getInstance(params);
	}

	public void addFragment(String params) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_ordercontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(params);
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_ordercontainer, fragment);
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
		}
		return false;
	}

	
}
