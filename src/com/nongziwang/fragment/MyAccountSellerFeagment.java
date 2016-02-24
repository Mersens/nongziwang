package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.main.R;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAccountSellerFeagment extends BaseFragment{
	private View view;
	private ViewPager viewPager;
	private ImageView imageView;
	private TextView tv_vip, tv_account;
	private List<Fragment> fragments;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private int selectedColor, unSelectedColor;
	private static final int pageSize = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_my_account, container,false);
		initViews();
		return view;
	}
	

	private void initViews() {
		selectedColor = getResources()
				.getColor(R.color.title_yellow_text_color);
		unSelectedColor = getResources().getColor(
				R.color.base_color_text_black);
		InitImageView();
		InitTextView();
		InitViewPager();
	}
	private void InitTextView() {
		tv_vip = (TextView) view.findViewById(R.id.tab_1);
		tv_account = (TextView)view.findViewById(R.id.tab_2);
		tv_vip.setTextColor(selectedColor);
		tv_account.setTextColor(unSelectedColor);

		tv_vip.setText("会员管理");
		tv_account.setText("账户安全");
		tv_vip.setOnClickListener(new MyOnClickListener(0));
		tv_account.setOnClickListener(new MyOnClickListener(1));

	}
	
	private void InitImageView() {
		imageView = (ImageView) view.findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / pageSize - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);
	}
	
	private void InitViewPager() {
		viewPager = (ViewPager) view.findViewById(R.id.viewPager);
		fragments = new ArrayList<Fragment>();
		fragments.add(SellerAccountVipFragment.getInstance(null));
		fragments.add(AccountSafeFragment.getInstance(null));
		viewPager.setAdapter(new MyPagerAdapter(getFragmentManager(),fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}
		public void onClick(View v) {

			switch (index) {
			case 0:
				tv_vip.setTextColor(selectedColor);
				tv_account.setTextColor(unSelectedColor);
				break;
			case 1:
				tv_vip.setTextColor(unSelectedColor);
				tv_account.setTextColor(selectedColor);
				break;
			}
			viewPager.setCurrentItem(index);
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;
		int two = one * 2;

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);
			currIndex = index;
			animation.setFillAfter(true);
			animation.setDuration(240);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				tv_vip.setTextColor(selectedColor);
				tv_account.setTextColor(unSelectedColor);

				break;
			case 1:
				tv_vip.setTextColor(unSelectedColor);
				tv_account.setTextColor(selectedColor);

				break;

			}
		}
	}
	class MyPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}
		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}
	public static Fragment getInstance(String params) {
		MyAccountSellerFeagment fragment = new MyAccountSellerFeagment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}


}
