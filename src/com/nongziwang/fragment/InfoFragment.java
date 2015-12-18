package com.nongziwang.fragment;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class InfoFragment extends BaseFragment{
	private View view;
	private ViewPager viewPager;// ҳ������
	private ImageView imageView;// ����ͼƬ
	private TextView tv_rmzt, tv_hyxw, tv_cpjg ,tv_nzzs;// ѡ������
	private int offset = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int bmpW;// ����ͼƬ���
	private int selectedColor, unSelectedColor;
	private static final int pageSize = 4;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_info, container,false);
		initViews();
		InitImageView();
		initEvent();
		return view;
	}
	public static Fragment getInstance(String params) {
		InfoFragment fragment = new InfoFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void initViews() {
		selectedColor = getResources()
				.getColor(R.color.title_yellow_text_color);
		unSelectedColor = getResources().getColor(
				R.color.base_color_text_black);
		setOnlyTileViewMethod(view,"��ҵ��Ѷ");
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
		viewPager=(ViewPager) view.findViewById(R.id.viewPager);
		imageView=(ImageView) view.findViewById(R.id.imageView);
		tv_rmzt=(TextView) view.findViewById(R.id.tab_1);
		tv_hyxw=(TextView) view.findViewById(R.id.tab_2);
		tv_cpjg =(TextView) view.findViewById(R.id.tab_3);
		tv_nzzs=(TextView) view.findViewById(R.id.tab_4);
		tv_rmzt.setTextColor(selectedColor);
		tv_hyxw.setTextColor(unSelectedColor);
		tv_cpjg.setTextColor(unSelectedColor);
		tv_nzzs.setTextColor(unSelectedColor);
		
		tv_rmzt.setText(R.string.rmzt);
		tv_hyxw.setText(R.string.hyxw);
		tv_cpjg.setText(R.string.cpjg);
		tv_nzzs.setText(R.string.nzzs);
		
		tv_rmzt.setOnClickListener(new MyOnClickListener(0));
		tv_hyxw.setOnClickListener(new MyOnClickListener(1));
		tv_cpjg.setOnClickListener(new MyOnClickListener(2));
		tv_nzzs.setOnClickListener(new MyOnClickListener(3));
		

	}
	private void InitImageView() {
		imageView = (ImageView) view.findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / pageSize - bmpW) / 2;// ����ƫ����--(��Ļ���/ҳ������-ͼƬʵ�ʿ��)/2
													// = ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// ���ö�����ʼλ��
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		viewPager.setAdapter(new myPagerAdapter(getFragmentManager()));
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
				tv_rmzt.setTextColor(selectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 1:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(selectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 2:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(selectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 3:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(selectedColor);
				break;
			}

			viewPager.setCurrentItem(index);
		}

	}

	/**
	 * Ϊѡ��󶨼�����
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
		int two = one * 2;// ҳ��1 -> ҳ��3 ƫ����

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// ��Ȼ����Ƚϼ�ֻ࣬��һ�д��롣
			currIndex = index;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(240);
			imageView.startAnimation(animation);

			switch (index) {
			case 0:
				tv_rmzt.setTextColor(selectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 1:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(selectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 2:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(selectedColor);
				tv_nzzs.setTextColor(unSelectedColor);
				break;
			case 3:
				tv_rmzt.setTextColor(unSelectedColor);
				tv_hyxw.setTextColor(unSelectedColor);
				tv_cpjg.setTextColor(unSelectedColor);
				tv_nzzs.setTextColor(selectedColor);
				break;
			}
		}
	}

	class myPagerAdapter extends FragmentPagerAdapter {
		public myPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return IndustryInfoFragment.getInstance("");
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
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
