package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.activity.SearchFragmentActivity;
import com.nongziwang.adapter.ViewPagerAdapter;
import com.nongziwang.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Fragment1 extends BaseFragment {
	private View view;
	private View bannersView;
	private ListView listView;
	private LayoutInflater mInflater;
	private ViewPager viewpager;
	private int[] pics = { R.drawable.banner, R.drawable.banner, R.drawable.banner};
			
	private Runnable viewpagerRunnable;
	private static Handler handler;
	private RadioGroup dotLayout;
	private LinearLayout layout_search;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_fragment1, container,
				false);
		handler = new Handler();
		initViews();
		setBanners();
		initDatas();
		return view;
	}

	private void initViews() {
		mInflater=LayoutInflater.from(getActivity());
		bannersView= mInflater.inflate(R.layout.layout_banners, null);
		viewpager=(ViewPager) bannersView.findViewById(R.id.viewpager);
		dotLayout = (RadioGroup)bannersView.findViewById(R.id.advertise_point_group);
		listView=(ListView) view.findViewById(R.id.listView);
		layout_search=(LinearLayout) view.findViewById(R.id.layout_search);
		listView.addHeaderView(bannersView);
	}

	private void initDatas() {
		layout_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity(),SearchFragmentActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.bottom_open,0);
				
				
			}
		});
		listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				
				return mInflater.inflate(R.layout.listview_type_item, parent,false);
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 5;
			}
		});
	}
	public void setBanners(){
		initListener();
		int len = pics.length;
		View view = null;
		ImageView imageview;
		LayoutInflater mInflater = LayoutInflater.from(getActivity());
		List<View> lists = new ArrayList<View>();
		for (int i = 0; i < len; i++)
		{
			view = mInflater.inflate(R.layout.imageview_layout, null);
			imageview = (ImageView) view.findViewById(R.id.viewpager_imageview);
			imageview.setBackgroundResource(pics[i]);
			lists.add(view);
		}
		viewpager.setAdapter(new ViewPagerAdapter(getActivity(),lists));
		initRunnable();
	}
	
	@SuppressWarnings("deprecation")
	private void initListener()
	{
		viewpager.setOnPageChangeListener(new OnPageChangeListener()
		{
			boolean isScrolled = false;

			@Override
			public void onPageScrollStateChanged(int status)
			{
				switch (status)
				{
				case 1:
					isScrolled = false;
					break;
				case 2:
					isScrolled = true;
					break;
				case 0:

					if (viewpager.getCurrentItem() == viewpager.getAdapter()
							.getCount() - 1 && !isScrolled)
					{
						viewpager.setCurrentItem(0);
					}
					else if (viewpager.getCurrentItem() == 0 && !isScrolled)
					{
						viewpager.setCurrentItem(viewpager.getAdapter()
								.getCount() - 1);
					}
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2)
			{
				((RadioButton) dotLayout.getChildAt(position)).setChecked(true);
			}

			@Override
			public void onPageSelected(int index)
			{
				
			}
		});
	}
	
	private static final int TIME = 3000;

	protected void initRunnable()
	{
		viewpagerRunnable = new Runnable()
		{

			@Override
			public void run()
			{
				int nowIndex = viewpager.getCurrentItem();
				int count = viewpager.getAdapter().getCount();
				if (nowIndex + 1 >= count)
				{
					viewpager.setCurrentItem(0);
				} else
				{
					viewpager.setCurrentItem(nowIndex + 1);
				}
				handler.postDelayed(viewpagerRunnable, TIME);
			}
		};
		handler.postDelayed(viewpagerRunnable, TIME);
		
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		handler.removeCallbacks(viewpagerRunnable);
	}
	@Override
	protected void lazyLoad() {

	}

}
