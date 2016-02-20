package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.SearchFragmentActivity;
import com.nongziwang.activity.TypeSearchFragmentActivity;
import com.nongziwang.adapter.MainListViewAdapter;
import com.nongziwang.adapter.ViewPagerAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.IndexBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.DialogWaiting;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeFragment extends BaseFragment implements OnClickListener {
	private View view;
	private View bannersView;
	private ListView listView;
	private LayoutInflater mInflater;
	private ViewPager viewpager;
	private Runnable viewpagerRunnable;
	private static Handler handler;
	private RadioGroup dotLayout;
	private LinearLayout layout_search;
	private LinearLayout layout_huafei;
	private LinearLayout layout_zhongzi;
	private LinearLayout layout_nongyao;
	private LinearLayout layout_nongji;
	private LinearLayout layout_nongmo;
	private Map<String,List<IndexBean>> mapdatas;
	private MainListViewAdapter adapter;
	private DialogWaiting dialog;
	private static final String BANNER_URL = AppConstants.SERVICE_ADDRESS
			+ "index/getIndexImg";
	private static final String TAG="HomeFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_home, container, false);
		handler = new Handler();
		initViews();
		initDatas();
		return view;
	}

	private void initViews() {
		mInflater = LayoutInflater.from(getActivity());
		bannersView = mInflater.inflate(R.layout.layout_banners, null);
		viewpager = (ViewPager) bannersView.findViewById(R.id.viewpager);
		dotLayout = (RadioGroup) bannersView
				.findViewById(R.id.advertise_point_group);
		listView = (ListView) view.findViewById(R.id.listView);
		layout_huafei = (LinearLayout) bannersView
				.findViewById(R.id.layout_huafei);
		layout_zhongzi = (LinearLayout) bannersView
				.findViewById(R.id.layout_zhongzi);
		layout_nongyao = (LinearLayout) bannersView
				.findViewById(R.id.layout_nongyao);
		layout_nongji = (LinearLayout) bannersView
				.findViewById(R.id.layout_nongji);
		layout_nongmo = (LinearLayout) bannersView
				.findViewById(R.id.layout_nongmo);
		layout_search = (LinearLayout) view.findViewById(R.id.layout_search);
		layout_huafei.setOnClickListener(this);
		layout_zhongzi.setOnClickListener(this);
		layout_nongyao.setOnClickListener(this);
		layout_nongji.setOnClickListener(this);
		layout_nongmo.setOnClickListener(this);
		listView.addHeaderView(bannersView);
		layout_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SearchFragmentActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.bottom_open, 0);
			}
		});
	}

	@SuppressLint("ViewHolder")
	private void initDatas() {
		
		HttpUtils.doPost(BANNER_URL, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				dialog=new DialogWaiting(getActivity());
				dialog.show();
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				try {
					mapdatas=JsonUtils.getIndexImg(arg2);
					List<List<IndexBean>> list=new ArrayList<List<IndexBean>>();
					list.add(mapdatas.get("huafei"));
					list.add(mapdatas.get("nongyao"));
					list.add(mapdatas.get("zhongzi"));
					setBanners();
					adapter=new MainListViewAdapter(list, getActivity());
					listView.setAdapter(adapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ToastUtils.showMessage(getActivity(), " ˝æ›º”‘ÿ ß∞‹£°");
				Log.e(TAG, arg2==null?"":arg2);
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				if(dialog!=null && dialog.isShowing()){
					dialog.dismiss();
				}
			}
		});
	}

	public void setBanners() {
		initListener();
		List<IndexBean> list=mapdatas.get("banner");
		int len = list.size();
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		List<View> lists = new ArrayList<View>();
		for (int i = 0; i < len; i++) {
			view = mInflater.inflate(R.layout.imageview_layout, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview);
			ImageLoader.getInstance().displayImage(list.get(i).getImgsrc(), imageview,
					ImageLoadOptions.getOptions());
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getActivity(), lists));
		initRunnable();
	}

	@SuppressWarnings("deprecation")
	private void initListener() {
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {
			boolean isScrolled = false;

			@Override
			public void onPageScrollStateChanged(int status) {
				switch (status) {
				case 1:
					isScrolled = false;
					break;
				case 2:
					isScrolled = true;
					break;
				case 0:
					if (viewpager.getCurrentItem() == viewpager.getAdapter()
							.getCount() - 1 && !isScrolled) {
						viewpager.setCurrentItem(0);
					} else if (viewpager.getCurrentItem() == 0 && !isScrolled) {
						viewpager.setCurrentItem(viewpager.getAdapter()
								.getCount() - 1);
					}
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
			}

			@Override
			public void onPageSelected(int index) {

			}
		});
	}

	private static final int TIME = 3000;

	protected void initRunnable() {
		viewpagerRunnable = new Runnable() {
			@Override
			public void run() {
				int nowIndex = viewpager.getCurrentItem();
				int count = viewpager.getAdapter().getCount();
				if (nowIndex + 1 >= count) {
					viewpager.setCurrentItem(0);
				} else {
					viewpager.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, TIME);
			}
		};
		handler.postDelayed(viewpagerRunnable, TIME);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		handler.removeCallbacks(viewpagerRunnable);
	}

	@Override
	protected void lazyLoad() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_huafei:
			intentAction(getActivity(), TypeSearchFragmentActivity.class,
					CommonSearchFragment.HUAFEI); 
			break;
		case R.id.layout_zhongzi:
			intentAction(getActivity(), TypeSearchFragmentActivity.class,
					CommonSearchFragment.ZHONGZI);
			break;
		case R.id.layout_nongyao:
			intentAction(getActivity(), TypeSearchFragmentActivity.class,
					CommonSearchFragment.NONGYAO);
			break;
		case R.id.layout_nongji:
			intentAction(getActivity(), TypeSearchFragmentActivity.class,
					CommonSearchFragment.NONGJI);
			break;
		case R.id.layout_nongmo:
			intentAction(getActivity(), TypeSearchFragmentActivity.class,
					CommonSearchFragment.NONGMO);
			break;
		}
	}
}
