package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.main.R;
import com.nongziwang.utils.NetUtils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.FrameLayout;
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
	public static final String ACTION_INFOFRAGMENT = "infofragment";
	private FrameLayout fragment_info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_info, container, false);
		fragment_info=(FrameLayout) view.findViewById(R.id.fragment_info);
		initHeadView();
		registerBoradcastReceiver();
		if (NetUtils.isNetworkConnected(getActivity())) {
			initViews();
			initEvent();
			fragment_info.setVisibility(View.GONE);
		} else {
			addNoNetWorkFragment();
		}
		return view;
	}

	public static Fragment getInstance(String params) {
		InfoFragment fragment = new InfoFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	private void initHeadView(){
		setOnlyTileViewMethod(view,"行业资讯");
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
	}
	private void initViews() {
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
			return IndustryInfoFragment.getInstance(idx+"");
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

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setTab(arg0);
	}
	public void addNoNetWorkFragment() {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_info);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment);
		}
		fragment = creatFragment();
		fm.beginTransaction().replace(R.id.fragment_info, fragment).commit();
	}

	public Fragment creatFragment() {
		return NetWorkFragment.getInstance(ACTION_INFOFRAGMENT);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_INFOFRAGMENT)) {
				if (NetUtils.isNetworkConnected(getActivity())) {
					FragmentManager fm = getFragmentManager();
					Fragment fragment = fm.findFragmentById(R.id.fragment_info);
					if (fragment != null) {
						fm.beginTransaction().remove(fragment).commit();
					}
					initViews();
					initEvent();
					fragment_info.setVisibility(View.GONE);
				}
			}
		}
	};

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_INFOFRAGMENT);
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
}
