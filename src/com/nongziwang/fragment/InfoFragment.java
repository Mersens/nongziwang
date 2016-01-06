package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;
import com.nongziwang.main.R;
import android.annotation.SuppressLint;
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
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


@SuppressLint("InflateParams")
public class InfoFragment extends BaseFragment implements OnPageChangeListener {
	private View view;
	private ViewPager viewPager;
	private RadioGroup rgChannel = null;
	private HorizontalScrollView hvChannel;
	private PageFragmentAdapter adapter = null;
	private List<String> channelList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_info, container, false);
		initViews();
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
		setOnlyTileViewMethod(view,"行业资讯");
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
		rgChannel = (RadioGroup) view.findViewById(R.id.rgChannel);
		viewPager = (ViewPager) view.findViewById(R.id.vpNewsList);
		hvChannel = (HorizontalScrollView) view.findViewById(R.id.hvChannel);
		rgChannel
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						viewPager.setCurrentItem(checkedId);
					}
				});
		viewPager.setOnPageChangeListener(this);
		initTab();// 动态产生RadioButton
		initViewPager();
		rgChannel.check(0);

	}

	private void initTab() {
		channelList = new ArrayList<String>();
		channelList.add("热门专题");
		channelList.add("行业新闻");
		channelList.add("产品价格");
		channelList.add("农资知识");
		channelList.add("农商学院");
		channelList.add("政策法规");
		for (int i = 0; i < channelList.size(); i++) {
			RadioButton rb = (RadioButton) LayoutInflater.from(getActivity())
					.inflate(R.layout.tab_rb, null);
			rb.setId(i);
			rb.setText(channelList.get(i));
			rb.setTextColor(getResources().getColor(
					R.color.base_color_text_black));
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			rgChannel.addView(rb, params);
		}
	}

	private void initViewPager() {
		adapter = new PageFragmentAdapter(getFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
		setTab(0);
	}

	private void initEvent() {

	}

	class PageFragmentAdapter extends FragmentPagerAdapter {
		public PageFragmentAdapter(FragmentManager fm) {
			super(fm);

		}

		@Override
		public Fragment getItem(int idx) {
			return IndustryInfoFragment.getInstance(null);
		}

		@Override
		public int getCount() {
			return channelList.size();
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE; // 没有找到child要求重新加载
		}
	}

	private void setTab(int idx) {
		resetColor();
		RadioButton rb = (RadioButton) rgChannel.getChildAt(idx);
		rb.setChecked(true);
		rb.setTextColor(getResources()
				.getColor(R.color.title_yellow_text_color));
		int left = rb.getLeft();
		int width = rb.getMeasuredWidth();
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		int screenWidth = metrics.widthPixels;
		int len = left + width / 2 - screenWidth / 2;
		hvChannel.smoothScrollTo(len, 0);// 滑动ScroollView
	}

	public void resetColor() {
		for (int i = 0; i < rgChannel.getChildCount(); i++) {
			RadioButton rb = (RadioButton) rgChannel.getChildAt(i);
			rb.setTextColor(getResources().getColor(
					R.color.base_color_text_black));
		}
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setTab(arg0);
	}
}
