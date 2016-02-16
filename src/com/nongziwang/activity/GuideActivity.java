package com.nongziwang.activity;

import com.nongziwang.fragment.GuideFragment;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * 
 * @title GuideActivity
 * @description:App引导页
 * @author Mersens
 * @time 2016年2月14日
 */
public class GuideActivity extends BaseActivity{
	private RadioGroup dotLayout;
	private ViewPager viewPager;
	private PageFragmentAdapter adapter;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_guide);
		initViews();
		initEvent();
	}

	private void initViews() {
		dotLayout = (RadioGroup) findViewById(R.id.advertise_point_group);
		viewPager = (ViewPager) findViewById(R.id.viewpager);		
	}
	
	private void initEvent() {
		adapter = new PageFragmentAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyPagerChangeListener());
		
	}
	public class MyPagerChangeListener implements OnPageChangeListener {

		public void onPageSelected(int position) {
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
		}

	}
	
	class PageFragmentAdapter extends FragmentPagerAdapter {
		public PageFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int idx) {
			return GuideFragment.getInstance(idx);
		}

		@Override
		public int getCount() {
			return GuideFragment.imags.length;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE; // 没有找到child要求重新加载
		}
	}
}
