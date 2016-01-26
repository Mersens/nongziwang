package com.nongziwang.activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongziwang.fragment.ProductManagementFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class ProductManagementFragmentActivity extends BaseActivity{
	private ViewPager viewPager;
	private ImageView imageView;
	private TextView tv_yfb, tv_shz,tv_wtg,tv_yxj;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private int selectedColor, unSelectedColor;
	private static final int pageSize = 4;
	private static String str[]={"已发布","审核中","审核未通过","已下架"};
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_pro_management);
		initViews();
		initImageView();
		initEvent();
	}

	private void initViews() {
		
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "产品管理", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
				
			}
		});
		
		selectedColor = getResources()
				.getColor(R.color.title_yellow_text_color);
		unSelectedColor = getResources().getColor(
				R.color.base_color_text_black);
		viewPager = (ViewPager)findViewById(R.id.viewPager);
		imageView = (ImageView)findViewById(R.id.cursor);
		tv_yfb=(TextView) findViewById(R.id.tab_1);
		tv_yfb.setText(str[0]);
		tv_shz=(TextView) findViewById(R.id.tab_2);
		tv_shz.setText(str[1]);
		tv_wtg=(TextView) findViewById(R.id.tab_3);
		tv_wtg.setText(str[2]);
		tv_yxj=(TextView) findViewById(R.id.tab_4);
		tv_yxj.setText(str[3]);
		setTabs(0);
	}
	private void initEvent() {
		tv_yfb.setOnClickListener(new MyOnClickListener(0));
		tv_shz.setOnClickListener(new MyOnClickListener(1));
		tv_wtg.setOnClickListener(new MyOnClickListener(2));
		tv_yxj.setOnClickListener(new MyOnClickListener(3));
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}
	private void initImageView() {
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = (screenW / pageSize - bmpW) / 2;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);
	}

	@SuppressWarnings("unused")
	private class MyOnClickListener implements OnClickListener {
		private int index = 0;
		
		public MyOnClickListener(int i) {
			index = i;
		}
		public void onClick(View v) {
			setTabs(index);
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
			setTabs(index);
		}
	}
	class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {

			return ProductManagementFragment.getInstance(arg0+"");
		}
		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return pageSize;
		}
	}
	
	public void setTabs(int i){
		resetColor();
		switch (i) {
		case 0:
			tv_yfb.setTextColor(selectedColor);
			break;
		case 1:
			tv_shz.setTextColor(selectedColor);
			break;
		case 2:
			tv_wtg.setTextColor(selectedColor);
			break;
		case 3:
			tv_yxj.setTextColor(selectedColor);
			break;
		}
	}
	
	private void resetColor(){
		tv_yfb.setTextColor(unSelectedColor);
		tv_shz.setTextColor(unSelectedColor);
		tv_wtg.setTextColor(unSelectedColor);
		tv_yxj.setTextColor(unSelectedColor);
	}
}
