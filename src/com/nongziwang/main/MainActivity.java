package com.nongziwang.main;

import com.nongziwang.activity.BaseActivity;
import com.nongziwang.fragment.Fragment1;
import com.nongziwang.fragment.Fragment2;
import com.nongziwang.fragment.Fragment3;
import com.nongziwang.fragment.Fragment4;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends BaseActivity implements OnClickListener{
	private Fragment f1,f2,f3,f4;
	private Fragment[] fragments;

	private ImageView img_home;
	private ImageView img_product;
	private ImageView img_info;
	private ImageView img_user;

	private RelativeLayout layout_home;
	private RelativeLayout layout_product;
	private RelativeLayout layout_info;
	private RelativeLayout layout_user;
	private RelativeLayout[] mTabs;

	private int index;
	private int currentTabIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		initEvents();
	}
	
	private void initViews() {
		f1 = new Fragment1();
		f2 = new Fragment2();
		f3 = new Fragment3();
		f4 = new Fragment4();
		fragments = new Fragment[] { f1, f2,f3,f4 };
				

		mTabs = new RelativeLayout[4];
		layout_home = (RelativeLayout) findViewById(R.id.layout_home);
		layout_product = (RelativeLayout) findViewById(R.id.layout_product);
		layout_info = (RelativeLayout) findViewById(R.id.layout_info);
		layout_user = (RelativeLayout) findViewById(R.id.layout_user);
		img_home = (ImageView) findViewById(R.id.img_home);
		img_product = (ImageView) findViewById(R.id.img_product);
		img_info = (ImageView) findViewById(R.id.img_info);
		img_user = (ImageView) findViewById(R.id.img_user);

		mTabs[0] = layout_home;
		mTabs[1] = layout_product;
		mTabs[2] = layout_info;
		mTabs[3] = layout_user;
		getSupportFragmentManager().beginTransaction()
		.add(R.id.fragment_container, fragments[0])
		.show(fragments[0]).commit();
	}
	private void initEvents() {
		layout_home.setOnClickListener(this);
		layout_product.setOnClickListener(this);
		layout_info.setOnClickListener(this);
		layout_user.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_home:
			index = 0;
			setTab(0);
			break;
		case R.id.layout_product:
			index = 1;
			setTab(1);
			break;
		case R.id.layout_info:
			index = 2;
			setTab(2);
			break;
		case R.id.layout_user:
			index = 3;
			setTab(3);
			break;

		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.remove(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		mTabs[index].setSelected(true);
		currentTabIndex = index;
		
	}
	private void setTab(int i) {
		resetImgs();
		switch (index) {
		case 0:
			img_home.setImageResource(R.drawable.icon_shouye_pressed);
			break;
		case 1:
			img_product.setImageResource(R.drawable.icon_chanpin_pressed);
			break;
		case 2:
			img_info.setImageResource(R.drawable.icon_zixun_pressed);
			break;
		case 3:
			img_user.setImageResource(R.drawable.icon_geren_pressed);
			break;
		}
	}

	private void resetImgs() {
		img_home.setImageResource(R.drawable.icon_shouye_normal);
		img_product.setImageResource(R.drawable.icon_chanpin_normal);
		img_info.setImageResource(R.drawable.icon_zixun_normal);
		img_user.setImageResource(R.drawable.icon_geren_normal);
	}


}
