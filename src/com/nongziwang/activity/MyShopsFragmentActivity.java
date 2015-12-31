package com.nongziwang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongziwang.fragment.MyShopsFragment;
import com.nongziwang.main.R;

public class MyShopsFragmentActivity extends BaseActivity implements
		OnClickListener {
	private ImageView img_menu_back;

	private LinearLayout layout_dianpu, layout_shangpin;
	private ImageView image_shangpin, image_dianpu;
	private TextView tv_shangpin, tv_dianpu;
	private View view_shangpin, view_dianpu;
	private RelativeLayout layout_more;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myshops_main);
		initViews();
		initEvent();
		addFragment(null, false);
	}

	private void initViews() {
		layout_more=(RelativeLayout) findViewById(R.id.layout_more);
		layout_dianpu = (LinearLayout) findViewById(R.id.layout_dianpu);
		layout_shangpin = (LinearLayout) findViewById(R.id.layout_shangpin);
		image_shangpin = (ImageView) findViewById(R.id.image_shangpin);
		image_dianpu = (ImageView) findViewById(R.id.image_dianpu);
		tv_shangpin = (TextView) findViewById(R.id.tv_shangpin);
		tv_dianpu = (TextView) findViewById(R.id.tv_dianpu);
		view_shangpin = findViewById(R.id.view_shangpin);
		view_dianpu = findViewById(R.id.view_dianpu);
		img_menu_back = (ImageView) findViewById(R.id.img_menu_back);
	}

	private void initEvent() {
		layout_dianpu.setOnClickListener(this);
		layout_shangpin.setOnClickListener(this);
		img_menu_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
	}

	public Fragment creatFragment(String params) {
		return MyShopsFragment.getInstance(null);
	}

	public void addFragment(String params, boolean isAnim) {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_container, fragment);
		ft.commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_dianpu:
			setTabs(0);
			addFragment(null, true);
			break;
		case R.id.layout_shangpin:
			setTabs(1);
			addFragment(null, true);
			break;
		}
	}

	public void setTabs(int i) {
		resetColor();
		switch (i) {
		case 0:
			image_dianpu.setImageResource(R.drawable.icon_dianpu_pressed);
			tv_dianpu.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			view_dianpu.setVisibility(View.VISIBLE);
			layout_more.setVisibility(View.VISIBLE);
			break;
		case 1:
			image_shangpin.setImageResource(R.drawable.icon_shangpin_pressed);
			tv_shangpin.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			view_shangpin.setVisibility(View.VISIBLE);
			layout_more.setVisibility(View.GONE);
			break;
		}
	}

	public void resetColor() {
		image_dianpu.setImageResource(R.drawable.icon_dianpu_normal);
		image_shangpin.setImageResource(R.drawable.icon_shangpin_normal);
		tv_shangpin.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
		tv_dianpu.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
		view_shangpin.setVisibility(View.GONE);
		view_dianpu.setVisibility(View.GONE);
	}
}
