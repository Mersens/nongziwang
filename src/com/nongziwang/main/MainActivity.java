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
	private ImageView img_account;
	private ImageView img_more;
	private ImageView img_more1;

	private RelativeLayout layout_home;
	private RelativeLayout layout_account;
	private RelativeLayout layout_more;
	private RelativeLayout layout_more1;
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
		layout_account = (RelativeLayout) findViewById(R.id.layout_account);
		layout_more = (RelativeLayout) findViewById(R.id.layout_more);
		layout_more1 = (RelativeLayout) findViewById(R.id.layout_more1);
		img_home = (ImageView) findViewById(R.id.img_home);
		img_account = (ImageView) findViewById(R.id.img_account);
		img_more = (ImageView) findViewById(R.id.img_more);
		img_more1 = (ImageView) findViewById(R.id.img_more1);

		mTabs[0] = layout_home;
		mTabs[1] = layout_account;
		mTabs[2] = layout_more;
		mTabs[3] = layout_more1;
		getSupportFragmentManager().beginTransaction()
		.add(R.id.fragment_container, fragments[0])
		.show(fragments[0]).commit();
	}
	private void initEvents() {
		layout_home.setOnClickListener(this);
		layout_account.setOnClickListener(this);
		layout_more.setOnClickListener(this);
		layout_more1.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_home:
			index = 0;
			setTab(0);
			break;
		case R.id.layout_account:
			index = 1;
			setTab(1);
			break;
		case R.id.layout_more:
			index = 2;
			setTab(2);
			break;
		case R.id.layout_more1:
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
			img_home.setImageResource(R.drawable.icon_home_pressed);
			break;
		case 1:
			img_account.setImageResource(R.drawable.icon_account_pressed);
			break;
		case 2:
			img_more.setImageResource(R.drawable.icon_more_pressd);
			break;
		case 3:
			img_more1.setImageResource(R.drawable.icon_more_pressd);
			break;
		}
	}

	private void resetImgs() {
		img_home.setImageResource(R.drawable.icon_home_normal);
		img_account.setImageResource(R.drawable.icon_account_normal);
		img_more.setImageResource(R.drawable.icon_more_normal);
		img_more1.setImageResource(R.drawable.icon_more_normal);
	}


}
